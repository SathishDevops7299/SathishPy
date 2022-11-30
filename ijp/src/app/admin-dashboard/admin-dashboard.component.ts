import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { SecurityService } from 'src/securityservice';
import { DisplayAdminJobsService } from '../display-admin-jobs.service';
import { PostJobService } from '../post-job.service';
import { UpdateJobService } from '../update-job.service';
import * as $ from "jquery";

import Swal from 'sweetalert2';
@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  name: string = "";
  countClick: boolean = false;
  role: any;
  filterTerm!: string;
  config: any;
  page: any;
  tableSize: any;
  close:boolean=false
  open:boolean=true;
  collection = { count: 60, data: [] }
  private baseUrl = environment.baseUrl
  jobPosts: Array<any>;
  constructor(private http: HttpClient, private securityService: SecurityService, private router: Router, private service: PostJobService) {
    this.jobPosts = [];
    this.config = {
      // itemsPerPage: 5,
      currentPage: 1,
    };
  }
  sessionIdx: any=0;
  count: number = 0;
  pageSize = 5;
  pageSizes = [5, 10, 15, 20];


  ngOnInit(): void {

    this.service.getAll().subscribe(data => {
      this.jobPosts = data;
      // console.log(this.jobPost);
    });

    this.getUserInfo().subscribe(data => {
      this.name = data.name;
      this.role = data.role;
      // console.log(this.role);
    });
  }


  getUserInfo(): Observable<any> {
    return this.http.get(environment.baseUrl + "/getUser");
  }

  logout() {
    this.securityService.logout().subscribe(() => {
      this.securityService.removeToken();
      this.router.navigate(['/login']);
    });
  }

  eyeClicked(i: any) {
    sessionStorage.setItem("index", i)
    this.sessionIdx=i;
    $('#myModal').css("display", "block");
  }
  // addressbookoClicked(i: any) {
  //   sessionStorage.setItem("index", i)
  //   this.sessionIdx=i;
  //   $('#myModal').css("display", "block");
  // }
  closeClicked() {
    $('#myModal').css("display", "none");
  }

  chatClicked() {
    if ($("#wrapper").attr('style') == "display: block;") {
      $("#wrapper").removeAttr("style")
      console.log($("#wrapper").attr('style'));
    } else {
      $('#wrapper').css("display", "block")
      console.log($("#wrapper").attr('style'));
    }
  }

  onDeleteJob(jobId: any) {
    Swal.fire({
      title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
      text: 'Do you want to delete this job ?',
      position: 'top',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      confirmButtonColor: '#F69659',
      cancelButtonText: 'No',
      cancelButtonColor: '#F69659'
    }).then((result) => {

      if (result.isConfirmed) {
        let resp = this.http.post(environment.baseUrl + '/deleteJob/' + jobId, { responseType: 'text' as 'json' }).subscribe(res => {
          // console.log(res)
        });
        window.location.reload();
      } else if (result.isDismissed) {
        // console.log('Clicked No!');
      }
    })


  }


  onGetJobById(jobId: any) {
    // console.log("update job button is clicked")
    this.service.getAll().subscribe(data => {
      this.jobPosts = data;
      // console.log(this.jobPost);
      let resp = this.http.get(environment.baseUrl + '/getJobById/' + jobId,
        { responseType: 'text' as 'json' }).subscribe(res =>{
          //  console.log("result is", res)
        });
      // console.log("result is ", res);
      // this.router.navigate(['/updatejob']);

    });
  }

  onCloseJob(jobId: any){
    // console.log("close job button is clicked")
    let resp = this.http.get(environment.baseUrl + '/closeJob/' + jobId+'/'+this.close).subscribe(res => 
      {
      // console.log("result is", res)
      Swal.fire({
        title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
        text: 'Job closed successfully !',
        position: 'top',
        confirmButtonColor: '#F69659',
      }).then((result) =>{
        if(result.isConfirmed){
          window.location.reload();
        }
      })
    });
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
