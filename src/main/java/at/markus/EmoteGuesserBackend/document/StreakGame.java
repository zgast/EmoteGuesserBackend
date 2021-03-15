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

@Document("StreakGame")
@FieldDefaults(level= AccessLevel.PRIVATE)
@Data
public class StreakGame {
    @Autowired
    UserRepository userRepository;

    @Field
    int streak;
    @Field
    User user;

    public  StreakGame(int streak, String name,String userID){
        this.streak = streak;

        List<User> userList= userRepository.findAll();
        for(User user:userList){
            if(user.getName().equals(name)&&user.getUserId().equals(userID)){
                this.user  = user;
            };
        }
    }
}
