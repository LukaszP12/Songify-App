package com.songify.domain.crud;

import org.springframework.data.repository.Repository;

public interface ArtistRepository extends Repository<Artist, Long> {

    Artist save(Artist artist);

}
