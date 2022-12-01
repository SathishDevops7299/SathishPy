import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { SecurityService } from 'src/securityservice';
import { UploadFilesService } from '../fileupload.service';
import { HttpRequest,HttpResponse,HttpEventType } from '@angular/common/http';
import { FormGroup,FormControl } from '@angular/forms';
import { Component, ErrorHandler, Injectable, OnInit } from '@angular/core';
import { SaveUserDetail } from '../SaveUserDetail';
import { SaveuserService } from '../saveuser.service';
import Swal from 'sweetalert2';
import { DatePipe } from '@angular/common';
import { DownloadResumeService } from '../download-resume.service';
import { DisplayAdminJobsService } from '../display-admin-jobs.service';
import * as $ from 'jquery';



@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css'],
  providers:[DatePipe,UserProfileComponent]
})
@Injectable({
  providedIn:'any'
})



export class UserProfileComponent implements OnInit {
  name: string = "";
  filterTerm!: string;
  skill: Array<string> = [];
  selectedFiles?: FileList;
  currentFile?: File | null;
  progress = 0;
  message = '';
  fileInfos?: Observable<any>;
  firstTimeUser=0;
  displayJobs: Array<any>;
  email:any;
  jobId:any;
  userId:any
  private baseUrl = environment.baseUrl
  constructor(private http: HttpClient, private saveUserDetailService: SaveuserService, private securityService: SecurityService,private router: Router,private service: DownloadResumeService,private Dservice: DisplayAdminJobsService ) {
  this.displayJobs = [];
  }
  ngOnInit(): void {
    
    this.getUserInfo().subscribe(data => {
      this.name = data.name;
      this.userId=data.userId
      // console.log(this.userId)
    });  
    this.Dservice.getAll().subscribe(data => {
      this.displayJobs = data;
      // console.log(this.displayJobs);
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
  viewResume() {
    return this.service.userViewResume().subscribe(data => {
    if(data==null){
      Swal.fire({
        title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
        text: 'Profile Not Exist !',
        position: 'top',
        confirmButtonColor: '#F69659',
      }).then((result)=>{
        if (result.isConfirmed) {
          window.location.reload();
        }
      } )
    }else{
    let blob = new Blob([data], {type : 'application/pdf'});
    let url = window.URL.createObjectURL(blob);
    window.open(url);
    }
    });
    
  }
  selectFile(event: any): void {
   
    this.selectedFiles = event.target.files;
    console.log(this.selectedFiles?.item(0));
  }

 selectText(event : any): void {
    this.skill.push(event.target.value);
    console.log(this.skill);
  }


 upload(): void {
    if(this.selectedFiles==null || this.skill.length==0)
    {
      Swal.fire({
        title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
        text: 'PLEASE FILL OUT ALL FILEDS !',
        position: 'top',
        confirmButtonColor: '#F69659',
      })
    }else{
    this.progress = 0;



   if (this.selectedFiles && (this.selectedFiles.item(0)?.type === "application/pdf" || this.selectedFiles.item(0)?.type === "application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
      const file: File | null = this.selectedFiles.item(0);



   if (file) {
        this.currentFile = file;
        this.saveUserDetailService.upload(this.currentFile, this.skill).subscribe({
          next: (event: any) => {
            if (event.type === HttpEventType.UploadProgress) {
              this.progress = Math.round(100 * event.loaded / event.total);
            } else if (event instanceof HttpResponse) {
              this.message = event.body.message;
             
            }
         },
          error: (err: any) => {
            console.log(err);
            this.progress = 0;
           if (err.error && err.error.message) {
              this.message = err.error.message;
            } else {
              this.message = 'Could not upload the file!';
            }
           this.currentFile = undefined;
          }
        });   
      }
     this.selectedFiles = undefined;
    }
    Swal.fire({
      title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
      text: 'Saved Profile Successfully !',
      position: 'top',
      confirmButtonColor: '#F69659',
    }).then((result)=>{
      if (result.isConfirmed) {
        window.location.reload();
      }
    } )
    }
  
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

  // viewResume() {
  //   return this.service.userViewResume().subscribe((response:any)=> {
  //   const byteArray = new Uint8Array(response);
  //   let blob = new Blob([byteArray], {type : 'application/pdf'});
  //   let url = window.URL.createObjectURL(blob);
  //   window.open(url);
  //   });
  //   }
}