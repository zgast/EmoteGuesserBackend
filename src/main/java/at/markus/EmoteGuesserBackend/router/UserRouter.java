package at.markus.EmoteGuesserBackend.router;

import at.markus.EmoteGuesserBackend.Routes;
import at.markus.EmoteGuesserBackend.document.UserStats;
import at.markus.EmoteGuesserBackend.repositories.UserStatsRepository;
import at.markus.EmoteGuesserBackend.security.Cryptography;
import at.markus.EmoteGuesserBackend.security.JSONWebToken;
import at.markus.EmoteGuesserBackend.security.Keys;
import at.markus.EmoteGuesserBackend.document.User;
import at.markus.EmoteGuesserBackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(Routes.MAIN + Routes.VERSION + "users/")
public class UserRouter {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserStatsRepository userStatsRepository;

    private Random rng = new Random();
    private Boolean first = false;

    @PostMapping("/all")
    public List<User> getAll(@RequestBody HashMap<String, String> json) {
        return userRepository.findAll();
    }

    @PostMapping("/add")
    public Map<String, String> addUser(@RequestBody HashMap<String, String> json) {
        String username = json.get("username");
        String id = null;

        boolean bool = true;
        while (bool) {
            id = String.valueOf(rng.nextInt(999999999));
            bool = userRepository.existsByUserIdAndName(id, username);
        }

        String token = Cryptography.createToken(20);
        User u = new User(id, username, Cryptography.hash(token));
        userRepository.insert(u);
        userStatsRepository.insert(new UserStats(id, username, "0", "0", "0", "0"));

        return Map.of("userID", id,
                "token", token,
                "JWT", JSONWebToken.issue(u));

    }

    @PostMapping("/login")
    public Map<String, String> loginUser(@RequestBody HashMap<String, String> json) {
        String username = json.get("username");
        String id = json.get("userID");

        if (userRepository.existsByUserIdAndNameAndToken(id, username, Cryptography.hash(json.get("token")))) {
            return Map.of("JWT", JSONWebToken.issue(userRepository.findByUserIdAndName(id, username)));
        }else{
            return null;
        }
    }
}
