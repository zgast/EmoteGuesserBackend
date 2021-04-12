package at.markus.EmoteGuesserBackend.router;

import at.markus.EmoteGuesserBackend.Keys;
import at.markus.EmoteGuesserBackend.document.StreakGame;
import at.markus.EmoteGuesserBackend.document.TimeGame;
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

@RestController
@RequestMapping("/EmoteGuesser/game/")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameRouter {
    @Autowired
    StreakGameRepository streakGameRepository;
    @Autowired
    TimeGameRepository timeGameRepository;

    @PostMapping("/streak/add")
    public void addStreakGame(@RequestBody HashMap<String, String> json) {
        if (json.get("key").equals(Keys.normal)) {
            StreakGame sg = new StreakGame(Integer.parseInt(json.get("guessed")), json.get("username"), json.get("userID"));
            streakGameRepository.insert(sg);
        }
    }

    @PostMapping("/time/add")
    public void addTimeGame(@RequestBody HashMap<String, String> json) {
        if (json.get("key").equals(Keys.normal)) {
            TimeGame tg = new TimeGame(Integer.parseInt(json.get("guessed")), json.get("username"), json.get("userID"));
            timeGameRepository.insert(tg);
        }
    }
}
