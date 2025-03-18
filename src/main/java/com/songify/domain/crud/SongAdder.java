package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
class SongAdder {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    SongDto addSong(final SongRequestDto song) {
        Artist artist = artistRepository.findById(song.artistId());
        Song newSong = new Song(song.name(), artist.getName(), song.releaseDate(), song.duration(), song.language());
        log.info("adding new song: " + song);
        // zapytanie do serwisu songs.com/validate?songName=song
        Song savedSong = songRepository.save(newSong);
        return new SongDto(savedSong.getId(), savedSong.getName());
    }
}
