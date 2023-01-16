import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RedactorTicketComponent } from './redactor-ticket.component';

describe('RedactorTicketComponent', () => {
  let component: RedactorTicketComponent;
  let fixture: ComponentFixture<RedactorTicketComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RedactorTicketComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RedactorTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
