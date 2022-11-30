import { Component, OnInit } from '@angular/core';
import { SecurityService } from 'src/securityservice';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup } from '@angular/forms';
import { FormControl } from '@angular/forms';
import { UpdateJob } from '../UpdateAJob';
import { PostJobService } from '../post-job.service';
import * as $ from 'jquery';

@Component({
  selector: 'app-updatejob',
  templateUrl: './updatejob.component.html',
  styleUrls: ['./updatejob.component.css']
})
export class UpdatejobComponent implements OnInit {
  name: string = "";
  message: any;
  id: any;
  jobId:any;
  currentModel: any = [];
  jobupdate: FormGroup;
  Job: UpdateJob = new UpdateJob(0, "", "", "", "", "", 0, true);
  private baseUrl = environment.baseUrl


  constructor(private securityService: SecurityService, private http: HttpClient, private fb: FormBuilder, private route: ActivatedRoute, private router: Router, private postjobservice: PostJobService) {
    this.jobupdate = this.fb.group({
      jobId: new FormControl(),
      jobDescription: new FormControl(),
      position: new FormControl(),
      jobTitle: new FormControl(),
      reportingManager: new FormControl(),
      jobType: new FormControl(),
      experience: new FormControl(),
      isJobOpen: new FormControl(),
    })
  }

  ngOnInit(): void {
    this.getUserInfo().subscribe(data => {
      this.name = data.name;
    });

    this.route.params.subscribe(params => {
      this.id = params['Id'];
     
    });

    this.postjobservice.getJobById(this.id).subscribe((result) => {
      // var tempCurrentModel = result.filter(key => item !== "");
      // console.log("tempCurrentModel ",tempCurrentModel);
      delete result["dateOfJobPosting"];
      this.currentModel = result;
      // console.log("currentModel ", this.currentModel);
    });
  }
  logout() {
    this.securityService.logout().subscribe(() => {
      this.securityService.removeToken();
      this.router.navigate(['/login']);
    });
  }
 
  updatejob(updatejobData:any) {
    if(this.jobupdate.valid){
      // console.log(this.jobupdate.value);
    // console.log("updatejobData ",JSON.stringify(updatejobData))
    // console.log("jobId ",updatejobData["jobId"]);
    this.postjobservice.updatejob(updatejobData["jobId"],updatejobData).subscribe((result) => {
      // console.log("updatejobData result ",result)
    });
    Swal.fire({
      title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
      text: 'Job Updated Successfully !',
      position: 'top',
      confirmButtonColor: '#F69659',
    }).then((result)=>{

      if (result.isConfirmed) {
        window.location.href='admin'   
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


