package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
@Transactional
class AlbumAdder {

    private final SongRetriever songRetriever;
    private final AlbumRepository albumRepository;

    AlbumDto addAlbum(final Set<Long> songIds, final String albumName, final Instant releaseDate) {
//        Song songById = songRetriever.findSongById(songIds);
        Set<Song> songs = songIds.stream()
                .map(songRetriever::findSongById)
                .collect(Collectors.toSet());

        Album album = new Album();
        album.setTitle(albumName);
        album.addSongsToAlbum(songs);
        album.setReleaseDate(releaseDate);
        Album savedAlbum = albumRepository.save(album);
        return new AlbumDto(savedAlbum.getId(), savedAlbum.getTitle(), savedAlbum.getSongsIds());
    }

    Album addAlbum(final String title, final Instant instant) {
        Album album = new Album();
        album.setTitle(title);
        album.setReleaseDate(instant);
        return albumRepository.save(album);
    }

}
