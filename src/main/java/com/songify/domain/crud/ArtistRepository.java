package com.songify.domain.crud;

import com.songify.domain.crud.song.dto.ArtistDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;


import java.util.Set;

interface ArtistRepository extends Repository<Artist, Long> {
    ArtistDto save(Artist artist);

    Set<Artist> findAll();

    Set<Artist> findAll(Pageable pageable);
}
