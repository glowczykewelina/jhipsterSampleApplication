import { BaseEntity } from './../../shared';

export const enum Size {
    'MEDIUM',
    'LARGE',
    'HUGE'
}

export class PizzaWithSize implements BaseEntity {
    constructor(
        public id?: number,
        public size?: Size,
        public pizzas?: BaseEntity[],
        public pizzaOrderId?: number,
    ) {
    }
}
