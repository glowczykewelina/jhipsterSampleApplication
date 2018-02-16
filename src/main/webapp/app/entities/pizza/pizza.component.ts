import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Pizza } from './pizza.model';
import { PizzaService } from './pizza.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-pizza',
    templateUrl: './pizza.component.html'
})
export class PizzaComponent implements OnInit, OnDestroy {
pizzas: Pizza[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pizzaService: PizzaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.pizzaService.query().subscribe(
            (res: HttpResponse<Pizza[]>) => {
                this.pizzas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPizzas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Pizza) {
        return item.id;
    }
    registerChangeInPizzas() {
        this.eventSubscriber = this.eventManager.subscribe('pizzaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
