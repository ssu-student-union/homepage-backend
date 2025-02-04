package ussum.homepage.infra.jpa.user.entity;
import jakarta.persistence.*;
import lombok.*;
import ussum.homepage.domain.user.User;
import ussum.homepage.infra.jpa.BaseEntity;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nickname;
    private String studentId;
    private String kakaoId;
    private String profileImage;
    private String accountId;
    private String password;
    private String refreshToken;

    public static UserEntity of(Long id, String name, String nickname, String studentId, String kakaoId, String profileImage,
                                String accountId, String password, String refreshToken) {
        return new UserEntity(id, name, nickname, studentId, kakaoId, profileImage, accountId, password, refreshToken);
    }
    public static UserEntity from(Long id){
        return new UserEntity(id, null, null,null, null, null, null, null, null);
    }
}
