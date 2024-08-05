package ussum.homepage.infra.jpa.post;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import ussum.homepage.application.post.service.dto.response.SimplePostResponse;
import ussum.homepage.infra.jpa.post.dto.SimplePostDto;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostRepository;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.infra.jpa.post.entity.*;
import ussum.homepage.infra.jpa.post.repository.BoardJpaRepository;
import ussum.homepage.infra.jpa.post.repository.CategoryJpaRepository;
import ussum.homepage.infra.jpa.post.repository.PostJpaRepository;
import ussum.homepage.infra.jpa.user.entity.UserEntity;
import ussum.homepage.infra.jpa.user.repository.UserJpaRepository;

import java.util.List;
import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.*;

import static ussum.homepage.infra.jpa.post.entity.PostEntity.increaseViewCount;
import static ussum.homepage.infra.jpa.post.entity.QPostEntity.postEntity;
import static ussum.homepage.infra.jpa.postlike.entity.QPostReactionEntity.postReactionEntity;
import static ussum.homepage.infra.jpa.post.entity.QBoardEntity.boardEntity;

import static ussum.homepage.infra.jpa.post.entity.PostEntity.updateLastEditedAt;


@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    private final PostJpaRepository postJpaRepository;
    private final BoardJpaRepository boardJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PostMapper postMapper;
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Post> findById(Long postId) {
        return postJpaRepository.findById(postId).map(postMapper::toDomain);
    }

    @Override
    public Optional<Post> findByBoardIdAndId(Long boardId, Long postId) {
        BoardEntity boardEntity = boardJpaRepository.findById(boardId).orElseThrow(() -> new GeneralException(BOARD_NOT_FOUND));
        Optional<PostEntity> post = postJpaRepository.findByBoardEntityAndId(boardEntity, postId);
        increaseViewCount(post.get());
        return post.map(postMapper::toDomain);
    }

    @Override
    public Optional<Post> findByBoardIdAndIdForEditAndDelete(Long boardId, Long postId) {
        BoardEntity boardEntity = boardJpaRepository.findById(boardId).orElseThrow(() -> new GeneralException(BOARD_NOT_FOUND));
        Optional<PostEntity> post = postJpaRepository.findByBoardEntityAndId(boardEntity, postId);
        updateLastEditedAt(post.get());
        return post.map(postMapper::toDomain);
    }

    @Override
    public Page<Post> findAllWithBoard(Pageable pageable, String boardCode) {
        BoardEntity boardEntity = boardJpaRepository.findByBoardCode(BoardCode.getEnumBoardCodeFromStringBoardCode(boardCode))
                .orElseThrow(() -> new GeneralException(BOARD_NOT_FOUND));

        return postJpaRepository.findAllByBoard(pageable,boardEntity).map(postMapper::toDomain);
    }

    @Override
    public Page<Post> findAllByBoardId(Long boardId, Pageable pageable) {
        return postJpaRepository.findAllByBoardId(boardId, pageable).map(postMapper::toDomain);
    }

    @Override
    public Post save(Post post){
        UserEntity userEntity = userJpaRepository.findById(post.getUserId())
                .orElseThrow(() -> new GeneralException(USER_NOT_FOUND));

        BoardEntity boardEntity = boardJpaRepository.findById(post.getBoardId())
                .orElseThrow(() -> new GeneralException(BOARD_NOT_FOUND));

        CategoryEntity categoryEntity = categoryJpaRepository.findById(post.getCategoryId())
                .orElseThrow(() -> new GeneralException(CATEGORY_NOT_FOUND));

        return postMapper.toDomain(
                postJpaRepository.save(postMapper.toEntity(post, userEntity, boardEntity, categoryEntity))
        );
    }

    @Override
    public void delete(Post post) {
        UserEntity userEntity = userJpaRepository.findById(post.getUserId())
                .orElseThrow(() -> new GeneralException(USER_NOT_FOUND));

        BoardEntity boardEntity = boardJpaRepository.findById(post.getBoardId())
                .orElseThrow(() -> new GeneralException(BOARD_NOT_FOUND));

        CategoryEntity categoryEntity = categoryJpaRepository.findById(post.getCategoryId())
                .orElseThrow(() -> new GeneralException(CATEGORY_NOT_FOUND));

        postJpaRepository.delete(postMapper.toEntity(post, userEntity, boardEntity, categoryEntity));
    }

    @Override
    public Page<Post> findBySearchCriteria(Pageable pageable, String boardCode, String q, String categoryCode) {
        BoardEntity boardEntity = boardJpaRepository.findByBoardCode(BoardCode.getEnumBoardCodeFromStringBoardCode(boardCode))
                .orElseThrow(() -> new GeneralException(BOARD_NOT_FOUND));

        CategoryCode enumCategoryCodeFromStringCategoryCode = CategoryCode.getEnumCategoryCodeFromStringCategoryCode(categoryCode);

        List<PostEntity> content = queryFactory
                .selectFrom(postEntity)
                .where(postEntity.boardEntity.eq(boardEntity)
                        .and(postEntity.categoryEntity.categoryCode.eq(enumCategoryCodeFromStringCategoryCode))
                        .and(postEntity.title.containsIgnoreCase(q)
                                .or(postEntity.content.containsIgnoreCase(q))))
                .orderBy(postEntity.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(postEntity.count())
                .from(postEntity)
                .where(postEntity.boardEntity.eq(boardEntity)
                        .and(postEntity.categoryEntity.categoryCode.eq(enumCategoryCodeFromStringCategoryCode))
                        .and(postEntity.title.containsIgnoreCase(q)
                                .or(postEntity.content.containsIgnoreCase(q)))
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
                .map(postMapper::toDomain);

//        return postJpaRepository.findBySearchCriteria(
//                pageable,
//                boardEntity,
//                /*q.isEmpty() ? null : */q,
//                /*categoryCode.isEmpty() ? null : */MajorCode.getEnumMajorCodeFromStringMajorCode(categoryCode))
//                .map(postMapper::toDomain);
    }

    @Override
    public Page<SimplePostResponse> findPostDtoListByBoardCode(String boardCode, Pageable pageable) {

        List<SimplePostResponse> contents = queryFactory
                .select(Projections.constructor(SimplePostDto.class,
                        postEntity,
                        postReactionEntity.countDistinct().castToNum(Long.class)
                ))
                .from(postEntity)
                .leftJoin(postReactionEntity).on(postReactionEntity.postEntity.eq(postEntity))
                .leftJoin(postEntity.boardEntity, boardEntity)
                .where(eqBoardCode(boardCode))
                .groupBy(postEntity)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(simplePostDto -> SimplePostResponse.of(
                        postMapper.toDomain(simplePostDto.postEntity()),
                        Math.toIntExact(simplePostDto.likeCount())))
                .toList();

        JPAQuery<Long> countQuery = queryFactory
                .select(boardEntity.boardCode.countDistinct())
                .from(postEntity)
                .leftJoin(postReactionEntity).on(postReactionEntity.postEntity.eq(postEntity))
                .leftJoin(postEntity.boardEntity, boardEntity)
                .where(eqBoardCode(boardCode))
                .groupBy(postEntity);
        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchCount);
    }
    private BooleanExpression eqBoardCode(String boardCode) {
        return boardCode != null ? boardEntity.boardCode.eq(BoardCode.getEnumBoardCodeFromStringBoardCode(boardCode)) : null;
    }
}
