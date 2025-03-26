package feature;

import com.songify.SongifyApplication;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = SongifyApplication.class)
@Testcontainers
@ActiveProfiles("integration")
public class HappyPathIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        postgreSQLContainer.getJdbcUrl();
    }

    @Test
    public void f() {

    }

}
