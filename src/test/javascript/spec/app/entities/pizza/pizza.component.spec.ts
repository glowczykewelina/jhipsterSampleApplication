/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PizzaComponent } from '../../../../../../main/webapp/app/entities/pizza/pizza.component';
import { PizzaService } from '../../../../../../main/webapp/app/entities/pizza/pizza.service';
import { Pizza } from '../../../../../../main/webapp/app/entities/pizza/pizza.model';

describe('Component Tests', () => {

    describe('Pizza Management Component', () => {
        let comp: PizzaComponent;
        let fixture: ComponentFixture<PizzaComponent>;
        let service: PizzaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [PizzaComponent],
                providers: [
                    PizzaService
                ]
            })
            .overrideTemplate(PizzaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PizzaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PizzaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Pizza(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.pizzas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
