import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';
import { ExcelService } from 'src/app/excel.service';
import { ToastrManager } from 'ng6-toastr-notifications';
import { ActivatedRoute } from '@angular/router';
declare var $;


@Component({
  selector: 'app-submissions',
  templateUrl: './submissions.component.html',
  styleUrls: ['./submissions.component.css']
})
export class SubmissionsComponent implements OnInit {
  RegId: string;
  pageSize = 10;
  currentPage = 1;
  first: any;
  status;
  totalItems;
  statusgetall;
  mappingI;
  bdmReqI;
  candidateI;
  statusSS;
  shtrlist;

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
  BDMID: any;
  bdmreqid: any;
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

  constructor(private ser: SuperadminserviceService,  private toaster: ToastrManager,
    private formBuilder: FormBuilder, private serv: ExcelService,private activeRoute: ActivatedRoute) { }

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
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          regId: this.RegId,
          flag: true,
          requirementName: term
        };
        if (term != '') {
          this.ser.getscreendetails(post1).subscribe(
            resp => {
              this.status = resp;
              this.totalItems = this.status.totalRecords;
              this.statusgetall = this.status.result;
              if (this.status.status === "Accepted") {
                this.search_result = true;
                this.data = this.status.result;
                this.showNodata = false;
              } else if (this.status.status === "DataIsEmpty") {
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
    this.bdmreqid = this.activeRoute.snapshot.paramMap.get('id');
    console.log(this.bdmreqid,"bdmreqid");
    this.getscreendetails()
  }

  save() {
    const searchData = {
      regId: this.RegId,
      pageSize: this.pageSize,
      pageNum: this.currentPage,
      flag: true,
      clientName: this.serchform.value.clientName,
      requirementName: this.serchform.value.requirementName,
      fromDate: Date.parse(this.serchform.value.fromDate),
      toDate: Date.parse(this.serchform.value.toDate)
    };
    this.ser.getscreendetails(searchData).subscribe(res => {
      this.status = res;
      this.totalItems = this.status.totalRecords;
      this.statusgetall = this.status.result;
      console.log(this.statusgetall, "searched submitted_candidates getAll dataaa");

    });
  }

  downloadExcel() {
    this.serv.exportAsExcelFile(this.statusgetall, 'Submissions data');
  }

  exel() {
    const post = {
      stage: "screening",
      pageSize: this.pageSize,
      pageNum: this.currentPage,
      regId: this.RegId,
    };
    this.ser.getscreendetails(post).subscribe(res => {
      this.status = res;
      this.statusgetall = this.status.result.json();
    });
  }

  setPage(pagNo: number): void {
    this.currentPage = pagNo;
     this.getscreendetails();
  }

  getscreendetails() {
    this.RegId = sessionStorage.getItem("registrationId");
    this.bdmreqid = this.activeRoute.snapshot.paramMap.get('id');
    let data = {
      stage: "Submission",
     pageSize: this.pageSize,
      pageNum: this.currentPage,
      regId: this.RegId,
      bdmReq:{
        id:this.bdmreqid
      },
      flag:true
    }
    debugger;
    this.ser.getscreendetails(data).subscribe((resp) => {
      console.log(resp);
      this.status = resp;
      this.totalItems = this.status.totalRecords;
      this.statusgetall = this.status.result;
      console.log("submitted_candidates getAll dataaa", this.statusgetall);
      for (let i = 0; i < this.statusgetall.length; i++) {
        this.mappingI = this.statusgetall[i].mappingId;
        console.log(this.mappingI, "mapping Id");
      }
      for (let i = 0; i < this.statusgetall.length; i++) {
        this.bdmReqI = this.statusgetall[i].bdmReqId;
        console.log(this.bdmReqI, "bdmReq Id");
      }
      for (let i = 0; i < this.statusgetall.length; i++) {
        this.candidateI = this.statusgetall[i].candidateId;
        console.log(this.candidateI, "candidate Id");
      }
      for (let i = 0; i < this.statusgetall.length; i++) {
        this.statusSS = this.statusgetall[i].status;
        console.log(this.statusSS, "status");
      }
    });
  }
  shortlist(mi, st) {
    this.mappingI = mi;
    console.log(this.mappingI, "mapping iddd");
    this.statusSS = st;
    console.log(this.statusSS, "statusss");

  }
  commetform = this.formBuilder.group(
    {
      remars: ['', Validators.required]
    }
  )

  shortlistedbyclient(flag) {
    let data = {
      regId: this.RegId,
      id: this.mappingI,
      candidateStatus: this.statusSS,
      remarks: this.commetform.value.remars,
      statusFlag: flag
    }
    this.ser.shortlistclient(data).subscribe(resp => {
      this.shtrlist = resp;
      if(this.shtrlist['status'] === 'StatusSuccess')
      {
        $("#shortlisted").modal("hide");
        this.toaster.successToastr('candidate shortlisted successfully');
        this.getscreendetails();
      }
      if(this.shtrlist['status'] === 'Bad_Request')
      {
        this.toaster.warningToastr(this.shtrlist['res']);
      }
    })
  }

}