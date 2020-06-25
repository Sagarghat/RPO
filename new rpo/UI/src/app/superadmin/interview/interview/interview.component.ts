import { Component, OnInit } from '@angular/core';
import { SuperadminserviceService } from '../../superadminservice.service';
import { Router } from '@angular/router';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { ToastrManager } from 'ng6-toastr-notifications';
import { ExcelService } from 'src/app/excel.service';
declare var $;
@Component({
  selector: 'app-interview',
  templateUrl: './interview.component.html',
  styleUrls: ['./interview.component.css']
})
export class InterviewComponent implements OnInit {
  RegId: string;
  userToken: string;
  first: any;
  totalItems;
  show: any;
  pageSize = 10;
  currentPage = 1;
  interviewdetails;
  interviewList: any;
  stat: boolean = false;
  status: boolean = false;
  sat: boolean = true;
  getById;
  getByIdList: any;
  candidateNameList: any;
  candidateNames: any;
  candiadteresume: any;
  InterviewName: any;
  bdmReq: any;
  candidateList: any;
  candidatemobile: any;
  candidateEmail: any;
  candidateStatusdetails;
  candidateStatusList: any;
  selectedCandidateStatus;
  status1 = 'Enable';
  toggle = false;
  select = false;
  CandidateStatusId: any;
  bdmReqId: any;
  candidateId: any;
  interviewId: any;
  statusOfCandidate: any;
  togglle = false;
  togggle = false;
  resumeFileName: any;
  resumefileExtension: any;
  interviewfeedbackForm = this.formBuilder.group({
    Comments: ['', Validators.required],
    candidateStatus: ['', Validators.required],
  });
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
  serchform = this.formBuilder.group({
    fromDate: [''],
    toDate: ['']
  });
  excel: any;
  interviewExel: any;

  constructor(private toaster: ToastrManager, private service: SuperadminserviceService, private formBuilder: FormBuilder, private router: Router, private serv: ExcelService) {
  }

  setPage(pagNo: number): void {
    this.currentPage = pagNo;
    this.getInterviewList();
  }

  search_values(event) {
    this.searchInput = event;
  }

  details() {
    this.status = true;
  }

  Resume() {
    this.status = false;
  }

  changeStatus() {
    this.sat = false;
  }

  change() {
    this.stat = true;
  }

  change1() {
    this.stat = false;
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
          regId: this.RegId,
          flag: true,
          candidateName: term,
        };
        if (term != '') {
          this.service.getInterviewList(post1).subscribe(
            resp => {
              this.interviewdetails = resp;
              this.excel = this.interviewdetails.result;
              console.log(this.excel, "exceeeeeeeeeeeeeee")
              this.interviewList = this.interviewdetails.result;
              this.totalItems = this.interviewdetails.totalRecords;
              if (this.interviewdetails.status === "Accepted") {
                this.search_result = true;
                this.interviewList = this.interviewdetails.result;
                this.showNodata = false;
              } else if (this.interviewdetails.status === "DataIsEmpty") {
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
    this.userToken = sessionStorage.getItem('Token');
    this.getInterviewList();
    this.addCandidateStatus();
    this.exel();
  }

  downloadExcel() {
    console.log(this.interviewList, "fffffffff")
    this.serv.exportAsExcelFile(this.interviewList, 'Interviews');
  }

  save() {
    const searchData = {
      pageNo: this.currentPage,
      pageSize: this.pageSize,
      regId: this.RegId,
      flag: true,
      from: this.serchform.value.fromDate,
      to: this.serchform.value.toDate,
    };
    this.service.getInterviewList(searchData).subscribe(res => {
      console.log(res, "totalserchhhhhhhhhhhhhhhhhhhhhhhinterviewlist----------");
      this.interviewdetails = res;
      this.totalItems = this.interviewdetails.totalRecords;
      console.log(this.totalItems, "totalItems");
      this.interviewList = this.interviewdetails.result;
      console.log(this.interviewList, "interviewist--------");
    });
  }

  enableDisableRule(statusOfCandidate) {
    console.log(statusOfCandidate, 'statusOfCandidate');
    this.statusOfCandidate = statusOfCandidate;
    this.toggle = !this.toggle;
    this.select = !this.select;
    this.togglle = !this.togglle;
    this.togggle = !this.togggle;
    this.status1 = this.toggle ? 'Enable' : 'Disable';
  }

  addCandidateStatus() {
    this.service.addCandidateStatusMaster().subscribe(res => {
      console.log(res, "totalinterviewlist----------");
      this.candidateStatusdetails = res;
      this.candidateStatusList = this.candidateStatusdetails.result
      console.log(this.candidateStatusdetails, "addCandidateStatus");
    });
  }

  interviewnameclick(interviewid) {
    this.router.navigate(['superadmin/interview/interviewname', interviewid]);
  }

  getInterviewList() {
    const post = {
      pageNo: this.currentPage,
      pageSize: this.pageSize,
      regId: this.RegId,
      flag: true
    };
    this.service.getInterviewList(post).subscribe(res => {
      console.log(res, "totalinterviewlist----------");
      this.interviewdetails = res;
      this.totalItems = this.interviewdetails.totalRecords;
      console.log(this.totalItems, "totalItems");
      this.interviewList = this.interviewdetails.result;
      console.log(this.interviewList, "interviewist--------");
    });
  }

  exel() {
    const post = {
      pageNo: this.currentPage,
      pageSize: this.pageSize,
      regId: this.RegId,
      flag: false
    };
    this.service.getInterviewList(post).subscribe(res => {
      console.log(res, "totalinterviewlist----------");
      this.interviewdetails = res;
      this.interviewList = this.interviewdetails.result.json();
      console.log(this.interviewList, "interviewist--------");
    });
  }

  download() {
    var filepdf = 'data:application/pdf;base64,' + this.candiadteresume;
    let a = document.createElement('a');
    a.href = filepdf;
    a.download = 'resume.pdf';
    a.click();
  }

  getInterviewById(interviewId) {
    this.service.getInterviewById(interviewId).subscribe(res => {
      this.getById = res;
      this.interviewList = this.getById.result;
      this.getByIdList = this.interviewList.details;
      this.interviewId = this.getByIdList.id
      this.candidateNameList = this.getByIdList.candidate;
      this.InterviewName = this.getByIdList.interviewName.modeofInterview;
      this.bdmReq = this.getByIdList.bdmReq.nameOfRequirement;
      this.bdmReqId = this.getByIdList.bdmReq.id;
      console.log(this.InterviewName, "this.InterviewName");
      this.candidateNames = this.candidateNameList.firstName;
      this.candidatemobile = this.candidateNameList.mobile
      this.candidateEmail = this.candidateNameList.email
      this.candiadteresume = this.candidateNameList.resume;
      this.candidateId = this.candidateNameList.id;
      this.resumeFileName = this.candidateNameList.fileName;
      this.resumefileExtension = this.candidateNameList.fileExtension;
      console.log(this.candiadteresume, "candiadteresume ------");
      console.log(this.getByIdList, "getbyidinterview ------");
    });
  }

  postInterviewFeedback() {
    var CandidateStatus = this.selectedCandidateStatus;
    for (var i = 0; i < this.candidateStatusList.length; i++) {
      if (this.candidateStatusList[i].status === CandidateStatus) {
        this.CandidateStatusId = this.candidateStatusList[i].id;
        break;
      }
    }
    let data = {
      date: "2019-02-02",
      requirementId: this.bdmReqId,
      candidateid: this.candidateId,
      interviewId: this.interviewId,
      status: this.statusOfCandidate,
      comments: this.interviewfeedbackForm.value.Comments,
      cStatus: {
        id: this.CandidateStatusId
      }
    }
    this.service.postInterviewFeedback(data).subscribe(res => {
      if (res['status'] == "StatusSuccess") {
        this.getInterviewList();
        this.toaster.successToastr('Interview feedback is given successfully');
      }
    })
  }

}
