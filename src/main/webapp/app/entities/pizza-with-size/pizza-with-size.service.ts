import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PizzaWithSize } from './pizza-with-size.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PizzaWithSize>;

@Injectable()
export class PizzaWithSizeService {

    private resourceUrl =  SERVER_API_URL + 'api/pizza-with-sizes';

    constructor(private http: HttpClient) { }

    create(pizzaWithSize: PizzaWithSize): Observable<EntityResponseType> {
        const copy = this.convert(pizzaWithSize);
        return this.http.post<PizzaWithSize>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(pizzaWithSize: PizzaWithSize): Observable<EntityResponseType> {
        const copy = this.convert(pizzaWithSize);
        return this.http.put<PizzaWithSize>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PizzaWithSize>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PizzaWithSize[]>> {
        const options = createRequestOption(req);
        return this.http.get<PizzaWithSize[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PizzaWithSize[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PizzaWithSize = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PizzaWithSize[]>): HttpResponse<PizzaWithSize[]> {
        const jsonResponse: PizzaWithSize[] = res.body;
        const body: PizzaWithSize[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PizzaWithSize.
     */
    private convertItemFromServer(pizzaWithSize: PizzaWithSize): PizzaWithSize {
        const copy: PizzaWithSize = Object.assign({}, pizzaWithSize);
        return copy;
    }

    /**
     * Convert a PizzaWithSize to a JSON which can be sent to the server.
     */
    private convert(pizzaWithSize: PizzaWithSize): PizzaWithSize {
        const copy: PizzaWithSize = Object.assign({}, pizzaWithSize);
        return copy;
    }
}
