package ussum.homepage.infra.jpa.acl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import ussum.homepage.domain.acl.PostAcl;
import ussum.homepage.domain.acl.PostAclRepository;
import ussum.homepage.infra.jpa.acl.repository.PostAclJpaRepository;
import ussum.homepage.infra.jpa.post.entity.BoardCode;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostAclRepositoryImpl implements PostAclRepository {
    private final PostAclJpaRepository postAclJpaRepository;
    private final PostAclMapper postAclMapper;

    @Override
    public List<PostAcl> findAllByBoardCode(String boardCode) {
        return postAclJpaRepository.findAllByBoardCode(BoardCode.getEnumBoardCodeFromStringBoardCode(boardCode))
                .stream()
                .map(postAclEntity -> postAclMapper.toDomain(postAclEntity))
                .toList();
    }

    //    @Override
//    public List<PostAcl> findByPostId(Long postId) {
//        return postAclJpaRepository.findAllByPostId(postId)
//                .stream()
//                .map(aclMapper::toDomain)
//                .toList();
//    }

//    @Override
//    public void save(PostAcl postAcl) {
//        postAclJpaRepository.save(aclMapper.toEntity(postAcl));
//    }
//
//    @Override
//    public PostAcl update(PostAcl postAcl) {
//        return aclMapper.toDomain(postAclJpaRepository.save(aclMapper.toEntity(postAcl)));
//    }
//
//    @Override
//    public Optional<PostAcl> findById(Long postAclId) {
//        return postAclJpaRepository.findById(postAclId).map(aclMapper::toDomain);
//    }
//
//    @Override
//    public void delete(PostAcl postAcl) {
//        postAclJpaRepository.delete(aclMapper.toEntity(postAcl));
//    }
}
