package at.markus.EmoteGuesserBackend.document;

import at.markus.EmoteGuesserBackend.util.ComparableStat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("StreakGame")
@FieldDefaults(level= AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Data
public class StreakGame implements ComparableStat {
    @Id
    String id;
    @Field
    int guessed;
    @Field
    String username;
    @Field
    String userID;

    public StreakGame(int guessed, String username, String userID) {
        this.guessed = guessed;
        this.username = username;
        this.userID = userID;
    }

    public boolean compare(int value) {
        return value > guessed;
    }
}
