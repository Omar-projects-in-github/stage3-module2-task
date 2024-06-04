import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = RepositoryTest.TestConfig.class)
public class RepositoryTest {
    private static final String BASE_REPOSITORY = "com.mjc.school.repository.BaseRepository";

    @Autowired
    private GenericApplicationContext ctx;

    @Test
    void contextShouldHave2Repositories() throws ClassNotFoundException {
        var repositories = ctx.getBeansOfType(Class.forName(BASE_REPOSITORY));
        assertEquals(
                2, repositories.size(), "There should be 2 repositories in context: Author and News.");
    }

    @Configuration
    @ComponentScan(basePackages = "com.mjc.school")
    public static class TestConfig {}
}
