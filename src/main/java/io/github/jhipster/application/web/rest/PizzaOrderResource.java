package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.PizzaOrderService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.service.dto.PizzaOrderDTO;
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
 * REST controller for managing PizzaOrder.
 */
@RestController
@RequestMapping("/api")
public class PizzaOrderResource {

    private final Logger log = LoggerFactory.getLogger(PizzaOrderResource.class);

    private static final String ENTITY_NAME = "pizzaOrder";

    private final PizzaOrderService pizzaOrderService;

    public PizzaOrderResource(PizzaOrderService pizzaOrderService) {
        this.pizzaOrderService = pizzaOrderService;
    }

    /**
     * POST  /pizza-orders : Create a new pizzaOrder.
     *
     * @param pizzaOrderDTO the pizzaOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pizzaOrderDTO, or with status 400 (Bad Request) if the pizzaOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pizza-orders")
    @Timed
    public ResponseEntity<PizzaOrderDTO> createPizzaOrder(@RequestBody PizzaOrderDTO pizzaOrderDTO) throws URISyntaxException {
        log.debug("REST request to save PizzaOrder : {}", pizzaOrderDTO);
        if (pizzaOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new pizzaOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PizzaOrderDTO result = pizzaOrderService.save(pizzaOrderDTO);
        return ResponseEntity.created(new URI("/api/pizza-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pizza-orders : Updates an existing pizzaOrder.
     *
     * @param pizzaOrderDTO the pizzaOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pizzaOrderDTO,
     * or with status 400 (Bad Request) if the pizzaOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the pizzaOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pizza-orders")
    @Timed
    public ResponseEntity<PizzaOrderDTO> updatePizzaOrder(@RequestBody PizzaOrderDTO pizzaOrderDTO) throws URISyntaxException {
        log.debug("REST request to update PizzaOrder : {}", pizzaOrderDTO);
        if (pizzaOrderDTO.getId() == null) {
            return createPizzaOrder(pizzaOrderDTO);
        }
        PizzaOrderDTO result = pizzaOrderService.save(pizzaOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pizzaOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pizza-orders : get all the pizzaOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pizzaOrders in body
     */
    @GetMapping("/pizza-orders")
    @Timed
    public List<PizzaOrderDTO> getAllPizzaOrders() {
        log.debug("REST request to get all PizzaOrders");
        return pizzaOrderService.findAll();
        }

    /**
     * GET  /pizza-orders/:id : get the "id" pizzaOrder.
     *
     * @param id the id of the pizzaOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pizzaOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pizza-orders/{id}")
    @Timed
    public ResponseEntity<PizzaOrderDTO> getPizzaOrder(@PathVariable Long id) {
        log.debug("REST request to get PizzaOrder : {}", id);
        PizzaOrderDTO pizzaOrderDTO = pizzaOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pizzaOrderDTO));
    }

    /**
     * DELETE  /pizza-orders/:id : delete the "id" pizzaOrder.
     *
     * @param id the id of the pizzaOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pizza-orders/{id}")
    @Timed
    public ResponseEntity<Void> deletePizzaOrder(@PathVariable Long id) {
        log.debug("REST request to delete PizzaOrder : {}", id);
        pizzaOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
