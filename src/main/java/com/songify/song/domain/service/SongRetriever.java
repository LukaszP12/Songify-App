package com.songify.song.domain.service;

import com.songify.song.domain.entities.Song;
import com.songify.song.domain.model.SongNotFoundException;
import com.songify.song.domain.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class SongRetriever {

    private final SongRepository songRepository;

    public List<Song> findAll(Pageable pageable) {
        log.info("retrieving all songs: ");
        return songRepository.findAll(pageable);
    }

//    public List<Song> findAllLimitedBy(Integer limit) {
//        return songRepository
//                .findAll()
//                .stream()
//                .limit(limit)
//                .collect(Collectors.toList());
//    }

    public Song findSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException("" + id));
    }

    public void existsById(Long id) {
        if (!songRepository.existsById(id)) {
            throw new SongNotFoundException("" + id);
        }
    }
}
