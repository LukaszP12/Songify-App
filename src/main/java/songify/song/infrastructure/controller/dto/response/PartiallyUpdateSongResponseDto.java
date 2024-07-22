package songify.song.infrastructure.controller.dto.response;

public record PartiallyUpdateSongResponseDto(
        String songName,
        String artist
) {
}
