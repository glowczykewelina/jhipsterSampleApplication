/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PizzaOrderDetailComponent } from '../../../../../../main/webapp/app/entities/pizza-order/pizza-order-detail.component';
import { PizzaOrderService } from '../../../../../../main/webapp/app/entities/pizza-order/pizza-order.service';
import { PizzaOrder } from '../../../../../../main/webapp/app/entities/pizza-order/pizza-order.model';

describe('Component Tests', () => {

    describe('PizzaOrder Management Detail Component', () => {
        let comp: PizzaOrderDetailComponent;
        let fixture: ComponentFixture<PizzaOrderDetailComponent>;
        let service: PizzaOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [PizzaOrderDetailComponent],
                providers: [
                    PizzaOrderService
                ]
            })
            .overrideTemplate(PizzaOrderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PizzaOrderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PizzaOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PizzaOrder(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.pizzaOrder).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
