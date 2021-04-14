package at.markus.EmoteGuesserBackend.repositories;

import at.markus.EmoteGuesserBackend.document.StreakGame;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StreakGameRepository extends MongoRepository<StreakGame, String> {
    List<StreakGame> findByUsernameAndUserID(String username, String userId);
}
