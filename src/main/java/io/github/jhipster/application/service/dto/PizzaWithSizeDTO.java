package io.github.jhipster.application.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.Size;

/**
 * A DTO for the PizzaWithSize entity.
 */
public class PizzaWithSizeDTO implements Serializable {

    private Long id;

    private Size size;

    private Long pizzaOrderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Long getPizzaOrderId() {
        return pizzaOrderId;
    }

    public void setPizzaOrderId(Long pizzaOrderId) {
        this.pizzaOrderId = pizzaOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PizzaWithSizeDTO pizzaWithSizeDTO = (PizzaWithSizeDTO) o;
        if(pizzaWithSizeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pizzaWithSizeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PizzaWithSizeDTO{" +
            "id=" + getId() +
            ", size='" + getSize() + "'" +
            "}";
    }
}
