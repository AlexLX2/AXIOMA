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
import { ListCatalogsComponent } from './components/catalogs/list-catalogues/list-catalogs.component';
import { ListUsersComponent } from './components/catalogs/list-users/list-users.component';
import {WebdatarocksPivotModule} from "ng-webdatarocks";

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
        WebdatarocksPivotModule
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


//TODO Add ticket messages (bodies) styling
//TODO Add catalogues
//TODO Fix main menu items
//TODO Fix ticket list title
//TODO Enable filters
//TODO Enable sorting
//TODO Enable pagination
//TODO Add ticket edit/reply
//TODO Fix create ticket
//TODO Add clients directory
//TODO Add employee directory
//TODO Fix favicon
//TODO Add due date to ticket
//TODO Discuss about user-roles. How to divide ticket body from agent from ticket body from user?