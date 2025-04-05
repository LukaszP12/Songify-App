package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.AlbumInfo;
import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.infrastructure.songplayer.controller.dto.response.GetAllAlbumsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/albums")
class AlbumController {

    private final SongifyCrudFacade songifyCrudFacade;

    @GetMapping
    ResponseEntity<GetAllAlbumsResponseDto> getAllAlbums() {
        Set<AlbumDto> allAlbums = songifyCrudFacade.findAllAlbums();
        return ResponseEntity.ok(new GetAllAlbumsResponseDto(allAlbums));
    }

    @PostMapping
    ResponseEntity<AlbumDto> postAlbum(@RequestBody AlbumRequestDto albumRequestDto) {
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSongs(albumRequestDto);
        return ResponseEntity.ok(albumDto);
    }

    @GetMapping("/{albumId}")
    ResponseEntity<AlbumInfo> getAlbumWithArtistsAndSongs(@PathVariable Long albumId) {
        AlbumInfo albumByIdWithArtistsAndSongs = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumId);
        return ResponseEntity.ok(albumByIdWithArtistsAndSongs);
    }

    @PutMapping("/{albumId}/songs/{songId}")
    ResponseEntity<AlbumDto> addSongToAlbum(@PathVariable Long albumId, @PathVariable Long songId) {
        AlbumDto albumDto = songifyCrudFacade.addSongToAlbum(albumId, songId);
        return ResponseEntity.ok(albumDto);
    }
}
