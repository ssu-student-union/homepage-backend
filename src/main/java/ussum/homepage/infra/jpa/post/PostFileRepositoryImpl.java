package ussum.homepage.infra.jpa.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.post.PostFile;
import ussum.homepage.domain.post.PostFileRepository;
import ussum.homepage.infra.jpa.post.entity.FileCategory;
import ussum.homepage.infra.jpa.post.repository.PostFileJpaRepository;

import java.util.List;
import java.util.Optional;

import static ussum.homepage.infra.jpa.post.entity.QPostFileEntity.postFileEntity;

@Repository
@RequiredArgsConstructor
public class PostFileRepositoryImpl implements PostFileRepository {
    private final PostFileJpaRepository postFileJpaRepository;
    private final PostFileMapper postFileMapper;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<PostFile> findAllByPostId(Long postId) {
        return queryFactory
                .selectFrom(postFileEntity)
                .where(postFileEntity.postEntity.id.eq(postId))
                .fetch()
                .stream()
                .map(postFileMapper::toDomain).toList();
    }

    @Override
    public Long deleteAllByUrl(List<String> urlList) {
        return queryFactory
                .delete(postFileEntity)
                .where(postFileEntity.url.in(urlList))
                .execute();
    }

    @Override
    public Optional<PostFile> findById(Long id) {
        return postFileJpaRepository.findById(id).map(postFileMapper::toDomain);
    }

    @Override
    public List<PostFile> saveAll(List<PostFile> fileList) {
        return postFileMapper.toDomain(
                postFileJpaRepository.saveAll(
                        fileList.stream()
                        .map(postFileMapper::toEntity)
                        .toList())
        );
    }

    @Override
    public void updatePostIdForIds(List<Long> postFileIds, Long postId, FileCategory fileCategory) {
        queryFactory
                .update(postFileEntity)
                .set(postFileEntity.postEntity.id, postId)
                .set(postFileEntity.fileCategory, fileCategory)
                .where(postFileEntity.id.in(postFileIds))
                .execute();
    }

    @Override
    public void updatePostIdAndFileCategoryForIds(List<Long> postFileIds, Long postId, FileCategory fileCategory) {

        queryFactory
                .update(postFileEntity)
                .set(postFileEntity.postEntity.id, postId)
                .set(postFileEntity.fileCategory, fileCategory)
                .where(postFileEntity.id.in(postFileIds))
                .execute();
    }
}
