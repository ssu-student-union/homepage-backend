package ussum.homepage.infra.jpa.sheet_email.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "college_department_email")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class CollegeDepartmentEmailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    public static CollegeDepartmentEmailEntity of(String name, String email) {
        return CollegeDepartmentEmailEntity.builder()
                .name(name)
                .email(email)
                .build();
    }

}
