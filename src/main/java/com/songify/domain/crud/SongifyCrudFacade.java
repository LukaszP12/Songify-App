package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Transactional
public class SongifyCrudFacade {

    private final SongRetriever songRetriever;
    private final SongUpdater songUpdater;
    private final SongDeleter songDeleter;
    private final SongAdder songAdder;
    private final ArtistAdder artistAdder;
    private final GenreAdder genreAdder;
    private final AlbumAdder albumAdder;

    public ArtistDto addArtist(ArtistRequestDto dto) {
        return artistAdder.addArtist(dto.name());
    }

    public GenreDto addGenre(GenreRequestDto dto) {
        return genreAdder.addGenre(dto.name());
    }

    public AlbumDto addAlbumWithSong(AlbumRequestDto albumRequestDto) {
        return albumAdder.addAlbum(albumRequestDto.songId(),
                albumRequestDto.title(),
                albumRequestDto.releaseDate());
    }

    public SongDto addSong(final SongRequestDto dto) {
        return songAdder.addSong(dto);
    }

    public List<SongDto> findAllSongs(Pageable pageable) {
        return songRetriever.findAll(pageable);
    }

    public SongDto findSongById(Long id) {
        Song song = songRetriever.findSongById(id);
        return SongDto.builder()
                .name(song.getName())
                .build();
    }

    public void deleteSongById(Long id) {
        songDeleter.deleteSongById(id);
    }

    public void updateSongById(Long id, SongDto newSongDto) {
        songRetriever.existsById(id);
        Song songValidatedAndReadyToUpdate = new Song(newSongDto.name());
        songUpdater.updateById(id, songValidatedAndReadyToUpdate);
    }

    public SongDto updateSongPartiallyById(Long id, Song songFromRequest) {
        songRetriever.existsById(id);
        Song songFromDatabase = songRetriever.findSongById(id);
        Song toSave = new Song();
        if (songFromRequest.getName() != null) {
            toSave.setName(songFromRequest.getName());
        } else {
            toSave.setName(songFromDatabase.getName());
        }

        songUpdater.updatePartiallyById(id, toSave);
        return SongDto.builder()
                .id(toSave.getId())
                .name(toSave.getName())
                .build();
    }
}
