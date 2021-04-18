package at.markus.EmoteGuesserBackend.repositories;

import at.markus.EmoteGuesserBackend.document.StreakGame;
import at.markus.EmoteGuesserBackend.document.UserStats;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserStatsRepository extends MongoRepository<UserStats, String> {
    List<UserStats> findByUsernameAndUserId(String username, String userId);
}
