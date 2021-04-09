package at.markus.EmoteGuesserBackend.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("TimeGame")
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Data
public class TimeGame {
    @Field
    int guessed;
    @Field
    String username;
    @Field
    String userID;
}