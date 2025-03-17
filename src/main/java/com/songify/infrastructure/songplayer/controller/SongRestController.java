package com.songify.infrastructure.songplayer.controller;

import com.songify.domain.crud.song.Song;
import com.songify.domain.crud.song.SongCrudFacade;
import com.songify.domain.crud.song.dto.SongDto;
import com.songify.infrastructure.songplayer.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.songplayer.controller.dto.response.PartiallyUpdateSongResponseDto;
import com.songify.infrastructure.songplayer.controller.dto.request.CreateSongRequestDto;
import com.songify.infrastructure.songplayer.controller.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.songplayer.controller.dto.response.CreateSongResponseDto;
import com.songify.infrastructure.songplayer.controller.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.songplayer.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.songplayer.controller.dto.response.GetSongResponseDto;
import com.songify.infrastructure.songplayer.controller.dto.response.UpdateSongResponseDto;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


import static com.songify.domain.crud.song.SongDomainMapper.mapFromCreateSongRequestDtoToSong;
import static com.songify.infrastructure.songplayer.controller.SongControllerMapper.mapFromPartiallyUpdateSongRequestDtoToSong;
import static com.songify.infrastructure.songplayer.controller.SongControllerMapper.mapFromSongDtoToPartiallyUpdateSongResponseDto;
import static com.songify.infrastructure.songplayer.controller.SongControllerMapper.mapFromSongToCreateSongResponseDto;
import static com.songify.infrastructure.songplayer.controller.SongControllerMapper.mapFromSongToDeleteSongResponseDto;
import static com.songify.infrastructure.songplayer.controller.SongControllerMapper.mapFromSongToGetAllSongsResponseDto;
import static com.songify.infrastructure.songplayer.controller.SongControllerMapper.mapFromSongToGetSongResponseDto;
import static com.songify.infrastructure.songplayer.controller.SongControllerMapper.mapFromSongToUpdateSongResponseDto;
import static com.songify.infrastructure.songplayer.controller.SongControllerMapper.mapFromUpdateSongRequestDtoToSongDto;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Log4j2
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongRestController {

    private final SongCrudFacade songFacade;

    @GetMapping
    ResponseEntity<GetAllSongsResponseDto> getAllSongs(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        List<SongDto> allSongs = songFacade.findAllSongs(pageable);
        GetAllSongsResponseDto response = mapFromSongToGetAllSongsResponseDto(allSongs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongById(@PathVariable Long id,
                                                          @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        SongDto songById = songFacade.findSongById(id);
//        log.info(songFacade.findByArtistEquals());
        GetSongResponseDto response = mapFromSongToGetSongResponseDto(songById);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/test")
//    public ResponseEntity<GetSongResponseDto> test() {
//        songRetriever.compareSongs();
//        return ResponseEntity.ok().build();
//    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid CreateSongRequestDto request) {
        Song savedSong = mapFromCreateSongRequestDtoToSong(request);
        SongDto savedSongDto = songFacade.addSong(savedSong);
        CreateSongResponseDto body = mapFromSongToCreateSongResponseDto(savedSongDto);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Long id) {
        songFacade.deleteSongById(id);
        log.info("You deleted song with id: " + id);
        DeleteSongResponseDto body = mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Long id,
                                                        @RequestBody @Valid UpdateSongRequestDto request) {
        SongDto newSongDto = mapFromUpdateSongRequestDtoToSongDto(request);
        songFacade.updateSongById(id, newSongDto);
        UpdateSongResponseDto body = mapFromSongToUpdateSongResponseDto(newSongDto);

        log.info("Updated song with id: " + id
                + " with new name: " + newSongDto.name());
        return ResponseEntity.ok(body);
    }

//    @PutMapping("/dumb")
//    public void update() {
//        songUpdater.someComplicatedLogic();
//    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(
            @PathVariable Long id,
            @RequestBody @Valid PartiallyUpdateSongRequestDto request) {
        SongDto updateSong = mapFromPartiallyUpdateSongRequestDtoToSong(request);
        SongDto savedSong = songFacade.updateSongPartiallyById(id, new Song(updateSong.name()));
        PartiallyUpdateSongResponseDto body = mapFromSongDtoToPartiallyUpdateSongResponseDto(savedSong);
        return ResponseEntity.ok(body);
    }
}
