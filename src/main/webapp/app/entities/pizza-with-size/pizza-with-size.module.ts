import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    PizzaWithSizeService,
    PizzaWithSizePopupService,
    PizzaWithSizeComponent,
    PizzaWithSizeDetailComponent,
    PizzaWithSizeDialogComponent,
    PizzaWithSizePopupComponent,
    PizzaWithSizeDeletePopupComponent,
    PizzaWithSizeDeleteDialogComponent,
    pizzaWithSizeRoute,
    pizzaWithSizePopupRoute,
} from './';

const ENTITY_STATES = [
    ...pizzaWithSizeRoute,
    ...pizzaWithSizePopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PizzaWithSizeComponent,
        PizzaWithSizeDetailComponent,
        PizzaWithSizeDialogComponent,
        PizzaWithSizeDeleteDialogComponent,
        PizzaWithSizePopupComponent,
        PizzaWithSizeDeletePopupComponent,
    ],
    entryComponents: [
        PizzaWithSizeComponent,
        PizzaWithSizeDialogComponent,
        PizzaWithSizePopupComponent,
        PizzaWithSizeDeleteDialogComponent,
        PizzaWithSizeDeletePopupComponent,
    ],
    providers: [
        PizzaWithSizeService,
        PizzaWithSizePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationPizzaWithSizeModule {}
