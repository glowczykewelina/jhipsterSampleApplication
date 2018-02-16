import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PizzaComponent } from './pizza.component';
import { PizzaDetailComponent } from './pizza-detail.component';
import { PizzaPopupComponent } from './pizza-dialog.component';
import { PizzaDeletePopupComponent } from './pizza-delete-dialog.component';

export const pizzaRoute: Routes = [
    {
        path: 'pizza',
        component: PizzaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pizzas'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pizza/:id',
        component: PizzaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pizzas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pizzaPopupRoute: Routes = [
    {
        path: 'pizza-new',
        component: PizzaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pizzas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pizza/:id/edit',
        component: PizzaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pizzas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pizza/:id/delete',
        component: PizzaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pizzas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
