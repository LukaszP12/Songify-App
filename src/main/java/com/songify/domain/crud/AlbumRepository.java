package com.songify.domain.crud;


import com.songify.domain.crud.song.dto.AlbumDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface AlbumRepository extends Repository<Album, Long> {

    @Modifying
    @Query("delete from Album a where a.id in :ids")
    int deleteByIdIn(Collection<Long> ids);

    Album save(Album album);

    @Query("""
            select a from Album a 
            join fetch a.songs songs
            join fetch a.artists artists 
            join fetch songs.genre g
            where artists.id = :id
            """)
    Set<Album> findAllAlbumsByArtistId(@Param("id") Long id);

    @Query("select a from Album a where a.id = :id")
    Optional<Album> findById(@Param("id") Long id);

    @Query("""
            select a from Album a
            join fetch a.songs songs on a.id
            join fetch a.artists artists
            where a.id = :id 
            """)
    Optional<AlbumInfo> findAlbumByIdWithSongsAndArtists(Long id);

    Optional<AlbumDto> findAlbumById(Long albumId);

    Set<Album> findAll();
}
