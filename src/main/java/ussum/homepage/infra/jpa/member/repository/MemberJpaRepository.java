package ussum.homepage.infra.jpa.member.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ussum.homepage.infra.jpa.member.entity.MemberEntity;

import java.util.List;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
    @Query("SELECT m FROM MemberEntity m WHERE m.userEntity.id = :userId")
    Optional<MemberEntity> findByUserId(@Param("userId") Long userId);

    @Query("SELECT m FROM MemberEntity m WHERE m.userEntity.id = :userId")
    List<MemberEntity> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT m FROM MemberEntity m WHERE m.userEntity.id = :userId AND m.groupEntity.id = :groupId ")
    Optional<MemberEntity> findByUserIdAndGroupId(@Param("userId") Long userid, @Param("groupId") Long groupId);

    @Modifying
    @Transactional
    @Query("DELETE FROM MemberEntity m WHERE m.userEntity.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);

}
