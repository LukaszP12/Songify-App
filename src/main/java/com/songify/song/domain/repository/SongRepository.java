package com.songify.song.domain.repository;


import com.songify.song.domain.entities.Song;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface SongRepository extends Repository<Song, Long> {

    Song save(Song song);

    List<Song> findAll();

    Optional<Song> findById(Long id);

}
