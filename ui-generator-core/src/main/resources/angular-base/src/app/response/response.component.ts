import {Component, Input, OnInit, OnDestroy} from '@angular/core';
import {HttpResponse, HttpErrorResponse, HttpResponseBase} from "@angular/common/http";

@Component({
    selector: 'app-response',
    templateUrl: './response.component.html',
    styleUrls: ['./response.component.css']
})
export class ResponseComponent implements OnInit, OnDestroy {

    private _response: HttpResponseBase | null = null;
    private _responseBody: any | null = null;

    @Input() set response(value: HttpResponseBase | null) {
        if (value == null) {
            return;
        }
        this._response = value
        if (value instanceof HttpResponse) {
            this._responseBody = value.body;
        } else if(value instanceof  HttpErrorResponse) {
            this._responseBody = value.error;
        }
    }

    get response(): HttpResponseBase | null {
        return this._response;
    }

    get responseBody(): any | null {
        return this._responseBody;
    }

    ngOnInit(): void {
    }

    ngOnDestroy(): void {
    }
}