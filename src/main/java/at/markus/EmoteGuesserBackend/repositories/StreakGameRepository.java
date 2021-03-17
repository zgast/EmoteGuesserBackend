package at.markus.EmoteGuesserBackend.repositories;

import at.markus.EmoteGuesserBackend.document.StreakGame;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StreakGameRepository extends MongoRepository<StreakGame, String> {
}
