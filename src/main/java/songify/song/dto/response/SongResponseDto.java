package songify.song.dto.response;

import java.util.Map;

public record SongResponseDto(Map<Integer, String> songs) {
}
