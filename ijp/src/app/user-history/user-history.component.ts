import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Routes } from '@angular/router';
import { Router } from '@angular/router';
import { SecurityService } from 'src/securityservice';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { GetJobsService } from '../get-jobs.service';
import * as $ from 'jquery';

@Component({
  selector: 'app-user-history',
  templateUrl: './user-history.component.html',
  styleUrls: ['./user-history.component.css']
})
export class UserHistoryComponent implements OnInit {

  name: string = "";
  userId: any
  email:string="";
  filterTerm!: string;
  getJobs :Array<any>;
  config: any;
   collection = { count: 60, data: []}
  page: any;
  tableSize: any;
  count: number = 0;
  pageSize = 5;
  pageSizes = [5,10,15,20];
  constructor(private http: HttpClient, private securityService: SecurityService, private router: Router,private service:GetJobsService) 
  {this.getJobs=[ ]; 
    this.config = {
       currentPage: 1,
       };
     }
     

  ngOnInit(): void {

    this.getUserInfo().subscribe(data => {
      this.userId=data.userId;  
      this.name=data.name;
      this.email=data.email;
      this.service.getApplied(this.userId).subscribe(data=>{
      this.getJobs=data;
      // console.log(this.getJobs);
    })
  });
  
  
}
  getUserInfo(): Observable<any> {
    return this.http.get(environment.baseUrl +"/getUser");
  }
  logout() {
    this.securityService.logout().subscribe(() => {
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
