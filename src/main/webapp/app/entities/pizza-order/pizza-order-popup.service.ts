import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PizzaOrder } from './pizza-order.model';
import { PizzaOrderService } from './pizza-order.service';

@Injectable()
export class PizzaOrderPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pizzaOrderService: PizzaOrderService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.pizzaOrderService.find(id)
                    .subscribe((pizzaOrderResponse: HttpResponse<PizzaOrder>) => {
                        const pizzaOrder: PizzaOrder = pizzaOrderResponse.body;
                        this.ngbModalRef = this.pizzaOrderModalRef(component, pizzaOrder);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.pizzaOrderModalRef(component, new PizzaOrder());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    pizzaOrderModalRef(component: Component, pizzaOrder: PizzaOrder): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pizzaOrder = pizzaOrder;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
