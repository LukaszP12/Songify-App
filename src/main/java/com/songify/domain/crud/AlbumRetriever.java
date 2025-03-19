package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumDtoWithArtistsAndSongs;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.SongDto;
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

    public AlbumDtoWithArtistsAndSongs findAlbumByIdWithArtistsAndSongs(final Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException("" + id));

        Set<Artist> artists = album.getArtists();
        Set<Song> songs = album.getSongs();

        AlbumDto albumDto = new AlbumDto(album.getId(), album.getTitle());
        Set<ArtistDto> artistDtos = artists.stream()
                .map(artist -> new ArtistDto(artist.getId(), artist.getName()))
                .collect(Collectors.toSet());

        Set<SongDto> songDtos = songs.stream()
                .map(song -> new SongDto(song.getId(), song.getName()))
                .collect(Collectors.toSet());

        return new AlbumDtoWithArtistsAndSongs(
                albumDto,
                artistDtos,
                songDtos
        );
    }
}
