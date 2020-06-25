import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditinterviewComponent } from './editinterview.component';

describe('EditinterviewComponent', () => {
  let component: EditinterviewComponent;
  let fixture: ComponentFixture<EditinterviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditinterviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditinterviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
