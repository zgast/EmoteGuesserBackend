package at.markus.EmoteGuesserBackend.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("Stats")
@FieldDefaults(level= AccessLevel.PRIVATE)
@AllArgsConstructor
@Data
public class Stats {
    @Field
    int streakGames;
    @Field
    int streakGuessed;
    @Field
    int timeGames;
    @Field
    int timeGuessed;
    @Field
    int player;
    @Field
    int pictures;
}
