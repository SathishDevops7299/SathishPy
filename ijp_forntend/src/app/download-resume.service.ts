import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})
export class DownloadResumeService {

  constructor(private http :HttpClient) { }
  
  getResume(userId:any): Observable<any> {
    return this.http.get(environment.baseUrl+"/getFile/"+userId, {responseType: 'arraybuffer'});
  }
  userViewResume(): Observable<any> {
    return this.http.get(environment.baseUrl+"/getFile", {responseType: 'arraybuffer'});
    }
}
