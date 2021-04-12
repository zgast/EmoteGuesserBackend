package at.markus.EmoteGuesserBackend.router;


import at.markus.EmoteGuesserBackend.Keys;
import at.markus.EmoteGuesserBackend.document.Stats;
import at.markus.EmoteGuesserBackend.document.StreakGame;
import at.markus.EmoteGuesserBackend.document.TimeGame;
import at.markus.EmoteGuesserBackend.repositories.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/EmoteGuesser/stats/")
@FieldDefaults(level= AccessLevel.PRIVATE)
public class StatsRouter {
    @Autowired
    PictureRepository pictureRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StreakGameRepository streakGameRepository;
    @Autowired
    TimeGameRepository timeGameRepository;
    @Autowired
    StatsRepository statsRepository;

    @PostMapping("/all")
    public Stats addStreakGame(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(Keys.stats)){
            int streakGames = streakGameRepository.findAll().size();
            int streakGuessed = 0;
            for(StreakGame sg: streakGameRepository.findAll()){
                streakGuessed+=sg.getGuessed();
            }
            int timeGames = timeGameRepository.findAll().size();
            int timeGuessed=0;
            for(TimeGame tg : timeGameRepository.findAll()){
                timeGuessed+=tg.getGuessed();
            }
            int player = userRepository.findAll().size();
            int pictures = pictureRepository.findAll().size();

            Stats stat = new Stats(streakGames,streakGuessed,timeGames,timeGuessed,player,pictures);
            statsRepository.insert(stat);
            return stat;
        }
        return null;
    }

    @PostMapping("/user")
    public Map<String, Integer> userStats(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(Keys.normal)){
            String userID = json.get("userID");
            String userName = json.get("username");


            List<StreakGame> listSG = null;
            List<TimeGame> listTG = null;
            try{
                 listSG= streakGameRepository.findAll();
                 listTG= timeGameRepository.findAll();
            }catch(Exception e){
                e.printStackTrace();
            }

            int streakGames= 0;
            int streakGuessed= 0;
            if(listSG!=null){
                for(StreakGame sg: listSG){
                    if(sg.getUsername().equals(userName)&&sg.getUserID().equals(userID)){
                        streakGames++;
                        streakGuessed+=sg.getGuessed();
                    }
                }
            }


            int timeGames= 0;
            int timeGuessed= 0;
            if(listTG!=null){
                for(TimeGame tg: listTG){
                    if(tg.getUsername().equals(userName)&&tg.getUserID().equals(userID)){
                        timeGames++;
                        timeGuessed+=tg.getGuessed();
                    }
                }
            }

            return Map.of("streakGames",streakGames,
                    "streakGuessed",streakGuessed,
                    "timeGames",timeGames,
                    "timeGuessed",timeGuessed);
        }
        return null;
    }

}
