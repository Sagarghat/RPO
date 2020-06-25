import { Component, OnInit } from '@angular/core';
import { SuperadminserviceService } from '../../superadminservice.service';
import { IfStmt } from '@angular/compiler';
import { Router } from '@angular/router';
import { FormControl, FormBuilder } from '@angular/forms';
import { ExcelService } from 'src/app/excel.service';
declare var $;


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  first: any;
  statusCount: any;
  submissionCount: any;
  screeningCount: any;
  RegId: any;
  mappingdata: any;
  mappData;
  count;
  screeningStage;
  submissionStage;
  pageSize1 = 10;
  currentPage1 = 1;
  pageSize3 = 10;
  pageSize = 2;
  currentPage = 2;
  mapplist;
  mapplistdata;

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
  hiringCount: any;
  hiringStage: any;
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

  constructor(private service: SuperadminserviceService, private rout: Router, private formBuilder: FormBuilder, private serv: ExcelService) { }

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
          this.service.mappings(post1).subscribe(
            resp => {
              this.mappingdata = resp;
              this.mappData = this.mappingdata.result;
              console.log(this.mappData, "dataaa");
              if (this.mappData.status === "Accepted") {
                this.search_result = true;
                this.data = this.mappData.result;
                this.showNodata = false;
              } else if (this.mappData.status === "DataIsEmpty") {
                this.showNodata = true;
                if (this.showNodata == true) {
                  this.search_result = false;
                  this.search_Previous_result = true;
                }
              }
            })
        }
      });
    this.RegId = sessionStorage.getItem('registrationId');
    this.postmappings()
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
    this.service.mappings(searchData).subscribe(res => {
      this.mappingdata = res;
      this.mappData = this.mappingdata.result;
      console.log(this.mappData, "dataaa");
    });
  }

  postmappings() {
    var data = {
      regId: this.RegId,
      pageSize: this.pageSize3,
      pageNum: this.currentPage1,
      flag: true
    }
    this.service.mappings(data).subscribe(resp => {
      this.mappingdata = resp;
      this.mappData = this.mappingdata.result;
      console.log(this.mappData, "dataaa");
      for (let i = 0; i < this.mappData.length; i++) {
        if (this.mappData[i].stages === "Submission") {
          this.submissionCount = this.mappData[i].statusCount;
          this.submissionStage = this.mappData[i].stages;
          console.log(this.submissionCount, "counrt");
        }
        if (this.mappData[i].stages === "Screening") {
          this.screeningCount = this.mappData[i].statusCount;
          this.screeningStage = this.mappData[i].stages;
        }

        if (this.mappData[i].stages === "Interveiw") {
          this.interviewCount = this.mappData[i].statusCount;
          this.interviewStage = this.mappData[i].stages;
        }
        if (this.mappData[i].stages === "Offered") {
          this.offeringCount = this.mappData[i].statusCount;
          this.offeringStage = this.mappData[i].stages;
        }
        if (this.mappData[i].stages === "Hiring") {
          this.hiringCount = this.mappData[i].statusCount;
          this.hiringStage = this.mappData[i].stages;
        }
  
  
      //   this.count = this.mappData.statusCount;
      //   console.log(this.count, "countt")
     }
    })
  }

  mappinglist(screeningd, count,bdmid) {
   
    if (count != 0) {
      this.BDMIDD=bdmid;
      let data = {
        stage: "Screening",
        regId: this.RegId,
        pageSize: this.pageSize,
        pageNum: this.currentPage,
        bdmReq:
        {
          id:this.BDMIDD
        },
        flag:true
      }
      debugger;
      this.service.getscreendetails(data).subscribe(res => {
        this.mapplist = res;
        this.mapplistdata = this.mapplist.result;
        console.log(this.mapplist, "mapdataa");
        if (this.mapplist.status == "OK") {
          this.rout.navigate(['superadmin/dashboard/map',this.BDMIDD])
        }
      });
    }
  }

  submissionRouting(screeningd,count,bdmid)
  {
    this.BDMIDD=bdmid
    console.log(this.BDMIDD,"BDMIDD");
    if (count != 0) {
    let data = {
      stage:"Submission",
      regId: this.RegId,
      pageSize: this.pageSize,
      pageNum: this.currentPage,
      bdmReq:
      {
        id:this.BDMIDD
      },
        flag:true
    }
    debugger;
    this.service.getscreendetails(data).subscribe(res => {
      this.mapplist = res;
      this.mapplistdata = this.mapplist.result;
      console.log(this.mapplist, "mapdataa");
   
      if (this.mapplist.status == "OK") {
      this.rout.navigate(['superadmin/dashboard/submissions',this.BDMIDD])
    }
  });
  }
}

interviewRouting(screeningd,count,bdmid)
  {
    this.BDMID=bdmid
    console.log(this.BDMIDD,"BDMIDD");
    if (count != 0) {
      let data = {
        stage:"Interview",
        regId: this.RegId,
        pageSize: this.pageSize,
        pageNum: this.currentPage,
        bdmReq:
        {
          id:this.BDMID
        },
        flag:true
      }
      debugger;
      this.service.getscreendetails(data).subscribe(res => {
        this.mapplist = res;
        this.mapplistdata = this.mapplist.result;
        console.log(this.mapplist, "mapdataa");
        if (this.mapplist.status == "OK") {
      this.rout.navigate(['superadmin/dashboard/interv',this.BDMID])
    }
  });
}
  }


  offeredRouting(screeningd,count,bdmid)
  {
    this.BDMIDD=bdmid
    console.log(this.BDMIDD,"BDMIDD");
    if (count != 0) {
      let data = {
        stage:"Offered",
        regId: this.RegId,
        pageSize: this.pageSize,
        pageNum: this.currentPage,
        bdmReq:
        {
          id:this.BDMIDD
        },
        flag:true
      }
      debugger;
      this.service.getscreendetails(data).subscribe(res => {
        this.mapplist = res;
        this.mapplistdata = this.mapplist.result;
        console.log(this.mapplist, "mapdataa");
        if (this.mapplist.status == "OK") {
      this.rout.navigate(['superadmin/dashboard/offered',this.BDMIDD])
    }
  })
}
  }


  hiredRouting(screeningd,count,bdmid)
{
  this.BDMIDD=bdmid
  console.log(this.BDMIDD,"BDMIDD");
  if (count != 0) {
    let data = {
      stage:"Hiring",
      regId: this.RegId,
      pageSize: this.pageSize,
      pageNum: this.currentPage,
      bdmReq:
      {
        id:this.BDMIDD
      },
      flag:true
    }
    this.service.getscreendetails(data).subscribe(res => {
      this.mapplist = res;
      this.mapplistdata = this.mapplist.result;
      console.log(this.mapplist, "mapdataa");
      if (this.mapplist.status == "OK") {
    this.rout.navigate(['superadmin/dashboard/hired',this.BDMIDD])
  }
})
}
  }

  downloadExcel() {
    console.log(this.mappData, "fffffffff")
    this.serv.exportAsExcelFile(this.mappData, 'Candidate data');
  }

  submitclient() {
    this.rout.navigate(['superadmin/dashboard/map'])
  }

}
