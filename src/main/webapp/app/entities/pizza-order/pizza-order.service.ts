import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PizzaOrder } from './pizza-order.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PizzaOrder>;

@Injectable()
export class PizzaOrderService {

    private resourceUrl =  SERVER_API_URL + 'api/pizza-orders';

    constructor(private http: HttpClient) { }

    create(pizzaOrder: PizzaOrder): Observable<EntityResponseType> {
        const copy = this.convert(pizzaOrder);
        return this.http.post<PizzaOrder>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(pizzaOrder: PizzaOrder): Observable<EntityResponseType> {
        const copy = this.convert(pizzaOrder);
        return this.http.put<PizzaOrder>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PizzaOrder>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PizzaOrder[]>> {
        const options = createRequestOption(req);
        return this.http.get<PizzaOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PizzaOrder[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PizzaOrder = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PizzaOrder[]>): HttpResponse<PizzaOrder[]> {
        const jsonResponse: PizzaOrder[] = res.body;
        const body: PizzaOrder[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PizzaOrder.
     */
    private convertItemFromServer(pizzaOrder: PizzaOrder): PizzaOrder {
        const copy: PizzaOrder = Object.assign({}, pizzaOrder);
        return copy;
    }

    /**
     * Convert a PizzaOrder to a JSON which can be sent to the server.
     */
    private convert(pizzaOrder: PizzaOrder): PizzaOrder {
        const copy: PizzaOrder = Object.assign({}, pizzaOrder);
        return copy;
    }
}
