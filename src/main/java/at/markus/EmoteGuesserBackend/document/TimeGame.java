package at.markus.EmoteGuesserBackend.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("TimeGame")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Data
public class TimeGame {
    @Id
    String id;
    @Field
    int guessed;
    @Field
    String username;
    @Field
    String userID;

    public TimeGame(int guessed, String username, String userID) {
        this.guessed = guessed;
        this.username = username;
        this.userID = userID;
    }

    public boolean compare(int value) {
        return value > guessed;
    }
}