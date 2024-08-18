package ussum.homepage.infra.jpa.post;

import com.querydsl.core.BooleanBuilder;
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
import ussum.homepage.domain.post.Category;
import ussum.homepage.domain.post.exception.PostException;
import ussum.homepage.domain.post.service.CategoryReader;
import ussum.homepage.infra.jpa.group.entity.GroupCode;
import ussum.homepage.infra.jpa.group.entity.QGroupEntity;
import ussum.homepage.infra.jpa.member.entity.MemberCode;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ussum.homepage.global.error.status.ErrorStatus.*;

import static ussum.homepage.infra.jpa.group.entity.QGroupEntity.groupEntity;
import static ussum.homepage.infra.jpa.member.entity.QMemberEntity.memberEntity;
import static ussum.homepage.infra.jpa.post.entity.PostEntity.increaseViewCount;
import static ussum.homepage.infra.jpa.post.entity.QPostEntity.postEntity;
import static ussum.homepage.infra.jpa.post.entity.QPostFileEntity.postFileEntity;
import static ussum.homepage.infra.jpa.postlike.entity.QPostReactionEntity.postReactionEntity;
import static ussum.homepage.infra.jpa.post.entity.QBoardEntity.boardEntity;

import static ussum.homepage.infra.jpa.post.entity.PostEntity.updateLastEditedAt;
import static ussum.homepage.infra.jpa.user.entity.QUserEntity.userEntity;


@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    private final PostJpaRepository postJpaRepository;
    private final BoardJpaRepository boardJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PostMapper postMapper;
    private final JPAQueryFactory queryFactory;
    private final CategoryMapper categoryMapper;

    @Override
    public Optional<Post> findById(Long postId) {
        return postJpaRepository.findById(postId).map(postMapper::toDomain);
    }

    @Override
    public Optional<Post> findByBoardIdAndId(Long boardId, Long postId) {
        BoardEntity boardEntity = boardJpaRepository.findById(boardId).orElseThrow(() -> new GeneralException(BOARD_NOT_FOUND));
        PostEntity postEntity = postJpaRepository.findByBoardEntityAndId(boardEntity, postId).orElseThrow(() -> new PostException(POST_NOT_FOUND));
        increaseViewCount(postEntity);
        return Optional.of(postMapper.toDomain(postEntity));
    }

    @Override
    public Optional<Post> findByBoardIdAndIdForEditAndDelete(Long boardId, Long postId) {
        BoardEntity boardEntity = boardJpaRepository.findById(boardId).orElseThrow(() -> new GeneralException(BOARD_NOT_FOUND));
        PostEntity postEntity = postJpaRepository.findByBoardEntityAndId(boardEntity, postId).orElseThrow(() -> new PostException(POST_NOT_FOUND));
        updateLastEditedAt(postEntity);
        return Optional.of(postMapper.toDomain(postEntity));
    }

    @Override
    public Page<Post> findAllByGroupCodeAndMemberCodeAndSubCategory(String groupCode, String memberCode, String subCategory, Pageable pageable) {
        BooleanBuilder whereClause = new BooleanBuilder();

        if (subCategory != null && !subCategory.isEmpty()) {
            whereClause.and(postFileEntity.subCategory.eq(subCategory));
        }
        if (memberCode != null && !memberCode.isEmpty()) {
            whereClause.and(memberEntity.memberCode.eq(MemberCode.valueOf(memberCode)));
        }
        if (groupCode != null && !groupCode.isEmpty()) {
            whereClause.and(groupEntity.groupCode.eq(GroupCode.valueOf(groupCode)));
        }

        if (whereClause.getValue() == null) {
            throw new IllegalArgumentException("At least one of subCategory, memberCode, or groupCode must be provided");
        }

        JPAQuery<PostEntity> query = queryFactory
                .selectFrom(postEntity)
                .leftJoin(postEntity.userEntity, userEntity)
                .leftJoin(memberEntity).on(memberEntity.userEntity.eq(userEntity))
                .leftJoin(memberEntity.groupEntity, groupEntity)
                .leftJoin(postFileEntity).on(postFileEntity.postEntity.eq(postEntity))
                .where(whereClause)
                .orderBy(postEntity.createdAt.desc());

        List<PostEntity> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(postEntity.count())
                .from(postEntity)
                .leftJoin(postEntity.userEntity, userEntity)
                .leftJoin(memberEntity).on(memberEntity.userEntity.eq(userEntity))
                .leftJoin(memberEntity.groupEntity, groupEntity)
                .leftJoin(postFileEntity).on(postFileEntity.postEntity.eq(postEntity))
                .where(whereClause);

        return PageableExecutionUtils.getPage(
                content.stream().map(postMapper::toDomain).collect(Collectors.toList()),
                pageable,
                countQuery::fetchOne
        );
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
        //7일 이내거만 확인
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);

        List<SimplePostResponse> contents = queryFactory
                .select(Projections.constructor(SimplePostDto.class,
                        postEntity,
                        postReactionEntity.countDistinct().castToNum(Long.class)
                ))
                .from(postEntity)
                .leftJoin(postReactionEntity).on(postReactionEntity.postEntity.eq(postEntity))
                .leftJoin(postEntity.boardEntity, boardEntity)
                .where(eqBoardCode(boardCode)
                        .and(postEntity.createdAt.after(sevenDaysAgo)))
                .groupBy(postEntity)
                .orderBy(postEntity.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(simplePostDto -> SimplePostResponse.of(
                        postMapper.toDomain(simplePostDto.postEntity()),
                        Math.toIntExact(simplePostDto.likeCount())))
                .toList();

        JPAQuery<Long> countQuery = queryFactory
                .select(postEntity.countDistinct())
                .from(postEntity)
                .leftJoin(postReactionEntity).on(postReactionEntity.postEntity.eq(postEntity))
                .leftJoin(postEntity.boardEntity, boardEntity)
                .where(eqBoardCode(boardCode)
                        .and(postEntity.createdAt.after(sevenDaysAgo)));

        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchCount);
    }

    private BooleanExpression eqBoardCode(String boardCode) {
        return boardCode != null ? boardEntity.boardCode.eq(BoardCode.getEnumBoardCodeFromStringBoardCode(boardCode)) : null;
    }

    @Override
    public Post updatePostOngoingStatus(Long postId, String onGoingStatus, Category category) {
        return postJpaRepository.findById(postId)
                .map(postEntity -> {
                    postEntity.updateStatusAndCategoryCode(
                            OngoingStatus.getEnumOngoingStatusFromStringOngoingStatus(onGoingStatus), categoryMapper.toEntity(category)
                    );
                    return postMapper.toDomain(postJpaRepository.save(postEntity));
                })
                .orElseThrow(() -> new PostException(POST_ONGOING_STATUS_IS_NOT_UPDATED));
    }
}
