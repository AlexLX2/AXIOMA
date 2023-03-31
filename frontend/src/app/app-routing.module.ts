import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { ReportsComponent } from './components/reports/reports.component';
import { ListTicketComponent } from './components/tickets/list-ticket/list-ticket.component';
import { CreateTicketComponent } from './components/tickets/create-ticket/create-ticket.component';
import { RedactorTicketComponent } from './components/tickets/redactor-ticket/redactor-ticket.component';
import {AuthGuard} from "./guards/auth.guard";
import {ListCatalogsComponent} from "./components/catalogs/list-catalogs/list-catalogs.component";
import {ListUsersComponent} from "./components/catalogs/users/list-users/list-users.component";
import {CreateUserComponent} from "./components/catalogs/users/create-user/create-user.component";

const routes: Routes = [
  { path: '', component: ListTicketComponent, canActivate: [AuthGuard], pathMatch:"full" },
  { path: 'login', component: LoginComponent },
  { path: 'tickets/:id', component: ListTicketComponent, canActivate: [AuthGuard]},
  { path: 'tickets', component: ListTicketComponent, canActivate: [AuthGuard] },
  { path: 'tickets-create', component: CreateTicketComponent, canActivate: [AuthGuard] },
  { path: 'tickets-open', component: ListTicketComponent, canActivate: [AuthGuard] },
  { path: 'tickets-redactor', component: RedactorTicketComponent, canActivate: [AuthGuard] },
  { path: 'reports', component: ReportsComponent, canActivate: [AuthGuard] },
  { path: 'catalogs', component:ListCatalogsComponent, canActivate: [AuthGuard]},
  { path: 'users', component:ListUsersComponent, canActivate: [AuthGuard], pathMatch:'full'},
  { path: 'users/create', component:CreateUserComponent, canActivate: [AuthGuard]}
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
