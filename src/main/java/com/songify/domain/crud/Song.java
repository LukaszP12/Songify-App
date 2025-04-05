package com.songify.domain.crud;

import com.songify.domain.crud.util.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "song",
        indexes = @Index(
                name = "idx_song_name",
                columnList = "name"
        ))
public class Song extends BaseEntity {

    @Id
    @GeneratedValue(generator = "song_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "song_id_seq",
            sequenceName = "song_id_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private String name;

    private Instant releaseDate;

    private Long duration;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Genre genre;

    @Enumerated(EnumType.STRING)
    private SongLanguage language;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;


    public Song(final String name) {
        this.name = name;
    }

    Song(final String name, final Instant releaseDate, final Long duration, final SongLanguage language) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.language = language;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                ", language=" + language +
                '}';
    }
}
