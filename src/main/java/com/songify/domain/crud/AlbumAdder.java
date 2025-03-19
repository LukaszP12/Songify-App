package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
class AlbumAdder {

    private final SongRetriever songRetriever;
    private final AlbumRepository albumRepository;

    AlbumDto addAlbum(final Long songId, final String albumName, final Instant releaseDate) {
        Song songById = songRetriever.findSongById(songId);
        Album album = new Album();
        album.setTitle(albumName);
        album.addSongToAlbum(songById);
        album.setReleaseDate(releaseDate);
        albumRepository.save(album);
        return new AlbumDto(album.getId(), album.getTitle());
    }
}
