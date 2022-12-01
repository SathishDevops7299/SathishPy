import { Component, OnInit } from '@angular/core';
import { PostJob } from '../PostAJob';
import { FormGroup } from '@angular/forms';
import { FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { SecurityService } from 'src/securityservice';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import * as $ from 'jquery';

@Component({
  selector: 'app-job-posting',
  templateUrl: './job-posting.component.html',
  styleUrls: ['./job-posting.component.css']
})
export class JobPostingComponent implements OnInit {
  name: string = "";
  userId:any;
  message: any;
  // Job: PostJob = new PostJob(0," ", "", "", "", "",  0, true);
  private baseUrl = environment.baseUrl

  jobPost = new FormGroup({
    jobDesc: new FormControl(),
    jobPosition: new FormControl(),
    jobName: new FormControl(),
    reportingManager: new FormControl(),
    jobType: new FormControl(),
    jobExperience: new FormControl(),
    isJobOpen: new FormControl(),
  })

  constructor(private securityService: SecurityService, private http: HttpClient, private router: Router) { }

  ngOnInit(): void {

    this.getUserInfo().subscribe(data => {
      this.name = data.name;
      this.userId=data.userId;
    });
  }
  logout() {
    this.securityService.logout().subscribe(() => {
      this.securityService.removeToken();
      this.router.navigate(['/login']);
    });
  }
  onSubmit() {
    if(this.jobPost.valid){
    // console.log(this.jobPost.value);
    let resp = this.http.post(`${this.baseUrl}/postJob/${this.userId}`, this.jobPost.value, { responseType: 'text' as 'json' }).subscribe(res => {
      // console.log(res)
    });
      Swal.fire({
        title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
        text: 'Job Posted Successfully !',
        position: 'top',
        confirmButtonColor: '#F69659',
      }).then((result)=>{

        if (result.isConfirmed) {
          window.location.reload();
  
        }
      } )
  }
    else{
      Swal.fire({
        title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
        text: 'PLEASE FILL OUT ALL THE FIELDS !',
        position: 'top',
        confirmButtonColor: '#F69659',
      })
    }
  }

  getUserInfo(): Observable<any> {
    return this.http.get(environment.baseUrl+"/getUser");
  }
 
  chatClicked() {
    $('#wrapper').show();
  }

  chatClosed() {
    $('#wrapper').hide();
  }
}
