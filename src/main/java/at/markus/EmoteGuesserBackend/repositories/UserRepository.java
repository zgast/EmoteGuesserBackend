package at.markus.EmoteGuesserBackend.repositories;

import at.markus.EmoteGuesserBackend.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUserIdAndName(String userID, String username);
    boolean existsByUserIdAndName(String userID, String username);
    boolean existsByUserIdAndNameAndToken(String userID, String username,String token);
}
