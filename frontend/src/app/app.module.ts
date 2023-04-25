import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { SidebarComponent } from './components/shared/sidebar/sidebar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TicketsComponent } from './components/tickets/tickets.component';
import { ReportsComponent } from './components/reports/reports.component';
import { HeaderComponent } from './components/shared/header/header.component';
import { FooterComponent } from './components/shared/footer/footer.component';
import { ListTicketComponent } from './components/tickets/list-ticket/list-ticket.component';
import { CreateTicketComponent } from './components/tickets/create-ticket/create-ticket.component';
import { RedactorTicketComponent } from './components/tickets/redactor-ticket/redactor-ticket.component';
import {JWT_OPTIONS, JwtHelperService} from "@auth0/angular-jwt";
import {AuthInterceptor} from "./interceptors/auth.interceptor";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { HoverClassDirective } from './directives/hover-class.directive';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {NgSelectModule} from "@ng-select/ng-select";
import {QuillModule} from "ngx-quill";
import { ViewTicketComponent } from './components/tickets/view-ticket/view-ticket.component';
import { SafehtmlPipe } from './pipes/safehtml.pipe';
import { ListCatalogsComponent } from './components/catalogs/generic-catalogs/list-catalogs/list-catalogs.component';
import { ListUsersComponent } from './components/catalogs/users/list-users/list-users.component';
import {WebdatarocksPivotModule} from "ng-webdatarocks";
import { CreateUserComponent } from './components/catalogs/users/create-user/create-user.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {AlertModule} from "./_alert";
import { ListRolesComponent } from './components/catalogs/users/list-roles/list-roles.component';
import { ListCatalogItemsComponent } from './components/catalogs/generic-catalogs/list-catalog-items/list-catalog-items.component';
import { EditUserComponent } from './components/catalogs/users/edit-user/edit-user.component';
import { EditRoleComponent } from './components/catalogs/users/edit-role/edit-role.component';
import { RolenamePipe } from './pipes/rolename.pipe';
import { ListCompanyComponent } from './components/catalogs/company/list-company/list-company.component';
import { CreateCompanyComponent } from './components/catalogs/company/create-company/create-company.component';
import { EditCompanyComponent } from './components/catalogs/company/edit-company/edit-company.component';
import { CreateRoleComponent } from './components/catalogs/users/create-role/create-role.component';
import { EditCatalogItemComponent } from './components/catalogs/generic-catalogs/edit-catalog-item/edit-catalog-item.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SidebarComponent,
    TicketsComponent,
    ReportsComponent,
    HeaderComponent,
    FooterComponent,
    ListTicketComponent,
    CreateTicketComponent,
    RedactorTicketComponent,
    HoverClassDirective,
    ViewTicketComponent,
    SafehtmlPipe,
    ListCatalogsComponent,
    ListUsersComponent,
    CreateUserComponent,
    ListRolesComponent,
    ListCatalogItemsComponent,
    EditUserComponent,
    EditRoleComponent,
    RolenamePipe,
    ListCompanyComponent,
    CreateCompanyComponent,
    EditCompanyComponent,
    CreateRoleComponent,
    EditCatalogItemComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        NgbModule,
        NgSelectModule,
        QuillModule.forRoot(),
        WebdatarocksPivotModule,
        BrowserAnimationsModule,
        AlertModule
    ],
  providers: [[{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  },
    {provide: JWT_OPTIONS,
      useValue: JWT_OPTIONS

    }, JwtHelperService]],
  bootstrap: [AppComponent]
})
export class AppModule { }

//TODO Enable filters
//TODO Enable sorting
//TODO Add employee directory
//TODO Add due date to ticket
//TODO Discuss about user-roles. How to divide ticket body from agent from ticket body from user?