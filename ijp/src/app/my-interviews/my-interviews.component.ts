import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Routes } from '@angular/router';
import { Router } from '@angular/router';
import { SecurityService } from 'src/securityservice';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import * as $ from 'jquery';

@Component({
  selector: 'app-my-interviews',
  templateUrl: './my-interviews.component.html',
  styleUrls: ['./my-interviews.component.css']
})

export class MyInterviewsComponent implements OnInit {

  name: string = "";
  filterTerm!: string;
  config: any;
   collection = { count: 60, data: []}
 page: any;
 tableSize: any;
 open:boolean = true;
 jobPost:Array<any>=[]
  constructor(private http: HttpClient, private securityService: SecurityService, private router: Router) {
    this.config = {
      // itemsPerPage: 5,
       currentPage: 1,
       };
     }
     
     count: number = 0;
     pageSize = 5;
     pageSizes = [5,10,15,20];
   

  ngOnInit(): void {

    this.getUserInfo().subscribe(data => {
      this.name = data.name;
    });    
  }

  
  getUserInfo(): Observable<any> {
    return this.http.get(environment.baseUrl+"/getUser" );
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

  onTableDataChange(event: any) {
    this.page = event;
    // this.fetchPosts();
  }
  onTableSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    // this.fetchPosts();
  }
  handlePageChange(event: number): void {
    this.page = event;
    // this.retrieveTutorials();
  }
  handlePageSizeChange(event: any): void {
     this.pageSize = event.target.value;
    this.page = 1;
    // this.retrieveTutorials();
  }
}
