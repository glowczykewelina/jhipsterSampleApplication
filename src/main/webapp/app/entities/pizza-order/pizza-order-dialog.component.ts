import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PizzaOrder } from './pizza-order.model';
import { PizzaOrderPopupService } from './pizza-order-popup.service';
import { PizzaOrderService } from './pizza-order.service';

@Component({
    selector: 'jhi-pizza-order-dialog',
    templateUrl: './pizza-order-dialog.component.html'
})
export class PizzaOrderDialogComponent implements OnInit {

    pizzaOrder: PizzaOrder;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private pizzaOrderService: PizzaOrderService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pizzaOrder.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pizzaOrderService.update(this.pizzaOrder));
        } else {
            this.subscribeToSaveResponse(
                this.pizzaOrderService.create(this.pizzaOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PizzaOrder>>) {
        result.subscribe((res: HttpResponse<PizzaOrder>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PizzaOrder) {
        this.eventManager.broadcast({ name: 'pizzaOrderListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-pizza-order-popup',
    template: ''
})
export class PizzaOrderPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pizzaOrderPopupService: PizzaOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pizzaOrderPopupService
                    .open(PizzaOrderDialogComponent as Component, params['id']);
            } else {
                this.pizzaOrderPopupService
                    .open(PizzaOrderDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
