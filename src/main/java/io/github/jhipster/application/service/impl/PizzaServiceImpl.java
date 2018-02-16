package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.PizzaService;
import io.github.jhipster.application.domain.Pizza;
import io.github.jhipster.application.repository.PizzaRepository;
import io.github.jhipster.application.service.dto.PizzaDTO;
import io.github.jhipster.application.service.mapper.PizzaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Pizza.
 */
@Service
@Transactional
public class PizzaServiceImpl implements PizzaService {

    private final Logger log = LoggerFactory.getLogger(PizzaServiceImpl.class);

    private final PizzaRepository pizzaRepository;

    private final PizzaMapper pizzaMapper;

    public PizzaServiceImpl(PizzaRepository pizzaRepository, PizzaMapper pizzaMapper) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaMapper = pizzaMapper;
    }

    /**
     * Save a pizza.
     *
     * @param pizzaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PizzaDTO save(PizzaDTO pizzaDTO) {
        log.debug("Request to save Pizza : {}", pizzaDTO);
        Pizza pizza = pizzaMapper.toEntity(pizzaDTO);
        pizza = pizzaRepository.save(pizza);
        return pizzaMapper.toDto(pizza);
    }

    /**
     * Get all the pizzas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PizzaDTO> findAll() {
        log.debug("Request to get all Pizzas");
        return pizzaRepository.findAll().stream()
            .map(pizzaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one pizza by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PizzaDTO findOne(Long id) {
        log.debug("Request to get Pizza : {}", id);
        Pizza pizza = pizzaRepository.findOne(id);
        return pizzaMapper.toDto(pizza);
    }

    /**
     * Delete the pizza by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pizza : {}", id);
        pizzaRepository.delete(id);
    }
}
