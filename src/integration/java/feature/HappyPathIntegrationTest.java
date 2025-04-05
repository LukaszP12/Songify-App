package feature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songify.SongifyApplication;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SongifyApplication.class)
@Testcontainers
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
@ActiveProfiles("integration")
public class HappyPathIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");

    static {
        postgreSQLContainer.start();
    }

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    }

    @Test
    public void happy_path() throws Exception {
// 1. when I go to /songs then I can see no songs
        // given
        // when
        ResultActions perform = mockMvc.perform(get("/songs")
                .contentType(MediaType.APPLICATION_JSON));
        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", empty()));

// 2. when I post to /songs with Song "Till I collapse" then Song "Till I collapse" is returned with id 1
        mockMvc.perform(post("/songs")
                        .content("""
                                {
                                   "name": "Till i collapse",
                                   "releaseDate": "2024-03-15T13:55:21.850Z",
                                   "duration": 0,
                                   "language": "ENGLISH"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songDto.id", is(1)))
                .andExpect(jsonPath("$.songDto.name", is("Till i collapse")))
                .andExpect(jsonPath("$.songDto.genre.id", is(1)))
                .andExpect(jsonPath("$.songDto.genre.name", is("default")));

// 3. when I post to /songs with Song "Lose Yourself" then Song "Lose Yourself" is returned with id 2

        mockMvc.perform(post("/songs")
                        .content("""
                                {
                                   "name": "Lose Yourself",
                                   "releaseDate": "2024-03-15T13:55:21.850Z",
                                   "duration": 0,
                                   "language": "ENGLISH"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songDto.id", is(2)))
                .andExpect(jsonPath("$.songDto.name", is("Lose Yourself")))
                .andExpect(jsonPath("$.songDto.genre.id", is(1)))
                .andExpect(jsonPath("$.songDto.genre.name", is("default")));

//// 4. when I go to /genres then I can see only default genre with id 1

        mockMvc.perform(get("/genres")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genres[0].id", is(1)))
                .andExpect(jsonPath("$.genres[0].name", is("default")));

//// 5. when I post to /genres with Genre "Rap" then Genre "Rap" is returned with id 1
        mockMvc.perform(post("/genres")
                        .content("""
                                {
                                  "name":  "Rap"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Rap")));

//// 6. when I go to /songs/1 then I can see default genre with id 1  and name default
        mockMvc.perform(get("/songs/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songDto.genre.id", is(1)))
                .andExpect(jsonPath("$.songDto.genre.name", is("default")));

//// 7. when I put to /songs/1/genres/1 then Genre with id 1 ("Rap") is added to Song with id 1 ("Till I collapse")
        mockMvc.perform(put("/songs/1/genres/2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("updated")));

//// 8. when I go to /song/1 then I can see "Rap" genre
        mockMvc.perform(get("/songs/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songDto.genre.id", is(2)))
                .andExpect(jsonPath("$.songDto.genre.name", is("Rap")));

//// 9. when I go to /album then I can see no albums
        mockMvc.perform(get("/albums")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.albums", empty()));

//// 10. when I post to /albums with Album "EminemAlbum1" and Song with id 1 then Album "EminemAlbum1" is returned with id 1
        mockMvc.perform(post("/albums")
                        .content("""
                                 {
                                    "title" : "EminemAlbum1",
                                    "releaseDate" : "2024-03-15T13:55:21.850Z",
                                    "songsIds" : [1]
                                 }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("EminemAlbum1")))
                .andExpect(jsonPath("$.songsIds", containsInAnyOrder(1)));

//// 11. when I go to /albums/5 then I can see song with id 1 added to it
        mockMvc.perform(get("/albums/5")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("album with id: 5 not found")))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")));

//// 12. when I post to /artists with Artist "Eminem" then Artist "Eminem" is returned with id 1
        mockMvc.perform(post("/artists")
                        .content("""
                                {
                                    "name" : "Eminem"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Eminem")));

//// 13. when I put to /artists/1/albums/2 then Artist with id 1 ("Eminem") is added to Album with id 1 ("EminemAlbum")
        mockMvc.perform(put("/artists/1/albums/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("probably assigned artist to album")));

//// 14. when I go to /albums/1 then I can see album with single song with id 1 and single artist with id 1
        mockMvc.perform(get("/albums/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs[*].id", containsInAnyOrder(1)))
                .andExpect(jsonPath("$.artists[*].id", containsInAnyOrder(1)));

//// 15. when I put to /albums/1/songs/2 then Song with id 2 ("Lose Yourself") is added to Album with id 1 ("EminemAlbum1")
        mockMvc.perform(put("/albums/1/songs/2")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("EminemAlbum1")))
                .andExpect(jsonPath("$.songsIds[*]", containsInAnyOrder(1, 2)));

//// 16. when I go to /albums/1 then I can see album with 2 songs (id1 and id2)
        mockMvc.perform(get("/albums/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.songs[*].id", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$.artists[*].id", containsInAnyOrder(1)));
    }
}
