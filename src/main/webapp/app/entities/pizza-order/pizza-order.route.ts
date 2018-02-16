import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PizzaOrderComponent } from './pizza-order.component';
import { PizzaOrderDetailComponent } from './pizza-order-detail.component';
import { PizzaOrderPopupComponent } from './pizza-order-dialog.component';
import { PizzaOrderDeletePopupComponent } from './pizza-order-delete-dialog.component';

export const pizzaOrderRoute: Routes = [
    {
        path: 'pizza-order',
        component: PizzaOrderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PizzaOrders'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pizza-order/:id',
        component: PizzaOrderDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PizzaOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pizzaOrderPopupRoute: Routes = [
    {
        path: 'pizza-order-new',
        component: PizzaOrderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PizzaOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pizza-order/:id/edit',
        component: PizzaOrderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PizzaOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pizza-order/:id/delete',
        component: PizzaOrderDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PizzaOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
