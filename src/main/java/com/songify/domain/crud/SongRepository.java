package com.songify.domain.crud;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@org.springframework.stereotype.Repository
public interface SongRepository extends Repository<Song, Long> {

    @Query("""
            SELECT s FROM Song s
            join fetch s.genre
            """)
    List<Song> findAll(Pageable pageable);

    @Query("SELECT s FROM Song s WHERE s.id =:id")
    Optional<Song> findById(Long id);

    List<Song> findAllByArtistEqualsIgnoreCaseOrderById(String artist);

//    @Modifying
//    @Query("DELETE FROM Song s WHERE s.id = :id")
//    void deleteById(Long id);

    @Modifying
    @Query("UPDATE Song s SET s.name = :#{#newSong.name}, s.artist = :#{#newSong.artist} WHERE s.id = :id")
    void updateById(Long id, Song newSong);

    Song save(Song song);

    boolean existsById(Long id);

    void deleteById(Long id);

    @Transactional
    @Modifying
    @Query("delete from Song s where s.id in :ids")
    int deleteByIdIn(Set<Long> ids);
}
