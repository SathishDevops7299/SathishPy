import { SecurityService } from './securityservice';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core"; 
import { Observable } from "rxjs";

@Injectable()
export class AuthHeaderInterceptor implements HttpInterceptor {

    constructor(private securityService: SecurityService) {

    }

    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        const req = request.clone({
            setHeaders: {
                Accept: 'application/json',
                Authorization: 'Bearer '+ this.securityService.getToken()
            }
        });
        return next.handle(req);
    }
    
}