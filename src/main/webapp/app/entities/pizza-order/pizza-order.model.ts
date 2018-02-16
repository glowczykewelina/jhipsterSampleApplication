import { BaseEntity } from './../../shared';

export class PizzaOrder implements BaseEntity {
    constructor(
        public id?: number,
        public total?: number,
        public pizzawithsizes?: BaseEntity[],
    ) {
    }
}
