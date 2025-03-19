package com.songify.domain.crud;


import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.SongRequestDto;
import com.songify.domain.crud.dto.SongDto;
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
        Long artistId = song.artistId();
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ArtistNotFoundException(artistId));
        Song newSong = new Song(song.name(),
                artist.getName(),
                song.releaseDate(),
                song.duration(),
                song.language());
        log.info("adding new song: " + song);
        // zapytanie do serwisu songs.com/validate?songName=song
        Song savedSong = songRepository.save(newSong);
        return new SongDto(savedSong.getId(), savedSong.getName(), new GenreDto(
                savedSong.getGenre().getId(),
                savedSong.getGenre().getName()
        ));
    }

    Song addSongAndGetEntity(final SongRequestDto songDto) {
        SongLanguage language = songDto.language();

        Artist artist = artistRepository.findById(songDto.artistId()).orElseThrow(() -> new ArtistNotFoundException(songDto.artistId()));
        Song song = new Song(songDto.name(),artist.getName(), songDto.releaseDate(), songDto.duration(), language);
        log.info("adding new song: " + song);
        return songRepository.save(song);
    }

}
