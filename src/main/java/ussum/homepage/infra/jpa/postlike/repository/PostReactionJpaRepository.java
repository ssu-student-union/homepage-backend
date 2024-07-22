package ussum.homepage.infra.jpa.postlike.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ussum.homepage.infra.jpa.postlike.entity.PostReactionEntity;

public interface PostReactionJpaRepository extends JpaRepository<PostReactionEntity, Long> {

}
