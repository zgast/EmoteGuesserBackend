package at.markus.EmoteGuesserBackend.document;

import at.markus.EmoteGuesserBackend.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

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