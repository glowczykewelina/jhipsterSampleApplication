package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.PizzaWithSizeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PizzaWithSize and its DTO PizzaWithSizeDTO.
 */
@Mapper(componentModel = "spring", uses = {PizzaOrderMapper.class})
public interface PizzaWithSizeMapper extends EntityMapper<PizzaWithSizeDTO, PizzaWithSize> {

    @Mapping(source = "pizzaOrder.id", target = "pizzaOrderId")
    PizzaWithSizeDTO toDto(PizzaWithSize pizzaWithSize);

    @Mapping(target = "pizzas", ignore = true)
    @Mapping(source = "pizzaOrderId", target = "pizzaOrder")
    PizzaWithSize toEntity(PizzaWithSizeDTO pizzaWithSizeDTO);

    default PizzaWithSize fromId(Long id) {
        if (id == null) {
            return null;
        }
        PizzaWithSize pizzaWithSize = new PizzaWithSize();
        pizzaWithSize.setId(id);
        return pizzaWithSize;
    }
}
