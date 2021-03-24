package at.markus.EmoteGuesserBackend.repositories;

import at.markus.EmoteGuesserBackend.document.Stats;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatsRepository extends MongoRepository<Stats, String> {
}
