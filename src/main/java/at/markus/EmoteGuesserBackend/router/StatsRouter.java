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
            List<StreakGame> streakGames = streakGameRepository.findAll();
            int streakGamesSize = streakGames.size();
            int streakGuessed = streakGames.stream().map(StreakGame::getGuessed).reduce(0, Integer::sum);

            List<TimeGame> timeGames = timeGameRepository.findAll();
            int timeGamesSize = timeGames.size();
            int timeGuessed = timeGames.stream().map(TimeGame::getGuessed).reduce(0, Integer::sum);

            long player = userRepository.count();
            long pictures = pictureRepository.count();

            Stats stat = new Stats(streakGamesSize,streakGuessed,timeGamesSize,timeGuessed,(int) player, (int) pictures);
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

            List<StreakGame> streaks = streakGameRepository.findByUsernameAndUserID(userName, userID);
            List<TimeGame> times = timeGameRepository.findByUsernameAndUserID(userName, userID);

            int streakGuessed = streaks.stream().map(StreakGame::getGuessed).reduce(0, Integer::sum);
            int timeGuessed = times.stream().map(TimeGame::getGuessed).reduce(0, Integer::sum);

            return Map.of(
                    "streakGames", streaks.size(),
                    "streakGuessed",streakGuessed,
                    "timeGames", times.size(),
                    "timeGuessed",timeGuessed
            );
        }
        return null;
    }

}
