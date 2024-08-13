package ussum.homepage.infra.jpa.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ussum.homepage.infra.jpa.member.entity.MemberEntity;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
    @Query("SELECT m FROM MemberEntity m WHERE m.userEntity.id = :userId")
    Optional<MemberEntity> findByUserId(Long userId);

}
