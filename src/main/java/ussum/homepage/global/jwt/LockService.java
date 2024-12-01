package ussum.homepage.global.jwt;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class LockService {
    private final EntityManager entityManager;

    public <T> T executeWithLock(String lockName, Supplier<T> supplier) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Query query = entityManager.createNativeQuery("SELECT GET_LOCK(:lockName, 10)");
            query.setParameter("lockName", lockName);
            query.getSingleResult();

            T result = supplier.get();

            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            Query releaseQuery = entityManager.createNativeQuery("SELECT RELEASE_LOCK(:lockName)");
            releaseQuery.setParameter("lockName", lockName);
            releaseQuery.getSingleResult();
        }
    }
}
