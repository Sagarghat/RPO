import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddcanresumeComponent } from './addcanresume.component';

describe('AddcanresumeComponent', () => {
  let component: AddcanresumeComponent;
  let fixture: ComponentFixture<AddcanresumeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddcanresumeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddcanresumeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
