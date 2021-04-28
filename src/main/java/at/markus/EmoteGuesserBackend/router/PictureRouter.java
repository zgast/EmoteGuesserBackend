package at.markus.EmoteGuesserBackend.router;

import at.markus.EmoteGuesserBackend.Routes;
import at.markus.EmoteGuesserBackend.security.Keys;
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
@RequestMapping(Routes.MAIN + Routes.VERSION + "pictures/")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PictureRouter {
    Random rng = new Random();

    @Autowired
    PictureRepository pictureRepository;


    @PostMapping("/random")
    public Pictures getRandom(@RequestBody HashMap<String, String> json) {
        List<Pictures> pictures = pictureRepository.findAll();
        return pictures.get(rng.nextInt(pictures.size()));
    }

    @PostMapping("/all")
    public List<Pictures> getAll(@RequestBody HashMap<String, String> json) {
        return pictureRepository.findAll();
    }


}
