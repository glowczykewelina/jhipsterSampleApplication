package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.PizzaOrderDTO;
import java.util.List;

/**
 * Service Interface for managing PizzaOrder.
 */
public interface PizzaOrderService {

    /**
     * Save a pizzaOrder.
     *
     * @param pizzaOrderDTO the entity to save
     * @return the persisted entity
     */
    PizzaOrderDTO save(PizzaOrderDTO pizzaOrderDTO);

    /**
     * Get all the pizzaOrders.
     *
     * @return the list of entities
     */
    List<PizzaOrderDTO> findAll();

    /**
     * Get the "id" pizzaOrder.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PizzaOrderDTO findOne(Long id);

    /**
     * Delete the "id" pizzaOrder.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
