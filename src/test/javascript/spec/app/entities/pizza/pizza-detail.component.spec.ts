/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PizzaDetailComponent } from '../../../../../../main/webapp/app/entities/pizza/pizza-detail.component';
import { PizzaService } from '../../../../../../main/webapp/app/entities/pizza/pizza.service';
import { Pizza } from '../../../../../../main/webapp/app/entities/pizza/pizza.model';

describe('Component Tests', () => {

    describe('Pizza Management Detail Component', () => {
        let comp: PizzaDetailComponent;
        let fixture: ComponentFixture<PizzaDetailComponent>;
        let service: PizzaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [PizzaDetailComponent],
                providers: [
                    PizzaService
                ]
            })
            .overrideTemplate(PizzaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PizzaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PizzaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Pizza(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.pizza).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
