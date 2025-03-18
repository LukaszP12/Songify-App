package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SongCrudFacade {

    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;

    public List<SongDto> findAllSongs(Pageable pageable) {
        return songRetriever.findAll(pageable);
    }

    public SongDto findSongById(Long id) {
        Song song = songRetriever.findSongById(id);
        return SongDto.builder()
                .name(song.getName())
                .build();
    }

    public SongDto addSong(Song song) {
        songAdder.addSong(song);
        return new SongDto(song.getId(), song.getName());
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
