import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SecurityService } from 'src/securityservice';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { PostJobService } from '../post-job.service';
import * as $ from "jquery";
@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  name: string = "";
  role:any;
  filterTerm!: string;
  getPostedJobs :Array<any>;
  config: any;
   collection = { count: 60, data: []}
  page: any;
  tableSize: any;
  constructor(private http: HttpClient, private securityService: SecurityService, private router: Router,private service:PostJobService) 
  { this.getPostedJobs=[ ]; 
    this.config = {
      // itemsPerPage: 5,
       currentPage: 1,
       };
     }
     
     count: number = 0;
     pageSize = 5;
     pageSizes = [5,10,15,20];

  ngOnInit(): void {

    this.service.getAll().subscribe(data=>{
      this.getPostedJobs=data;
      // console.log(this.getPostedJobs);
    });


    this.getUserInfo().subscribe(data => {
      this.name = data.name;
      this.role = data.role;
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
