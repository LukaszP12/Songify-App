package com.songify.domain.crud;

import com.songify.domain.crud.song.dto.AlbumDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
class AlbumRetriever {

    private final AlbumRepository albumRepository;

    public AlbumDto findDtoById(final Long albumId){
        return albumRepository.findAlbumById(albumId)
                .orElseThrow(() -> new AlbumNotFoundException("Album with id: " + albumId + " not found "));
    }

    Album findById(final Long  albumId){
        return albumRepository.findById(albumId)
                .orElseThrow(
                        () -> new AlbumNotFoundException("Album with id: " + albumId + " not found")
                );
    }

    AlbumInfo findAlbumByIdWithArtistsAndSongs(final Long id) {
        return albumRepository.findAlbumByIdWithSongsAndArtists(id)
                .orElseThrow(() -> new AlbumNotFoundException("Album with id: " + id + " not found"));
    }

    Set<AlbumDto> findAlbumsDtoByArtistId(final Long artistId){
        return findAlbumsByArtistId(artistId).stream()
                .map(album -> new AlbumDto(album.getId(), album.getTitle(), album.getSongsIds()))
                .collect(Collectors.toSet());
    }

    private Set<Album> findAlbumsByArtistId(final Long artistId) {
        return albumRepository.findAllAlbumsByArtistId(artistId);
    }

    public long countArtistsByAlbumId(final Long albumId) {
        return findById(albumId)
                .getArtists()
                .size();
    }

    public Collection<Object> findAll() {
        return albumRepository.findAll()
                .stream()
                .map(album -> new AlbumDto(album.getId(),album.getTitle(),album.getSongsIds()))
                .collect(Collectors.toSet());
    }

//        Set<Artist> artists = album.getArtists();
//        Set<Song> songs = album.getSongs();
//
//        AlbumDto albumDto = new AlbumDto(id, album.getTitle());
//
//        Set<ArtistDto> artistDtos = artists
//                .stream()
//                .map(
//                        artist -> new ArtistDto(
//                                artist.getId(),
//                                artist.getName())
//                ).collect(Collectors.toSet());
//
//        Set<SongDto> songDtos = songs
//                .stream()
//                .map(
//                        song -> new SongDto(
//                                song.getId(),
//                                song.getName()
//                        )
//                ).collect(Collectors.toSet());

//        return new AlbumDtoWithArtistsAndSongs(
//                albumDto,
//                artistDtos,
//                songDtos
//        );
}
