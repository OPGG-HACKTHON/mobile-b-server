package co.mobile.b.server.entity;

import co.mobile.b.server.dto.request.AddRoomParam;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
/* equals, hashCode 자동 생성 */
@EqualsAndHashCode(of = "roomSeq", callSuper = false)
@Entity
@Table(name = "room")
public class Room extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_seq", updatable = false, unique = false)
    private Long roomSeq;                                                       // 기본키

    // TODO : 유저 사용하지 않고 디바이스 고유 키를 사용할 건지?
//    @JsonIgnore
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "seq",
//            referencedColumnName = "seq",
//            nullable = false,
//            foreignKey = @ForeignKey(name = "FK_ROOM_USER_SEQ"))
//    private User user;                                                      // 방장
    @Column(name = "user_key")
    private String userKey;                                                 // 방장

    @Column(name = "invite_code")
    private String inviteCode;                                              // 초대 코드

    public Room(AddRoomParam addRoomParam, String domain) {
        this.userKey = addRoomParam.getUserKey();
        // RandomStringUtils.random(7,true,true)
        this.inviteCode = domain + UUID.randomUUID();
    }
}
