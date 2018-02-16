package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.PizzaWithSizeService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.service.dto.PizzaWithSizeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PizzaWithSize.
 */
@RestController
@RequestMapping("/api")
public class PizzaWithSizeResource {

    private final Logger log = LoggerFactory.getLogger(PizzaWithSizeResource.class);

    private static final String ENTITY_NAME = "pizzaWithSize";

    private final PizzaWithSizeService pizzaWithSizeService;

    public PizzaWithSizeResource(PizzaWithSizeService pizzaWithSizeService) {
        this.pizzaWithSizeService = pizzaWithSizeService;
    }

    /**
     * POST  /pizza-with-sizes : Create a new pizzaWithSize.
     *
     * @param pizzaWithSizeDTO the pizzaWithSizeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pizzaWithSizeDTO, or with status 400 (Bad Request) if the pizzaWithSize has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pizza-with-sizes")
    @Timed
    public ResponseEntity<PizzaWithSizeDTO> createPizzaWithSize(@RequestBody PizzaWithSizeDTO pizzaWithSizeDTO) throws URISyntaxException {
        log.debug("REST request to save PizzaWithSize : {}", pizzaWithSizeDTO);
        if (pizzaWithSizeDTO.getId() != null) {
            throw new BadRequestAlertException("A new pizzaWithSize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PizzaWithSizeDTO result = pizzaWithSizeService.save(pizzaWithSizeDTO);
        return ResponseEntity.created(new URI("/api/pizza-with-sizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pizza-with-sizes : Updates an existing pizzaWithSize.
     *
     * @param pizzaWithSizeDTO the pizzaWithSizeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pizzaWithSizeDTO,
     * or with status 400 (Bad Request) if the pizzaWithSizeDTO is not valid,
     * or with status 500 (Internal Server Error) if the pizzaWithSizeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pizza-with-sizes")
    @Timed
    public ResponseEntity<PizzaWithSizeDTO> updatePizzaWithSize(@RequestBody PizzaWithSizeDTO pizzaWithSizeDTO) throws URISyntaxException {
        log.debug("REST request to update PizzaWithSize : {}", pizzaWithSizeDTO);
        if (pizzaWithSizeDTO.getId() == null) {
            return createPizzaWithSize(pizzaWithSizeDTO);
        }
        PizzaWithSizeDTO result = pizzaWithSizeService.save(pizzaWithSizeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pizzaWithSizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pizza-with-sizes : get all the pizzaWithSizes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pizzaWithSizes in body
     */
    @GetMapping("/pizza-with-sizes")
    @Timed
    public List<PizzaWithSizeDTO> getAllPizzaWithSizes() {
        log.debug("REST request to get all PizzaWithSizes");
        return pizzaWithSizeService.findAll();
        }

    /**
     * GET  /pizza-with-sizes/:id : get the "id" pizzaWithSize.
     *
     * @param id the id of the pizzaWithSizeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pizzaWithSizeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pizza-with-sizes/{id}")
    @Timed
    public ResponseEntity<PizzaWithSizeDTO> getPizzaWithSize(@PathVariable Long id) {
        log.debug("REST request to get PizzaWithSize : {}", id);
        PizzaWithSizeDTO pizzaWithSizeDTO = pizzaWithSizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pizzaWithSizeDTO));
    }

    /**
     * DELETE  /pizza-with-sizes/:id : delete the "id" pizzaWithSize.
     *
     * @param id the id of the pizzaWithSizeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pizza-with-sizes/{id}")
    @Timed
    public ResponseEntity<Void> deletePizzaWithSize(@PathVariable Long id) {
        log.debug("REST request to delete PizzaWithSize : {}", id);
        pizzaWithSizeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
