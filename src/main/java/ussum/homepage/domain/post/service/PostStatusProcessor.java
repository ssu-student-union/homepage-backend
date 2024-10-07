package ussum.homepage.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.domain.post.PostRepository;
import ussum.homepage.infra.jpa.post.entity.PostEntity;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostStatusProcessor {
    private final PostRepository postRepository;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행됨, 특정 게시물의 생성시간이 3일이 지나면 스케줄러에 의해서 GENERAL 상태로 바뀜
    @Transactional
    public void updatePostStatusNewToGeneral() {
        LocalDateTime dueDateOfNew = LocalDateTime.now().minusDays(3);
        postRepository.updatePostStatusNewToGeneral(dueDateOfNew);
    }

    /**
     메모리 효율성:

     IN 쿼리 방식: 모든 대상 엔티티를 한 번에 메모리에 로드하므로 대량의 데이터 처리 시 OutOfMemoryError 발생 가능성이 있습니다.
     단일 UPDATE 방식: 서버 측 메모리 사용은 적지만, DB 서버에서 큰 부하가 발생할 수 있습니다.
     배치 처리 방식: 한 번에 제한된 수의 레코드만 처리하므로 메모리 사용을 효과적으로 제어할 수 있습니다.


     데이터베이스 부하:

     IN 쿼리 방식: 많은 데이터를 한 번에 조회하므로 DB에 순간적으로 큰 부하를 줄 수 있습니다.
     단일 UPDATE 방식: 대량의 레코드를 한 번에 업데이트하여 DB에 극심한 부하를 줄 수 있습니다.
     배치 처리 방식: 작업을 작은 단위로 나누어 실행하므로 DB 부하를 분산시킬 수 있습니다.


     트랜잭션 관리:

     IN 쿼리 방식: 모든 업데이트가 하나의 큰 트랜잭션에서 이루어져, 롤백 시 모든 변경사항이 취소됩니다.
     단일 UPDATE 방식: 하나의 대규모 트랜잭션으로 인해 롤백 시 많은 시간이 소요될 수 있습니다.
     배치 처리 방식: 작은 트랜잭션들로 나누어 처리하므로, 문제 발생 시 해당 배치만 롤백되어 리스크를 줄일 수 있습니다.


     락(Lock) 관리:

     IN 쿼리 방식: 많은 레코드를 동시에 락을 걸 수 있어 다른 트랜잭션을 차단할 수 있습니다.
     단일 UPDATE 방식: 대량의 레코드에 대해 동시에 락을 획득하여 다른 작업을 오랫동안 차단할 수 있습니다.
     배치 처리 방식: 소수의 레코드만 짧은 시간 동안 락을 걸기 때문에 다른 트랜잭션과의 충돌 가능성이 낮습니다.


     에러 처리와 재시도:

     IN 쿼리 방식: 전체 프로세스 중 오류 발생 시 어떤 레코드가 처리되었는지 파악하기 어렵습니다.
     단일 UPDATE 방식: 오류 발생 시 전체 작업이 실패하며, 부분적 성공을 처리하기 어렵습니다.
     배치 처리 방식: 각 배치별로 오류를 독립적으로 처리할 수 있어, 실패한 배치만 재시도할 수 있습니다.


     진행 상황 모니터링:

     IN 쿼리와 단일 UPDATE 방식: 전체 작업의 진행 상황을 중간에 확인하기 어렵습니다.
     배치 처리 방식: 각 배치마다 처리된 레코드 수를 확인할 수 있어 진행 상황을 실시간으로 모니터링할 수 있습니다.


     시스템 리소스 활용:

     배치 처리 방식은 시스템 리소스를 더 균형있게 사용할 수 있게 해주며, 다른 작업과의 리소스 경쟁을 줄일 수 있습니다.
     **/
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updatePostStatusToGeneral() {
        postRepository.updatePostStatusEmergencyToGeneralInBatches();
        System.out.println("게시물 상태 업데이트 작업이 실행되었습니다. - " + LocalDateTime.now());
    }
}
