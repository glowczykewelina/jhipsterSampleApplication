package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.PizzaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pizza and its DTO PizzaDTO.
 */
@Mapper(componentModel = "spring", uses = {PizzaWithSizeMapper.class})
public interface PizzaMapper extends EntityMapper<PizzaDTO, Pizza> {

    @Mapping(source = "pizzaWithSize.id", target = "pizzaWithSizeId")
    PizzaDTO toDto(Pizza pizza);

    @Mapping(source = "pizzaWithSizeId", target = "pizzaWithSize")
    Pizza toEntity(PizzaDTO pizzaDTO);

    default Pizza fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pizza pizza = new Pizza();
        pizza.setId(id);
        return pizza;
    }
}
