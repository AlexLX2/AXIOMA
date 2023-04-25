import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListCatalogItemsComponent } from './list-catalog-items.component';

describe('EditCatalogItemComponent', () => {
  let component: ListCatalogItemsComponent;
  let fixture: ComponentFixture<ListCatalogItemsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListCatalogItemsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListCatalogItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
