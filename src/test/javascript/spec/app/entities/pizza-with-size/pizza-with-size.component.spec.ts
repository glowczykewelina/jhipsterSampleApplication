/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PizzaWithSizeComponent } from '../../../../../../main/webapp/app/entities/pizza-with-size/pizza-with-size.component';
import { PizzaWithSizeService } from '../../../../../../main/webapp/app/entities/pizza-with-size/pizza-with-size.service';
import { PizzaWithSize } from '../../../../../../main/webapp/app/entities/pizza-with-size/pizza-with-size.model';

describe('Component Tests', () => {

    describe('PizzaWithSize Management Component', () => {
        let comp: PizzaWithSizeComponent;
        let fixture: ComponentFixture<PizzaWithSizeComponent>;
        let service: PizzaWithSizeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [PizzaWithSizeComponent],
                providers: [
                    PizzaWithSizeService
                ]
            })
            .overrideTemplate(PizzaWithSizeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PizzaWithSizeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PizzaWithSizeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PizzaWithSize(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.pizzaWithSizes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
