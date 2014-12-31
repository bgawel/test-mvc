package bgawel.testing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import bgawel.testing.exception.controllers.GlobalUnexpectedExceptionHandler;

@Configuration
@EnableWebMvc
@ComponentScan("bgawel.testing.*.controllers")
public class WebContext extends WebMvcConfigurerAdapter {

    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        return new GlobalUnexpectedExceptionHandler();
    }
}
