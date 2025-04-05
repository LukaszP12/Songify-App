package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Transactional
class SongAssigner {

    private final AlbumRetriever albumRetriever;
    private final SongRetriever songRetriever;

    AlbumDto assignSongToAlbum(Long albumId, Long songId) {
        Album album = albumRetriever.findById(albumId);
        Song songById = songRetriever.findSongById(songId);
        album.addSongToAlbum(songById);
        return new AlbumDto(
                albumId,
                album.getTitle(),
                album.getSongsIds()
        );
    }
}
