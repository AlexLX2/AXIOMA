import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListTicketComponent } from './list-ticket.component';

describe('DetailTicketComponent', () => {
  let component: ListTicketComponent;
  let fixture: ComponentFixture<ListTicketComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListTicketComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
