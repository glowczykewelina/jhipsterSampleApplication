package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PizzaOrder.
 */
@Entity
@Table(name = "pizza_order")
public class PizzaOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total")
    private Double total;

    @OneToMany(mappedBy = "pizzaOrder")
    @JsonIgnore
    private Set<PizzaWithSize> pizzawithsizes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public PizzaOrder total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Set<PizzaWithSize> getPizzawithsizes() {
        return pizzawithsizes;
    }

    public PizzaOrder pizzawithsizes(Set<PizzaWithSize> pizzaWithSizes) {
        this.pizzawithsizes = pizzaWithSizes;
        return this;
    }

    public PizzaOrder addPizzawithsize(PizzaWithSize pizzaWithSize) {
        this.pizzawithsizes.add(pizzaWithSize);
        pizzaWithSize.setPizzaOrder(this);
        return this;
    }

    public PizzaOrder removePizzawithsize(PizzaWithSize pizzaWithSize) {
        this.pizzawithsizes.remove(pizzaWithSize);
        pizzaWithSize.setPizzaOrder(null);
        return this;
    }

    public void setPizzawithsizes(Set<PizzaWithSize> pizzaWithSizes) {
        this.pizzawithsizes = pizzaWithSizes;
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
        PizzaOrder pizzaOrder = (PizzaOrder) o;
        if (pizzaOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pizzaOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PizzaOrder{" +
            "id=" + getId() +
            ", total=" + getTotal() +
            "}";
    }
}
