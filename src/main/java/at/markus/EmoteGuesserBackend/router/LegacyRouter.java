package at.markus.EmoteGuesserBackend.router;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/EmoteGuesser/legacy")
public class LegacyRouter {

    @GetMapping("/get")
    public Map<String, String> getRandom() {
        return Map.of(
                "picture", "https://blog.cdn.own3d.tv/resize=fit:crop,height:400,width:600/Dm78XXKlTkGJdpOVvIkn",
                "name", "pepeHands"
        );
    }
}