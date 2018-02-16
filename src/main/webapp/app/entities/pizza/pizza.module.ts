import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    PizzaService,
    PizzaPopupService,
    PizzaComponent,
    PizzaDetailComponent,
    PizzaDialogComponent,
    PizzaPopupComponent,
    PizzaDeletePopupComponent,
    PizzaDeleteDialogComponent,
    pizzaRoute,
    pizzaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...pizzaRoute,
    ...pizzaPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PizzaComponent,
        PizzaDetailComponent,
        PizzaDialogComponent,
        PizzaDeleteDialogComponent,
        PizzaPopupComponent,
        PizzaDeletePopupComponent,
    ],
    entryComponents: [
        PizzaComponent,
        PizzaDialogComponent,
        PizzaPopupComponent,
        PizzaDeleteDialogComponent,
        PizzaDeletePopupComponent,
    ],
    providers: [
        PizzaService,
        PizzaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationPizzaModule {}
