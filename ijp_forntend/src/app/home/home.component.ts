import { Component, OnInit, ViewChild, ElementRef, Inject, TemplateRef, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { interval, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { SecurityService } from 'src/securityservice';
import { PostJobService } from '../post-job.service';
import Swal from 'sweetalert2';
import { UserProfileComponent } from '../user-profile/user-profile.component';
import { stringify } from 'flatted';
import { DOCUMENT } from '@angular/common';
import { debounce, debounceTime } from "rxjs/operators";
import * as $ from 'jquery';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  name: string = "";
  jobTitle: string = "";
  sessionIdx: any=0;
  private baseUrl = environment.baseUrl
  filterTerm!: string;
  jobPost: Array<any>;
  config: any;
  collection = { count: 60, data: [] }
  page: any;
  tableSize: any;
  btnText: any;
  jobId: any;
  appliedJobs: any;
  statusAvailable: Boolean = false;
  isDisabledBtn: Boolean = false;
  // @ViewChild('myDiv') divRef: ElementRef;
  checkProfileResponse: any;
  // isDisabled!: boolean;
  isDisabled: boolean = false;
  userId:number
  open:boolean=true;
  constructor(private http: HttpClient, private securityService: SecurityService
    , private router: Router, private postJobService: PostJobService) {

    this.jobPost = [];
    this.config = {
      currentPage: 1,
    };
  }

  count: number = 0;
  pageSize = 5;
  pageSizes = [5, 10, 15, 20];

  ngOnInit(): void {
    this.getUserInfo().subscribe(data => {
      // console.log("Actual data", data)
      this.name = data.name;
      this.userId=data.userId;
    });

    this.postJobService.checkProfile().subscribe(resp => {
      this.checkProfileResponse = resp;
      // console.log("check profile details are ", this.checkProfileResponse);
      this.postJobService.getAll().subscribe(data =>{
        this.jobPost= data;
      });
    })
  }

  getUserInfo(): Observable<any> {
    return this.http.get(environment.baseUrl+"/getUser");
  }

  logout() {
    this.securityService.logout().subscribe(() => {
      this.securityService.removeToken();
      this.router.navigate(['/login']);
    });
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


  checkProfile() {
    return this.http.get(environment.baseUrl + '/profileExists', { responseType: 'text' as 'json' }).subscribe(res => {
      // console.log(res)
    });
  }

  onApplyJob(jobId: any) {
    if (this.checkProfileResponse.includes("User profile exists")) {
      this.http.post(environment.baseUrl + '/applyJob/' + jobId+'/'+this.userId, { responseType: 'text' as 'json' })
        .subscribe(res => {
          // console.log("applied job details are", JSON.stringify(res))
          if (stringify(res).includes('You have already applied for this Job, cannot apply again!')) {
            Swal.fire({
              title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
              text: 'Already Applied',
              position: 'top',
              confirmButtonColor: '#F69659',
            })
          }
          else {
            Swal.fire({
              title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
              text: 'Successfully Applied To Job!',
              position: 'top',
              confirmButtonColor: '#F69659',
            })
            this.refresh();
          }
        });
    }
    else {
      Swal.fire({
        title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
        text: 'Please Complete Your Profile Before Applying To Job',
        position: 'top',
        confirmButtonColor: '#F69659',
      })
    }
  }

  eyeClicked(i: any) {
    sessionStorage.setItem("index", i)
    this.sessionIdx=i;
    $('#myModal').css("display", "block");
  }

  closeClicked() {
    $('#myModal').css("display", "none");
  }

  refresh(): void {
    setTimeout(function () {
      console.log("Time out in");
      window.location.reload();
    }, 2000);
  }

  onTableDataChange(event: any) {
    this.page = event;
  }

  onTableSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
  }

  handlePageChange(event: number): void {
    this.page = event;
  }

  handlePageSizeChange(event: any): void {
    this.pageSize = event.target.value;
    this.page = 1;
  }
}