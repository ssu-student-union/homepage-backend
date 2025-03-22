package ussum.homepage.domain.user;


import java.time.LocalDateTime;

public interface UserCountRepository {

    Long count();

    Long countByDate(LocalDateTime date);

    Long countByDateAfter(LocalDateTime date);
}
