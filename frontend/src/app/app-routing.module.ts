import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { ReportsComponent } from './components/reports/reports.component';
import { ListTicketComponent } from './components/tickets/list-ticket/list-ticket.component';
import { CreateTicketComponent } from './components/tickets/create-ticket/create-ticket.component';
import { RedactorTicketComponent } from './components/tickets/redactor-ticket/redactor-ticket.component';
import {AuthGuard} from "./guards/auth.guard";

const routes: Routes = [
  { path: '', component: ListTicketComponent, canActivate: [AuthGuard], pathMatch:"full" },
  { path: 'login', component: LoginComponent },
  { path: 'tickets/:id', component: ListTicketComponent, canActivate: [AuthGuard]},
  { path: 'tickets', component: ListTicketComponent, canActivate: [AuthGuard] },
  { path: 'tickets-create', component: CreateTicketComponent, canActivate: [AuthGuard] },
  { path: 'tickets-open', component: ListTicketComponent, canActivate: [AuthGuard] },
  { path: 'tickets-redactor', component: RedactorTicketComponent, canActivate: [AuthGuard] },
  { path: 'reports', component: ReportsComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
