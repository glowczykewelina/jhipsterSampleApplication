package io.github.jhipster.application.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PizzaOrder entity.
 */
public class PizzaOrderDTO implements Serializable {

    private Long id;

    private Double total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PizzaOrderDTO pizzaOrderDTO = (PizzaOrderDTO) o;
        if(pizzaOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pizzaOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PizzaOrderDTO{" +
            "id=" + getId() +
            ", total=" + getTotal() +
            "}";
    }
}
