package com.songify.domain.crud;

import com.songify.domain.crud.song.dto.AlbumDto;
import com.songify.domain.crud.song.dto.AlbumDtoWithArtistsAndSongs;
import com.songify.domain.crud.song.dto.ArtistDto;
import com.songify.domain.crud.song.dto.SongDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
class AlbumRetriever {

    private final AlbumRepository albumRepository;

    public AlbumInfo findAlbumByIdWithArtistsAndSongs(final Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException("Album with id: " + id + " not found "));
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
