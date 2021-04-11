package at.markus.EmoteGuesserBackend.router;

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
    private String accessKey = "usahd9720hd23807d23g2h8ofbgv24876fv24809fb2480fbn0ofhb<o83rg32ad78ashd8co89awhf9ofhaloifhf789obvaoisdzbvösadcvbasipf";

    @Autowired
    UserRepository userRepository;

    private Random rng = new Random();

    @PostMapping("/all")
    public List<User> getAll(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(accessKey)) {
            return userRepository.findAll();
        }
        return null;
    }

    @PostMapping("/add")
    public Map<String, String> addUser(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(accessKey)){
            var bool = true;
            List<User> users = userRepository.findAll();
            int ID = 69420;

            while(bool){
                 ID = rng.nextInt(9999);

                for(User user : users){
                    if(!(user.getUserId().equals(String.valueOf(ID))&&(user.getName().equals(json.get("username"))))){
                        bool = false;
                    }
                }
            }
            String token = createToken(20);
            User u = new User(String.valueOf(ID),json.get("username"),token);
            userRepository.insert(u);

            return Map.of("userID",String.valueOf(ID),
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
