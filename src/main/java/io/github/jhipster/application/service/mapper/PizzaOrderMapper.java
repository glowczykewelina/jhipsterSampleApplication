package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.PizzaOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PizzaOrder and its DTO PizzaOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PizzaOrderMapper extends EntityMapper<PizzaOrderDTO, PizzaOrder> {


    @Mapping(target = "pizzawithsizes", ignore = true)
    PizzaOrder toEntity(PizzaOrderDTO pizzaOrderDTO);

    default PizzaOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        PizzaOrder pizzaOrder = new PizzaOrder();
        pizzaOrder.setId(id);
        return pizzaOrder;
    }
}
