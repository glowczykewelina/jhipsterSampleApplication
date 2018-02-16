import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PizzaWithSize } from './pizza-with-size.model';
import { PizzaWithSizeService } from './pizza-with-size.service';

@Injectable()
export class PizzaWithSizePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pizzaWithSizeService: PizzaWithSizeService

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
                this.pizzaWithSizeService.find(id)
                    .subscribe((pizzaWithSizeResponse: HttpResponse<PizzaWithSize>) => {
                        const pizzaWithSize: PizzaWithSize = pizzaWithSizeResponse.body;
                        this.ngbModalRef = this.pizzaWithSizeModalRef(component, pizzaWithSize);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.pizzaWithSizeModalRef(component, new PizzaWithSize());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    pizzaWithSizeModalRef(component: Component, pizzaWithSize: PizzaWithSize): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pizzaWithSize = pizzaWithSize;
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
