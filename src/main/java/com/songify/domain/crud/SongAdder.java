package com.songify.domain.crud;

import com.songify.domain.crud.song.dto.GenreDto;
import com.songify.domain.crud.song.dto.SongDto;
import com.songify.domain.crud.song.dto.SongLanguageDto;
import com.songify.infrastructure.crud.song.controller.dto.request.SongRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongAdder {

    private final SongRepository songRepository;
    private final GenreAssigner genreAssigner;

    SongDto addSong(final SongRequestDto songDto) {
        SongLanguageDto language = songDto.language();
        SongLanguage songLanguage = SongLanguage.valueOf(language.name());
        Song song = new Song(songDto.name(), songDto.releaseDate(), songDto.duration(), songLanguage);
        log.info("adding new song: " + song);
        Song save = songRepository.save(song);
        genreAssigner.assignDefaultGenreToSong(song.getId());
        return new SongDto(save.getId(), save.getName(), new GenreDto(save.getGenre().getId(), save.getGenre().getName()));
    }
}
