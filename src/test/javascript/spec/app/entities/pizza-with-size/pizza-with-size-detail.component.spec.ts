/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PizzaWithSizeDetailComponent } from '../../../../../../main/webapp/app/entities/pizza-with-size/pizza-with-size-detail.component';
import { PizzaWithSizeService } from '../../../../../../main/webapp/app/entities/pizza-with-size/pizza-with-size.service';
import { PizzaWithSize } from '../../../../../../main/webapp/app/entities/pizza-with-size/pizza-with-size.model';

describe('Component Tests', () => {

    describe('PizzaWithSize Management Detail Component', () => {
        let comp: PizzaWithSizeDetailComponent;
        let fixture: ComponentFixture<PizzaWithSizeDetailComponent>;
        let service: PizzaWithSizeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [PizzaWithSizeDetailComponent],
                providers: [
                    PizzaWithSizeService
                ]
            })
            .overrideTemplate(PizzaWithSizeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PizzaWithSizeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PizzaWithSizeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PizzaWithSize(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.pizzaWithSize).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
