import { HttpClient } from '@angular/common/http';
import { Injectable } from "@angular/core"; 
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
})
export class SecurityService {

    private authorizeEndpoint = '/oauth2/authorization/azure-client'
    private tokenEndpoint = '/login/oauth2/code/'
    private baseUrl = environment.authUrl;
    private tokenkey = 'token';
    private logoutUrl = environment.logoutUrl;

    constructor(private http: HttpClient) {}

    login() {
        window.open(this.baseUrl + this.authorizeEndpoint, '_self');
    }

    updateToken(token: any) {
        sessionStorage.setItem(this.tokenkey, token);
        localStorage.setItem(this.tokenkey, token);
    }

    fetchToken(code:any, state: any): Observable<any> {
        console.log("Fetchtoken:"+this.baseUrl + this.tokenEndpoint + '?code=' + code + '&state=' + state);
        return this.http.get(this.baseUrl + this.tokenEndpoint + '?code=' + code + '&state=' + state);
    }

    getToken() {
       return sessionStorage.getItem(this.tokenkey);  
    }

    isLoggedIn(): boolean {
        const token = this.getToken();
        return token!=null;
    }

    removeToken() {
        sessionStorage.clear();
        localStorage.removeItem(this.tokenkey);
         window.location.href = this.logoutUrl;
      }
    
    logout(): Observable<any> {
      return this.http.post(this.baseUrl + '/logout', this.getToken());
    }
}
