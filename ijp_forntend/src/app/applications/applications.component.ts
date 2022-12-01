import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SecurityService } from 'src/securityservice';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { DisplayAdminJobsService } from '../display-admin-jobs.service';
import { DownloadResumeService } from '../download-resume.service';
import Swal from 'sweetalert2';
import * as $ from "jquery";

@Component({
  selector: 'app-applications',
  templateUrl: './applications.component.html',
  styleUrls: ['./applications.component.css']
})
export class ApplicationsComponent implements OnInit {

  name: string = "";
  filterTerm:any;
  displayJobs: Array<any>;
  email:any;
  role:any;
  filterTerms:any;
  jobId:any;
  applications: Array<any> = [];
  count: number = 0;
  config: any;
  collection = { count: 60, data: []}
  page: any;
  selected: boolean = true;
  notSelected: boolean = false;
  tableSize: any;
  blobURL : string = "";
  constructor(private http: HttpClient, private securityService: SecurityService, private router: Router, private service: DownloadResumeService, private Dservice: DisplayAdminJobsService) {
    this.displayJobs = [];
    this.config = {
      // itemsPerPage: 5,
       currentPage: 1,
       };
     }
     
    
     pageSize = 5;
     pageSizes = [5,10,15,20];
  

  ngOnInit(): void {
    this.Dservice.getAll().subscribe(data => {
      this.displayJobs = data;
      console.log(this.displayJobs);
    });
    
   
    this.getUserInfo().subscribe(data => {
      this.name = data.name;
      this.role = data.role;
    });
  }

  viewResume(userId:number) {

    return this.service.getResume(userId).subscribe(data => {
      let blob = new Blob([data], {type : 'application/pdf'});
      let url = window.URL.createObjectURL(blob);
      window.open(url);
    });

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
    // alert("hema")
    $('#wrapper').css("display:block")
    $('#wrapper').show();
  }

  chatClosed() {
    // alert("hema")
    // $('#wrapper').css("display:none")
    $('#wrapper').hide();
  }

  showSelectAlert(jobId: any,userId: any)
  {
     Swal.fire({
      title:"<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal'+ "</h5>" ,
      text: 'Do You Want To Select This Candidate ?',
      position: 'top',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      confirmButtonColor:'#F69659',
      cancelButtonText: 'No',
      cancelButtonColor:'#F69659'
    }).then((result) => {

      if (result.isConfirmed) {
        console.log('Clicked Yes!');
        this.http.get(environment.baseUrl+"/selectApplicant/"+jobId+"/"+userId+"/"+this.selected,{ responseType: 'text' as 'json' }).subscribe(res =>{
           console.log(res)
        });
        location.reload();
      } else if (result.isDismissed) {
        console.log('Clicked No!');
      }
    })
  
  }

  showRejectAlert(jobId: any,userId: any)
  {
     Swal.fire({
      title:"<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal'+ "</h5>" ,
      text: 'Do You Want To Reject This Candidate ?',
      position: 'top',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      confirmButtonColor:'#F69659',
      cancelButtonText: 'No',
      cancelButtonColor:'#F69659',
      
    }).then((result) => {
      if (result.isConfirmed) {
        console.log('Clicked Yes!');
        this.http.get(environment.baseUrl+"/selectApplicant/"+jobId+"/"+userId+"/"+this.notSelected,{ responseType: 'text' as 'json' }).subscribe(res =>{
          console.log(res)
       });
       location.reload();
      } else if (result.isDismissed) {
        console.log('Clicked No!');
      }
    })
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






