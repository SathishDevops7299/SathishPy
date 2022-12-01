import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { SecurityService } from 'src/securityservice';
import { Component, OnInit } from '@angular/core';
import * as $ from 'jquery';

@Component({
  selector: 'app-tool-admin',
  templateUrl: './tool-admin.component.html',
  styleUrls: ['./tool-admin.component.css']
})
export class ToolAdminComponent implements OnInit {
  name: string = "";
  private baseUrl = environment.baseUrl
  filterTerm!: string;

  constructor(private http: HttpClient, private securityService: SecurityService, private router: Router)
  {}
  ngOnInit(): void {

    this.getUserInfo().subscribe(data => {
      this.name = data.name;
    });    
  }
  getUserInfo(): Observable<any> {
    return this.http.get(environment.baseUrl+"/getUser");
  }
  logout() {
    this.securityService.logout() .subscribe(() => {
      this.securityService.removeToken();
      this.router.navigate(['/login']);
    });
  }

  chatClicked() {
    $('#wrapper').show();
  }

  chatClosed() {
    $('#wrapper').hide();
  }

  }


