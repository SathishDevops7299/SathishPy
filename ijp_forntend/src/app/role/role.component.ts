import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SecurityService } from 'src/securityservice';
import { PostJobService } from '../post-job.service';
import * as $ from "jquery";
import { FormControl, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-role',
  templateUrl: './role.component.html',
  styleUrls: ['./role.component.css']
})
export class RoleComponent implements OnInit {
      name: string = "";
      private baseUrl = environment.baseUrl;
      filterTerm!: string;
      roleData: Array<any>=[];
      config: any;
      collection = { count: 60, data: [] }
      page: any;
      tableSize: any;
      btnText: any;
      isDisabled: boolean = false;
      userId:number;
      response:Array<any>=[]
      constructor(private http: HttpClient, private securityService: SecurityService
        , private router: Router, private postJobService: PostJobService) {
        this.roleData = [];
        this.config = {
          currentPage: 1,
        };
      }
      sessionIdx: any=0;
      count: number = 0;
      pageSize = 5;
      pageSizes = [5, 10, 15, 20];
    
      ngOnInit(): void {
        this.getUserInfo().subscribe(data => {
          this.name = data.name;
          this.userId=data.userId;
        });

        this.getRole().subscribe(data=>{
          this.roleData=data
          console.log(this.roleData)
        });
      }

      addRole=new FormGroup({
        roleName:new FormControl('',[Validators.required]),
        roleDesc:new FormControl('',[Validators.required])
      })
      
      onSubmit(){
        if(this.addRole.valid){
          console.log(this.addRole.value);
          let resp= this.http.post(`${this.baseUrl}/saveRole`,this.addRole.value,{responseType:'text' as 'json'}).subscribe(res => {
            console.log(res)
            this.response = [res]
            console.log(this.response[0])
            if(this.response[0]=='{"message":"Ok"}'){
            Swal.fire({
              title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
              text: 'Role Added Successfully !',
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
              text: 'Role Already Exist !',
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

        onDeleteRole(roleId: any) {
          Swal.fire({
            title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
            text: 'Do you want to delete this role ?',
            position: 'top',
            showCancelButton: true,
            confirmButtonText: 'Yes',
            confirmButtonColor: '#F69659',
            cancelButtonText: 'No',
            cancelButtonColor: '#F69659'
          }).then((result) => {
      
            if (result.isConfirmed) {
              let resp = this.http.get(environment.baseUrl + '/deleteRole/' + roleId, { responseType: 'text' as 'json' }).subscribe(res => {
                console.log(res)
              });
              window.location.reload();
            } else if (result.isDismissed) {
              // console.log('Clicked No!');
            }
          })
      
      
        }

        getRole() :Observable<any>{
          return this.http.get(environment.baseUrl+'/getRole')}    
      

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

          eyeClicked(i:any) {
            sessionStorage.setItem("index", i)
            this.sessionIdx=i;
            $('#myModal').css("display", "block");
          }
          closeClicked() {
            $('#myModal').css("display", "none");
          }
          eyeClicked2(i:any){
            sessionStorage.setItem("index", i)
            this.sessionIdx=i;
            $('#myModals').css("display", "block");
          }
          closeClicked2() {
            $('#myModals').css("display", "none");
          }

          updateRole=new FormGroup({
            roleName:new FormControl('',[Validators.required]),
            roleDesc:new FormControl('',[Validators.required])
          })
          
          onSubmitForm(roleId: any){
            console.log(roleId)
            if(this.updateRole.valid){
              console.log(this.updateRole.value);
              let resp= this.http.post(`${this.baseUrl}/updateRole/${roleId}`,this.updateRole.value,{responseType:'text' as 'json'}).subscribe(res => {
                console.log(res)
              });
              Swal.fire({
                  title: "<h5 style='background-color:#F69659; color:white;margin-top:-22px; margin-left:-29px;width:510px;'>" + 'Internal Job Portal' + "</h5>",
                  text: 'Role Updated Successfully !',
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