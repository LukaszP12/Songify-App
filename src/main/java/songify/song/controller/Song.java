package songify.song.controller;

import lombok.Builder;

@Builder
public record Song(String song, String artist) {
}
