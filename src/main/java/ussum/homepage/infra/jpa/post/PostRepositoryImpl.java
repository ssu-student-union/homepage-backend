package ussum.homepage.infra.jpa.post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import ussum.homepage.application.post.service.dto.response.SimplePostResponse;
import ussum.homepage.domain.post.exception.PostException;
import ussum.homepage.infra.jpa.comment.repository.PostCommentJpaRepository;
import ussum.homepage.infra.jpa.comment.repository.PostReplyCommentJpaRepository;
import ussum.homepage.infra.jpa.group.entity.GroupCode;
import ussum.homepage.infra.jpa.member.entity.MemberCode;
import ussum.homepage.infra.jpa.post.dto.SimplePostDto;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostRepository;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.infra.jpa.post.entity.*;
import ussum.homepage.infra.jpa.post.repository.BoardJpaRepository;
import ussum.homepage.infra.jpa.post.repository.PostFileJpaRepository;
import ussum.homepage.infra.jpa.post.repository.PostJpaRepository;
import ussum.homepage.infra.jpa.postlike.entity.PostReactionEntity;
import ussum.homepage.infra.jpa.postlike.repository.PostReactionJpaRepository;
import ussum.homepage.infra.jpa.reaction.entity.PostCommentReactionEntity;
import ussum.homepage.infra.jpa.reaction.entity.PostReplyCommentReactionEntity;
import ussum.homepage.infra.jpa.reaction.repository.PostCommentReactionJpaRepository;
import ussum.homepage.infra.jpa.reaction.repository.PostReplyCommentReactionJpaRepository;
import ussum.homepage.infra.jpa.user.entity.UserEntity;
import ussum.homepage.infra.jpa.user.repository.UserJpaRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ussum.homepage.global.error.status.ErrorStatus.*;

import static ussum.homepage.infra.jpa.group.entity.QGroupEntity.groupEntity;
import static ussum.homepage.infra.jpa.member.entity.QMemberEntity.memberEntity;;
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
    private final PostFileJpaRepository postFileJpaRepository;
    private final PostReactionJpaRepository postReactionJpaRepository;
    private final PostCommentReactionJpaRepository postCommentReactionJpaRepository;
    private final PostReplyCommentReactionJpaRepository postReplyCommentReactionJpaRepository;
    private final BoardJpaRepository boardJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PostMapper postMapper;
    private final JPAQueryFactory queryFactory;
    private final PostReplyCommentJpaRepository postReplyCommentJpaRepository;
    private final PostCommentJpaRepository postCommentJpaRepository;
    private final EntityManager entityManager;

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

//    @Override
//    public Page<Post> findAllByGroupCodeAndMemberCodeAndSubCategory(GroupCode groupCode, MemberCode memberCode, String subCategory, Pageable pageable) {
//        BooleanBuilder whereClause = new BooleanBuilder(postEntity.boardEntity.id.eq(6L));
//
//        if (subCategory != null && !subCategory.isEmpty()) {
//            whereClause.and(postFileEntity.subCategory.eq(subCategory));
//        }
//        if (memberCode != null) {
//            whereClause.and(memberEntity.memberCode.eq(memberCode));
//        }
//        if (groupCode != null) {
//            whereClause.and(groupEntity.groupCode.eq(groupCode));
//        }
//
////        if (whereClause.getValue() == null) {
////            throw new IllegalArgumentException("At least one of subCategory, memberCode, or groupCode must be provided");
////        }
//
//        JPAQuery<PostEntity> query = queryFactory
//                .selectFrom(postEntity)
//                .leftJoin(postEntity.userEntity, userEntity)
//                .leftJoin(memberEntity).on(memberEntity.userEntity.eq(userEntity))
//                .leftJoin(memberEntity.groupEntity, groupEntity)
//                .leftJoin(postFileEntity).on(postFileEntity.postEntity.eq(postEntity))
//                .where(whereClause)
//                .orderBy(postEntity.createdAt.desc());
//
//        List<PostEntity> content = query
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        JPAQuery<Long> countQuery = queryFactory
//                .select(postEntity.count())
//                .from(postEntity)
//                .leftJoin(postEntity.userEntity, userEntity)
//                .leftJoin(memberEntity).on(memberEntity.userEntity.eq(userEntity))
//                .leftJoin(memberEntity.groupEntity, groupEntity)
//                .leftJoin(postFileEntity).on(postFileEntity.postEntity.eq(postEntity))
//                .where(whereClause);
//
//        return PageableExecutionUtils.getPage(
//                content.stream().map(postMapper::toDomain).collect(Collectors.toList()),
//                pageable,
//                countQuery::fetchOne
//        );
//    }
    @Override
    public Page<Post> findAllByFileCategories(List<FileCategory> fileCategories, Pageable pageable) {
        BooleanExpression whereClause = postEntity.boardEntity.id.eq(6L);

        if (fileCategories != null && !fileCategories.isEmpty()) {
            whereClause = whereClause.and(postEntity.id.in(
                    JPAExpressions
                            .select(postFileEntity.postEntity.id)
                            .from(postFileEntity)
                            .where(postFileEntity.fileCategory.in(fileCategories))
            ));
        }

        List<PostEntity> content = queryFactory
                .selectFrom(postEntity)
                .where(whereClause)
                .orderBy(postEntity.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(postEntity.count())
                .from(postEntity)
                .where(whereClause);

        return PageableExecutionUtils.getPage(
                content.stream().map(postMapper::toDomain).collect(Collectors.toList()),
                pageable,
                countQuery::fetchOne
        );
    }
    @Override
    public Page<Post> findAllByBoardIdAndGroupCodeAndMemberCode(Long boardId, GroupCode groupCode, MemberCode memberCode, Pageable pageable) {
        BooleanBuilder whereClause = new BooleanBuilder(postEntity.boardEntity.id.eq(boardId));

        if (memberCode != null) {
            whereClause.and(memberEntity.memberCode.eq(memberCode));
        }
        if (groupCode != null) {
            whereClause.and(groupEntity.groupCode.eq(groupCode));
        }

        // Status 우선순위를 정의합니다.
        NumberExpression<Integer> statusOrder = new CaseBuilder()
                .when(postEntity.status.eq(Status.EMERGENCY_NOTICE)).then(1)
                .when(postEntity.status.eq(Status.NEW)).then(2)
                .when(postEntity.status.eq(Status.GENERAL)).then(3)
                .otherwise(Integer.MAX_VALUE);


//        if (whereClause.getValue() == null) {
//            throw new IllegalArgumentException("At least one of memberCode, or groupCode must be provided");
//        }

        JPAQuery<PostEntity> query = queryFactory
                .selectFrom(postEntity)
                .leftJoin(postEntity.userEntity, userEntity)
                .leftJoin(memberEntity).on(memberEntity.userEntity.eq(userEntity))
                .leftJoin(memberEntity.groupEntity, groupEntity)
                .leftJoin(postFileEntity).on(postFileEntity.postEntity.eq(postEntity))
                .where(whereClause)
//                .orderBy(postEntity.createdAt.desc());
                .orderBy(statusOrder.asc(), postEntity.createdAt.desc());



        List<PostEntity> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

//        JPAQuery<Long> countQuery = queryFactory
//                .select(postEntity.count())
//                .from(postEntity)
//                .where(whereClause);

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
    public Page<Post> findAllByBoardIdAndCategory(Long boardId, Category category, Pageable pageable) {
        BooleanBuilder whereClause = new BooleanBuilder(postEntity.boardEntity.id.eq(boardId));

        if (category != null) {
            whereClause.and(postEntity.category.eq(category));
        }

        JPAQuery<PostEntity> query = queryFactory
                .selectFrom(postEntity)
                .where(whereClause)
                .orderBy(postEntity.createdAt.desc());

        List<PostEntity> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(postEntity.count())
                .from(postEntity)
                .where(whereClause);

        return PageableExecutionUtils.getPage(
                content.stream().map(postMapper::toDomain).collect(Collectors.toList()),
                pageable,
                countQuery::fetchOne
        );
    }

    @Override
    public Post save(Post post){

        return postMapper.toDomain(
                postJpaRepository.save(postMapper.toEntity(post))
        );
    }

    @Override
    public void delete(Post post) {
//        UserEntity userEntity = userJpaRepository.findById(post.getUserId())
//                .orElseThrow(() -> new GeneralException(USER_NOT_FOUND));

        BoardEntity boardEntity = boardJpaRepository.findById(post.getBoardId())
                .orElseThrow(() -> new GeneralException(BOARD_NOT_FOUND));

        if (boardEntity.getBoardCode().equals(BoardCode.getEnumBoardCodeFromStringBoardCode("청원게시판"))) {
            // 게시물에 해당하는 모든 댓글 조회 및 처리
            postCommentJpaRepository.findAllByPostId(post.getId())
                    .forEach(postCommentEntity -> {
                        // 대댓글 삭제
                        postReplyCommentJpaRepository.findAllByPostCommentId(postCommentEntity.getId())
                                .forEach(postReplyCommentEntity -> {
                                    // 대댓글 반응 삭제
                                    List<PostReplyCommentReactionEntity> postReplyCommentReactionList = postReplyCommentReactionJpaRepository.findAllByPostReplyCommentId(postReplyCommentEntity.getId());
                                    postReplyCommentReactionJpaRepository.deleteAll(postReplyCommentReactionList);

                                    // 대댓글 삭제
                                    postReplyCommentJpaRepository.delete(postReplyCommentEntity);
                                });

                        // 댓글 반응 삭제
                        List<PostCommentReactionEntity> postCommentReactionEntityList = postCommentReactionJpaRepository.findAllByPostCommentId(postCommentEntity.getId());
                        postCommentReactionJpaRepository.deleteAll(postCommentReactionEntityList);

                        // 댓글 삭제
                        postCommentJpaRepository.delete(postCommentEntity);
                    });
        }
        // 게시물에 연결된 파일 삭제
        postFileJpaRepository.deleteAll(postFileJpaRepository.findAllByPostId(post.getId()));

        // 게시물 반응 삭제
        List<PostReactionEntity> postReactionEntityList = postReactionJpaRepository.findAllByPostId(post.getId());
        postReactionJpaRepository.deleteAll(postReactionEntityList);

        // 게시물 삭제
        postJpaRepository.delete(postMapper.toEntity(post));
    }

    @Override
    public Page<Post> findBySearchCriteria(Pageable pageable, String boardCode, String q, String categoryCode) {
        BoardEntity boardEntity = boardJpaRepository.findByBoardCode(BoardCode.getEnumBoardCodeFromStringBoardCode(boardCode))
                .orElseThrow(() -> new GeneralException(BOARD_NOT_FOUND));

        Category enumCategoryCodeFromStringCategory = Category.getEnumCategoryCodeFromStringCategoryCode(categoryCode);

        List<PostEntity> content = queryFactory
                .selectFrom(postEntity)
                .where(postEntity.boardEntity.eq(boardEntity)
                        .and(postEntity.category.eq(enumCategoryCodeFromStringCategory))
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
                        .and(postEntity.category.eq(enumCategoryCodeFromStringCategory))
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
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minus(30, ChronoUnit.DAYS);
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);

        OrderSpecifier<?>[] orderSpecifiers = new OrderSpecifier<?>[]{
                postEntity.createdAt.desc()
        };
        if (boardCode.equals(BoardCode.PETITION.getStringBoardCode())) {
            orderSpecifiers = new OrderSpecifier<?>[]{
                    postReactionEntity.countDistinct().castToNum(Long.class).desc(),
                    postEntity.createdAt.desc()
            };
        }

        BooleanExpression timeCondition = postEntity.category.eq(Category.IN_PROGRESS)
                .and(postEntity.createdAt.after(thirtyDaysAgo))
                .or(postEntity.category.eq(Category.ANSWERED)
                        .and(postEntity.createdAt.after(sevenDaysAgo)))
                .or(postEntity.category.notIn(Category.IN_PROGRESS, Category.ANSWERED, Category.COMPLETED));

        List<SimplePostResponse> contents = queryFactory
                .select(Projections.constructor(SimplePostDto.class,
                        postEntity,
                        postReactionEntity.countDistinct().castToNum(Long.class)
                ))
                .from(postEntity)
                .leftJoin(postReactionEntity).on(postReactionEntity.postEntity.eq(postEntity))
                .leftJoin(postEntity.boardEntity, boardEntity)
                .where(eqBoardCode(boardCode)
                        .and(timeCondition)
                        .and(postEntity.category.ne(Category.COMPLETED)))
                .groupBy(postEntity)
                .having(postReactionEntity.countDistinct().goe(1L))
                .orderBy(orderSpecifiers)
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
                        .and(timeCondition)
                        .and(postEntity.category.ne(Category.COMPLETED)));

        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchCount);
    }

    private BooleanExpression eqBoardCode(String boardCode) {
        return boardCode != null ? boardEntity.boardCode.eq(BoardCode.getEnumBoardCodeFromStringBoardCode(boardCode)) : null;
    }

    @Override
    public List<Post> findAllByCategory(List<String> statuses) {
//        return postJpaRepository.findAllByOngoingStatusIn(statuses).stream()
//                .map(postMapper::toDomain)
//                .collect(Collectors.toList());
        BooleanBuilder whereClause = new BooleanBuilder();
        if (statuses != null && !statuses.isEmpty()) {
            List<Category> categories = statuses.stream()
                    .map(Category::getEnumCategoryCodeFromStringCategoryCode)
                    .collect(Collectors.toList());
            whereClause.and(postEntity.category.in(categories));
        }

        return queryFactory
                .selectFrom(postEntity)
                .where(whereClause)
                .fetch()
                .stream()
                .map(postMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Post updatePostCategory(Post post, Category category) {
        PostEntity postEntity = postMapper.toEntity(post);
        postEntity.updateCategory(category);
        return postMapper.toDomain(postJpaRepository.save(postEntity));
    }

    @Override
    public Page<Post> searchAllByBoardIdAndGroupCodeAndMemberCode(Long boardId, String q, GroupCode groupCode, MemberCode memberCode, Pageable pageable) {
        BooleanBuilder whereClause = new BooleanBuilder(postEntity.boardEntity.id.eq(boardId));

        if (memberCode != null) {
            whereClause.and(memberEntity.memberCode.eq(memberCode));
        }
        if (groupCode != null) {
            whereClause.and(groupEntity.groupCode.eq(groupCode));
        }

//        if (whereClause.getValue() == null) {
//            throw new IllegalArgumentException("At least one of memberCode, or groupCode must be provided");
//        }

        // 검색어 q가 지정된 경우, 제목에 해당 검색어가 포함된 게시물만 필터링
        if (q != null && !q.isEmpty()) {
            whereClause.and(postEntity.title.like("%" + q + "%"));
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
    public Page<Post> searchAllByBoardIdAndCategory(Long boardId, String q, Category category, Pageable pageable) {
        // 기본 where 조건: 게시판 ID가 일치하는 게시물 필터링
        BooleanBuilder whereClause = new BooleanBuilder(postEntity.boardEntity.id.eq(boardId));

        // 카테고리가 지정된 경우 해당 카테고리의 게시물만 필터링
        if (category != null) {
            whereClause.and(postEntity.category.eq(category));
        }

        // 검색어 q가 지정된 경우, 제목에 해당 검색어가 포함된 게시물만 필터링
        if (q != null && !q.isEmpty()) {
            whereClause.and(postEntity.title.like("%" + q + "%"));
        }


        // 쿼리 작성: 게시물을 가져오고 페이지네이션 및 정렬 적용
        JPAQuery<PostEntity> query = queryFactory
                .selectFrom(postEntity)
                .where(whereClause)
                .orderBy(postEntity.createdAt.desc());

        // 실제 데이터 가져오기
        List<PostEntity> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 카운트 쿼리: 페이징 정보 생성에 필요
        JPAQuery<Long> countQuery = queryFactory
                .select(postEntity.count())
                .from(postEntity)
                .where(whereClause);

        // 페이지 객체 반환
        return PageableExecutionUtils.getPage(
                content.stream().map(postMapper::toDomain).collect(Collectors.toList()),
                pageable,
                countQuery::fetchOne
        );
    }

    @Override
    public Page<Post> searchAllByFileCategories(String q, List<FileCategory> fileCategories, Pageable pageable) {
        BooleanExpression whereClause = postEntity.boardEntity.id.eq(6L);

        if (q != null && !q.isEmpty()) {
            whereClause = whereClause.and(postEntity.title.like("%" + q + "%"));
        }

        if (fileCategories != null && !fileCategories.isEmpty()) {
            whereClause = whereClause.and(postEntity.id.in(
                    JPAExpressions
                            .select(postFileEntity.postEntity.id)
                            .from(postFileEntity)
                            .where(postFileEntity.fileCategory.in(fileCategories))
            ));
        }

        List<PostEntity> content = queryFactory
                .selectFrom(postEntity)
                .where(whereClause)
                .orderBy(postEntity.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(postEntity.count())
                .from(postEntity)
                .where(whereClause);

        return PageableExecutionUtils.getPage(
                content.stream().map(postMapper::toDomain).collect(Collectors.toList()),
                pageable,
                countQuery::fetchOne
        );
    }

    @Override
    public void updatePostStatusNewToGeneral(LocalDateTime dueDateForNewStatus) {
        queryFactory
                .update(postEntity)
                .set(postEntity.status, Status.GENERAL)
                .where(postEntity.status.eq(Status.NEW)
                        .and(postEntity.createdAt.loe(dueDateForNewStatus)))
                .execute();
    }

    @Override
    public void updatePostStatusEmergencyToGeneralInBatches() {
        // 3일 전 날짜 계산
        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);
        // 한 번에 처리할 게시물 수 설정
        int batchSize = 500;
        // 총 처리된 게시물 수를 추적하기 위한 변수
        long processedCount = 0;

        while (true) {
            // 배치 크기만큼의 게시물 ID를 가져옴
            List<Long> batchIds = queryFactory
                    .select(postEntity.id)
                    .from(postEntity)
                    .where(postEntity.boardEntity.id.eq(2L)  // 공지사항 게시판 ID가 1이라고 가정
                            .and(postEntity.status.ne(Status.GENERAL))  // 상태가 GENERAL이 아닌 것
                            .and(postEntity.createdAt.before(threeDaysAgo)))  // 3일 이전에 생성된 것
                    .limit(batchSize)
                    .fetch();

            // 더 이상 처리할 게시물이 없으면 반복 종료
            if (batchIds.isEmpty()) {
                break;
            }

            // 가져온 ID에 해당하는 게시물들의 상태를 GENERAL로 업데이트
            long updatedCount = queryFactory
                    .update(postEntity)
                    .set(postEntity.status, Status.GENERAL)
                    .where(postEntity.id.in(batchIds))
                    .execute();

            // 처리된 게시물 수 누적
            processedCount += updatedCount;

            // 영속성 컨텍스트 초기화 (메모리 관리를 위해)
            entityManager.clear();
        }

        // 총 처리된 게시물 수 출력
        System.out.println("총 업데이트된 게시물 수: " + processedCount);
    }
}
