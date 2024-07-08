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
    private Long studentId;
    private Long kakaoId;
    private String profileImage;

    public static UserEntity of(Long id, String name, Long studentId, Long kakaoId, String profileImage) {
        return new UserEntity(id, name, studentId, kakaoId, profileImage);
    }
    public static UserEntity from(Long id){
        return new UserEntity(id, null,null, null, null);
    }
}
