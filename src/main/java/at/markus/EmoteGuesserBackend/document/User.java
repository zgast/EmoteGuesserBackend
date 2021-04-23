package at.markus.EmoteGuesserBackend.document;

import at.markus.EmoteGuesserBackend.repositories.PictureRepository;
import at.markus.EmoteGuesserBackend.repositories.UserRepository;
import com.auth0.jwt.interfaces.Claim;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document("User")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class User{
    @Field
    String userId;
    @Field
    String name;
    @Field
    String token;

    public User(Claim userID, Claim username) {
    }
}