import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AuthHeaderInterceptor } from 'src/auth-header.interceptor';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { JobPostingComponent } from './job-posting/job-posting.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { LoginComponent } from './login/login.component';
import { CallbackComponent } from './callback/callback.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { HistoryComponent } from './history/history.component';
import { MyInterviewsComponent } from './my-interviews/my-interviews.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PostJobService } from './post-job.service';
import { UploadFilesService } from './fileupload.service';
import { ApplicationsComponent } from './applications/applications.component';
import { UserHistoryComponent } from './user-history/user-history.component';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { ToolAdminComponent } from './tool-admin/tool-admin.component';
import { AddHMComponent } from './add-hm/add-hm.component';
import { UpdatejobComponent } from './updatejob/updatejob.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NullWithDefaultPipe } from './pipe/NullWithDefaultPipe ';
import { HrDashboardComponent } from './hr-dashboard/hr-dashboard.component';
import { RoleComponent } from './role/role.component';
import { DepartmentComponent } from './department/department.component';
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    JobPostingComponent,
    AdminDashboardComponent,
    LoginComponent,
    CallbackComponent,
    UserProfileComponent,
    HistoryComponent,
    MyInterviewsComponent,
    ApplicationsComponent,
    UserHistoryComponent,
    ToolAdminComponent,
    AddHMComponent,
    UpdatejobComponent,
    NullWithDefaultPipe,
    HrDashboardComponent,
    RoleComponent,
    DepartmentComponent
  ],
  imports: [
    RouterModule.forRoot([
      {path:'home',component:HomeComponent},
      {path:'userprofile',component:UserProfileComponent},
      {path:'myinterviews',component:MyInterviewsComponent},
      {path:'applications',component:ApplicationsComponent},
      {path:'history',component:HistoryComponent},
      {path:'user-history',component:UserHistoryComponent} ,     
      {path:'tool-admin',component:ToolAdminComponent},
      {path:'addmanager',component:AddHMComponent}
    ]),
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    Ng2SearchPipeModule,
    NgxPaginationModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthHeaderInterceptor,
      multi: true
    },
    PostJobService,
    UploadFilesService
  ],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
  bootstrap: [AppComponent]
})
export class AppModule { }
