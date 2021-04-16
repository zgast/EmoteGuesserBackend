package at.markus.EmoteGuesserBackend.router;

import at.markus.EmoteGuesserBackend.Keys;
import at.markus.EmoteGuesserBackend.document.StreakGame;
import at.markus.EmoteGuesserBackend.document.TimeGame;
import at.markus.EmoteGuesserBackend.document.UserStats;
import at.markus.EmoteGuesserBackend.repositories.StreakGameRepository;
import at.markus.EmoteGuesserBackend.repositories.TimeGameRepository;
import at.markus.EmoteGuesserBackend.repositories.UserRepository;
import at.markus.EmoteGuesserBackend.repositories.UserStatsRepository;
import at.markus.EmoteGuesserBackend.util.ComparableStat;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.util.HashMap;
import java.util.Optional;

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
            StreakGame sg = new StreakGame(Integer.parseInt(json.get("guessed")), json.get("username"), userId);
            streakGameRepository.insert(sg);
            updateBest(userId, sg);
        }
    }

    @PostMapping("/time/add")
    public void addTimeGame(@RequestBody HashMap<String, String> json) {
        if (json.get("key").equals(Keys.normal)) {
            String userId = json.get("userID");
            TimeGame tg = new TimeGame(Integer.parseInt(json.get("guessed")), json.get("username"), json.get("userID"));
            timeGameRepository.insert(tg);
            updateBest(userId, tg);
        }
    }

    private void updateBest (String userId, ComparableStat stat) {
        if (!userStatsRepository.existsById(userId)) {
            userStatsRepository.insert(new UserStats(userId, "", ""));
            return;
        }
        UserStats best = userStatsRepository.findById(userId).get();
        if (stat instanceof TimeGame) {
            Optional<TimeGame> other = timeGameRepository.findById(best.getBestTimeGame());
            if ((other.isPresent() && other.get().compare(((TimeGame) stat).getGuessed())) || other.isEmpty())
                best.setBestTimeGame(stat.getId());
        } else if (stat instanceof StreakGame) {
            Optional<StreakGame> other = streakGameRepository.findById(best.getBestStreakGame());
            if ((other.isPresent() && other.get().compare(((StreakGame) stat).getGuessed())) || other.isEmpty())
                best.setBestStreakGame(stat.getId());
        }
        userStatsRepository.save(best);
    }
}
