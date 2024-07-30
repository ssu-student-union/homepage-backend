package ussum.homepage.infra.jpa.postlike.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ussum.homepage.infra.jpa.post.entity.PostEntity;
import ussum.homepage.infra.jpa.postlike.entity.PostReactionEntity;
import ussum.homepage.infra.jpa.postlike.entity.Reaction;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

import java.util.Optional;

public interface PostReactionJpaRepository extends JpaRepository<PostReactionEntity, Long> {
    Optional<PostReactionEntity> findByPostEntityAndUserEntityAndReaction(PostEntity postEntity, UserEntity userEntity, Reaction reaction);
}
