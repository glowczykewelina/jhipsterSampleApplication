import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PizzaWithSize } from './pizza-with-size.model';
import { PizzaWithSizeService } from './pizza-with-size.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-pizza-with-size',
    templateUrl: './pizza-with-size.component.html'
})
export class PizzaWithSizeComponent implements OnInit, OnDestroy {
pizzaWithSizes: PizzaWithSize[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pizzaWithSizeService: PizzaWithSizeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.pizzaWithSizeService.query().subscribe(
            (res: HttpResponse<PizzaWithSize[]>) => {
                this.pizzaWithSizes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPizzaWithSizes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PizzaWithSize) {
        return item.id;
    }
    registerChangeInPizzaWithSizes() {
        this.eventSubscriber = this.eventManager.subscribe('pizzaWithSizeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
