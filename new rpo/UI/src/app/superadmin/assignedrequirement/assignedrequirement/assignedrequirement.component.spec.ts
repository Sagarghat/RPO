import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignedrequirementComponent } from './assignedrequirement.component';

describe('AssignedrequirementComponent', () => {
  let component: AssignedrequirementComponent;
  let fixture: ComponentFixture<AssignedrequirementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignedrequirementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignedrequirementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
