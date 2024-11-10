package ussum.homepage.infra.jpa.post;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.post.RightsDetail;
import ussum.homepage.infra.jpa.post.entity.RightsDetailEntity;
import ussum.homepage.infra.jpa.post.entity.RightsDetailEntity.PersonType;

@Component
public class RightsDetailMapper {
    public static RightsDetail toDomain(RightsDetailEntity rightsDetailEntity){
        return RightsDetail.of(rightsDetailEntity.getId(), rightsDetailEntity.getName(), rightsDetailEntity.getStudentId(),rightsDetailEntity.getMajor(),rightsDetailEntity.getPersonType().toString());
    }

    public static RightsDetailEntity toEntity(RightsDetail rightsDetail){
        PersonType personType = RightsDetailEntity.PersonType.getEnumPersonTypeFromStringType(rightsDetail.getPersonType());
        return RightsDetailEntity.of(rightsDetail.getId(), rightsDetail.getName(), rightsDetail.getStudentId(),rightsDetail.getMajor(),null,null);
    }

}
