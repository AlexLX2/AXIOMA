import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCatalogItemComponent } from './edit-catalog-item.component';

describe('EditCatalogItemComponent', () => {
  let component: EditCatalogItemComponent;
  let fixture: ComponentFixture<EditCatalogItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditCatalogItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditCatalogItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
