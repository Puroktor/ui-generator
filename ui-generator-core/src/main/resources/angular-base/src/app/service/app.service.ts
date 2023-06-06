import {Injectable} from '@angular/core';
import {HttpClient, HttpParams, HttpResponse, HttpHeaders} from "@angular/common/http";
import {Observable} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AppService {

    constructor(private http: HttpClient) {
    }

    public submitForm(requestType: string, mapping: string, values: any): Observable<HttpResponse<any>> {
        let queryParams = new HttpParams();
        let requestBody: any = {};
        for (const param of Object.entries(values)) {
            let paramName = param[0].split('_');
            if (paramName[0] == 'path') {
                mapping = mapping.replace(`{${paramName[1]}}`, param[1] as string)
            } else if (paramName[0] == 'query') {
                queryParams = queryParams.set(paramName[1], param[1] as string);
            } else if (paramName[0] == 'body') {
                let element = requestBody
                for (let i = 1; i < paramName.length - 1; i++) {
                    if (!element[paramName[i]]) {
                        element[paramName[i]] = {}
                    }
                    element = element[paramName[i]]
                }
                let innerFieldName = paramName[paramName.length - 1]
                element[innerFieldName] = param[1];
            }
        }

        const headers = new HttpHeaders()
            .set('Accept', 'application/json')
            .set('Content-Type', 'application/json')

        return this.http.request<HttpResponse<any>>(
            requestType, mapping, {headers: headers, params: queryParams, body: JSON.stringify(requestBody), observe: 'response'}
        );
    }
}