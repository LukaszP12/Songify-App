package songify.song.dto.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SongRequestDto(
        @NotNull(message = "songName must not be null")
        @NotEmpty(message = "songName must not be empty")
        String songName,

        @NotNull(message = "artistName must not be null")
        @NotEmpty(message = "artistName must not be empty")
        String artistName
) {
}