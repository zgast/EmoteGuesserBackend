package at.markus.EmoteGuesserBackend.repositories;

import at.markus.EmoteGuesserBackend.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScoreRepository extends MongoRepository<User, String> {
}
