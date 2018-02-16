import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PizzaOrder } from './pizza-order.model';
import { PizzaOrderService } from './pizza-order.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-pizza-order',
    templateUrl: './pizza-order.component.html'
})
export class PizzaOrderComponent implements OnInit, OnDestroy {
pizzaOrders: PizzaOrder[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pizzaOrderService: PizzaOrderService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.pizzaOrderService.query().subscribe(
            (res: HttpResponse<PizzaOrder[]>) => {
                this.pizzaOrders = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPizzaOrders();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PizzaOrder) {
        return item.id;
    }
    registerChangeInPizzaOrders() {
        this.eventSubscriber = this.eventManager.subscribe('pizzaOrderListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
