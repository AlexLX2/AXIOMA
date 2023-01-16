import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { ReportsComponent } from './components/reports/reports.component';
import { DetailTicketComponent } from './components/tickets/detail-ticket/detail-ticket.component';
import { NewTicketComponent } from './components/tickets/new-ticket/new-ticket.component';
import { RedactorTicketComponent } from './components/tickets/redactor-ticket/redactor-ticket.component';
import {AuthGuard} from "./guards/auth.guard";

const routes: Routes = [
  { path: '', component: DetailTicketComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'tickets', component: DetailTicketComponent, canActivate: [AuthGuard] },
  { path: 'tickets-create', component: NewTicketComponent, canActivate: [AuthGuard] },
  { path: 'tickets-open', component: DetailTicketComponent, canActivate: [AuthGuard] },
  { path: 'tickets-redactor', component: RedactorTicketComponent, canActivate: [AuthGuard] },
  { path: 'reports', component: ReportsComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
