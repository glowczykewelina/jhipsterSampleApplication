/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PizzaWithSizeDialogComponent } from '../../../../../../main/webapp/app/entities/pizza-with-size/pizza-with-size-dialog.component';
import { PizzaWithSizeService } from '../../../../../../main/webapp/app/entities/pizza-with-size/pizza-with-size.service';
import { PizzaWithSize } from '../../../../../../main/webapp/app/entities/pizza-with-size/pizza-with-size.model';
import { PizzaOrderService } from '../../../../../../main/webapp/app/entities/pizza-order';

describe('Component Tests', () => {

    describe('PizzaWithSize Management Dialog Component', () => {
        let comp: PizzaWithSizeDialogComponent;
        let fixture: ComponentFixture<PizzaWithSizeDialogComponent>;
        let service: PizzaWithSizeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [PizzaWithSizeDialogComponent],
                providers: [
                    PizzaOrderService,
                    PizzaWithSizeService
                ]
            })
            .overrideTemplate(PizzaWithSizeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PizzaWithSizeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PizzaWithSizeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PizzaWithSize(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.pizzaWithSize = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'pizzaWithSizeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PizzaWithSize();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.pizzaWithSize = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'pizzaWithSizeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
