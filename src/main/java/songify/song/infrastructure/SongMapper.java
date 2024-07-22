package songify.song.infrastructure;

import org.springframework.http.HttpStatus;
import songify.song.domain.model.Song;
import songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import songify.song.infrastructure.controller.dto.response.CreateSongResponseDto;
import songify.song.infrastructure.controller.dto.response.DeleteSongResponseDto;
import songify.song.infrastructure.controller.dto.response.GetAllSongsResponseDto;
import songify.song.infrastructure.controller.dto.response.GetSongResponseDto;
import songify.song.infrastructure.controller.dto.response.PartiallyUpdateSongResponseDto;
import songify.song.infrastructure.controller.dto.response.UpdateSongResponseDto;

import java.util.Map;

public class SongMapper {

    public static Song mapFromCreateSongRequestDtoToSong(CreateSongRequestDto dto) {
        return new Song(dto.songName(), dto.artistName());
    }

    public static Song mapFromUpdateSongRequestDtoToSong(UpdateSongRequestDto dto) {
        String newSongName = dto.songName();
        String newArtist = dto.artistName();
        return new Song(newSongName, newArtist);
    }

    public static Song mapFromPartiallyUpdateSongRequestDtoToSong(PartiallyUpdateSongRequestDto dto) {
        return new Song(dto.songName(), dto.artist());
    }

    public static CreateSongResponseDto mapFromSongToCreateSongResponseDto(Song song) {
        return new CreateSongResponseDto(song);
    }

    public static DeleteSongResponseDto mapFromSongToDeleteSongResponseDto(Integer id) {
        return new DeleteSongResponseDto("You deleted song with id: " + id, HttpStatus.OK);
    }

    public static UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(Song newSong) {
        return new UpdateSongResponseDto(newSong.name(), newSong.artist());
    }

    public static PartiallyUpdateSongResponseDto mapFromSongToPartiallyUpdateSongResponseDto(Song updatedSong) {
        return new PartiallyUpdateSongResponseDto(updatedSong.name(), updatedSong.artist());
    }

    public static GetSongResponseDto mapFromSongToGetSongResponseDto(Song song) {
        return new GetSongResponseDto(song);
    }

    public static GetAllSongsResponseDto mapFromSongToGetAllSongsResponseDto(Map<Integer, Song> database) {
        return new GetAllSongsResponseDto(database);
    }
}
