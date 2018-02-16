import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PizzaWithSize } from './pizza-with-size.model';
import { PizzaWithSizeService } from './pizza-with-size.service';

@Component({
    selector: 'jhi-pizza-with-size-detail',
    templateUrl: './pizza-with-size-detail.component.html'
})
export class PizzaWithSizeDetailComponent implements OnInit, OnDestroy {

    pizzaWithSize: PizzaWithSize;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pizzaWithSizeService: PizzaWithSizeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPizzaWithSizes();
    }

    load(id) {
        this.pizzaWithSizeService.find(id)
            .subscribe((pizzaWithSizeResponse: HttpResponse<PizzaWithSize>) => {
                this.pizzaWithSize = pizzaWithSizeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPizzaWithSizes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pizzaWithSizeListModification',
            (response) => this.load(this.pizzaWithSize.id)
        );
    }
}
