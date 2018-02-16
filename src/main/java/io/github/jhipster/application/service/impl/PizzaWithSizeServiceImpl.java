package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.PizzaWithSizeService;
import io.github.jhipster.application.domain.PizzaWithSize;
import io.github.jhipster.application.repository.PizzaWithSizeRepository;
import io.github.jhipster.application.service.dto.PizzaWithSizeDTO;
import io.github.jhipster.application.service.mapper.PizzaWithSizeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PizzaWithSize.
 */
@Service
@Transactional
public class PizzaWithSizeServiceImpl implements PizzaWithSizeService {

    private final Logger log = LoggerFactory.getLogger(PizzaWithSizeServiceImpl.class);

    private final PizzaWithSizeRepository pizzaWithSizeRepository;

    private final PizzaWithSizeMapper pizzaWithSizeMapper;

    public PizzaWithSizeServiceImpl(PizzaWithSizeRepository pizzaWithSizeRepository, PizzaWithSizeMapper pizzaWithSizeMapper) {
        this.pizzaWithSizeRepository = pizzaWithSizeRepository;
        this.pizzaWithSizeMapper = pizzaWithSizeMapper;
    }

    /**
     * Save a pizzaWithSize.
     *
     * @param pizzaWithSizeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PizzaWithSizeDTO save(PizzaWithSizeDTO pizzaWithSizeDTO) {
        log.debug("Request to save PizzaWithSize : {}", pizzaWithSizeDTO);
        PizzaWithSize pizzaWithSize = pizzaWithSizeMapper.toEntity(pizzaWithSizeDTO);
        pizzaWithSize = pizzaWithSizeRepository.save(pizzaWithSize);
        return pizzaWithSizeMapper.toDto(pizzaWithSize);
    }

    /**
     * Get all the pizzaWithSizes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PizzaWithSizeDTO> findAll() {
        log.debug("Request to get all PizzaWithSizes");
        return pizzaWithSizeRepository.findAll().stream()
            .map(pizzaWithSizeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one pizzaWithSize by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PizzaWithSizeDTO findOne(Long id) {
        log.debug("Request to get PizzaWithSize : {}", id);
        PizzaWithSize pizzaWithSize = pizzaWithSizeRepository.findOne(id);
        return pizzaWithSizeMapper.toDto(pizzaWithSize);
    }

    /**
     * Delete the pizzaWithSize by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PizzaWithSize : {}", id);
        pizzaWithSizeRepository.delete(id);
    }
}
