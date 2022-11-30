import { HttpClient ,HttpRequest, HttpEvent, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable}from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SaveuserService {
  private baseUrl = environment.baseUrl

  constructor(private http: HttpClient) { }
// userEmail:string=""
// resume:File | null = null;
// skills:Array<string>=[]
upload(file: File, skill: Array<string>):Observable<any>{
  const formData: FormData = new FormData();

  formData.append('file', file);
  formData.append('userDto', JSON.stringify(skill));

  return this.http.post(`${this.baseUrl}/updateUser`,formData,{ responseType:'json' });
    // console.warn(this.userEmail+'\n'+this.resume+'\n'+this.skills);
    // console.log("file updated successfully");
  }
}