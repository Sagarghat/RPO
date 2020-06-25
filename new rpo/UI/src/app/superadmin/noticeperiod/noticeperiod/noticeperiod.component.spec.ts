import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NoticeperiodComponent } from './noticeperiod.component';

describe('NoticeperiodComponent', () => {
  let component: NoticeperiodComponent;
  let fixture: ComponentFixture<NoticeperiodComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NoticeperiodComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NoticeperiodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
