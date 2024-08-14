package ussum.homepage.infra.jpa.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.post.PostFile;
import ussum.homepage.domain.post.PostFileRepository;
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
    public Optional<PostFile> findById(Long id) {
        return postFileJpaRepository.findById(id).map(postFileMapper::toDomain);
    }

    @Override
    public List<PostFile> saveAll(List<PostFile> fileList) {
        return postFileMapper.toDomain(
                postFileJpaRepository.saveAll(
                        fileList.stream()
                        .map(file -> postFileMapper.toEntity(file))
                        .toList())
        );
    }

    @Override
    public void save(PostFile postFile) {
        postFileJpaRepository.save(postFileMapper.toEntity(postFile));
    }
}
