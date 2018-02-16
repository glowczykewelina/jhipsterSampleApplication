package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.PizzaWithSizeDTO;
import java.util.List;

/**
 * Service Interface for managing PizzaWithSize.
 */
public interface PizzaWithSizeService {

    /**
     * Save a pizzaWithSize.
     *
     * @param pizzaWithSizeDTO the entity to save
     * @return the persisted entity
     */
    PizzaWithSizeDTO save(PizzaWithSizeDTO pizzaWithSizeDTO);

    /**
     * Get all the pizzaWithSizes.
     *
     * @return the list of entities
     */
    List<PizzaWithSizeDTO> findAll();

    /**
     * Get the "id" pizzaWithSize.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PizzaWithSizeDTO findOne(Long id);

    /**
     * Delete the "id" pizzaWithSize.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
