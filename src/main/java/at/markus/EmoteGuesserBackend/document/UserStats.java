package at.markus.EmoteGuesserBackend.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("UserStats")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserStats {
    @Id
    String userId;
    @Field
    String username;
    @Field
    String streakGames;
    @Field
    String timeGames;
    @Field
    String avgStreakGame;
    @Field
    String avgTimeGame;
}
