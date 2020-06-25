import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormControl } from '@angular/forms';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';
import { ActivatedRoute } from '@angular/router';
import { ToastrManager } from 'ng6-toastr-notifications';
import { ExcelService } from 'src/app/excel.service';
declare var $;
@Component({
  selector: 'app-offered',
  templateUrl: './offered.component.html',
  styleUrls: ['./offered.component.css']
})
export class OfferedComponent implements OnInit {

  downloadExcel: any;
  first: any;
  pageSize1 = 10;
  currentPage1 = 1;
  shortlist: any;
  commetform: any;
  shortlistedbyclient: any;
  RegId: any;
  pageSize = 10;
  currentPage = 1
  status;
  totalItems;
  statusgetall;
  mappingI;
  bdmReqI;
  candidateI;
  statusSS;
  shtrlist;
  bdmrqid: string;
  MAppingId: any;
  STatus: any;
  candid: any;
  offerrelse: Object;

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
  data: [];
  obj: any;
  searchInput: any;
  pageNumsearch: number;
  pageSizesearch: number;
  registrationIdsearch: number;
  postdata;
  getnoteDeletedata;
  profileExcel: any;
  ishotVar: number;
  BDMIDD;
  BDMID;
  interviewCount: any;
  interviewStage: any;
  offeringCount: any;
  offeringStage: any;
  search_values(event) {
    this.searchInput = event;
  }
  serchform = this.formBuilder.group({
    clientName: [''],
    requirementName: [''],
    fromDate: [''],
    toDate: ['']
  });
  // search code end
  constructor(private ser: SuperadminserviceService, private formBuilder: FormBuilder, private activeRoute: ActivatedRoute, private toaster: ToastrManager, private serv: ExcelService) { }

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
          pageNum: this.currentPage1,
          pageSize: this.pageSize1,
          regId: this.RegId,
          flag: true,
          requirementName: term
        };
        console.log("post values", post1)
        if (term != '') {
          this.ser.mappings(post1).subscribe(
            resp => {
              this.status = resp;
              this.statusgetall = this.status.result;
              console.log(this.statusgetall, "dataaa");
              if (this.statusgetall.status === "Accepted") {
                this.search_result = true;
                this.data = this.statusgetall.result;
                this.showNodata = false;
              } else if (this.statusgetall.status === "DataIsEmpty") {
                this.showNodata = true;
                if (this.showNodata == true) {
                  this.search_result = false;
                  this.search_Previous_result = true;
                }
              }
            })
        }
      });
    this.RegId = sessionStorage.getItem("registrationId");
    this.bdmrqid = this.activeRoute.snapshot.paramMap.get('id');
    this.getscreendetails()
  }

  save() {
    const searchData = {
      regId: this.RegId,
      pageSize: this.pageSize1,
      pageNum: this.currentPage1,
      flag: true,
      clientName: this.serchform.value.clientName,
      requirementName: this.serchform.value.requirementName,
      fromDate: Date.parse(this.serchform.value.fromDate),
      toDate: Date.parse(this.serchform.value.toDate)
    };
    this.ser.mappings(searchData).subscribe(res => {
      this.status = res;
      this.statusgetall = this.status.result;
    });
  }

  setPage(pagNo: number): void {
    this.currentPage = pagNo;
    this.getscreendetails();
  }

  getscreendetails() {
    this.RegId = sessionStorage.getItem("registrationId");
    this.bdmrqid = this.activeRoute.snapshot.paramMap.get('id');

    let data = {
      stage: "Offered",
      pageSize: this.pageSize,
      pageNum: this.currentPage,
      regId: this.RegId,
      bdmReq: {
        id: this.bdmrqid
      },
      flag: true
    }
    console.log("data ----->", data);
    this.ser.getscreendetails(data).subscribe((resp) => {
      console.log(resp);
      this.status = resp;
      this.totalItems = this.status.totalRecords;
      this.statusgetall = this.status.result;
      console.log("data in table", this.statusgetall);
      for (let i = 0; i < this.statusgetall.length; i++) {
        this.mappingI = this.statusgetall[i].mappingId;
        console.log(this.mappingI, "mappingI");
      }
      for (let i = 0; i < this.statusgetall.length; i++) {
        this.bdmReqI = this.statusgetall[i].bdmReqId;
        console.log(this.bdmReqI, "bdmReqI");
      }
      for (let i = 0; i < this.statusgetall.length; i++) {
        this.candidateI = this.statusgetall[i].candidateId;
        console.log(this.candidateI, "candidateI");
      }
      for (let i = 0; i < this.statusgetall.length; i++) {
        this.statusSS = this.statusgetall[i].status;
        console.log(this.statusSS, "statusSS");
      }
    });
  }

  offerclick(mappingidd, sttus, cand) {
    this.MAppingId = mappingidd;
    console.log(this.MAppingId, "MAppingId");
    this.STatus = sttus;
    console.log(this.STatus, "STatus");
    this.candid = cand
    console.log(this.candid, "candid");
  }
  offerform = this.formBuilder.group(
    {
      ctcOffered: ['', Validators.required],
      dateOfJoining: ['', Validators.required],
      remarks: ['', Validators.required]
    }
  )

  onboardstatus(flag) {
    let data = {
      stage: "Offered",
      candidateStatus: this.STatus,
      statusFlag: flag,
      dateOfJoining: this.offerform.value.dateOfJoining,
      ctcOffered: this.offerform.value.ctcOffered,
      candidate:
      {
        id: this.candid
      },
      remarks: this.offerform.value.remarks,
      regId: this.RegId,
      id: this.MAppingId
    }
    this.ser.Onboardstatus(data).subscribe(res => {
      this.offerrelse = res;
      console.log(this.offerrelse, "offerrelease");
      if (this.offerrelse['status'] === 'StatusSuccess') {
        this.toaster.successToastr('Candidate OnBoarded Successfully');
      }
      else (this.offerrelse['status'] === 'Bad_Request')
      {
        this.toaster.warningToastr(this.offerrelse['res']);
      }
    })
  }

}