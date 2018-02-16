import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    PizzaOrderService,
    PizzaOrderPopupService,
    PizzaOrderComponent,
    PizzaOrderDetailComponent,
    PizzaOrderDialogComponent,
    PizzaOrderPopupComponent,
    PizzaOrderDeletePopupComponent,
    PizzaOrderDeleteDialogComponent,
    pizzaOrderRoute,
    pizzaOrderPopupRoute,
} from './';

const ENTITY_STATES = [
    ...pizzaOrderRoute,
    ...pizzaOrderPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PizzaOrderComponent,
        PizzaOrderDetailComponent,
        PizzaOrderDialogComponent,
        PizzaOrderDeleteDialogComponent,
        PizzaOrderPopupComponent,
        PizzaOrderDeletePopupComponent,
    ],
    entryComponents: [
        PizzaOrderComponent,
        PizzaOrderDialogComponent,
        PizzaOrderPopupComponent,
        PizzaOrderDeleteDialogComponent,
        PizzaOrderDeletePopupComponent,
    ],
    providers: [
        PizzaOrderService,
        PizzaOrderPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationPizzaOrderModule {}
