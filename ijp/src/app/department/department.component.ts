import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SecurityService } from 'src/securityservice';
import { PostJobService } from '../post-job.service';
import * as $ from "jquery";
import Swal from 'sweetalert2';
@Component({
  selector: 'app-department',
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.css']
})
export class DepartmentComponent implements OnInit {
  private baseUrl = environment.baseUrl;
  name: string = "";
  sessionIdx: any=0;
  filterTerm!: string;
  deptData: Array<any>=[];
  config: any;
  collection = { count: 60, data: [] }
  page: any;
  tableSize: any;
  btnText: any;
  isDisabled: boolean = false;
  userId:number
  response:Array<any> = [];
  constructor(private http: HttpClient, private securityService: SecurityService
    , private router: Router, private postJobService: PostJobService) {
    this.deptData = [];
    this.config = {
      currentPage: 1,
    };
  }

  count: number = 0;
  pageSize = 5;
  pageSizes = [5, 10, 15, 20];

  ngOnInit(): void {
    this.getUserInfo().subscribe(data => {
      this.name = data.name;
      this.userId=data.userId;
    });

    this.getRole().subscribe(data=>{
      this.deptData=data
      console.log(this.deptData)
    });
  }
    getRole() :Observable<any>{
      return this.http.get(environment.baseUrl+'/getDept')}    
  

    logout() {
      this.securityService.logout().subscribe(() => {
        this.securityService.removeToken();
        this.router.navigate(['/login']);
      });
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
  
    onTableDataChange($event: number) {
      throw new Error('Method not implemented.');
      }

      handlePageSizeChange($event: Event) {
      throw new Error('Method not implemented.');
      }
      eyeClicked(i: any) {
        sessionStorage.setItem("index", i)
        this.sessionIdx=i;
        $('#myModal').css("display", "block");
      }

      closeClicked() {
        $('#myModal').css("display", "none");
      }

      addDepartment=new FormGroup({
        departmentName:new FormControl('',[Validators.required]),
        departmentDesc:new FormControl('',[Validators.required])
      })

      onSubmit(){
        if(this.addDepartment.valid){
          console.log(this.addDepartment.value);
          let resp= this.http.post(`${this.baseUrl}/saveDept`,this.addDepartment.value,{responseType:'text' as 'json'}).subscribe(res => {
            console.log(res)
            this.response = [res]
            console.log(this.response[0])
            if(this.response[0]=='{"message":"Ok"}'){
            Swal.fire({
              title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
              text: 'Department Added Successfully !',
              position: 'top',
              confirmButtonColor: '#F69659',
            }).then((result)=>{
        
              if (result.isConfirmed) {
        
                window.location.reload();
        
              }
            } )
          }
          else {
            Swal.fire({
              title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
              text: 'Department Already Exist !',
              position: 'top',
              confirmButtonColor: '#F69659',
            }).then((result)=>{
              if(result.isConfirmed){
                window.location.reload();
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

      onDeleteDept(DeptId: any){
        Swal.fire({
          title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
          text: 'Do you want to delete this department ?',
          position: 'top',
          showCancelButton: true,
          confirmButtonText: 'Yes',
          confirmButtonColor: '#F69659',
          cancelButtonText: 'No',
          cancelButtonColor: '#F69659'
        }).then((result) => {
    
          if (result.isConfirmed) {
            let resp = this.http.get(environment.baseUrl + '/deleteDept/' + DeptId, { responseType: 'text' as 'json' }).subscribe(res => {
              // console.log(res)
            });
            window.location.reload();
          } else if (result.isDismissed) {
            // console.log('Clicked No!');
          }
        })
    
    
      }

      eyeClicked2(i:any){
        sessionStorage.setItem("index", i)
        this.sessionIdx=i;
        $('#myModals').css("display", "block");
      }
      closeClicked2() {
        $('#myModals').css("display", "none");
      }

      updateDept=new FormGroup({
        departmentName:new FormControl('',[Validators.required]),
        departmentDesc:new FormControl('',[Validators.required])
      })

      onSubmitForm(deptId: any){
        if(this.updateDept.valid){
          console.log(this.updateDept.value);
          let resp= this.http.post(`${this.baseUrl}/updateDept/${deptId}`,this.updateDept.value,{responseType:'text' as 'json'}).subscribe(res => {
            console.log(res)
          });
          Swal.fire({
              title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
              text: 'Department Updated Successfully !',
              position: 'top',
              confirmButtonColor: '#F69659',
            }).then((result)=>{
        
              if (result.isConfirmed) {
        
                window.location.reload();
        
              }})
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
  }
