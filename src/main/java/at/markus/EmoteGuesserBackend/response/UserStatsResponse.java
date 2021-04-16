package at.markus.EmoteGuesserBackend.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStatsResponse {
    private int streakGame;
    private int timeGame;
    private String username;
}
