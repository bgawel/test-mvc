package bgawel.testing.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WebContext.class, ServiceContext.class, PersistenceContext.class})
@ComponentScan("bgawel.testing.fruit")
public class ApplicationContext {
}
