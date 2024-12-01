package ussum.homepage.infra.jpa.group.entity;
import jakarta.persistence.*;
import lombok.*;
import ussum.homepage.infra.jpa.BaseEntity;

@Entity
@Table(name = "`groups`")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GroupEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GroupCode groupCode;

    private String name;

    public static GroupEntity of(Long id, GroupCode groupCode, String name) {
        return new GroupEntity(id, groupCode, name);
    }

    public static GroupEntity from(Long id) {
        return new GroupEntity(id, null, null);
    }
}
