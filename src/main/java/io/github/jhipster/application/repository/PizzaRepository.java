package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Pizza;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Pizza entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

}
