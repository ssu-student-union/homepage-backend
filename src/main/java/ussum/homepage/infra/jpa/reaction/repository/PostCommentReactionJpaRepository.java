package ussum.homepage.infra.jpa.reaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ussum.homepage.infra.jpa.reaction.entity.PostCommentReactionEntity;

public interface PostCommentReactionJpaRepository extends JpaRepository<PostCommentReactionEntity, Long> {
}
