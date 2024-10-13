import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCheckInComponent } from './checkIn.component';

describe('NewCheckInComponent', () => {
  let component: NewCheckInComponent;
  let fixture: ComponentFixture<NewCheckInComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewCheckInComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewCheckInComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
