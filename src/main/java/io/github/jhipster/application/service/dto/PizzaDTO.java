package io.github.jhipster.application.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Pizza entity.
 */
public class PizzaDTO implements Serializable {

    private Long id;

    private String name;

    private Long basePrice;

    private Boolean isVegan;

    private Boolean isSpicy;

    private Integer number;

    private Long pizzaWithSizeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Long basePrice) {
        this.basePrice = basePrice;
    }

    public Boolean isIsVegan() {
        return isVegan;
    }

    public void setIsVegan(Boolean isVegan) {
        this.isVegan = isVegan;
    }

    public Boolean isIsSpicy() {
        return isSpicy;
    }

    public void setIsSpicy(Boolean isSpicy) {
        this.isSpicy = isSpicy;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getPizzaWithSizeId() {
        return pizzaWithSizeId;
    }

    public void setPizzaWithSizeId(Long pizzaWithSizeId) {
        this.pizzaWithSizeId = pizzaWithSizeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PizzaDTO pizzaDTO = (PizzaDTO) o;
        if(pizzaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pizzaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PizzaDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", basePrice=" + getBasePrice() +
            ", isVegan='" + isIsVegan() + "'" +
            ", isSpicy='" + isIsSpicy() + "'" +
            ", number=" + getNumber() +
            "}";
    }
}
