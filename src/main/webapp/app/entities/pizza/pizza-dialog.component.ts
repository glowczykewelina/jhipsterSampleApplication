import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Pizza } from './pizza.model';
import { PizzaPopupService } from './pizza-popup.service';
import { PizzaService } from './pizza.service';
import { PizzaWithSize, PizzaWithSizeService } from '../pizza-with-size';

@Component({
    selector: 'jhi-pizza-dialog',
    templateUrl: './pizza-dialog.component.html'
})
export class PizzaDialogComponent implements OnInit {

    pizza: Pizza;
    isSaving: boolean;

    pizzawithsizes: PizzaWithSize[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private pizzaService: PizzaService,
        private pizzaWithSizeService: PizzaWithSizeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.pizzaWithSizeService.query()
            .subscribe((res: HttpResponse<PizzaWithSize[]>) => { this.pizzawithsizes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pizza.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pizzaService.update(this.pizza));
        } else {
            this.subscribeToSaveResponse(
                this.pizzaService.create(this.pizza));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Pizza>>) {
        result.subscribe((res: HttpResponse<Pizza>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Pizza) {
        this.eventManager.broadcast({ name: 'pizzaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPizzaWithSizeById(index: number, item: PizzaWithSize) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pizza-popup',
    template: ''
})
export class PizzaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pizzaPopupService: PizzaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pizzaPopupService
                    .open(PizzaDialogComponent as Component, params['id']);
            } else {
                this.pizzaPopupService
                    .open(PizzaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
