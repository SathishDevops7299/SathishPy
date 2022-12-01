import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})
export class UpdateJobService {

  constructor( private http :HttpClient )
  { }

  //  public updateJob(jobId:number)
  //  {
  //     return this.http.post(environment.baseUrl+"/update-job/"+jobId, {responseType:'text' as 'json'});
  //  }

  getAll(): Observable<any> {
   return this.http.get(environment.baseUrl+'/getJobs');
 }
 
}
