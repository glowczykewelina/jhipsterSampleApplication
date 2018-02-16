import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Pizza } from './pizza.model';
import { PizzaService } from './pizza.service';

@Component({
    selector: 'jhi-pizza-detail',
    templateUrl: './pizza-detail.component.html'
})
export class PizzaDetailComponent implements OnInit, OnDestroy {

    pizza: Pizza;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pizzaService: PizzaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPizzas();
    }

    load(id) {
        this.pizzaService.find(id)
            .subscribe((pizzaResponse: HttpResponse<Pizza>) => {
                this.pizza = pizzaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPizzas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pizzaListModification',
            (response) => this.load(this.pizza.id)
        );
    }
}
