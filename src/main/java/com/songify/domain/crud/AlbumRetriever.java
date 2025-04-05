package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
class AlbumRetriever {

    private final AlbumRepository albumRepository;

    AlbumInfo findAlbumByIdWithArtistsAndSongs(final Long id) {
        return albumRepository.findAlbumByIdWithSongsAndArtists(id)
                .orElseThrow(() -> new AlbumNotFoundException(id));
    }

    long countArtistsByAlbumId(final Long albumId) {
        return findById(albumId)
                .getArtists()
                .size();
    }

    Set<Album> findAlbumsByArtistId(final Long artistId) {
        return albumRepository.findAllAlbumsByArtistId(artistId);
    }

    Set<AlbumDto> findAlbumsDtoByArtistId(final Long artistId) {
        return albumRepository.findAllAlbumsByArtistId(artistId)
                .stream().map(
                        album -> new AlbumDto(album.getId(),
                                album.getTitle(),
                                album.getSongsIds()))
                .collect(Collectors.toSet());
    }

    Album findById(final Long albumId) {
        return albumRepository.findById(albumId)
                .orElseThrow(
                        () -> new AlbumNotFoundException(albumId)
                );
    }

    AlbumDto findDtoById(Long albumId) {
        Album AlbumById = findById(albumId);
        return new AlbumDto(
                AlbumById.getId(),
                AlbumById.getTitle(),
                AlbumById.getSongsIds());
    }

    Set<AlbumDto> findAll() {
        return albumRepository.findAll()
                .stream()
                .map(album -> new AlbumDto(album.getId(),
                        album.getTitle(),
                        album.getSongsIds()))
                .collect(Collectors.toSet());
    }
}
