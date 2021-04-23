package at.markus.EmoteGuesserBackend.router;

import at.markus.EmoteGuesserBackend.security.Cryptography;
import at.markus.EmoteGuesserBackend.security.JSONWebToken;
import at.markus.EmoteGuesserBackend.security.Keys;
import at.markus.EmoteGuesserBackend.document.User;
import at.markus.EmoteGuesserBackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.*;

@RestController
@RequestMapping("/emote-guesser/users/")
public class UserRouter {
    @Autowired
    UserRepository userRepository;

    private Random rng = new Random();
    private Boolean first = false;

    @PostMapping("/all")
    public List<User> getAll(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(Keys.STATS)) {
            return userRepository.findAll();
        }
        return null;
    }

    @PostMapping("/add")
    public Map<String, String> addUser(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(Keys.NORMAL)){
            String username = json.get("username");
            String id = null;

            boolean bool = true;
            while (bool){
                id = String.valueOf(rng.nextInt(999999999));
                bool = userRepository.existsByUserIdAndName(id,username);

            }

            String token = Cryptography.createToken(20);
            User u = new User(id,username, Cryptography.hash(token));
            userRepository.insert(u);

            return Map.of("userID", id,
                    "token",token,
                    "JWT", JSONWebToken.issue(u));
        }
        return null;
    }
}
