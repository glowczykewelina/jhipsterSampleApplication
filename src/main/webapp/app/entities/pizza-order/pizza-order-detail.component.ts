import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PizzaOrder } from './pizza-order.model';
import { PizzaOrderService } from './pizza-order.service';

@Component({
    selector: 'jhi-pizza-order-detail',
    templateUrl: './pizza-order-detail.component.html'
})
export class PizzaOrderDetailComponent implements OnInit, OnDestroy {

    pizzaOrder: PizzaOrder;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pizzaOrderService: PizzaOrderService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPizzaOrders();
    }

    load(id) {
        this.pizzaOrderService.find(id)
            .subscribe((pizzaOrderResponse: HttpResponse<PizzaOrder>) => {
                this.pizzaOrder = pizzaOrderResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPizzaOrders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pizzaOrderListModification',
            (response) => this.load(this.pizzaOrder.id)
        );
    }
}
