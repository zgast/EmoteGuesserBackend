package at.markus.EmoteGuesserBackend.repositories;

import at.markus.EmoteGuesserBackend.document.TimeGame;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TimeGameRepository extends MongoRepository<TimeGame, String> {
    List<TimeGame> findByUsernameAndUserID(String username, String userId);
}
