package songify.song.dto.response;

public record PartiallyUpdateSongResponseDto(
        String songName,
        String artist
) {
}
