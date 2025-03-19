package com.songify.domain.crud;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface AlbumRepository extends Repository<Album, Long> {
    Optional<Album> findById(Long id);

    Album save(Album album);

}