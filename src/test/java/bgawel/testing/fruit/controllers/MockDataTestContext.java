package bgawel.testing.fruit.controllers;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import bgawel.testing.fruit.usecases.FruitUseCases;

@Configuration
@Profile("!int-test")
public class MockDataTestContext {

    @Bean
    public FruitUseCases fruitUseCases() {
        return Mockito.mock(FruitUseCases.class);
    }
}
