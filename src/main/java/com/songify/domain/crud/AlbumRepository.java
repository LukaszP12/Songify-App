package com.songify.domain.crud;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface AlbumRepository extends Repository<Album, Long> {

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
    Optional<AlbumInfo> findById(@Param("id") Long id);

    @Query("""
            select a from Album a
            join fetch a.songs songs on a.id
            join fetch a.artists artists
            where a.id = :id 
            """)
    Optional<AlbumInfo> findAlbumByIdWithSongsAndArtists(Long id);

}
