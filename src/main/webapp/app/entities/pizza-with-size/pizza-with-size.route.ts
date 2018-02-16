import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PizzaWithSizeComponent } from './pizza-with-size.component';
import { PizzaWithSizeDetailComponent } from './pizza-with-size-detail.component';
import { PizzaWithSizePopupComponent } from './pizza-with-size-dialog.component';
import { PizzaWithSizeDeletePopupComponent } from './pizza-with-size-delete-dialog.component';

export const pizzaWithSizeRoute: Routes = [
    {
        path: 'pizza-with-size',
        component: PizzaWithSizeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PizzaWithSizes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pizza-with-size/:id',
        component: PizzaWithSizeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PizzaWithSizes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pizzaWithSizePopupRoute: Routes = [
    {
        path: 'pizza-with-size-new',
        component: PizzaWithSizePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PizzaWithSizes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pizza-with-size/:id/edit',
        component: PizzaWithSizePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PizzaWithSizes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pizza-with-size/:id/delete',
        component: PizzaWithSizeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PizzaWithSizes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
