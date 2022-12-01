import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})
export class PostJobService {

  constructor( private http :HttpClient ) { }
  
   getAll(): Observable<any> {
    return this.http.get(environment.baseUrl+'/getJobs');
  }
  getJobById(id: any): Observable<any> {
    return this.http.get(environment.baseUrl+'/getJobById/'+id);
  }
   updatejob(jobId: any,jobData:any): Observable<any> {
     return this.http.post(environment.baseUrl+'/updateJob/'+jobId,jobData);
   }

   checkJobApplyStatus(): Observable<any>{
    return this.http.get((environment.baseUrl+'/getAppliedJobs_Status'))
  }
   checkProfile(): Observable<any>
   {
     return this.http.get(environment.baseUrl+'/profileExists',{responseType:'text' as 'json'})
     //.subscribe(res =>     console.log("FROM ANGULAR APPLICATION CHECK PROFILE RESPONSE IS ",res));
   }
}
