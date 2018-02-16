package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Pizza;

import io.github.jhipster.application.repository.PizzaRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
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

    private final PizzaRepository pizzaRepository;

    public PizzaResource(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    /**
     * POST  /pizzas : Create a new pizza.
     *
     * @param pizza the pizza to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pizza, or with status 400 (Bad Request) if the pizza has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pizzas")
    @Timed
    public ResponseEntity<Pizza> createPizza(@RequestBody Pizza pizza) throws URISyntaxException {
        log.debug("REST request to save Pizza : {}", pizza);
        if (pizza.getId() != null) {
            throw new BadRequestAlertException("A new pizza cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pizza result = pizzaRepository.save(pizza);
        return ResponseEntity.created(new URI("/api/pizzas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pizzas : Updates an existing pizza.
     *
     * @param pizza the pizza to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pizza,
     * or with status 400 (Bad Request) if the pizza is not valid,
     * or with status 500 (Internal Server Error) if the pizza couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pizzas")
    @Timed
    public ResponseEntity<Pizza> updatePizza(@RequestBody Pizza pizza) throws URISyntaxException {
        log.debug("REST request to update Pizza : {}", pizza);
        if (pizza.getId() == null) {
            return createPizza(pizza);
        }
        Pizza result = pizzaRepository.save(pizza);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pizza.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pizzas : get all the pizzas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pizzas in body
     */
    @GetMapping("/pizzas")
    @Timed
    public List<Pizza> getAllPizzas() {
        log.debug("REST request to get all Pizzas");
        return pizzaRepository.findAll();
        }

    /**
     * GET  /pizzas/:id : get the "id" pizza.
     *
     * @param id the id of the pizza to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pizza, or with status 404 (Not Found)
     */
    @GetMapping("/pizzas/{id}")
    @Timed
    public ResponseEntity<Pizza> getPizza(@PathVariable Long id) {
        log.debug("REST request to get Pizza : {}", id);
        Pizza pizza = pizzaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pizza));
    }

    /**
     * DELETE  /pizzas/:id : delete the "id" pizza.
     *
     * @param id the id of the pizza to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pizzas/{id}")
    @Timed
    public ResponseEntity<Void> deletePizza(@PathVariable Long id) {
        log.debug("REST request to delete Pizza : {}", id);
        pizzaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
