package co.mobile.b.server.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
/* equals, hashCode 자동 생성 */
@EqualsAndHashCode(of = "userSeq", callSuper = false)
@Entity
@Table(name = "user",
        /* 인덱스 설정 */
        indexes = {
            @Index(name = "user_email_idx", unique = true, columnList = "email")
        }
)
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_seq", updatable = false, unique = false)
    private Long userSeq;                   // 기본 키

    @Column(name = "email")
    private String email;               // 이메일

    @Column(name = "password")
    private String password;            // 비밀번호

    @Column(name = "nickname")
    private String nickname;            // 닉네임
}
