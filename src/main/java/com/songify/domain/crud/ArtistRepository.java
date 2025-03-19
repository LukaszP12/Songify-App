package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.Set;

@org.springframework.stereotype.Repository
public interface ArtistRepository extends Repository<Artist, Long> {

    Optional<Artist> findById(Long id);

    Artist save(Artist artist);

    Set<Artist> findAll();

    Set<Artist> findAll(Pageable pageable);

    void deleteById(Long artistId);

}
