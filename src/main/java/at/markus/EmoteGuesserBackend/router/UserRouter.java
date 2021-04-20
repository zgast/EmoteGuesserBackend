package at.markus.EmoteGuesserBackend.router;

import at.markus.EmoteGuesserBackend.Keys;
import at.markus.EmoteGuesserBackend.document.User;
import at.markus.EmoteGuesserBackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/EmoteGuesser/users/")
public class UserRouter {
    @Autowired
    UserRepository userRepository;

    private Random rng = new Random();
    private Boolean first = false;

    @PostMapping("/all")
    public List<User> getAll(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(Keys.stats)) {
            return userRepository.findAll();
        }
        return null;
    }

    @PostMapping("/add")
    public Map<String, String> addUser(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(Keys.normal)){
            String username = json.get("username");
            String id = null;

            boolean bool = true;
            while (bool){
                id = String.valueOf(rng.nextInt(999999999));
                String finalId = id;
                bool = userRepository.findAll().stream().
                        anyMatch(User -> User.getUserId().equals(finalId)&&User.getName().equals(username));

            }

            String token = createToken(20);
            User u = new User(id,json.get("username"),token);
            userRepository.insert(u);

            return Map.of("userID", id,
                    "token",token);
        }
        return null;
    }

    public static String createToken(int length){
        final String allowedChars = "0123456789abcdefghijklmnopqrstuvwABCDEFGHIJKLMNOP";
        SecureRandom random = new SecureRandom();
        StringBuilder pass = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            pass.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }
        return pass.toString();
    }


}
