package com.songify.domain.crud;

import org.springframework.data.repository.Repository;

import java.util.Set;

public interface ArtistRepository extends Repository<Artist, Long> {
    Artist findById(Long id);

    Artist save(Artist artist);

    Set<Artist> findAll();
}
