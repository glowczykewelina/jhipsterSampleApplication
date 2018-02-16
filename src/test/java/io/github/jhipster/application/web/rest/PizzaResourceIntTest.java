package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Pizza;
import io.github.jhipster.application.repository.PizzaRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PizzaResource REST controller.
 *
 * @see PizzaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class PizzaResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_BASE_PRICE = 1L;
    private static final Long UPDATED_BASE_PRICE = 2L;

    private static final Boolean DEFAULT_IS_VEGAN = false;
    private static final Boolean UPDATED_IS_VEGAN = true;

    private static final Boolean DEFAULT_IS_SPICY = false;
    private static final Boolean UPDATED_IS_SPICY = true;

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPizzaMockMvc;

    private Pizza pizza;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PizzaResource pizzaResource = new PizzaResource(pizzaRepository);
        this.restPizzaMockMvc = MockMvcBuilders.standaloneSetup(pizzaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pizza createEntity(EntityManager em) {
        Pizza pizza = new Pizza()
            .name(DEFAULT_NAME)
            .basePrice(DEFAULT_BASE_PRICE)
            .isVegan(DEFAULT_IS_VEGAN)
            .isSpicy(DEFAULT_IS_SPICY)
            .number(DEFAULT_NUMBER);
        return pizza;
    }

    @Before
    public void initTest() {
        pizza = createEntity(em);
    }

    @Test
    @Transactional
    public void createPizza() throws Exception {
        int databaseSizeBeforeCreate = pizzaRepository.findAll().size();

        // Create the Pizza
        restPizzaMockMvc.perform(post("/api/pizzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pizza)))
            .andExpect(status().isCreated());

        // Validate the Pizza in the database
        List<Pizza> pizzaList = pizzaRepository.findAll();
        assertThat(pizzaList).hasSize(databaseSizeBeforeCreate + 1);
        Pizza testPizza = pizzaList.get(pizzaList.size() - 1);
        assertThat(testPizza.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPizza.getBasePrice()).isEqualTo(DEFAULT_BASE_PRICE);
        assertThat(testPizza.isIsVegan()).isEqualTo(DEFAULT_IS_VEGAN);
        assertThat(testPizza.isIsSpicy()).isEqualTo(DEFAULT_IS_SPICY);
        assertThat(testPizza.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void createPizzaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pizzaRepository.findAll().size();

        // Create the Pizza with an existing ID
        pizza.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPizzaMockMvc.perform(post("/api/pizzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pizza)))
            .andExpect(status().isBadRequest());

        // Validate the Pizza in the database
        List<Pizza> pizzaList = pizzaRepository.findAll();
        assertThat(pizzaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPizzas() throws Exception {
        // Initialize the database
        pizzaRepository.saveAndFlush(pizza);

        // Get all the pizzaList
        restPizzaMockMvc.perform(get("/api/pizzas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pizza.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].basePrice").value(hasItem(DEFAULT_BASE_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].isVegan").value(hasItem(DEFAULT_IS_VEGAN.booleanValue())))
            .andExpect(jsonPath("$.[*].isSpicy").value(hasItem(DEFAULT_IS_SPICY.booleanValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));
    }

    @Test
    @Transactional
    public void getPizza() throws Exception {
        // Initialize the database
        pizzaRepository.saveAndFlush(pizza);

        // Get the pizza
        restPizzaMockMvc.perform(get("/api/pizzas/{id}", pizza.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pizza.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.basePrice").value(DEFAULT_BASE_PRICE.intValue()))
            .andExpect(jsonPath("$.isVegan").value(DEFAULT_IS_VEGAN.booleanValue()))
            .andExpect(jsonPath("$.isSpicy").value(DEFAULT_IS_SPICY.booleanValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingPizza() throws Exception {
        // Get the pizza
        restPizzaMockMvc.perform(get("/api/pizzas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePizza() throws Exception {
        // Initialize the database
        pizzaRepository.saveAndFlush(pizza);
        int databaseSizeBeforeUpdate = pizzaRepository.findAll().size();

        // Update the pizza
        Pizza updatedPizza = pizzaRepository.findOne(pizza.getId());
        // Disconnect from session so that the updates on updatedPizza are not directly saved in db
        em.detach(updatedPizza);
        updatedPizza
            .name(UPDATED_NAME)
            .basePrice(UPDATED_BASE_PRICE)
            .isVegan(UPDATED_IS_VEGAN)
            .isSpicy(UPDATED_IS_SPICY)
            .number(UPDATED_NUMBER);

        restPizzaMockMvc.perform(put("/api/pizzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPizza)))
            .andExpect(status().isOk());

        // Validate the Pizza in the database
        List<Pizza> pizzaList = pizzaRepository.findAll();
        assertThat(pizzaList).hasSize(databaseSizeBeforeUpdate);
        Pizza testPizza = pizzaList.get(pizzaList.size() - 1);
        assertThat(testPizza.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPizza.getBasePrice()).isEqualTo(UPDATED_BASE_PRICE);
        assertThat(testPizza.isIsVegan()).isEqualTo(UPDATED_IS_VEGAN);
        assertThat(testPizza.isIsSpicy()).isEqualTo(UPDATED_IS_SPICY);
        assertThat(testPizza.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingPizza() throws Exception {
        int databaseSizeBeforeUpdate = pizzaRepository.findAll().size();

        // Create the Pizza

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPizzaMockMvc.perform(put("/api/pizzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pizza)))
            .andExpect(status().isCreated());

        // Validate the Pizza in the database
        List<Pizza> pizzaList = pizzaRepository.findAll();
        assertThat(pizzaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePizza() throws Exception {
        // Initialize the database
        pizzaRepository.saveAndFlush(pizza);
        int databaseSizeBeforeDelete = pizzaRepository.findAll().size();

        // Get the pizza
        restPizzaMockMvc.perform(delete("/api/pizzas/{id}", pizza.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pizza> pizzaList = pizzaRepository.findAll();
        assertThat(pizzaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pizza.class);
        Pizza pizza1 = new Pizza();
        pizza1.setId(1L);
        Pizza pizza2 = new Pizza();
        pizza2.setId(pizza1.getId());
        assertThat(pizza1).isEqualTo(pizza2);
        pizza2.setId(2L);
        assertThat(pizza1).isNotEqualTo(pizza2);
        pizza1.setId(null);
        assertThat(pizza1).isNotEqualTo(pizza2);
    }
}
