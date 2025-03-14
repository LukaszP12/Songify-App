package com.songify.song.domain.service;

import com.songify.song.domain.entities.Song;
import com.songify.song.domain.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class SongUpdater {

    private final SongRepository songRepository;
    private final SongRetriever songRetriever;

    public void updateById(Long id, Song newSong) {
        songRetriever.existsById(id);
        songRepository.updateById(id, newSong);
    }

    public Song updatePartiallyById(Long id, Song updatedSong) {
        songRetriever.existsById(id);
        Song songFromDatabase = songRetriever.findSongById(id);
        Song.SongBuilder builder = Song.builder();
        if (updatedSong.getName() != null) {
            builder.name(updatedSong.getName());
            log.info("partially updated song name");
        } else {
            builder.name(songFromDatabase.getName());
        }
        if (updatedSong.getArtist() != null) {
            builder.artist(updatedSong.getArtist());
            log.info("partially updated artist name");
        } else {
            builder.artist(songFromDatabase.getArtist());
        }
        ;
        log.info("Partially updated song with id: " + id +
                " with oldSongName: " + songFromDatabase.getName() + " to newSongName: " + updatedSong.getName() +
                " oldArtist: " + songFromDatabase.getArtist() + " to newArtist: " + updatedSong.getArtist());
        Song toSave = builder.build();
        updateById(id,toSave);
        return toSave;
    }
}
