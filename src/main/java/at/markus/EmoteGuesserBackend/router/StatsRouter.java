package at.markus.EmoteGuesserBackend.router;


import at.markus.EmoteGuesserBackend.Keys;
import at.markus.EmoteGuesserBackend.document.Stats;
import at.markus.EmoteGuesserBackend.document.StreakGame;
import at.markus.EmoteGuesserBackend.document.TimeGame;
import at.markus.EmoteGuesserBackend.document.UserStats;
import at.markus.EmoteGuesserBackend.repositories.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    UserStatsRepository userStatsRepository;
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

    @PostMapping("/global")
    public List<Map<String, Integer>> globalStats (@RequestBody HashMap<String,String> json) {
        if(json.get("key").equals(Keys.normal)){
            Pageable pageable = PageRequest.of(0, 100);
            return userRepository.findAll(pageable).stream().map(user -> {
                UserStats userStats = userStatsRepository.getByUserId(user.getUserId());
                if (userStats == null)
                    return null;
                Map<String, Integer> map = new HashMap<>();
                timeGameRepository.findById(userStats.getBestTimeGame()).ifPresent(game -> map.put("timeGame", game.getGuessed()));
                streakGameRepository.findById(userStats.getBestStreakGame()).ifPresent(game -> map.put("streakGame", game.getGuessed()));

                return map;
            }).collect(Collectors.toList());
        }
        return null;
    }
}
