package at.markus.EmoteGuesserBackend.repositories;

import at.markus.EmoteGuesserBackend.document.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
}
