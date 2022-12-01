import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddHMComponent } from './add-hm.component';

describe('AddHMComponent', () => {
  let component: AddHMComponent;
  let fixture: ComponentFixture<AddHMComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddHMComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddHMComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
