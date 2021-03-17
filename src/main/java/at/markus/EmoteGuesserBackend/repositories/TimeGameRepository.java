package at.markus.EmoteGuesserBackend.repositories;

import at.markus.EmoteGuesserBackend.document.TimeGame;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TimeGameRepository extends MongoRepository<TimeGame, String> {
}
