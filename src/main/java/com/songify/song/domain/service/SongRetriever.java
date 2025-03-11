package com.songify.song.domain.service;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class SongRetriever {

    private final SongRepository songRepository;

    public Map<Integer, Song> findAll() {
        log.info("retrieving all songs: ");
        return songRepository.findAll();
    }

    public Map<Integer, Song> findAllLimitedBy(Integer limit) {
        return songRepository.findAll().entrySet()
                .stream()
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
