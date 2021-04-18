package at.markus.EmoteGuesserBackend.router;


import at.markus.EmoteGuesserBackend.Keys;
import at.markus.EmoteGuesserBackend.document.Stats;
import at.markus.EmoteGuesserBackend.document.StreakGame;
import at.markus.EmoteGuesserBackend.document.TimeGame;
import at.markus.EmoteGuesserBackend.document.UserStats;
import at.markus.EmoteGuesserBackend.repositories.*;
import at.markus.EmoteGuesserBackend.response.UserStatsResponse;
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
import java.util.Optional;
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
    public Map<String, String> userStats(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(Keys.normal)){
            String userID = json.get("userID");
            String userName = json.get("username");

            UserStats stats = userStatsRepository.findByUsernameAndUserId(userName,userID).get(0);

            return Map.of(
                    "streakGames", stats.getStreakGames(),
                    "streakGuessed",stats.getAvgStreakGame(),
                    "timeGames", stats.getTimeGames(),
                    "timeGuessed",stats.getAvgTimeGame()
            );
        }
        return null;
    }

    @PostMapping("/global")
    public List<UserStatsResponse> globalStats (@RequestBody HashMap<String,String> json) {
        if(json.get("key").equals(Keys.normal)){
            Pageable pageable = PageRequest.of(0, 100);
            return userRepository.findAll(pageable).stream().map(user -> {
                Optional<UserStats> userStats = userStatsRepository.findById(user.getUserId());
                UserStatsResponse.UserStatsResponseBuilder response = UserStatsResponse.builder().username(user.getName());
                userStats.ifPresent(stats -> {
                    timeGameRepository.findById(stats.getAvgTimeGame()).ifPresent(game -> response.timeGame(game.getGuessed()));
                    streakGameRepository.findById(stats.getStreakGames()).ifPresent(game -> response.streakGame(game.getGuessed()));
                });

                return response.build();
            }).collect(Collectors.toList());
        }
        return null;
    }
}
