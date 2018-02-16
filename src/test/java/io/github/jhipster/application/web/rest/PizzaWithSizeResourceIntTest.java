package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.PizzaWithSize;
import io.github.jhipster.application.repository.PizzaWithSizeRepository;
import io.github.jhipster.application.service.PizzaWithSizeService;
import io.github.jhipster.application.service.dto.PizzaWithSizeDTO;
import io.github.jhipster.application.service.mapper.PizzaWithSizeMapper;
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

import io.github.jhipster.application.domain.enumeration.Size;
/**
 * Test class for the PizzaWithSizeResource REST controller.
 *
 * @see PizzaWithSizeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class PizzaWithSizeResourceIntTest {

    private static final Size DEFAULT_SIZE = Size.MEDIUM;
    private static final Size UPDATED_SIZE = Size.LARGE;

    @Autowired
    private PizzaWithSizeRepository pizzaWithSizeRepository;

    @Autowired
    private PizzaWithSizeMapper pizzaWithSizeMapper;

    @Autowired
    private PizzaWithSizeService pizzaWithSizeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPizzaWithSizeMockMvc;

    private PizzaWithSize pizzaWithSize;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PizzaWithSizeResource pizzaWithSizeResource = new PizzaWithSizeResource(pizzaWithSizeService);
        this.restPizzaWithSizeMockMvc = MockMvcBuilders.standaloneSetup(pizzaWithSizeResource)
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
    public static PizzaWithSize createEntity(EntityManager em) {
        PizzaWithSize pizzaWithSize = new PizzaWithSize()
            .size(DEFAULT_SIZE);
        return pizzaWithSize;
    }

    @Before
    public void initTest() {
        pizzaWithSize = createEntity(em);
    }

    @Test
    @Transactional
    public void createPizzaWithSize() throws Exception {
        int databaseSizeBeforeCreate = pizzaWithSizeRepository.findAll().size();

        // Create the PizzaWithSize
        PizzaWithSizeDTO pizzaWithSizeDTO = pizzaWithSizeMapper.toDto(pizzaWithSize);
        restPizzaWithSizeMockMvc.perform(post("/api/pizza-with-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pizzaWithSizeDTO)))
            .andExpect(status().isCreated());

        // Validate the PizzaWithSize in the database
        List<PizzaWithSize> pizzaWithSizeList = pizzaWithSizeRepository.findAll();
        assertThat(pizzaWithSizeList).hasSize(databaseSizeBeforeCreate + 1);
        PizzaWithSize testPizzaWithSize = pizzaWithSizeList.get(pizzaWithSizeList.size() - 1);
        assertThat(testPizzaWithSize.getSize()).isEqualTo(DEFAULT_SIZE);
    }

    @Test
    @Transactional
    public void createPizzaWithSizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pizzaWithSizeRepository.findAll().size();

        // Create the PizzaWithSize with an existing ID
        pizzaWithSize.setId(1L);
        PizzaWithSizeDTO pizzaWithSizeDTO = pizzaWithSizeMapper.toDto(pizzaWithSize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPizzaWithSizeMockMvc.perform(post("/api/pizza-with-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pizzaWithSizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PizzaWithSize in the database
        List<PizzaWithSize> pizzaWithSizeList = pizzaWithSizeRepository.findAll();
        assertThat(pizzaWithSizeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPizzaWithSizes() throws Exception {
        // Initialize the database
        pizzaWithSizeRepository.saveAndFlush(pizzaWithSize);

        // Get all the pizzaWithSizeList
        restPizzaWithSizeMockMvc.perform(get("/api/pizza-with-sizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pizzaWithSize.getId().intValue())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())));
    }

    @Test
    @Transactional
    public void getPizzaWithSize() throws Exception {
        // Initialize the database
        pizzaWithSizeRepository.saveAndFlush(pizzaWithSize);

        // Get the pizzaWithSize
        restPizzaWithSizeMockMvc.perform(get("/api/pizza-with-sizes/{id}", pizzaWithSize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pizzaWithSize.getId().intValue()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPizzaWithSize() throws Exception {
        // Get the pizzaWithSize
        restPizzaWithSizeMockMvc.perform(get("/api/pizza-with-sizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePizzaWithSize() throws Exception {
        // Initialize the database
        pizzaWithSizeRepository.saveAndFlush(pizzaWithSize);
        int databaseSizeBeforeUpdate = pizzaWithSizeRepository.findAll().size();

        // Update the pizzaWithSize
        PizzaWithSize updatedPizzaWithSize = pizzaWithSizeRepository.findOne(pizzaWithSize.getId());
        // Disconnect from session so that the updates on updatedPizzaWithSize are not directly saved in db
        em.detach(updatedPizzaWithSize);
        updatedPizzaWithSize
            .size(UPDATED_SIZE);
        PizzaWithSizeDTO pizzaWithSizeDTO = pizzaWithSizeMapper.toDto(updatedPizzaWithSize);

        restPizzaWithSizeMockMvc.perform(put("/api/pizza-with-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pizzaWithSizeDTO)))
            .andExpect(status().isOk());

        // Validate the PizzaWithSize in the database
        List<PizzaWithSize> pizzaWithSizeList = pizzaWithSizeRepository.findAll();
        assertThat(pizzaWithSizeList).hasSize(databaseSizeBeforeUpdate);
        PizzaWithSize testPizzaWithSize = pizzaWithSizeList.get(pizzaWithSizeList.size() - 1);
        assertThat(testPizzaWithSize.getSize()).isEqualTo(UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingPizzaWithSize() throws Exception {
        int databaseSizeBeforeUpdate = pizzaWithSizeRepository.findAll().size();

        // Create the PizzaWithSize
        PizzaWithSizeDTO pizzaWithSizeDTO = pizzaWithSizeMapper.toDto(pizzaWithSize);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPizzaWithSizeMockMvc.perform(put("/api/pizza-with-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pizzaWithSizeDTO)))
            .andExpect(status().isCreated());

        // Validate the PizzaWithSize in the database
        List<PizzaWithSize> pizzaWithSizeList = pizzaWithSizeRepository.findAll();
        assertThat(pizzaWithSizeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePizzaWithSize() throws Exception {
        // Initialize the database
        pizzaWithSizeRepository.saveAndFlush(pizzaWithSize);
        int databaseSizeBeforeDelete = pizzaWithSizeRepository.findAll().size();

        // Get the pizzaWithSize
        restPizzaWithSizeMockMvc.perform(delete("/api/pizza-with-sizes/{id}", pizzaWithSize.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PizzaWithSize> pizzaWithSizeList = pizzaWithSizeRepository.findAll();
        assertThat(pizzaWithSizeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PizzaWithSize.class);
        PizzaWithSize pizzaWithSize1 = new PizzaWithSize();
        pizzaWithSize1.setId(1L);
        PizzaWithSize pizzaWithSize2 = new PizzaWithSize();
        pizzaWithSize2.setId(pizzaWithSize1.getId());
        assertThat(pizzaWithSize1).isEqualTo(pizzaWithSize2);
        pizzaWithSize2.setId(2L);
        assertThat(pizzaWithSize1).isNotEqualTo(pizzaWithSize2);
        pizzaWithSize1.setId(null);
        assertThat(pizzaWithSize1).isNotEqualTo(pizzaWithSize2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PizzaWithSizeDTO.class);
        PizzaWithSizeDTO pizzaWithSizeDTO1 = new PizzaWithSizeDTO();
        pizzaWithSizeDTO1.setId(1L);
        PizzaWithSizeDTO pizzaWithSizeDTO2 = new PizzaWithSizeDTO();
        assertThat(pizzaWithSizeDTO1).isNotEqualTo(pizzaWithSizeDTO2);
        pizzaWithSizeDTO2.setId(pizzaWithSizeDTO1.getId());
        assertThat(pizzaWithSizeDTO1).isEqualTo(pizzaWithSizeDTO2);
        pizzaWithSizeDTO2.setId(2L);
        assertThat(pizzaWithSizeDTO1).isNotEqualTo(pizzaWithSizeDTO2);
        pizzaWithSizeDTO1.setId(null);
        assertThat(pizzaWithSizeDTO1).isNotEqualTo(pizzaWithSizeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pizzaWithSizeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pizzaWithSizeMapper.fromId(null)).isNull();
    }
}
