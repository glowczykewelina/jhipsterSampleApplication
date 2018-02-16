package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.PizzaOrder;
import io.github.jhipster.application.repository.PizzaOrderRepository;
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
 * Test class for the PizzaOrderResource REST controller.
 *
 * @see PizzaOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class PizzaOrderResourceIntTest {

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    @Autowired
    private PizzaOrderRepository pizzaOrderRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPizzaOrderMockMvc;

    private PizzaOrder pizzaOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PizzaOrderResource pizzaOrderResource = new PizzaOrderResource(pizzaOrderRepository);
        this.restPizzaOrderMockMvc = MockMvcBuilders.standaloneSetup(pizzaOrderResource)
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
    public static PizzaOrder createEntity(EntityManager em) {
        PizzaOrder pizzaOrder = new PizzaOrder()
            .total(DEFAULT_TOTAL);
        return pizzaOrder;
    }

    @Before
    public void initTest() {
        pizzaOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createPizzaOrder() throws Exception {
        int databaseSizeBeforeCreate = pizzaOrderRepository.findAll().size();

        // Create the PizzaOrder
        restPizzaOrderMockMvc.perform(post("/api/pizza-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pizzaOrder)))
            .andExpect(status().isCreated());

        // Validate the PizzaOrder in the database
        List<PizzaOrder> pizzaOrderList = pizzaOrderRepository.findAll();
        assertThat(pizzaOrderList).hasSize(databaseSizeBeforeCreate + 1);
        PizzaOrder testPizzaOrder = pizzaOrderList.get(pizzaOrderList.size() - 1);
        assertThat(testPizzaOrder.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createPizzaOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pizzaOrderRepository.findAll().size();

        // Create the PizzaOrder with an existing ID
        pizzaOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPizzaOrderMockMvc.perform(post("/api/pizza-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pizzaOrder)))
            .andExpect(status().isBadRequest());

        // Validate the PizzaOrder in the database
        List<PizzaOrder> pizzaOrderList = pizzaOrderRepository.findAll();
        assertThat(pizzaOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPizzaOrders() throws Exception {
        // Initialize the database
        pizzaOrderRepository.saveAndFlush(pizzaOrder);

        // Get all the pizzaOrderList
        restPizzaOrderMockMvc.perform(get("/api/pizza-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pizzaOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())));
    }

    @Test
    @Transactional
    public void getPizzaOrder() throws Exception {
        // Initialize the database
        pizzaOrderRepository.saveAndFlush(pizzaOrder);

        // Get the pizzaOrder
        restPizzaOrderMockMvc.perform(get("/api/pizza-orders/{id}", pizzaOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pizzaOrder.getId().intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPizzaOrder() throws Exception {
        // Get the pizzaOrder
        restPizzaOrderMockMvc.perform(get("/api/pizza-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePizzaOrder() throws Exception {
        // Initialize the database
        pizzaOrderRepository.saveAndFlush(pizzaOrder);
        int databaseSizeBeforeUpdate = pizzaOrderRepository.findAll().size();

        // Update the pizzaOrder
        PizzaOrder updatedPizzaOrder = pizzaOrderRepository.findOne(pizzaOrder.getId());
        // Disconnect from session so that the updates on updatedPizzaOrder are not directly saved in db
        em.detach(updatedPizzaOrder);
        updatedPizzaOrder
            .total(UPDATED_TOTAL);

        restPizzaOrderMockMvc.perform(put("/api/pizza-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPizzaOrder)))
            .andExpect(status().isOk());

        // Validate the PizzaOrder in the database
        List<PizzaOrder> pizzaOrderList = pizzaOrderRepository.findAll();
        assertThat(pizzaOrderList).hasSize(databaseSizeBeforeUpdate);
        PizzaOrder testPizzaOrder = pizzaOrderList.get(pizzaOrderList.size() - 1);
        assertThat(testPizzaOrder.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingPizzaOrder() throws Exception {
        int databaseSizeBeforeUpdate = pizzaOrderRepository.findAll().size();

        // Create the PizzaOrder

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPizzaOrderMockMvc.perform(put("/api/pizza-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pizzaOrder)))
            .andExpect(status().isCreated());

        // Validate the PizzaOrder in the database
        List<PizzaOrder> pizzaOrderList = pizzaOrderRepository.findAll();
        assertThat(pizzaOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePizzaOrder() throws Exception {
        // Initialize the database
        pizzaOrderRepository.saveAndFlush(pizzaOrder);
        int databaseSizeBeforeDelete = pizzaOrderRepository.findAll().size();

        // Get the pizzaOrder
        restPizzaOrderMockMvc.perform(delete("/api/pizza-orders/{id}", pizzaOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PizzaOrder> pizzaOrderList = pizzaOrderRepository.findAll();
        assertThat(pizzaOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PizzaOrder.class);
        PizzaOrder pizzaOrder1 = new PizzaOrder();
        pizzaOrder1.setId(1L);
        PizzaOrder pizzaOrder2 = new PizzaOrder();
        pizzaOrder2.setId(pizzaOrder1.getId());
        assertThat(pizzaOrder1).isEqualTo(pizzaOrder2);
        pizzaOrder2.setId(2L);
        assertThat(pizzaOrder1).isNotEqualTo(pizzaOrder2);
        pizzaOrder1.setId(null);
        assertThat(pizzaOrder1).isNotEqualTo(pizzaOrder2);
    }
}
