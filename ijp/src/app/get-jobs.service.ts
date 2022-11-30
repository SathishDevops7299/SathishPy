import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GetJobsService {

  constructor( private http :HttpClient ){}
   getApplied(userId:any): Observable<any> {
    return this.http.get(environment.baseUrl+'/jobApplied/'+userId);
  }
}
