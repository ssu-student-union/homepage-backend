package ussum.homepage.infra.jpa.user.entity;
import jakarta.persistence.*;
import lombok.*;
import ussum.homepage.infra.jpa.BaseEntity;

@Entity
@Table(name = "`groups`")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MajorCode majorCode;
    private String name;

}
