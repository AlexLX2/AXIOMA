import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {StorageService} from "./storage.service";
import {JwtHelperService} from "@auth0/angular-jwt";
import {environment} from "../../environments/environment";
import {Login} from "../interfaces/login";
import {User} from "../interfaces/user";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    baseUrl: string = environment.baseUrl;

    private currentUser = new BehaviorSubject<User>({email: "", firstName: "", lastName: "", login: ""});

    constructor(private http: HttpClient,
                private storageService: StorageService,
                private jwtHelper: JwtHelperService
    ) {
    }

    login(login: Login): Observable<any> {
        return this.http.post<any>(`${this.baseUrl}/auth/login`, login);
    }

    logout() {
        this.storageService.clean();
    }

    isAuthenticated(): boolean {
        const token = this.storageService.getItem('token');
        this.currentUser.next(this.storageService.decodeToken(token));
        return token !== null && !this.jwtHelper.isTokenExpired(token);
    }

    getCurrenUserInfo(): Observable<User> {
        return this.currentUser.asObservable();
    };

    setCurrentUserInfo(user:User) {
        this.currentUser.next(user);
    }

}
