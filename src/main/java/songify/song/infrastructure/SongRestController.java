package songify.song.infrastructure;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
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
import songify.song.domain.model.Song;
import songify.song.domain.model.SongNotFoundException;
import songify.song.domain.service.SongAdder;
import songify.song.domain.service.SongRetreiver;
import songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import songify.song.infrastructure.controller.dto.response.CreateSongResponseDto;
import songify.song.infrastructure.controller.dto.response.DeleteSongResponseDto;
import songify.song.infrastructure.controller.dto.response.GetAllSongsResponseDto;
import songify.song.infrastructure.controller.dto.response.GetSongResponseDto;
import songify.song.infrastructure.controller.dto.response.PartiallyUpdateSongResponseDto;
import songify.song.infrastructure.controller.dto.response.UpdateSongResponseDto;

import java.util.HashMap;
import java.util.Map;

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

    private final SongAdder songAdder;
    private final SongRetreiver songRetreiver;
    private final ArtistSaver artistSaver;

    public SongRestController(SongAdder songAdder, SongRetreiver songRetreiver, ArtistSaver artistSaver) {
        this.songAdder = songAdder;
        this.songRetreiver = songRetreiver;
        this.artistSaver = artistSaver;
    }

    @GetMapping(path = {"/songs1", "/songs2"})
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        Map<Integer, Song> allSongs = songRetreiver.findAll();
        if (limit != null) {
            Map<Integer, Song> limitedMap = songRetreiver.findAllLimitedBy(limit);
            GetAllSongsResponseDto response = new GetAllSongsResponseDto(limitedMap);
            return ResponseEntity.ok(response);
        }
        GetAllSongsResponseDto response = SongMapper.mapFromSongToGetAllSongsResponseDto(database);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongById(@PathVariable Integer id,
                                                          @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        if (!songRetreiver.findAll().containsKey(id)) {
            throw new SongNotFoundException("Song with id " + id + " not found");
        }
        Song song = songRetreiver.findAll().get(id);
        GetSongResponseDto response = new GetSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid CreateSongRequestDto request) {
        // 1. mapowanie z CreateSongResponseDto na obiekt domenowy (Song)
        SongMapper songMapper = new SongMapper();
        Song song = songMapper.mapFromCreateSongRequestDtoToSong(request);

        artistSaver.addArtist(song.artist());
        artistSaver.printArtists();
        artistSaver.printArtistsSize();
        artistSaver.printSaverName();

        // 2. Warstwa logiki biznesowej/serwisów domenowych : wyświetlamy informacje
        songAdder.addSong(song);
        // 3. Warstwę bazodanową: zapisujemy do bazy danych
        songRetreiver.findAll().put(songRetreiver.findAll().size() + 1, song);
        // 4. mapowanie z obiektu domenowego (Song) na DTO CreateSongResponseDto
        CreateSongResponseDto body = SongMapper.mapFromSongToCreateSongResponseDto(song);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Integer id) {
        if (!songRetreiver.findAll().containsKey(id)) {
            throw new SongNotFoundException("Song with id " + id + " not found");
        }
        songRetreiver.findAll().remove(id);
        DeleteSongResponseDto body = SongMapper.mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Integer id,
                                                        @RequestBody @Valid UpdateSongRequestDto request) {
        if (!songRetreiver.findAll().containsKey(id)) {
            throw new SongNotFoundException("Song with id " + id + " not found ");
        }
        String newSongName = request.songName();
        String newArtist = request.artistName();
        Song newSong = new Song(newSongName, newArtist);
        Song oldSong = songRetreiver.findAll().put(id, newSong);
        log.info("Updated song with id: " + id +
                "with oldSongName: " + oldSong.name() + " to newSongName " + newSongName +
                " oldArtist: " + oldSong.artist() + " to newArtist: " + newArtist);
        UpdateSongResponseDto body = SongMapper.mapFromSongToUpdateSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Integer id,
                                                                              @RequestBody PartiallyUpdateSongRequestDto request) {
        if (!songRetreiver.findAll().containsKey(id)) {
            throw new SongNotFoundException("Song with id " + id + " not found");
        }
        Song songFromDatabase = songRetreiver.findAll().get(id);
        Song updatedSong = SongMapper.mapFromPartiallyUpdateSongRequestDtoToSong(request);
        Song.SongBuilder builder = Song.builder();
        if (updatedSong.name() != null) {
            builder.name(updatedSong.name());
        } else {
            builder.name(songFromDatabase.name());
        }
        if (updatedSong.artist() != null) {
            builder.artist(updatedSong.artist());
        } else {
            builder.artist(songFromDatabase.artist());
        }
        songRetreiver.findAll().put(id, updatedSong);
        PartiallyUpdateSongResponseDto body = SongMapper.mapFromSongToPartiallyUpdateSongResponseDto(updatedSong);
        return ResponseEntity.ok(body);
    }

}
