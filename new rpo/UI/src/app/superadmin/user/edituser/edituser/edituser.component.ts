import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { ToastrManager } from 'ng6-toastr-notifications';
import { formatDate } from '@angular/common';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';

@Component({
  selector: 'app-edituser',
  templateUrl: './edituser.component.html',
  styleUrls: ['./edituser.component.css']
})
export class EdituserComponent implements OnInit {
  baseUrl = `${environment.serviceUrl}`;
  UserId: any;
  submitted: boolean;
  pageSize: any=4;
  selectedRole: any;
  minDate: any;
  maxDate: any;
  RegId: string;
  Domain: string;
  userToken: string;
  users: any;
  usersData: any;
  selectedReportingManager: any;
  selectedTargetDuration: any;
  ReportingId: any;
  reportingManager: any;
  UserById: any;
  editUser;
  flag: boolean;
  pazeSize = 4;
  totalItems;
  currentPageUser: number = 1;
  managers: any;
  managerData: any;
  totalManagers;
  RoleData = [
    { name: 'Admin' }, { name: 'BDM' }, { name: 'AM' }, { name: 'Lead' }, { name: 'Recruiter' }
  ];

  EditUserForm = this.formBuilder.group({
    FirstName: ['', [Validators.required, Validators.pattern("[A-Za-z]+")]],
    LastName: ['', [Validators.required, Validators.pattern("[A-Za-z]+")]],
    Email: ['', [Validators.required, Validators.email]],
    MobileNumber: ['', [Validators.required, Validators.pattern("^(\([0-9]{3}\) |[6-9]{1})[0-9]{9}$")]],
    ExtensionNumber: ['', Validators.required],
    Role: ['', Validators.required],
    DOB: [''],
    DOJ: [''],
    ReportingManager: ['', Validators.required],
    EmployeeId: [''],
  });
  usersData1: any;
  role: string;

  constructor(private activatedRoute: ActivatedRoute, private service: SuperadminserviceService, private formBuilder: FormBuilder, private http: HttpClient, private router: Router, private toaster: ToastrManager) { }

  setPage(pageNo: number): void {
    this.currentPageUser = pageNo;
    this.getReportingManagerList();
  }

  ngOnInit() {
    this.RegId = sessionStorage.getItem('registrationId');
    this.Domain = sessionStorage.getItem('Domain');
    this.userToken = sessionStorage.getItem('Token');
    this.role = sessionStorage.getItem('role');
    this.UserId = this.activatedRoute.snapshot.paramMap.get("id");
    this.totalManagers = sessionStorage.getItem('totalUserList');
    console.log(this.totalManagers, "totalUserList");
    console.log(this.UserId, "userid");
    this.getReportingManagerList()
    this.getUserById();
    this.getList();
  }

  get f() {
    return this.EditUserForm.controls;
  }

  getReportingManagerList() {

    let postdata={
      pageNo:this.currentPageUser,
      pageSize:this.pageSize,
     flag:true,
 registrationId: this.RegId,
 role: this.role 

    }
  
    this.service.getuserList(postdata).subscribe((res: any) => {
      console.log(res, "repoting user--------");
      this.users = res;
      this.totalItems = this.users.totalRecords;
      this.usersData1 = this.users.result;
      this.usersData= this.usersData1.list;
      console.log("getAll response users" + this.usersData);
    })
  }

  getList() {
    this.flag = true;

    let postdata={
      pageNo:this.currentPageUser,
      pageSize:this.totalManagers,
     flag:true,
 registrationId: this.RegId,
 role: this.role
    }
    this.service.getuserList(postdata).subscribe((res: any) => {
      this.managers = res;
      this.managerData = this.managers.result;
    })
  }

  getUserById() {
    this.service.getUserById(this.UserId, this.RegId).subscribe((res: any) => {
      console.log(res, "byuserid------------");
      this.users = res;
      this.UserById = this.users.result;
      console.log("mama", formatDate(this.UserById.dob, 'yyyy-MM-dd', 'en'));
      this.EditUserForm.controls.FirstName.setValue(this.UserById.firstName);
      this.EditUserForm.controls.LastName.setValue(this.UserById.lastName);
      this.EditUserForm.controls.Email.setValue(this.UserById.email);
      this.EditUserForm.controls.MobileNumber.setValue(this.UserById.contactNumber);
      this.EditUserForm.controls.ExtensionNumber.setValue(this.UserById.extension);
      this.EditUserForm.controls.Role.setValue(this.UserById.role);
      this.EditUserForm.controls.ReportingManager.setValue(this.UserById.reportingId.name);
      this.EditUserForm.controls.EmployeeId.setValue(this.UserById.employeeId);
      if(this.UserById.dob !=null){
        this.EditUserForm.controls.DOB.setValue(formatDate(this.UserById.dob, 'yyyy-MM-dd', 'en'));
      }
      if(this.UserById.doj !=null){
        this.EditUserForm.controls.DOJ.setValue(formatDate(this.UserById.doj, 'yyyy-MM-dd', 'en'));
      }
    })
  }

  saveUser() {

    if (this.EditUserForm.invalid) {
      this.toaster.errorToastr('Please fill the mandatory fields', 'Notification');
    } else {

      this.submitted = true;
      for (let i = 0; i <= this.managerData.length; i++) {
        if (this.selectedReportingManager == this.managerData[i].name) {
          this.ReportingId = this.managerData[i].id;
          this.reportingManager = this.managerData[i].name
          break;
        }
      }

      let userData = {
        firstName: this.EditUserForm.value.FirstName,
        lastName: this.EditUserForm.value.LastName,
        name: this.EditUserForm.value.Email,
        email: this.EditUserForm.value.Email,
        contactNumber: this.EditUserForm.value.MobileNumber,
        extension: JSON.parse(this.EditUserForm.value.ExtensionNumber),
        role: this.EditUserForm.value.Role,
        reportingId: { "id": this.ReportingId },
        employeeId: this.EditUserForm.value.EmployeeId,
        registrationId: JSON.parse(this.RegId),
        domin: this.Domain,
        dob: Date.parse(this.EditUserForm.value.DOB),
        doj: Date.parse(this.EditUserForm.value.DOJ),
      }

      this.service.editUser(this.UserId, this.RegId, userData).subscribe(resp => {
        console.log(resp, "edit update response");
        this.editUser = resp;
        if (this.editUser.status === 'StatusSuccess') {
          this.toaster.successToastr('User edited successfully');
          this.router.navigate(['/superadmin/user']);
        } else if (this.editUser.status === 'DuplicateRecord') {
          this.toaster.warningToastr('Please Change the email.', 'Alert!');
        }
      });
    }
  }

}
