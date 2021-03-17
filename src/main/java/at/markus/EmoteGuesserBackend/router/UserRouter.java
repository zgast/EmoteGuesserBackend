package at.markus.EmoteGuesserBackend.router;

import at.markus.EmoteGuesserBackend.document.User;
import at.markus.EmoteGuesserBackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/EmoteGuesser/users/")
public class UserRouter {
    private String accessKey = "usahd9720hd23807d23g2h8ofbgv24876fv24809fb2480fbn0ofhb<o83rg32ad78ashd8co89awhf9ofhaloifhf789obvaoisdzbvösadcvbasipf";

    @Autowired
    UserRepository userRepository;

    @PostMapping("/all")
    public List<User> getAll(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(accessKey)) {
            return userRepository.findAll();
        }
        return null;
    }

    @PostMapping("/add")
    public void addUser(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(accessKey)){
            User u = new User(userRepository.findAll().size()+1,json.get("ID"),json.get("name"));
            userRepository.insert(u);
        }
    }
    @PostMapping("/check")
    public HashMap<String,String> checkUser(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(accessKey)){
            List<User> users = userRepository.findAll();

            for(User user : users){
                if(user.getUserId().equals(json.get("ID"))&&(user.getName().equals(json.get("name")))){
                    return (HashMap<String, String>) Map.of("user","unavailable");
                }
            }

            return (HashMap<String, String>) Map.of("user","available");
        }
        return null;
    }

}
