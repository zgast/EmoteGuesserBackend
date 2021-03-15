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
@FieldDefaults(level= AccessLevel.PRIVATE)
@Data
public class TimeGame {
    @Autowired
    UserRepository userRepository;
    @Field
    int guessed;
    @Field
    User user;

    public  TimeGame(int guessed, String name,String userID){
        this.guessed = guessed;

        List<User> userList= userRepository.findAll();
        for(User user:userList){
            if(user.getName().equals(name)&&user.getUserId().equals(userID)){
                this.user  = user;
            };
        }
    }
}