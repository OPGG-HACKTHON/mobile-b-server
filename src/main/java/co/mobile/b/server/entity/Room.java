package co.mobile.b.server.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
/* equals, hashCode 자동 생성 */
@EqualsAndHashCode(of = "seq", callSuper = false)
@Entity
@Table(name = "room")
public class Room extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq", updatable = false, unique = false)
    private Long seq;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "invite_code")
    private String inviteCode;

    @Column(name = "end_yn")
    private boolean endYn;
}
