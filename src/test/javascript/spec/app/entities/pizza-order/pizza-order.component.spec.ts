/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PizzaOrderComponent } from '../../../../../../main/webapp/app/entities/pizza-order/pizza-order.component';
import { PizzaOrderService } from '../../../../../../main/webapp/app/entities/pizza-order/pizza-order.service';
import { PizzaOrder } from '../../../../../../main/webapp/app/entities/pizza-order/pizza-order.model';

describe('Component Tests', () => {

    describe('PizzaOrder Management Component', () => {
        let comp: PizzaOrderComponent;
        let fixture: ComponentFixture<PizzaOrderComponent>;
        let service: PizzaOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [PizzaOrderComponent],
                providers: [
                    PizzaOrderService
                ]
            })
            .overrideTemplate(PizzaOrderComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PizzaOrderComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PizzaOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PizzaOrder(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.pizzaOrders[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
