package bgawel.testing.fruit.controllers;

import static bgawel.testing.JsonTestUtils.convertObjectToJsonContent;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import bgawel.testing.config.ApplicationContext;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContext.class})
@WebAppConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("twoFruits.xml")
@ActiveProfiles({"int-test"})
public class FruitControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void getAllFruits() throws Exception {
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
        mockMvc.perform(get("/fruit/2"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id", is(2)))
            .andExpect(jsonPath("$.name", is("apple")));
    }

    @Test
    public void reportErrorIfFruitCannotBeFound() throws Exception {
        mockMvc.perform(get("/fruit/3"))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.message", notNullValue()));
    }

    @Test
    @ExpectedDatabase(value="banana.xml", table="FRUIT", query="select * from FRUIT where NAME='banana'",
            assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void createNewFruit() throws Exception {
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
        mockMvc.perform(post("/fruit")
                .contentType("application/json")
                .content(convertObjectToJsonContent(new NewFruitCommand("mango-padding"))))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.field", is("nameWithPadding")))
            .andExpect(jsonPath("$.message", notNullValue()));
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
}
