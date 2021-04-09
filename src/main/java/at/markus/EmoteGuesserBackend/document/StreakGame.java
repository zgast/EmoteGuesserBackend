package at.markus.EmoteGuesserBackend.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("StreakGame")
@FieldDefaults(level= AccessLevel.PRIVATE)
@AllArgsConstructor
@Data
public class StreakGame {
    @Field
    int guessed;
    @Field
    String username;
    @Field
    String userID;
}
