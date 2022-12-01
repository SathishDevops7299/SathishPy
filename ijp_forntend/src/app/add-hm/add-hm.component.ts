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


@Component({
  selector: 'app-add-hm',
  templateUrl: './add-hm.component.html',
  styleUrls: ['./add-hm.component.css']
})
export class AddHMComponent implements OnInit {

  HRname: string = "";
  name:string ="";
  message:any;
  HRemail:string=""
  private baseUrl = environment.baseUrl;
  response:Array<any> = [];
  AddHM = new FormGroup({

    email: new FormControl(),
    roles: new FormControl(),
    username: new FormControl()
  })

  constructor(private securityService: SecurityService,private http :HttpClient,private router: Router) { }
  ngOnInit(): void {
    this.getUserInfo().subscribe(data => {
      this.name = data.name;
    });   
  }
  
  logout() {
    this.securityService.logout() .subscribe(() => {
      this.securityService.removeToken();
      this.router.navigate(['/login']);
    });
  }

  chatClicked() {
    $('#wrapper').css("display:block")
  }

  chatClosed() {
    $('#wrapper').hide();
  }

  onSubmit() {
    if(this.AddHM.valid){
    this.HRemail=this.AddHM.get('email').value
    this.HRname=this.AddHM.get('username').value
    // console.log(this.HRemail+'------'+this.HRname);
    let resp= this.http.post(`${this.baseUrl}/saveHrManager/${this.HRemail}/${this.HRname}`,{responseType:'text' as 'json'}).subscribe(res => {
      console.log(res)
      this.response = [res]
      console.log(this.response)
      if(this.response[0].message=='Hiring manager created'){
      Swal.fire({
        title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
        text: 'Hiring Manager Added Successfully !',
        position: 'top',
        confirmButtonColor: '#F69659',
      }).then((result)=>{
  
        if (result.isConfirmed) {
  
          // window.location.reload();
  
        }
      } )
    }
    else {
      Swal.fire({
        title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
        text: 'Hiring Manager Already Exist !',
        position: 'top',
        confirmButtonColor: '#F69659',
      }).then((result)=>{
        if(result.isConfirmed){
          // window.location.reload();
        }
      })
    }
    }); 
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
    return this.http.get(environment.baseUrl+"/getUser" );
  }


}

