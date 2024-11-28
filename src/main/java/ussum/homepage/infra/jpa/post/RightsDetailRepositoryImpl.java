package ussum.homepage.infra.jpa.post;

import static ussum.homepage.global.error.status.ErrorStatus.RIGHTS_DETAIL_ID_NOT_FOUND;
import static ussum.homepage.infra.jpa.post.entity.QRightsDetailEntity.rightsDetailEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.application.post.service.dto.request.RightsDetailRequest;
import ussum.homepage.domain.post.RightsDetail;
import ussum.homepage.domain.post.RightsDetailRepository;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.infra.jpa.post.entity.RightsDetailEntity;
import ussum.homepage.infra.jpa.post.entity.RightsDetailEntity.PersonType;
import ussum.homepage.infra.jpa.post.entity.Status;
import ussum.homepage.infra.jpa.post.repository.RightsDetailJpaRepository;

@Repository
@RequiredArgsConstructor
public class RightsDetailRepositoryImpl implements RightsDetailRepository {

    private final RightsDetailJpaRepository rightsDetailJpaRepository;
    private final RightsDetailMapper rightsDetailMapper;
    private final JPAQueryFactory queryFactory;

    @Override
    public void saveAll(List<RightsDetail> rightsDetails) {
        rightsDetailJpaRepository.saveAll(rightsDetails.stream()
                                .map(rightsDetailMapper::toEntity)
                                .toList());
    }

    @Override
    public List<RightsDetail> findAllByPostId(Long postId) {
        return rightsDetailJpaRepository.findByPostEntityId(postId).stream()
                .map(rightsDetailMapper::toDomain)
                .toList();

    }

    @Override
    public void deleteAll(Long postId) {
        rightsDetailJpaRepository.deleteByPostEntityId(postId);
    }

    @Override
    public Optional<RightsDetail> findById(Long rightsDetailId) {
        return rightsDetailJpaRepository.findById(rightsDetailId)
                .map(rightsDetailMapper::toDomain);
    }

    @Override
    public Long updateRightsDetail(Long rightsDetailId, RightsDetailRequest rightsDetailRequest) {
        PersonType personType = PersonType.getEnumPersonTypeFromStringType(rightsDetailRequest.personType());
        queryFactory
                .update(rightsDetailEntity)
                .set(rightsDetailEntity.name, rightsDetailRequest.name())
                .set(rightsDetailEntity.major, rightsDetailRequest.major())
                .set(rightsDetailEntity.personType, personType)
                .set(rightsDetailEntity.studentId, rightsDetailRequest.studentId())
                .set(rightsDetailEntity.phoneNumber, rightsDetailRequest.phoneNumber())// 필드3 업데이트
                .where(rightsDetailEntity.id.eq(rightsDetailId))
                .execute();
        return rightsDetailId;
    }

    @Override
    @Transactional
    public void updateRightsDetailList(Long postId,List<RightsDetail> rightsDetailList) {
        queryFactory.delete(rightsDetailEntity)
                .where(rightsDetailEntity.postEntity.id.eq(postId))
                .execute();

        rightsDetailJpaRepository.saveAll(rightsDetailList.stream()
                .map(rightsDetailMapper::toEntity)
                .toList());
    }

    @Override
    public List<Long> findRightsDetailIdsByPostId(Long postId) {

        return queryFactory
                .select(rightsDetailEntity.id)
                .from(rightsDetailEntity)
                .where(rightsDetailEntity.postEntity.id.eq(postId))
                .fetch();
    }
}

