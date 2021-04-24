package at.markus.EmoteGuesserBackend.router;

import at.markus.EmoteGuesserBackend.security.JSONWebToken;
import at.markus.EmoteGuesserBackend.security.Keys;
import at.markus.EmoteGuesserBackend.document.UserStats;
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
@RequestMapping("/emote-guesser/game/")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameRouter {
    @Autowired
    UserStatsRepository userStatsRepository;

    @PostMapping("/streak/add")
    public void addStreakGame(@RequestBody HashMap<String, String> json) {
        if (json.get("key").equals(Keys.NORMAL)) {
            var jwt = JSONWebToken.parse(json.get("jwt"));
            int guessed = Integer.parseInt(json.get("guessed"));

            updateStats(guessed, jwt.get("username").asString(),jwt.get("userID").asString(), Game.StreakGame);
        }
    }

    @PostMapping("/time/add")
    public void addTimeGame(@RequestBody HashMap<String, String> json) {
        if (json.get("key").equals(Keys.NORMAL)) {
            var jwt = JSONWebToken.parse(json.get("jwt"));
            int guessed = Integer.parseInt(json.get("guessed"));

            updateStats(guessed, jwt.get("username").asString(),jwt.get("userID").asString(), Game.TimeGame);
        }
    }

    private void updateStats(int guessed, String username, String userId, Game game ) {
        UserStats stats = userStatsRepository.findByUsernameAndUserId(username,userId);

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
