import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PizzaOrder } from './pizza-order.model';
import { PizzaOrderPopupService } from './pizza-order-popup.service';
import { PizzaOrderService } from './pizza-order.service';

@Component({
    selector: 'jhi-pizza-order-delete-dialog',
    templateUrl: './pizza-order-delete-dialog.component.html'
})
export class PizzaOrderDeleteDialogComponent {

    pizzaOrder: PizzaOrder;

    constructor(
        private pizzaOrderService: PizzaOrderService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pizzaOrderService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pizzaOrderListModification',
                content: 'Deleted an pizzaOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pizza-order-delete-popup',
    template: ''
})
export class PizzaOrderDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pizzaOrderPopupService: PizzaOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pizzaOrderPopupService
                .open(PizzaOrderDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
