package at.markus.EmoteGuesserBackend.router;

import at.markus.EmoteGuesserBackend.Keys;
import at.markus.EmoteGuesserBackend.document.StreakGame;
import at.markus.EmoteGuesserBackend.document.TimeGame;
import at.markus.EmoteGuesserBackend.document.UserStats;
import at.markus.EmoteGuesserBackend.repositories.StreakGameRepository;
import at.markus.EmoteGuesserBackend.repositories.TimeGameRepository;
import at.markus.EmoteGuesserBackend.repositories.UserStatsRepository;
import at.markus.EmoteGuesserBackend.util.Game;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/EmoteGuesser/game/")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameRouter {
    @Autowired
    StreakGameRepository streakGameRepository;
    @Autowired
    TimeGameRepository timeGameRepository;
    @Autowired
    UserStatsRepository userStatsRepository;

    @PostMapping("/streak/add")
    public void addStreakGame(@RequestBody HashMap<String, String> json) {
        if (json.get("key").equals(Keys.normal)) {
            String userId = json.get("userID");
            String username = json.get("username");
            int guessed = Integer.parseInt(json.get("guessed"));

            updateStats(guessed, username,userId, Game.StreakGame);
        }
    }

    @PostMapping("/time/add")
    public void addTimeGame(@RequestBody HashMap<String, String> json) {
        if (json.get("key").equals(Keys.normal)) {
            String userId = json.get("userID");
            String username = json.get("username");
            int guessed = Integer.parseInt(json.get("guessed"));

            updateStats(guessed, username,userId, Game.TimeGame);
        }
    }

    private void updateStats(int guessed, String username, String userId, Game game ) {
        if (!userStatsRepository.existsByUsernameAndUserId(username,userId)) {
            if(game == Game.StreakGame){
                userStatsRepository.insert(new UserStats(userId, username, "1", "0", String.valueOf(guessed), "0"));
            }else{
                userStatsRepository.insert(new UserStats(userId, username, "0", "1", "0", String.valueOf(guessed)));
            }
            return;
        }

        UserStats stats = userStatsRepository.findByUsernameAndUserId(username,userId).get(0);

        if(game == Game.StreakGame){
            double avgStreak = Double.parseDouble(stats.getAvgStreakGame());
            int streakGames = Integer.parseInt(stats.getStreakGames());

            stats.setAvgStreakGame(Double.toString((((avgStreak*streakGames)+guessed + 0d) / (streakGames+1d))));
            stats.setStreakGames(String.valueOf(streakGames+1));
        }else{
            double avgTime = Double.parseDouble(stats.getAvgTimeGame());
            int timeGames = Integer.parseInt(stats.getTimeGames());

            stats.setAvgTimeGame(Double.toString((((avgTime*timeGames)+guessed + 0d) / (timeGames+1d))));
            stats.setTimeGames(String.valueOf(timeGames+1));
        }

        userStatsRepository.save(stats);
    }
}
