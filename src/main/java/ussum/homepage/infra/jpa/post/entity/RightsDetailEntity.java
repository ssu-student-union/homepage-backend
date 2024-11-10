package ussum.homepage.infra.jpa.post.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "rights_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RightsDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String studentId;

    private String major;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PersonType personType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;


    @RequiredArgsConstructor
    @Getter
    public enum PersonType {

        REPORTER("신고자"),

        VICTIM("피침해자"),

        ATTACKER("침해자");

        private final String type;

        public static PersonType getEnumPersonTypeFromStringType(String type) {
            for (PersonType person : values()) {
                if (person.getType().equals(type)) {
                    return person;
                }
            }
            throw new IllegalArgumentException("Unknown rights person type: " + type);
        }
    }
    public static RightsDetailEntity of(Long id, String name, String studentId, String major, PersonType personType,PostEntity postEntity) {
        return new RightsDetailEntity(id, name, studentId, major, personType, postEntity);
    }
}
