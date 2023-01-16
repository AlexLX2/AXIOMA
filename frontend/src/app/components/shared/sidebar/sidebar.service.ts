import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export enum themeTypes {
  dark = 'dark',
  white = 'white',
}

@Injectable({
  providedIn: 'root',
})
export class SidebarService {
  constructor() {}

  public theme = new BehaviorSubject(localStorage.getItem('axioma_theme') ?? 'dark');

  public switchTheme(theme: themeTypes) {
    this.theme.next(theme);
    localStorage.setItem('axioma_theme', theme);
  }
}
