import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BillingmodalComponent } from './billingmodal.component';

describe('BillingmodalComponent', () => {
  let component: BillingmodalComponent;
  let fixture: ComponentFixture<BillingmodalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BillingmodalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BillingmodalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
