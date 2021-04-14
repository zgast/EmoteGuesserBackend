package at.markus.EmoteGuesserBackend.router;

import at.markus.EmoteGuesserBackend.Keys;
import at.markus.EmoteGuesserBackend.document.Pictures;
import at.markus.EmoteGuesserBackend.repositories.PictureRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
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
    Random rng= new Random();

    @Autowired
    PictureRepository pictureRepository;

    @PostMapping("/all")
    public List<Pictures> getAll(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(Keys.stats)) {
            return pictureRepository.findAll();
        }
        return null;
    }

    @PostMapping("/random")
    public Pictures getRandom(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(Keys.normal)) {
            List<Pictures> pictures = pictureRepository.findAll();
            return pictures.get(rng.nextInt(pictures.size()));
        }
        return null;
    }

    @PostMapping("/add")
    public void setNewUser(@RequestBody HashMap<String,String> json){
        if(json.get("key").equals(Keys.stats)){
            pictureRepository.insert(new Pictures(json.get("URL"),json.get("name")));
        }
    }
}
