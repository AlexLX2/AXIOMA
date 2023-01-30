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
import { DetailTicketComponent } from './components/tickets/detail-ticket/detail-ticket.component';
import { NewTicketComponent } from './components/tickets/new-ticket/new-ticket.component';
import { RedactorTicketComponent } from './components/tickets/redactor-ticket/redactor-ticket.component';
import {JWT_OPTIONS, JwtHelperService} from "@auth0/angular-jwt";
import {AuthInterceptor} from "./interceptors/auth.interceptor";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { SelectDropdownComponent } from './components/shared/select-dropdown/select-dropdown.component';
import { HoverClassDirective } from './directives/hover-class.directive';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {AngularEditorModule} from "@kolkov/angular-editor";
import {NgxEditorModule} from "ngx-editor";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SidebarComponent,
    TicketsComponent,
    ReportsComponent,
    HeaderComponent,
    FooterComponent,
    DetailTicketComponent,
    NewTicketComponent,
    RedactorTicketComponent,
    SelectDropdownComponent,
    HoverClassDirective,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    AngularEditorModule,
    NgxEditorModule
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

//TODO Add ticket list styles
//TODO Add ticket messages (bodies) styling
//TODO Add catalogues
//TODO Add logout
//TODO Fix main menu items
//TODO Fix ticket list title
//TODO Enable filters
//TODO Enable sorting
//TODO Enable pagination
//TODO Add ticket edit/reply
//TODO Fix create ticket
//TODO Add clients directory
//TODO Add employee directory
//TODO Add favicon
