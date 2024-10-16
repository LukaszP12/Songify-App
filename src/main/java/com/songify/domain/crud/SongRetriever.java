package com.songify.domain.crud;

import com.songify.domain.crud.song.dto.GenreDto;
import com.songify.domain.crud.song.dto.SongDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Log4j2
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongRetriever {

    final List<Song> songs = new ArrayList<>();
    private final SongRepository songRepository;

    List<SongDto> findAll(Pageable pageable) {
        log.info("retrieving all songs: ");
        return songRepository.findAll(pageable)
                .stream()
                .map(song -> SongDto.builder()
                        .id(song.getId())
                        .name(song.getName())
                        .genre(new GenreDto(song.getGenre().getId(), song.getGenre().getName()))
                        .build())
                .toList();
    }

    SongDto findSongDtoById(Long id) {
        return songRepository.findById(id)
                .map(song -> SongDto.builder()
                        .id(song.getId())
                        .name(song.getName())
                        .build())
                .orElseThrow(() -> new SongNotFoundException("Song with id " + id + " not found"));
    }

    Song findSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException("Song with id " + id + " not found"));
    }

    List<Song> findByArtistEquals(String artist) {
        return songRepository.findAllByArtistEqualsIgnoreCaseOrderById(artist);
    }

    void existsById(Long id) {
        if (!songRepository.existsById(id)) {
            throw new SongNotFoundException("Song with id " + id + " not found");
        }
    }

    Song compareSongs() {
        Song song1 = songRepository.findById(3L)
                .orElseThrow(() -> new SongNotFoundException("Song with id " + 3L + " not found"));

        Song song2 = songRepository.findById(4l)
                .orElseThrow(() -> new SongNotFoundException("Song with id " + 4L + " not found"));

        log.info(song1);
        log.info(song2);
        songs.add(song1);
        songs.add(song2);

        return null;
    }
}
