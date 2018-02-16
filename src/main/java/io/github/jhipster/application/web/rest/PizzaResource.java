package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.PizzaService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.service.dto.PizzaDTO;
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
 * REST controller for managing Pizza.
 */
@RestController
@RequestMapping("/api")
public class PizzaResource {

    private final Logger log = LoggerFactory.getLogger(PizzaResource.class);

    private static final String ENTITY_NAME = "pizza";

    private final PizzaService pizzaService;

    public PizzaResource(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    /**
     * POST  /pizzas : Create a new pizza.
     *
     * @param pizzaDTO the pizzaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pizzaDTO, or with status 400 (Bad Request) if the pizza has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pizzas")
    @Timed
    public ResponseEntity<PizzaDTO> createPizza(@RequestBody PizzaDTO pizzaDTO) throws URISyntaxException {
        log.debug("REST request to save Pizza : {}", pizzaDTO);
        if (pizzaDTO.getId() != null) {
            throw new BadRequestAlertException("A new pizza cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PizzaDTO result = pizzaService.save(pizzaDTO);
        return ResponseEntity.created(new URI("/api/pizzas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pizzas : Updates an existing pizza.
     *
     * @param pizzaDTO the pizzaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pizzaDTO,
     * or with status 400 (Bad Request) if the pizzaDTO is not valid,
     * or with status 500 (Internal Server Error) if the pizzaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pizzas")
    @Timed
    public ResponseEntity<PizzaDTO> updatePizza(@RequestBody PizzaDTO pizzaDTO) throws URISyntaxException {
        log.debug("REST request to update Pizza : {}", pizzaDTO);
        if (pizzaDTO.getId() == null) {
            return createPizza(pizzaDTO);
        }
        PizzaDTO result = pizzaService.save(pizzaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pizzaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pizzas : get all the pizzas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pizzas in body
     */
    @GetMapping("/pizzas")
    @Timed
    public List<PizzaDTO> getAllPizzas() {
        log.debug("REST request to get all Pizzas");
        return pizzaService.findAll();
        }

    /**
     * GET  /pizzas/:id : get the "id" pizza.
     *
     * @param id the id of the pizzaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pizzaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pizzas/{id}")
    @Timed
    public ResponseEntity<PizzaDTO> getPizza(@PathVariable Long id) {
        log.debug("REST request to get Pizza : {}", id);
        PizzaDTO pizzaDTO = pizzaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pizzaDTO));
    }

    /**
     * DELETE  /pizzas/:id : delete the "id" pizza.
     *
     * @param id the id of the pizzaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pizzas/{id}")
    @Timed
    public ResponseEntity<Void> deletePizza(@PathVariable Long id) {
        log.debug("REST request to delete Pizza : {}", id);
        pizzaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
