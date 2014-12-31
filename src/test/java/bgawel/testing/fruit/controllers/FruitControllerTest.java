package bgawel.testing.fruit.controllers;

import static bgawel.testing.JsonTestUtils.convertObjectToJsonContent;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import bgawel.testing.config.ServiceContext;
import bgawel.testing.config.WebContext;
import bgawel.testing.fruit.usecases.FruitDTO;
import bgawel.testing.fruit.usecases.FruitUseCases;
import bgawel.testing.fruit.usecases.ValidationException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebContext.class, ServiceContext.class, MockDataTestContext.class})
@WebAppConfiguration
public class FruitControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private FruitUseCases fruitUseCases;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void getAllFruits() throws Exception {
        given(fruitUseCases.getAllFruits()).willReturn(twoFruits());

        mockMvc.perform(get("/fruits"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].name", is("mango")))
            .andExpect(jsonPath("$[1].id", is(2)))
            .andExpect(jsonPath("$[1].name", is("apple")));
    }

    @Test
    public void getOneFruit() throws Exception {
        given(fruitUseCases.getFruit(2L)).willReturn(twoFruits().get(1));

        mockMvc.perform(get("/fruit/2"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id", is(2)))
            .andExpect(jsonPath("$.name", is("apple")));
    }

    @Test
    public void reportErrorIfFruitCannotBeFound() throws Exception {
        given(fruitUseCases.getFruit(3L)).willThrow(new EntityNotFoundException("test message"));

        mockMvc.perform(get("/fruit/3"))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.message", is("test message")));
    }

    @Test
    public void createNewFruit() throws Exception {
        given(fruitUseCases.createFruit(new FruitDTO("banana"))).willReturn(new FruitDTO(3L, "banana"));

        mockMvc.perform(post("/fruit")
                .contentType("application/json")
                .content(convertObjectToJsonContent(new NewFruitCommand("banana-padding"))))
            .andExpect(status().isCreated())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id", is(3)))
            .andExpect(jsonPath("$.name", is("banana")));
    }

    @Test
    public void reportErrorIfMessageWithNewFruitCannotBeProcessed() throws Exception {
        mockMvc.perform(post("/fruit")
                .contentType("application/json")
                .content(convertObjectToJsonContent(new NewFruitCommand("banana123456789012-to"))))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void reportErrorIfValidationOfNewFruitFailed() throws Exception {
        given(fruitUseCases.createFruit(new FruitDTO("banana")))
            .willThrow(new ValidationException("name", "test message"));

        mockMvc.perform(post("/fruit")
                .contentType("application/json")
                .content(convertObjectToJsonContent(new NewFruitCommand("banana-padding"))))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.field", is("nameWithPadding")))
            .andExpect(jsonPath("$.message", is("test message")));
    }

    @Test
    public void reportInternalServerErrorForUnexpectedException() throws Exception {
        given(fruitUseCases.getFruit(2L)).willThrow(new IllegalStateException("description that user shouldn't see"));

        mockMvc.perform(get("/fruit/2"))
            .andExpect(status().isInternalServerError())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.message", is("Unexpected error")));
    }

    @Before
    public void setUp() {
        Mockito.reset(fruitUseCases);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private List<FruitDTO> twoFruits() {
        return Arrays.asList(new FruitDTO(1L, "mango"), new FruitDTO(2L, "apple"));
    }
}
