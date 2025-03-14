package com.songify.song.infrastructure.controller;

import com.songify.artist.domain.service.ArtistSaver;
import com.songify.song.domain.entities.Song;
import com.songify.song.domain.repository.SongRepository;
import com.songify.song.domain.service.SongAdder;
import com.songify.song.domain.service.SongDeleter;
import com.songify.song.domain.service.SongRetriever;
import com.songify.song.domain.service.SongUpdater;
import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.CreateSongResponseDto;
import com.songify.song.infrastructure.controller.dto.response.DeleteSongResponseDto;
import com.songify.song.infrastructure.controller.dto.response.GetAllSongsResponseDto;
import com.songify.song.infrastructure.controller.dto.response.GetSongResponseDto;
import com.songify.song.infrastructure.controller.dto.response.PartiallyUpdateSongResponseDto;
import com.songify.song.infrastructure.controller.dto.response.UpdateSongResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import static com.songify.song.domain.service.SongMapper.mapFromCreateSongRequestDtoToSong;
import static com.songify.song.domain.service.SongMapper.mapFromPartiallyUpdateSongRequestDtoToSong;
import static com.songify.song.domain.service.SongMapper.mapFromSongToCreateSongResponseDto;
import static com.songify.song.domain.service.SongMapper.mapFromSongToDeleteSongResponseDto;
import static com.songify.song.domain.service.SongMapper.mapFromSongToGetAllSongsResponseDto;
import static com.songify.song.domain.service.SongMapper.mapFromSongToGetSongResponseDto;
import static com.songify.song.domain.service.SongMapper.mapFromSongToPartiallyUpdateSongResponseDto;
import static com.songify.song.domain.service.SongMapper.mapFromSongToUpdateSongResponseDto;
import static com.songify.song.domain.service.SongMapper.mapFromUpdateSongRequestDtoToSongDto;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Log4j2
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongRestController {
    private final SongRepository songRepository;

    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;
    private final ArtistSaver artistSaver;

    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@PageableDefault(page = 0, size = 10) @RequestParam(required = false) Pageable pageable) {
        List<Song> allSongs = songRetriever.findAll(pageable);
        GetAllSongsResponseDto response = mapFromSongToGetAllSongsResponseDto(allSongs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongById(@PathVariable Long id,
                                                          @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        Song song = songRetriever.findSongById(id);
        GetSongResponseDto response = mapFromSongToGetSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid CreateSongRequestDto request) {
        Song song = mapFromCreateSongRequestDtoToSong(request);

        artistSaver.printArtistsSize();
        artistSaver.printSaverName();
        artistSaver.addArtist(song.getArtist());
        artistSaver.printArtistsSize();

        Song savedSong = songAdder.addSong(song);
        CreateSongResponseDto body = mapFromSongToCreateSongResponseDto(savedSong);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Long id) {
        songDeleter.deleteSongById(id);
        log.info("You deleted song with id: " + id);
        DeleteSongResponseDto body = mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Long id,
                                                        @RequestBody @Valid UpdateSongRequestDto request) {
        Song oldSong = songRetriever.findSongById(id);

        Song newSong = mapFromUpdateSongRequestDtoToSongDto(request);
        songUpdater.updateById(id, newSong);

        log.info("Updated song with id: " + id
                + " with new name: " + newSong
                + " and previously oldSong name: " + oldSong.getName());
        UpdateSongResponseDto body = mapFromSongToUpdateSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(
            @PathVariable Long id,
            @RequestBody @Valid PartiallyUpdateSongRequestDto request) {
        Song updatedSong = mapFromPartiallyUpdateSongRequestDtoToSong(request);
        Song savedSong = songUpdater.updatePartiallyById(id, updatedSong);
        PartiallyUpdateSongResponseDto body = mapFromSongToPartiallyUpdateSongResponseDto(savedSong);
        return ResponseEntity.ok(body);
    }
}
