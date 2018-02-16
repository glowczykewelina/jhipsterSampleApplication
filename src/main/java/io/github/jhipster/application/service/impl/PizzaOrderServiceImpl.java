package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.PizzaOrderService;
import io.github.jhipster.application.domain.PizzaOrder;
import io.github.jhipster.application.repository.PizzaOrderRepository;
import io.github.jhipster.application.service.dto.PizzaOrderDTO;
import io.github.jhipster.application.service.mapper.PizzaOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PizzaOrder.
 */
@Service
@Transactional
public class PizzaOrderServiceImpl implements PizzaOrderService {

    private final Logger log = LoggerFactory.getLogger(PizzaOrderServiceImpl.class);

    private final PizzaOrderRepository pizzaOrderRepository;

    private final PizzaOrderMapper pizzaOrderMapper;

    public PizzaOrderServiceImpl(PizzaOrderRepository pizzaOrderRepository, PizzaOrderMapper pizzaOrderMapper) {
        this.pizzaOrderRepository = pizzaOrderRepository;
        this.pizzaOrderMapper = pizzaOrderMapper;
    }

    /**
     * Save a pizzaOrder.
     *
     * @param pizzaOrderDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PizzaOrderDTO save(PizzaOrderDTO pizzaOrderDTO) {
        log.debug("Request to save PizzaOrder : {}", pizzaOrderDTO);
        PizzaOrder pizzaOrder = pizzaOrderMapper.toEntity(pizzaOrderDTO);
        pizzaOrder = pizzaOrderRepository.save(pizzaOrder);
        return pizzaOrderMapper.toDto(pizzaOrder);
    }

    /**
     * Get all the pizzaOrders.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PizzaOrderDTO> findAll() {
        log.debug("Request to get all PizzaOrders");
        return pizzaOrderRepository.findAll().stream()
            .map(pizzaOrderMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one pizzaOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PizzaOrderDTO findOne(Long id) {
        log.debug("Request to get PizzaOrder : {}", id);
        PizzaOrder pizzaOrder = pizzaOrderRepository.findOne(id);
        return pizzaOrderMapper.toDto(pizzaOrder);
    }

    /**
     * Delete the pizzaOrder by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PizzaOrder : {}", id);
        pizzaOrderRepository.delete(id);
    }
}
