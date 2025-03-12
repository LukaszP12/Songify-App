package com.songify.song.infrastructure.controller;

import com.songify.artist.domain.service.ArtistSaver;
import com.songify.song.domain.entities.Song;
import com.songify.song.domain.model.SongNotFoundException;
import com.songify.song.domain.repository.SongRepository;
import com.songify.song.domain.service.SongAdder;
import com.songify.song.domain.service.SongMapper;
import com.songify.song.domain.service.SongRetriever;
import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.CreateSongResponseDto;
import com.songify.song.infrastructure.controller.dto.response.GetAllSongsResponseDto;
import com.songify.song.infrastructure.controller.dto.response.GetSongResponseDto;
import com.songify.song.infrastructure.controller.dto.response.PartiallyUpdateSongResponseDto;
import com.songify.song.infrastructure.controller.dto.response.UpdateSongResponseDto;
import com.songify.song.infrastructure.controller.error.ErrorSongResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Log4j2
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongRestController {
    private final SongRepository songRepository;

    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final ArtistSaver artistSaver;

    //    @GetMapping(params = "myParam=myValue")
    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
//        throw new RuntimeException();
        List<Song> allSongs = songRetriever.findAll();
        if (limit != null) {
            List<Song> allLimitedBy = songRetriever.findAllLimitedBy(limit);
            GetAllSongsResponseDto response = new GetAllSongsResponseDto(allLimitedBy);
            return ResponseEntity.ok(response);
        }
        GetAllSongsResponseDto response = SongMapper.mapFromSongToGetAllSongsResponseDto(allSongs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongById(@PathVariable Long id,
                                                          @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        List<Song> allSongs = songRetriever.findAll();

        boolean present = allSongs.stream()
                .filter(song -> song.getId().equals(id))
                .findFirst()
                .isPresent();

        if (!present) {
            throw new SongNotFoundException("Song with id " + id + " not found");
        }

        Song song = allSongs
                .stream()
                .filter(song1 -> song1.getId().equals(id))
                .findFirst().get();
        GetSongResponseDto response = SongMapper.mapFromSongToGetSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid CreateSongRequestDto request) {
        Song song = SongMapper.mapFromCreateSongRequestDtoToSong(request);

        artistSaver.printArtistsSize();
        artistSaver.printSaverName();
        artistSaver.addArtist(song.getArtist());
        artistSaver.printArtistsSize();

        songAdder.addSong(song);
        CreateSongResponseDto body = SongMapper.mapFromSongToCreateSongResponseDto(song);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Integer id) {
        List<Song> allSongs = songRetriever.findAll();
        if (!allSongs.contains(id)) {
            throw new SongNotFoundException("Song with id " + id + "not found");
        }
        allSongs.remove(id);
        log.info("You deleted song with id: " + id);
        return ResponseEntity.ok(new ErrorSongResponseDto("You deleted song with id: " + id, HttpStatus.OK));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Integer id,
                                                        @RequestBody @Valid UpdateSongRequestDto request) {
        List<Song> allSongs = songRetriever.findAll();
        if (!allSongs.contains(id)) {
            throw new SongNotFoundException("Song with id " + id + "not found");
        }
        Song oldSong = allSongs.get(id);
        Song newSong = SongMapper.mapFromUpdateSongRequestDtoToSongDto(request);
        allSongs.add(id, newSong);
        log.info("Updated song with id: " + id
                + " with new name: " + newSong
                + " and previously oldSong name: " + oldSong.getName());
        return ResponseEntity.ok(new UpdateSongResponseDto(newSong.getName(), newSong.getArtist()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(
            @PathVariable Integer id,
            @RequestBody @Valid PartiallyUpdateSongRequestDto request) {
        List<Song> allSongs = songRetriever.findAll();
        if (!allSongs.contains(id)) {
            throw new SongNotFoundException("Song with id " + id + " not found");
        }
        Song songFromDatabase = allSongs.get(id);
        Song.SongBuilder builder = Song.builder();
        if (request.songName() != null) {
            builder.name(request.songName());
            log.info("partially updated song name");
        } else {
            builder.name(songFromDatabase.getName());
        }
        if (request.artistName() != null) {
            builder.artist(request.artistName());
            log.info("partially updated artist name");
        } else {
            builder.artist(songFromDatabase.getArtist());
        }
        Song updatedSong = builder.build();
        allSongs.add(id, updatedSong);
        log.info("Partially updated song with id: " + id +
                " with oldSongName: " + songFromDatabase.getName() + " to newSongName: " + updatedSong.getName() +
                " oldArtist: " + songFromDatabase.getArtist() + " to newArtist: " + updatedSong.getArtist());
        return ResponseEntity.ok(new PartiallyUpdateSongResponseDto(updatedSong.getName(), updatedSong.getArtist()));
    }
}
