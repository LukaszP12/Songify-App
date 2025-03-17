package com.songify.domain.crud.song;

import com.songify.domain.crud.song.dto.SongDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
class SongRetriever {

    private final SongRepository songRepository;

    private final List<Song> songs = new ArrayList<>();

    public List<SongDto> findAll(Pageable pageable) {
        log.info("retrieving all songs: ");
        return songRepository.findAll(pageable)
                .stream()
                .map(song -> SongDto.builder()
                        .id(song.getId())
                        .name(song.getName())
                        .build()).collect(Collectors.toList());
    }

//    public List<Song> findAllLimitedBy(Integer limit) {
//        return songRepository
//                .findAll()
//                .stream()
//                .limit(limit)
//                .collect(Collectors.toList());
//    }

    Song findSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException("" + id));
    }

    public List<Song> findByArtistEquals() {
        return songRepository.findAllByArtistEqualsIgnoreCaseOrderById("Ariana Grande");
//                .orElseThrow(() -> new SongNotFoundException("Song with id " + "arianagrande" + " not found"));
    }

    public void existsById(Long id) {
        if (!songRepository.existsById(id)) {
            throw new SongNotFoundException("" + id);
        }
    }

    public Song compareSongs() {
        Song song1 = songRepository.findById(1L)
                .orElseThrow(() -> new SongNotFoundException("Song with id " + 1L + " not found"));

        log.info(song1);
        songs.add(song1);

        Song song2 = new Song("Tik Tok", "Ariana Grande");
        log.info(song2);
        songs.add(song2);

        for (Song song : songs) {
            log.info(songs.get(0).equals(songs.get(1)));
        }
        return song1;
    }
}
