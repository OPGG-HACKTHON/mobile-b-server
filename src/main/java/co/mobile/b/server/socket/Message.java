package co.mobile.b.server.socket;

import co.mobile.b.server.entity.BaseEntity;
import co.mobile.b.server.entity.Room;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of = "message_seq")
@DynamicUpdate
@Entity
@Table(name = "Message")
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_seq", updatable = false, unique = false)
    private Long messageSeq;                                                   // 기본키

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_seq",
            referencedColumnName = "room_seq",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_CHAT_ROOM_SEQ"))
    private Room room;                                                      // 방

    @Column(name = "uer_key")
    private String userKey;                                                 // 유저 키
    
    @Column(name = "position_type")
    private Integer positionType;                                          // 포지션 타입

    @Column(name = "content")
    private String content;                                                 // 메세지

//    public static Room createRoomChat(Room room) {
//        this.room = room;
//        return null;
//    }
}
