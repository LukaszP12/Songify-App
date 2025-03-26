package com.songify.infrastructure.crud.song;

import com.songify.domain.crud.Song;
import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import com.songify.infrastructure.songplayer.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.songplayer.controller.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.songplayer.controller.dto.response.CreateSongResponseDto;
import com.songify.infrastructure.songplayer.controller.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.songplayer.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.songplayer.controller.dto.response.GetSongResponseDto;
import com.songify.infrastructure.songplayer.controller.dto.response.PartiallyUpdateSongResponseDto;
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

import static com.songify.infrastructure.songplayer.controller.SongControllerMapper.mapFromPartiallyUpdateSongRequestDtoToSong;
import static com.songify.infrastructure.songplayer.controller.SongControllerMapper.mapFromSongDtoToPartiallyUpdateSongResponseDto;
import static com.songify.infrastructure.songplayer.controller.SongControllerMapper.mapFromSongToCreateSongResponseDto;
import static com.songify.infrastructure.songplayer.controller.SongControllerMapper.mapFromSongToDeleteSongResponseDto;
import static com.songify.infrastructure.songplayer.controller.SongControllerMapper.mapFromSongToGetAllSongsResponseDto;
import static com.songify.infrastructure.songplayer.controller.SongControllerMapper.mapFromSongToGetSongResponseDto;
import static com.songify.infrastructure.songplayer.controller.SongControllerMapper.mapFromSongToUpdateSongResponseDto;
import static com.songify.infrastructure.songplayer.controller.SongControllerMapper.mapFromUpdateSongRequestDtoToSongDto;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongController {

    private final SongifyCrudFacade songFacade;

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
        SongDto songById = songFacade.findSongDtoById(id);
        GetSongResponseDto response = mapFromSongToGetSongResponseDto(songById);
        return ResponseEntity.ok(response);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid SongRequestDto request) {
        SongDto savedSongDto = songFacade.addSong(request);
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

//    @DeleteMapping("/{id}/genre")
//    public ResponseEntity<DeleteSongResponseDto> deleteSongWithGenre(@PathVariable Long id) {
//        songFacade.deleteSongAndGenreById(id);
//        log.info("You deleted song with id: " + id);
//        DeleteSongResponseDto body = mapFromSongToDeleteSongResponseDto(id);
//        return ResponseEntity.ok(body);
//    }

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
