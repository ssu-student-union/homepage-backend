package ussum.homepage.infra.jpa.acl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.acl.BoardAcl;
import ussum.homepage.domain.acl.BoardAclRepository;
import ussum.homepage.infra.jpa.acl.entity.BoardAclEntity;
import ussum.homepage.infra.jpa.acl.repository.BoardAclJpaRepository;

import java.util.List;
import java.util.Optional;

import static ussum.homepage.infra.jpa.acl.entity.QBoardAclEntity.boardAclEntity;

@Repository
@RequiredArgsConstructor
public class BoardAclRepositoryImpl implements BoardAclRepository {
    private final BoardAclJpaRepository boardAclJpaRepository;
    private final AclMapper aclMapper;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<BoardAcl> findByBoardId(Long boardId){
        return boardAclJpaRepository.findAllByBoardId(boardId)
                .stream()
                .map(aclMapper::toDomain)
                .toList();
    }
    @Override
    public BoardAcl save(BoardAcl boardAcl){
        return aclMapper.toDomain(boardAclJpaRepository.save(aclMapper.toEntity(boardAcl)));
    }
    @Override
    public BoardAcl update(BoardAcl boardAcl){
        return aclMapper.toDomain(boardAclJpaRepository.save(aclMapper.toEntity(boardAcl)));
    }
    @Override
    public Optional<BoardAcl> findById(Long boardAclId){
        return boardAclJpaRepository.findById(boardAclId).map(aclMapper::toDomain);
    }
    @Override
    public void delete(BoardAcl boardAcl){
        BoardAclEntity boardAclEntity = aclMapper.toEntity(boardAcl);
        boardAclJpaRepository.delete(boardAclEntity);
    }
    @Override
    public List<BoardAcl> findByBoardIdDsl(Long boardId) {
        return queryFactory
                .select(boardAclEntity)
                .from(boardAclEntity)
                .where(boardAclEntity.boardEntity.id.eq(boardId))
                .fetch()
                .stream()
                .map(aclMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<BoardAcl> findByIdDsl(Long boardAclId) {
        BoardAclEntity entity = queryFactory
                .selectFrom(boardAclEntity)
                .where(boardAclEntity.id.eq(boardAclId))
                .fetchOne();
        return Optional.ofNullable(entity).map(aclMapper::toDomain);
    }
}
