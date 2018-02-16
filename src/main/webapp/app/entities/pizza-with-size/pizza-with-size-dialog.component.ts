import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PizzaWithSize } from './pizza-with-size.model';
import { PizzaWithSizePopupService } from './pizza-with-size-popup.service';
import { PizzaWithSizeService } from './pizza-with-size.service';
import { PizzaOrder, PizzaOrderService } from '../pizza-order';

@Component({
    selector: 'jhi-pizza-with-size-dialog',
    templateUrl: './pizza-with-size-dialog.component.html'
})
export class PizzaWithSizeDialogComponent implements OnInit {

    pizzaWithSize: PizzaWithSize;
    isSaving: boolean;

    pizzaorders: PizzaOrder[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private pizzaWithSizeService: PizzaWithSizeService,
        private pizzaOrderService: PizzaOrderService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.pizzaOrderService.query()
            .subscribe((res: HttpResponse<PizzaOrder[]>) => { this.pizzaorders = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pizzaWithSize.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pizzaWithSizeService.update(this.pizzaWithSize));
        } else {
            this.subscribeToSaveResponse(
                this.pizzaWithSizeService.create(this.pizzaWithSize));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PizzaWithSize>>) {
        result.subscribe((res: HttpResponse<PizzaWithSize>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PizzaWithSize) {
        this.eventManager.broadcast({ name: 'pizzaWithSizeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPizzaOrderById(index: number, item: PizzaOrder) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pizza-with-size-popup',
    template: ''
})
export class PizzaWithSizePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pizzaWithSizePopupService: PizzaWithSizePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pizzaWithSizePopupService
                    .open(PizzaWithSizeDialogComponent as Component, params['id']);
            } else {
                this.pizzaWithSizePopupService
                    .open(PizzaWithSizeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
