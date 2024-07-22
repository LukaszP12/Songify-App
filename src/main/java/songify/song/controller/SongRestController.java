package songify.song.controller;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import songify.song.dto.request.PartiallyUpdateSongRequestDto;
import songify.song.dto.request.CreateSongRequestDto;
import songify.song.dto.request.UpdateSongRequestDto;
import songify.song.dto.response.CreateSongResponseDto;
import songify.song.dto.response.DeleteSongResponseDto;
import songify.song.dto.response.GetSongResponseDto;
import songify.song.dto.response.PartiallyUpdateSongResponseDto;
import songify.song.dto.response.GetAllSongsResponseDto;
import songify.song.dto.response.UpdateSongResponseDto;
import songify.song.error.SongNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/songs")
public class SongRestController {

    Map<Integer, Song> database = new HashMap<>(Map.of(
            1, new Song("shawnmendes song1", "Shawn Mendes"),
            2, new Song("shawnmendes song2", "Shawn Mendes"),
            3, new Song("shawnmendes song3", "Shawn Mendes"),
            4, new Song("shawnmendes song4", "Shawn Mendes")
    ));

    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            Map<Integer, Song> limitedMap = database.entrySet()
                    .stream()
                    .limit(limit)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            GetAllSongsResponseDto response = new GetAllSongsResponseDto(limitedMap);
            return ResponseEntity.ok(response);
        }
        GetAllSongsResponseDto response = new GetAllSongsResponseDto(database);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongById(@PathVariable Integer id,
                                                          @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        Song song = database.get(id);
        if (song == null) {
            return ResponseEntity.notFound().build();
        }
        GetSongResponseDto response = new GetSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid CreateSongRequestDto request) {
        Song song = new Song(request.songName(), request.artistName());
        log.info("adding new song: " + song);
        database.put(database.size() + 1, song);
        return ResponseEntity.ok(new CreateSongResponseDto(song));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Integer id) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("Song with id " + id + " not found");
        }
        database.remove(id);
        return ResponseEntity.ok(new DeleteSongResponseDto("You deleted song with id: " + id, HttpStatus.OK));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Integer id,
                                                        @RequestBody @Valid UpdateSongRequestDto request) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("Song with id " + id + " not found ");
        }
        String newSongName = request.songName();
        String newArtist = request.artistName();
        Song newSong = new Song(newSongName, newArtist);
        Song oldSong = database.put(id, newSong);
        log.info("Updated song with id: " + id +
                "with oldSongName: " + oldSong.song() + " to newSongName " + newSongName +
                " oldArtist: " + oldSong.artist() + " to newArtist: " + newArtist);
        return ResponseEntity.ok(new UpdateSongResponseDto(newSongName, newArtist));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(
            @PathVariable Integer id,
            @RequestBody PartiallyUpdateSongRequestDto request) {

        if (!database.containsKey(id)) {
            throw new SongNotFoundException("Song with id " + id + " not found ");
        }
        Song song = database.get(id);
        Song.SongBuilder builder = Song.builder();
        if (request.songName() != null) {
            builder.song(request.songName());
            log.info("partially updated song name ");
        } else {
            builder.song(song.song());
        }
        if (request.artist() != null) {
            builder.artist(request.artist());
            log.info("partially updated artist ");
        } else {
            builder.artist(song.artist());
        }
        Song updatedSong = builder.build();
        database.put(id, updatedSong);
        return ResponseEntity.ok(new PartiallyUpdateSongResponseDto(updatedSong.song(), updatedSong.artist()));
    }

}
