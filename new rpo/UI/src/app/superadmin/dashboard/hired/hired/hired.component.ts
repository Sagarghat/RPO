import { Component, OnInit } from '@angular/core';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';
import { FormBuilder, FormControl } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ToastrManager } from 'ng6-toastr-notifications';
import { ExcelService } from 'src/app/excel.service';
declare var $;
@Component({
  selector: 'app-hired',
  templateUrl: './hired.component.html',
  styleUrls: ['./hired.component.css']
})
export class HiredComponent implements OnInit {

  first: any;
  pageSize1 = 10;
  currentPage1 = 1;
  RegId: any;
  pageSize = 10;
  currentPage = 1;
  intrview;
  interviewdataa;
  mappingI: any;
  bdmReqI: any;
  candidateI: any;
  statusSS: any;
  STatus;
  MAppingId;
  intrv;
  intervww;
  offering
  offeringdata: any;
  bdmrqid: any;
  totalItems;
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
  constructor(private ser: SuperadminserviceService, private formBuilder: FormBuilder,private activeRoute: ActivatedRoute,private toaster: ToastrManager, private serv: ExcelService) { }

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
              this.intrview = resp;
              this.interviewdataa = this.intrview.result;
              console.log(this.interviewdataa, "dataaa");
              if (this.interviewdataa.status === "Accepted") {
                this.search_result = true;
                this.data = this.interviewdataa.result;
                this.showNodata = false;
              } else if (this.interviewdataa.status === "DataIsEmpty") {
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
    this.gethiring();
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
      this.intrview = res;
      this.interviewdataa = this.intrview.result;
      console.log(this.interviewdataa, "dataaa");
    });
  }
  
  setPage(pagNo: number): void {
    this.currentPage = pagNo;
     this.gethiring();
  }

  gethiring() {
  
    this.bdmrqid = this.activeRoute.snapshot.paramMap.get('id');
    this.RegId = sessionStorage.getItem("registrationId");
    let data = {
      stage: "Hiring",
      pageSize: this.pageSize,
      pageNum: this.currentPage,
      regId: this.RegId,
      bdmReq:{
        id:this.bdmrqid
      },
      flag:true
    }
    debugger;
    this.ser.getscreendetails(data).subscribe(resp => {
      this.intrview = resp;
      this.totalItems = this.intrview.totalRecords;
      this.interviewdataa = this.intrview.result;
      console.log(this.interviewdataa, "interview dataa");
      for (let i = 0; i < this.interviewdataa.length; i++) {
        this.mappingI = this.interviewdataa[i].mappingId;
        console.log(this.mappingI, "mappingI");
      }
      for (let i = 0; i < this.interviewdataa.length; i++) {
        this.bdmReqI = this.interviewdataa[i].bdmReqId;
        console.log(this.bdmReqI, "bdmReqI");
      }
      for (let i = 0; i < this.interviewdataa.length; i++) {
        this.candidateI = this.interviewdataa[i].candidateId;
        console.log(this.candidateI, "candidateI");
      }
      for (let i = 0; i < this.interviewdataa.length; i++) {
        this.statusSS = this.interviewdataa[i].status;
        console.log(this.statusSS, "statusSS");
      }
    });
  }
  
  downloadExcel() {
    console.log(this.interviewdataa, "fffffffff")
    this.serv.exportAsExcelFile(this.interviewdataa, 'Hired data');
  }

}
