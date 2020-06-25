import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { ToastrManager } from 'ng6-toastr-notifications';
import { SuperadminserviceService } from '../../superadminservice.service';
import { FormControl, FormBuilder } from '@angular/forms';
import { ExcelService } from 'src/app/excel.service';
declare var $;

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  baseUrl = `${environment.serviceUrl}`;
  first: any;
  users: any;
  usersData: any;
  userToken: string;
  RegId: string;
  totalItems;
  pageSize = 10;
  currentPage = 1;
  idOfUserRole: any;
  userDetails = [];
  PlanId: number;
  flag: boolean;
  permissionName: any;
  selecteddata: any;
  search_result: boolean = false;
  showNodata: boolean = false;
  search_Previous_result: boolean = false;
  dummy: boolean = true;
  asli: boolean = false;
  firstt: boolean = false;
  second: boolean = false;
  asd: boolean = false;
  qwe: boolean = false;
  lkj: boolean = false;
  searchTerm: FormControl = new FormControl();
  pageNum: any;
  registrationId: any;
  searchvalue: any;
  data: [];
  obj: any;
  searchInput: any;
  searchform = this.formBuilder.group({
    email: [''],
    contact: [''],
    role: [, '']
  });
  usersData1: any;
  role: string;
  usersExcel: any;
  userD: any;

  setPage(pageNo: number): void {
    this.currentPage = pageNo;
    this.getUsers();
  }

  constructor(private serv: ExcelService, private formBuilder: FormBuilder,
    private service: SuperadminserviceService, private toaster: ToastrManager) { }

  search_values(event) {
    this.searchInput = event;
  }

  ngOnInit() {
    $(document).click(function (e) {
      console.log(e);
      if (e.target.id == "usr") {
        $(".item").show();
      }
      else {
        $(".item").hide();
      }
    });
    this.searchTerm.valueChanges.subscribe(
      term => {
        var post1 = {
          pageNo: this.currentPage,
          pageSize: this.pageSize,
          flag: true,
          registrationId: this.RegId,
          fullName: term,
          role: this.role
        };
        if (term != '') {
          this.service.getuserList(post1).subscribe(
            resp => {
              this.users = resp;
              this.usersData1 = this.users.result;
              this.usersData = this.usersData1.list;
              this.totalItems = this.users.totalRecords;
              if (this.users.status === "Accepted") {
                this.search_result = true;
                this.usersData1 = this.users.result;
                this.usersData = this.usersData1.list;
                this.showNodata = false;
              } else if (this.users.status === "DataIsEmpty") {
                this.showNodata = true;
                if (this.showNodata == true) {
                  this.search_result = false;
                  this.search_Previous_result = true;
                }
              }
            })
        }
      });

    this.userToken = sessionStorage.getItem('Token');
    this.RegId = sessionStorage.getItem('registrationId');
    this.role = sessionStorage.getItem('role');
    this.Excel();
    this.getUsers();
  }

  downloadExcel() {
    console.log(this.usersData, "fffffffff")
    this.serv.exportAsExcelFile(this.usersData, 'Users');
  }

  save() {
    const searchData = {
      pageNo: this.currentPage,
      pageSize: this.pageSize,
      flag: true,
      role: this.role,
      registrationId: this.RegId,
      email: this.searchform.value.email,
      contactNo: this.searchform.value.contact,
      roleSearchParam: this.searchform.value.role,
    };
    this.service.getuserList(searchData).subscribe(res => {
      console.log(res, "totalsearch----------");
      this.users = res;
      this.totalItems = this.users.totalRecords;
      console.log(this.totalItems, "totalItems");
      this.usersData1 = this.users.result;
      this.usersData = this.usersData1.list;
      console.log(this.usersData, "usersData--------");
    });
  }

  adduser() {
    if (this.PlanId == 1 && this.totalItems == 2) {
      this.toaster.errorToastr('You can create only two users.', 'Notification');
    }
  }

  getUsers() {
    let postdata = {
      pageNo: this.currentPage,
      pageSize: this.pageSize,
      flag: true,
      registrationId: this.RegId,
      role: this.role
    }
    this.service.getuserList(postdata).subscribe((res: any) => {
      console.log('userlist', res);
      this.users = res;
      this.totalItems = this.users.totalRecords;
      this.usersData1 = this.users.result;
      this.usersData = this.usersData1.list;
      console.log(this.totalItems, "totalItems");
      sessionStorage.setItem("totalUserList", this.totalItems)
      for (let i = 0; i < this.usersData.length; i++) {
        if (this.usersData[i].role === 'SUPERADMIN') {
          this.idOfUserRole = i;
          console.log('role id.....' + this.idOfUserRole);
        }
      }
      for (let k = 0; k < this.totalItems; k++) {
        for (let i = 0; i < this.usersData.length; i++) {
          if (i !== this.idOfUserRole) {
            this.userDetails[k] = this.usersData[i];
            k++;
          } else {
            return;
          }
        }
      }
    })
  }

  Excel() {
    let postdata = {
      pageNo: this.currentPage,
      pageSize: this.pageSize,
      flag: false,
      registrationId: this.RegId,
      role: this.role
    }
    this.service.getuserList(postdata).subscribe((res: any) => {
      console.log('userlist', res);
      this.users = res;
      this.usersData1 = this.users.result;
      this.usersData = this.usersData1.list.json();
    });
  }

}
