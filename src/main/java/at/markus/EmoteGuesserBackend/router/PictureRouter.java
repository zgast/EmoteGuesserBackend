package at.markus.EmoteGuesserBackend.router;

import at.markus.EmoteGuesserBackend.document.Pictures;
import at.markus.EmoteGuesserBackend.repositories.PictureRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/EmoteGuesser/pictures/")
@FieldDefaults(level= AccessLevel.PRIVATE)
public class PictureRouter {
    String accessKey = "usahd9720hd23807d23g2h8ofbgv24876fv24809fb2480fbn0ofhb<o83rg32ad78ashd8co89awhf9ofhaloifhf789obvaoisdzbvösadcvbasipf";
    Random rng;

    @Autowired
    PictureRepository pictureRepository;

    @PostMapping("/all")
    public List<Pictures> getAll(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(accessKey)) {
            return pictureRepository.findAll();
        }
        return null;
    }

    @PostMapping("/random")
    public Pictures getRandom(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(accessKey)) {
            rng = new Random();
            return pictureRepository.findAll().get(rng.nextInt(pictureRepository.findAll().size()));
        }
        return null;
    }

    @PostMapping("/set")
    public void setNewUser(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(accessKey)){
            pictureRepository.insert(new Pictures(json.get("URL"),json.get("name")));
        }
    }
}
