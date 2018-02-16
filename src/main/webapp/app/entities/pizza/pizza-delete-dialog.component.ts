import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Pizza } from './pizza.model';
import { PizzaPopupService } from './pizza-popup.service';
import { PizzaService } from './pizza.service';

@Component({
    selector: 'jhi-pizza-delete-dialog',
    templateUrl: './pizza-delete-dialog.component.html'
})
export class PizzaDeleteDialogComponent {

    pizza: Pizza;

    constructor(
        private pizzaService: PizzaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pizzaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pizzaListModification',
                content: 'Deleted an pizza'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pizza-delete-popup',
    template: ''
})
export class PizzaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pizzaPopupService: PizzaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pizzaPopupService
                .open(PizzaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
