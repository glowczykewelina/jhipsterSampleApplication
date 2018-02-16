package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.Size;

/**
 * A PizzaWithSize.
 */
@Entity
@Table(name = "pizza_with_size")
public class PizzaWithSize implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_size")
    private Size size;

    @OneToMany(mappedBy = "pizzaWithSize")
    @JsonIgnore
    private Set<Pizza> pizzas = new HashSet<>();

    @ManyToOne
    private PizzaOrder pizzaOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Size getSize() {
        return size;
    }

    public PizzaWithSize size(Size size) {
        this.size = size;
        return this;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Set<Pizza> getPizzas() {
        return pizzas;
    }

    public PizzaWithSize pizzas(Set<Pizza> pizzas) {
        this.pizzas = pizzas;
        return this;
    }

    public PizzaWithSize addPizza(Pizza pizza) {
        this.pizzas.add(pizza);
        pizza.setPizzaWithSize(this);
        return this;
    }

    public PizzaWithSize removePizza(Pizza pizza) {
        this.pizzas.remove(pizza);
        pizza.setPizzaWithSize(null);
        return this;
    }

    public void setPizzas(Set<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public PizzaOrder getPizzaOrder() {
        return pizzaOrder;
    }

    public PizzaWithSize pizzaOrder(PizzaOrder pizzaOrder) {
        this.pizzaOrder = pizzaOrder;
        return this;
    }

    public void setPizzaOrder(PizzaOrder pizzaOrder) {
        this.pizzaOrder = pizzaOrder;
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
        PizzaWithSize pizzaWithSize = (PizzaWithSize) o;
        if (pizzaWithSize.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pizzaWithSize.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PizzaWithSize{" +
            "id=" + getId() +
            ", size='" + getSize() + "'" +
            "}";
    }
}
