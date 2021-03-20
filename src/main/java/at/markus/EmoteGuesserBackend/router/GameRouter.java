package at.markus.EmoteGuesserBackend.router;

import at.markus.EmoteGuesserBackend.document.StreakGame;
import at.markus.EmoteGuesserBackend.document.TimeGame;
import at.markus.EmoteGuesserBackend.document.User;
import at.markus.EmoteGuesserBackend.repositories.StreakGameRepository;
import at.markus.EmoteGuesserBackend.repositories.TimeGameRepository;
import at.markus.EmoteGuesserBackend.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/EmoteGuesser/game/")
@FieldDefaults(level= AccessLevel.PRIVATE)
public class GameRouter {
    String accessKey = "usahd9720hd23807d23g2h8ofbgv24876fv24809fb2480fbn0ofhb<o83rg32ad78ashd8co89awhf9ofhaloifhf789obvaoisdzbvösadcvbasipf";
    @Autowired
    StreakGameRepository streakGameRepository;
    @Autowired
    TimeGameRepository timeGameRepository;
    @Autowired
    UserRepository userRepository;



    @PostMapping("/streak/add")
    public void addStreakGame(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(accessKey)){
            StreakGame sg = new StreakGame(Integer.parseInt(json.get("streak")), json.get("username"), json.get("userID"));
            streakGameRepository.insert(sg);
        }
    }

    @PostMapping("/timegame/add")
    public void addTimeGame(@RequestBody HashMap<String,String> json){
        String name = json.get("userID"), userID = json.get("username");

        if(json.get("key").equals(accessKey)){
            User player = null;
            List<User> userList= userRepository.findAll();
            for(User user:userList){
                if(user.getName().equals(name)&&user.getUserId().equals(userID)){
                    player  = user;
                };
            }

            TimeGame tg = new TimeGame(Integer.parseInt(json.get("guessed")),player );
            timeGameRepository.insert(tg);
            System.out.println("luö");
        }
    }
}
