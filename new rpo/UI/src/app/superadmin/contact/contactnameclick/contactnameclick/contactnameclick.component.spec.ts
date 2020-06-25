import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactnameclickComponent } from './contactnameclick.component';

describe('ContactnameclickComponent', () => {
  let component: ContactnameclickComponent;
  let fixture: ComponentFixture<ContactnameclickComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ContactnameclickComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContactnameclickComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
