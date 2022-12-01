import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CallbackComponent } from './callback/callback.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from 'src/auth.guard';
import { HomeComponent } from './home/home.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { JobPostingComponent } from './job-posting/job-posting.component';
import { MyInterviewsComponent } from './my-interviews/my-interviews.component';
import { HistoryComponent } from './history/history.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { ApplicationsComponent } from './applications/applications.component';
import { UserHistoryComponent } from './user-history/user-history.component';
import { ToolAdminComponent } from './tool-admin/tool-admin.component';
import { AddHMComponent } from './add-hm/add-hm.component';
import { UpdatejobComponent } from './updatejob/updatejob.component';
import { HrDashboardComponent } from './hr-dashboard/hr-dashboard.component';
import { RoleComponent } from './role/role.component';
import { DepartmentComponent } from './department/department.component';
const routes: Routes = [

  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent},
  {path: 'callback', component: CallbackComponent},
  {path: 'admin', component: AdminDashboardComponent,canActivate:[AuthGuard]},
  {path: 'jobposting', component: JobPostingComponent,canActivate:[AuthGuard]},
  {path: 'myinterviews', component: MyInterviewsComponent, canActivate:[AuthGuard]},
  {path: 'history', component: HistoryComponent, canActivate:[AuthGuard]},
  {path: 'applications', component: ApplicationsComponent, canActivate:[AuthGuard]},
  {path: 'user-history', component:UserHistoryComponent, canActivate:[AuthGuard]},
  {path: 'userprofile', component:UserProfileComponent, canActivate:[AuthGuard]},
  {path: 'tool-admin', component:ToolAdminComponent, canActivate:[AuthGuard]},
  {path:'addmanager',component:AddHMComponent,canActivate:[AuthGuard]},
  {path:'updatejob/:Id',component:UpdatejobComponent,canActivate:[AuthGuard]},
  {path:'hiringManager',component:HrDashboardComponent,canActivate:[AuthGuard]},
  {path:'role',component:RoleComponent,canActivate:[AuthGuard]},
  {path:'department',component:DepartmentComponent,canActivate:[AuthGuard]},
  {path:'admin-history',component:HistoryComponent,canActivate:[AuthGuard]},
  {path: '', redirectTo: '/login', pathMatch: 'full'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
