package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface SongRepository extends Repository<Song, Long> {

    @Query("SELECT s FROM Song s")
    List<Song> findAll(Pageable pageable);

    @Query("SELECT s FROM Song s WHERE s.id =:id")
    Optional<Song> findById(Long id);

    @Query("Select s from Song s WHERE s.artist =:artist")
    Optional<Song> findByArtist(String artist);

    Optional<Song> findByArtistEquals(String artist);

    List<Song> findAllByArtistEqualsIgnoreCaseOrderById(String artist);

    @Modifying
    @Query("DELETE FROM Song s WHERE s.id = :id")
    void deleteById(Long id);

    @Modifying
    @Query("DELETE FROM Song s WHERE s.id in : songsIds")
    void deleteByIdIn(Set<Long> songsIds);

    @Modifying
    @Query("UPDATE Song s SET s.name = :#{#newSong.name}, s.artist = :#{#newSong.artist} WHERE s.id = :id")
    void updateById(Long id, Song newSong);

    Song save(Song song);

    boolean existsById(Long id);

}
