package ussum.homepage.infra.jpa.post.entity;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_PERSON_TYPE;
import static ussum.homepage.global.error.status.ErrorStatus.PERSON_TYPE_NULL;

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
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import ussum.homepage.global.error.exception.InvalidValueException;

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

    private String phoneNumber;

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
            if (type == null) {
                throw new InvalidValueException(PERSON_TYPE_NULL);
            }
            return Arrays.stream(values())
                    .filter(personType -> personType.getType().equals(type))
                    .findFirst()
                    .orElseThrow(() -> new InvalidValueException(INVALID_PERSON_TYPE));
        }
    }
    public static RightsDetailEntity of(Long id, String name, String phoneNumber, String studentId, String major, PersonType personType,PostEntity postEntity) {
        return new RightsDetailEntity(id, name, phoneNumber,studentId, major, personType, postEntity);
    }
}
