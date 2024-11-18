package com.songify.domain.crud;

class ArtistInfoTestImpl implements AlbumInfo.ArtistInfo {

    private Artist artist;

    ArtistInfoTestImpl(final Artist artist) {
        this.artist = artist;
    }

    @Override
    public Long getId() {
        return artist.getId();
    }

    @Override
    public String getName() {
        return null;
    }
}
