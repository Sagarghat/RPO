import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModeofinterviewComponent } from './modeofinterview.component';

describe('ModeofinterviewComponent', () => {
  let component: ModeofinterviewComponent;
  let fixture: ComponentFixture<ModeofinterviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModeofinterviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModeofinterviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
