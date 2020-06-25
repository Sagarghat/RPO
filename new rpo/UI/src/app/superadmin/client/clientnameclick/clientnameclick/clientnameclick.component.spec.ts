import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientnameclickComponent } from './clientnameclick.component';

describe('ClientnameclickComponent', () => {
  let component: ClientnameclickComponent;
  let fixture: ComponentFixture<ClientnameclickComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClientnameclickComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientnameclickComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
