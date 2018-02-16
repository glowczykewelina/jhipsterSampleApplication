package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.PizzaDTO;
import java.util.List;

/**
 * Service Interface for managing Pizza.
 */
public interface PizzaService {

    /**
     * Save a pizza.
     *
     * @param pizzaDTO the entity to save
     * @return the persisted entity
     */
    PizzaDTO save(PizzaDTO pizzaDTO);

    /**
     * Get all the pizzas.
     *
     * @return the list of entities
     */
    List<PizzaDTO> findAll();

    /**
     * Get the "id" pizza.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PizzaDTO findOne(Long id);

    /**
     * Delete the "id" pizza.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
