package at.markus.EmoteGuesserBackend.router;


import at.markus.EmoteGuesserBackend.security.JSONWebToken;
import at.markus.EmoteGuesserBackend.security.Keys;
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

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/emote-guesser/stats/")
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
        if(json.get("key").equals(Keys.STATS)){
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
        if(json.get("key").equals(Keys.NORMAL)){
            var jwt = JSONWebToken.parse(json.get("jwt"));
            String userID = jwt.get("userID").asString();
            String userName = jwt.get("username").asString();
            UserStats stats = null;
            int streakGlobal = 0;
            int timeGlobal = 0;

            List<UserStats> list = userStatsRepository.findAll();
            list.sort(Comparator.comparing(UserStats -> Double.parseDouble(UserStats.getAvgStreakGame())));

            for(int i=list.size()-1;i>0;i--){
                streakGlobal++;
                if(list.get(i).getUsername().equals(userName)&&list.get(i).getUserId().equals(userID)){
                    stats = list.get(i);
                    break;
                }
            }

            list.sort(Comparator.comparing(UserStats -> Double.parseDouble(UserStats.getAvgTimeGame())));
            for(int i=list.size()-1;i>0;i--){
                timeGlobal++;
                if(list.get(i).getUsername().equals(userName)&&list.get(i).getUserId().equals(userID)){
                    break;
                }
            }

            return Map.of(
                    "streakGames", stats.getStreakGames(),
                    "streakGuessed",stats.getAvgStreakGame(),
                    "streakGlobal",String.valueOf(streakGlobal),
                    "timeGames", stats.getTimeGames(),
                    "timeGuessed",stats.getAvgTimeGame(),
                    "timeGlobal",String.valueOf(timeGlobal)
            );
        }
        return null;
    }

    @PostMapping("/global")
    public List<UserStatsResponse> globalStats (@RequestBody HashMap<String,String> json) {
        if(json.get("key").equals(Keys.NORMAL)){
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
