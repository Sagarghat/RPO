import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { SuperadminserviceService } from '../../superadminservice.service';
import { Router } from '@angular/router';
import { Validators, FormBuilder, FormControl } from '@angular/forms';
import { ToastrManager } from 'ng6-toastr-notifications';
import { ExcelService } from 'src/app/excel.service';
declare var $;
@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit {
  baseUrl = `${environment.serviceUrl}`;


  ShowHideClick: any;
  status: any;
  getdetails: any;
  first: any;
  selectedClientId;
  selectedClientName;
  getCandidatelist: any;
  data: any;
  abc: any;
  id: string;
  client: any;
  clientList: any;
  LeadList;
  LeadListData: any;
  cont1: any;
  lead_id: any;
  leadName: any;
  AmList;
  accountManger_id: any;
  amName: any;
  details: any;
  records: any;
  userToken: string;
  RegId: any;
  clientId: any;
  clientListById: any;
  clientDetails: any;
  clientName: any;
  clientid: any;
  clientdata: any;
  clientclickid: any;
  addressDetails: any;
  clients: any;
  modalData;
  clientIdDetails: any;
  clientListBy: any;
  city: any;
  pincode: any;
  state: any;
  addressLine2: any;
  country: any;
  gstpercentage: any;
  gst: any;
  addressList: any;
  addressLine1: any;
  typeAddress: any;
  customerType: any;
  startDate: any;
  endDate: any;
  phone: any;
  email: any;
  sourceName: any;
  leavesAllowed: any;
  fax: any;
  website: any;
  totalItems;
  pageSize = 10;
  currentPage = 1;
  contractfile: any;
  attachment: any;
  clientresume: any;
  attach: any;
  contractFile: any;

  postdata;
  noteId: any;
  checkboxclientId;
  getnotedata;
  getnoteDeletedata;




  // serach code
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
  registrationId: any;
  searchvalue: any;
  data1: [];
  obj: any;
  searchInput: any;
  pageNumsearch: number;
  pageSizesearch: number;
  registrationIdsearch: number;
  search_values(event) {
    this.searchInput = event;
  }
  // search code end

  setPage(pagNo: number): void {
    this.currentPage = pagNo;
    this.getClientList();
  }

  serchform = this.formBuilder.group({
    clientName: [''],
    amName: [''],
    startDate: ['']
  });

  constructor(private serv :ExcelService,private service: SuperadminserviceService, private router: Router, private formBuilder: FormBuilder, private toaster: ToastrManager) { }

  ngOnInit() {
    // this.search();
    this.RegId = sessionStorage.getItem('registrationId');
    this.userToken = sessionStorage.getItem('Token');
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
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          registrationId: this.RegId,
          flag: true,
          clientName: term
        };
        console.log("post values", post1)
        if (term != '') {
          this.service.getClientList(post1).subscribe(
            resp => {
              this.details = resp;
              this.clientList = this.details.result;
              this.totalItems = this.details.totalRecords;
              if (this.details.status === "Accepted") {
                this.search_result = true;
                this.showNodata = false;
              } else if (this.details.status === "DataIsEmpty") {
                this.showNodata = true;
                if (this.showNodata == true) {
                  this.search_result = false;
                  this.search_Previous_result = true;
                }
              }
            })
        }
      });
    console.log(this.selectedClientName);
    this.getClientList();
    this.Exel();
  }


  save() {
    const searchData = {
      pageNum: this.currentPage,
      pageSize: this.pageSize,
      registrationId: this.RegId,
      flag: true,
      startDate: this.serchform.value.startDate,
      amName: this.serchform.value.amName,
      clientName: this.serchform.value.clientName
    };
    this.service.getClientList(searchData).subscribe(res => {
      console.log(res, "totalserchhhhhhhhhhhhhhhhhhhhhhhinterviewlist----------");
      this.details = res;
      this.totalItems = this.details.totalRecords;
      this.clientList = this.details.result;
      console.log(this.totalItems, "totalItems");
      console.log(this.clientList, "interviewist--------");
    });
    }

  ale() {
    window.alert('The field is empty');
  }

  getClientList() {
    const post = {
      pageNum: this.currentPage,
      pageSize: this.pageSize,
      registrationId: this.RegId,
      flag: true
    };
    this.service.getClientList(post).subscribe(res => {
      this.details = res;
      this.totalItems = this.details.totalRecords;
      this.clientList = this.details.result;
      console.log(this.totalItems, "totalItems");
      console.log(this.clientList, 'clientlist...........');
    });
  }
  Exel() {
    const post = {
      pageNum: this.currentPage,
      pageSize: this.pageSize,
      registrationId: this.RegId,
      flag: false
    };
    this.service.getClientList(post).subscribe(res => {
      this.details = res;
      // this.totalItems = this.details.totalRecords;
      this.clientList = this.details.result.json();
      console.log(this.totalItems, "totalItems");
      console.log(this.clientList, 'clientlist...........');
    });
  }
  getClientById(l) {
    this.clientid = l;
    var id = {
      id: this.clientid
    }
    debugger;
    this.service.getClientById(this.clientid, this.RegId).subscribe(res => {
      this.modalData = res;
      this.clientIdDetails = this.modalData.result;
      console.log(this.clientIdDetails, "clientgetbyiddetails-----===");

      this.clientListBy = this.clientIdDetails.client;
      this.clientName = this.clientListBy.clientName;
      this.contractFile = this.clientListBy.contractFile;
      console.log(this.contractFile, "atttttttttttttttt");
      this.customerType = this.clientListBy.customerType.customerType;
      this.startDate = this.clientListBy.startDate
      this.endDate = this.clientListBy.endDate;
      this.phone = this.clientListBy.phone;
      this.email = this.clientListBy.email;
      this.leavesAllowed = this.clientListBy.leavesAllowed;
      this.sourceName = this.clientListBy.source.sourceName;
      this.fax = this.clientListBy.fax;
      this.website = this.clientListBy.website;
      this.addressDetails = this.clientIdDetails.addressDetails;
      this.clientresume = this.clientListBy.contractFile;
      console.log(this.clientresume, "files...................")


      //  this.clientListBy=this.clientDetails.customerType;
      console.log(this.clientListBy, "id-------details");
      for (let j = 0; j < this.addressDetails.length; j++) {
        // if (this.addressDetails.length != 0) {
        this.city = this.addressDetails[j].city;
        this.pincode = this.addressDetails[j].pincode;
        this.state = this.addressDetails[j].state;
        this.addressLine1 = this.addressDetails[j].addressLane1;
        this.addressLine2 = this.addressDetails[j].addressLane2;
        this.country = this.addressDetails[j].country;
        this.typeAddress = this.addressDetails[j].typeOfAddress.typeOfAddress;
        this.gstpercentage = this.addressDetails[j].gstpercentage;
        this.gst = this.addressDetails[j].gst;
        // }
      }
    });
  }

  downloadExcel(){

    console.log(this.clientList,"fffffffff")
    this.serv.exportAsExcelFile(this.clientList, 'sample');
  }

  download() {
    var filepdf = 'data:application/pdf;base64,' + this.clientListBy.contractFile;
    //  }
    let a = document.createElement('a');
    a.href = filepdf;
    a.download = 'resume.pdf';
    a.click();
  }

  printPage() {
    window.print();
  }
  getclientId(checkboxclientId) {
    console.log(checkboxclientId, "clientiddddd========");
    this.checkboxclientId = checkboxclientId;

  }

  mail() {
    this.router.navigate(['superadmin/client/clientmail', this.checkboxclientId])
  }

  clientnameclick(id){
    this.router.navigate(['superadmin/client/clientnameclick',id])
  }

}

