import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ToolAdminComponent } from './tool-admin.component';

describe('ToolAdminComponent', () => {
  let component: ToolAdminComponent;
  let fixture: ComponentFixture<ToolAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ToolAdminComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ToolAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
