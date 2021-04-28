package at.markus.EmoteGuesserBackend.router;

import at.markus.EmoteGuesserBackend.Routes;
import at.markus.EmoteGuesserBackend.document.*;
import at.markus.EmoteGuesserBackend.repositories.PictureRepository;
import at.markus.EmoteGuesserBackend.repositories.StatsRepository;
import at.markus.EmoteGuesserBackend.repositories.UserRepository;
import at.markus.EmoteGuesserBackend.repositories.UserStatsRepository;
import at.markus.EmoteGuesserBackend.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(Routes.MAIN + "/private/")
public class PrivateRouter {
    @Autowired
    PictureRepository pictureRepository;
    @Autowired
    StatsRepository statsRepository;
    @Autowired
    UserStatsRepository userStatsRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/picture/add")
    public void setNewUser(@RequestBody HashMap<String, String> json) {
        pictureRepository.insert(new Pictures(json.get("URL"), json.get("name")));
    }


    @PostMapping("/stats/all")
    public Stats addStreakGame(@RequestBody HashMap<String, String> json) {
        List<UserStats> list = userStatsRepository.findAll();
        int streakGames = list.stream().mapToInt(UserStats -> Integer.parseInt(UserStats.getStreakGames())).sum();
        int streakGuessed = list.stream().mapToInt(UserStats -> (int) (Double.parseDouble(UserStats.getAvgStreakGame()) * streakGames)).sum();

        int timeGames = list.stream().mapToInt(UserStats -> Integer.parseInt(UserStats.getTimeGames())).sum();
        int timeGuessed = list.stream().mapToInt(UserStats -> (int) (Double.parseDouble(UserStats.getAvgTimeGame()) * timeGames)).sum();

        long player = userRepository.count();
        long pictures = pictureRepository.count();

        Stats stat = new Stats(streakGames, streakGuessed, timeGames, timeGuessed, (int) player, (int) pictures);
        statsRepository.insert(stat);
        return stat;
    }
}
