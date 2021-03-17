package at.markus.EmoteGuesserBackend.router;

import at.markus.EmoteGuesserBackend.document.User;
import at.markus.EmoteGuesserBackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/EmoteGuesser/users/")
public class UserRouter {
    private String accessKey = "usahd9720hd23807d23g2h8ofbgv24876fv24809fb2480fbn0ofhb<o83rg32ad78ashd8co89awhf9ofhaloifhf789obvaoisdzbvösadcvbasipf";

    @Autowired
    UserRepository userRepository;

    @PostMapping("/user")
    public List<User> getAll(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(accessKey)) {
            return userRepository.findAll();
        }
        return null;
    }

    @PostMapping("/set")
    public void setNewUser(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(accessKey)){
            User u = new User(userRepository.findAll().size()+1,json.get("ID"),json.get("name"));
            userRepository.insert(u);
        }
    }
}
