package io.github.jhipster.application.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Pizza.
 */
@Entity
@Table(name = "pizza")
public class Pizza implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "base_price")
    private Long basePrice;

    @Column(name = "is_vegan")
    private Boolean isVegan;

    @Column(name = "is_spicy")
    private Boolean isSpicy;

    @Column(name = "jhi_number")
    private Integer number;

    @ManyToOne
    private PizzaWithSize pizzaWithSize;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Pizza name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBasePrice() {
        return basePrice;
    }

    public Pizza basePrice(Long basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public void setBasePrice(Long basePrice) {
        this.basePrice = basePrice;
    }

    public Boolean isIsVegan() {
        return isVegan;
    }

    public Pizza isVegan(Boolean isVegan) {
        this.isVegan = isVegan;
        return this;
    }

    public void setIsVegan(Boolean isVegan) {
        this.isVegan = isVegan;
    }

    public Boolean isIsSpicy() {
        return isSpicy;
    }

    public Pizza isSpicy(Boolean isSpicy) {
        this.isSpicy = isSpicy;
        return this;
    }

    public void setIsSpicy(Boolean isSpicy) {
        this.isSpicy = isSpicy;
    }

    public Integer getNumber() {
        return number;
    }

    public Pizza number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public PizzaWithSize getPizzaWithSize() {
        return pizzaWithSize;
    }

    public Pizza pizzaWithSize(PizzaWithSize pizzaWithSize) {
        this.pizzaWithSize = pizzaWithSize;
        return this;
    }

    public void setPizzaWithSize(PizzaWithSize pizzaWithSize) {
        this.pizzaWithSize = pizzaWithSize;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pizza pizza = (Pizza) o;
        if (pizza.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pizza.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pizza{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", basePrice=" + getBasePrice() +
            ", isVegan='" + isIsVegan() + "'" +
            ", isSpicy='" + isIsSpicy() + "'" +
            ", number=" + getNumber() +
            "}";
    }
}
