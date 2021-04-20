package at.markus.EmoteGuesserBackend.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserStatsResponse {
    private int streakGame;
    private int timeGame;
    private String username;

}
