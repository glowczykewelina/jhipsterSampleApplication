import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleApplicationPizzaModule } from './pizza/pizza.module';
import { JhipsterSampleApplicationPizzaWithSizeModule } from './pizza-with-size/pizza-with-size.module';
import { JhipsterSampleApplicationPizzaOrderModule } from './pizza-order/pizza-order.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhipsterSampleApplicationPizzaModule,
        JhipsterSampleApplicationPizzaWithSizeModule,
        JhipsterSampleApplicationPizzaOrderModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
