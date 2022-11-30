import { HttpClient, HttpRequest } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { SecurityService } from 'src/securityservice';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
@Component({
  selector: 'app-callback',
  templateUrl: './callback.component.html',
  styleUrls: ['./callback.component.css']
})
export class CallbackComponent implements OnInit {

  constructor( private route: ActivatedRoute, private router: Router, private securityService: SecurityService, private http: HttpClient) {}
  private baseUrl = environment.baseUrl
  name:string="";
  email:string="";
  userId:number
  ngOnInit(): void {
    this.route.queryParams.subscribe(p => {
      this.securityService.fetchToken(p['code'], p['state']).subscribe(data => {
        this.securityService.updateToken(data.accessToken);
        this.getUserInfo().subscribe(data => {
          // console.log(data);
          this.name=data.name;
          this.email=data.email;
          this.userId=data.userId;
          if(data.role === "1") {
            // console.log(data.role)
             this.router.navigate(['/admin']);
          }
          else if(data.role === "2") {
            // console.log(data.role)
             this.router.navigate(['/hiringManager']);
          }
          else{
            this.router.navigate(['/home']);
            this.http.post(`${this.baseUrl}/saveUser/${this.name}/${this.email}`,{ responseType: 'text' as 'json' }).subscribe(res => {
              // console.log(res)
            });
          }
        }) // subscribe function ends
      })
    })
  }

  getUserInfo(): Observable<any> {
    return this.http.get(environment.baseUrl+"/getUser");
  }
}
