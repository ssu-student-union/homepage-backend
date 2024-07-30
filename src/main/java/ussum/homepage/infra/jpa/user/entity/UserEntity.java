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
    private String studentId;
    private String kakaoId;
    private String profileImage;
    private String refreshToken;

    public static UserEntity of(Long id, String name, String studentId, String kakaoId, String profileImage,String refreshToken) {
        return new UserEntity(id, name, studentId, kakaoId, profileImage, refreshToken);
    }
    public static UserEntity from(Long id){
        return new UserEntity(id, null,null, null, null,null);
    }
}
