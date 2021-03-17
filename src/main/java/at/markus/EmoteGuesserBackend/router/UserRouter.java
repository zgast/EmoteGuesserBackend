package at.markus.EmoteGuesserBackend.router;

import at.markus.EmoteGuesserBackend.document.User;
import at.markus.EmoteGuesserBackend.repositories.ScoreRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/EmoteGuesser/users/")
public class UserRouter {
    private String accessKey = "usahd9720hd23807d23g2h8ofbgv24876fv24809fb2480fbn0ofhb<o83rg32ad78ashd8co89awhf9ofhaloifhf789obvaoisdzbvösadcvbasipf";

    @Autowired
    ScoreRepository scoreRepository;

    @PostMapping("/user")
    public List<User> getAll(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(accessKey)) {
            return scoreRepository.findAll();
        }
        return null;
    }

    @PostMapping("/set")
    public void setNewUser(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(accessKey)){
            User u = new User(scoreRepository.findAll().size()+1,json.get("ID"),json.get("name"));
            scoreRepository.insert(u);
        }
    }
}
