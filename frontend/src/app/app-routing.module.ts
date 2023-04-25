import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { ReportsComponent } from './components/reports/reports.component';
import { ListTicketComponent } from './components/tickets/list-ticket/list-ticket.component';
import { CreateTicketComponent } from './components/tickets/create-ticket/create-ticket.component';
import { RedactorTicketComponent } from './components/tickets/redactor-ticket/redactor-ticket.component';
import {AuthGuard} from "./guards/auth.guard";
import {ListCatalogsComponent} from "./components/catalogs/generic-catalogs/list-catalogs/list-catalogs.component";
import {ListUsersComponent} from "./components/catalogs/users/list-users/list-users.component";
import {CreateUserComponent} from "./components/catalogs/users/create-user/create-user.component";
import {ListRolesComponent} from "./components/catalogs/users/list-roles/list-roles.component";
import {ListCatalogItemsComponent} from "./components/catalogs/generic-catalogs/list-catalog-items/list-catalog-items.component";
import {EditUserComponent} from "./components/catalogs/users/edit-user/edit-user.component";
import {EditRoleComponent} from "./components/catalogs/users/edit-role/edit-role.component";
import {ListCompanyComponent} from "./components/catalogs/company/list-company/list-company.component";
import {EditCompanyComponent} from "./components/catalogs/company/edit-company/edit-company.component";
import {CreateCompanyComponent} from "./components/catalogs/company/create-company/create-company.component";
import {CreateRoleComponent} from "./components/catalogs/users/create-role/create-role.component";
import {EditCatalogItemComponent} from "./components/catalogs/generic-catalogs/edit-catalog-item/edit-catalog-item.component";

const routes: Routes = [
  { path: '', component: ListTicketComponent, canActivate: [AuthGuard], pathMatch:"full" },
  { path: 'login', component: LoginComponent },
  { path: 'tickets', component: ListTicketComponent, canActivate: [AuthGuard] },
  { path: 'tickets/:id', component: ListTicketComponent, canActivate: [AuthGuard]},
  { path: 'tickets-create', component: CreateTicketComponent, canActivate: [AuthGuard] },
  { path: 'tickets-redactor', component: RedactorTicketComponent, canActivate: [AuthGuard] },
  { path: 'reports', component: ReportsComponent, canActivate: [AuthGuard] },
  { path: 'catalogs', component:ListCatalogsComponent, canActivate: [AuthGuard], pathMatch:'full'},
  { path: 'catalogs/:catalogName', component:ListCatalogItemsComponent, canActivate: [AuthGuard]},
  { path: 'catalogs/:catalogName/edit/:id', component:EditCatalogItemComponent, canActivate: [AuthGuard]},
  { path: 'users', component:ListUsersComponent, canActivate: [AuthGuard], pathMatch:'full'},
  { path: 'users/create', component:CreateUserComponent, canActivate: [AuthGuard], pathMatch:'prefix'},
  { path: 'users/edit/:id', component:EditUserComponent, canActivate: [AuthGuard], pathMatch:'prefix'},
  { path: 'roles', component: ListRolesComponent, canActivate: [AuthGuard]},
  { path: 'roles/create', component: CreateRoleComponent, canActivate: [AuthGuard]},
  { path: 'roles/edit/:id', component: EditRoleComponent, canActivate: [AuthGuard]},
  { path: 'companies', component: ListCompanyComponent, canActivate: [AuthGuard]},
  { path: 'companies/create', component: CreateCompanyComponent, canActivate: [AuthGuard]},
  { path: 'companies/edit/:id', component: EditCompanyComponent, canActivate: [AuthGuard]}

];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
