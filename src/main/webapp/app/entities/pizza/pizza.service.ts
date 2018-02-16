import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Pizza } from './pizza.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Pizza>;

@Injectable()
export class PizzaService {

    private resourceUrl =  SERVER_API_URL + 'api/pizzas';

    constructor(private http: HttpClient) { }

    create(pizza: Pizza): Observable<EntityResponseType> {
        const copy = this.convert(pizza);
        return this.http.post<Pizza>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(pizza: Pizza): Observable<EntityResponseType> {
        const copy = this.convert(pizza);
        return this.http.put<Pizza>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Pizza>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Pizza[]>> {
        const options = createRequestOption(req);
        return this.http.get<Pizza[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Pizza[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Pizza = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Pizza[]>): HttpResponse<Pizza[]> {
        const jsonResponse: Pizza[] = res.body;
        const body: Pizza[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Pizza.
     */
    private convertItemFromServer(pizza: Pizza): Pizza {
        const copy: Pizza = Object.assign({}, pizza);
        return copy;
    }

    /**
     * Convert a Pizza to a JSON which can be sent to the server.
     */
    private convert(pizza: Pizza): Pizza {
        const copy: Pizza = Object.assign({}, pizza);
        return copy;
    }
}
