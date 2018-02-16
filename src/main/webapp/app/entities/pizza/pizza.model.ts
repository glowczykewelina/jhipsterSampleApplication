import { BaseEntity } from './../../shared';

export class Pizza implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public basePrice?: number,
        public isVegan?: boolean,
        public isSpicy?: boolean,
        public number?: number,
        public pizzaWithSizeId?: number,
    ) {
        this.isVegan = false;
        this.isSpicy = false;
    }
}
