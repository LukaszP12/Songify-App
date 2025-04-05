package com.songify.domain.crud;


import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SongAdder {

    private final SongRepository songRepository;
    private final GenreAssigner genreAssigner;

    SongDto addSong(final SongRequestDto songDto) {
        SongLanguageDto language = songDto.language();
        SongLanguage songLanguage = SongLanguage.valueOf(language.name());
        Song newSong = new Song(songDto.name(),
                songDto.releaseDate(),
                songDto.duration(),
                songLanguage);
        log.info("adding new song: " + songDto.name());
        // zapytanie do serwisu songs.com/validate?songName=song
        Song savedSong = songRepository.save(newSong);
        genreAssigner.assignDefaultGenreToSong(newSong.getId());
        return new SongDto(savedSong.getId(), savedSong.getName(), new GenreDto(
                savedSong.getGenre().getId(),
                savedSong.getGenre().getName()
        ));
    }

    Song addSongAndGetEntity(final SongRequestDto songDto) {
        SongLanguageDto language = songDto.language();
        SongLanguage songLanguage = SongLanguage.valueOf(language.name());
        Song song = new Song(songDto.name(), songDto.releaseDate(), songDto.duration(), songLanguage);
        log.info("adding new song: " + song);
        return songRepository.save(song);
    }

}
