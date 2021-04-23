package at.markus.EmoteGuesserBackend.repositories;
import at.markus.EmoteGuesserBackend.document.UserStats;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserStatsRepository extends MongoRepository<UserStats, String> {
    UserStats findByUsernameAndUserId(String username, String userId);
    boolean existsByUsernameAndUserId(String username, String userId);
}
