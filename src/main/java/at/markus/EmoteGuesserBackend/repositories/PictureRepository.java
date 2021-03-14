package at.markus.EmoteGuesserBackend.repositories;

import at.markus.EmoteGuesserBackend.document.Pictures;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PictureRepository extends MongoRepository<Pictures, String> {
}
