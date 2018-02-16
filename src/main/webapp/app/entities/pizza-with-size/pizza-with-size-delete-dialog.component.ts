import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PizzaWithSize } from './pizza-with-size.model';
import { PizzaWithSizePopupService } from './pizza-with-size-popup.service';
import { PizzaWithSizeService } from './pizza-with-size.service';

@Component({
    selector: 'jhi-pizza-with-size-delete-dialog',
    templateUrl: './pizza-with-size-delete-dialog.component.html'
})
export class PizzaWithSizeDeleteDialogComponent {

    pizzaWithSize: PizzaWithSize;

    constructor(
        private pizzaWithSizeService: PizzaWithSizeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pizzaWithSizeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pizzaWithSizeListModification',
                content: 'Deleted an pizzaWithSize'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pizza-with-size-delete-popup',
    template: ''
})
export class PizzaWithSizeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pizzaWithSizePopupService: PizzaWithSizePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pizzaWithSizePopupService
                .open(PizzaWithSizeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
