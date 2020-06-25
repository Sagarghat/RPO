import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Validators, FormBuilder } from '@angular/forms';
import { ToastrManager } from 'ng6-toastr-notifications';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';
import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-editinterview',
  templateUrl: './editinterview.component.html',
  styleUrls: ['./editinterview.component.css']
})
export class EditinterviewComponent implements OnInit {
  interviewId: string;


  files: any;
  filestringContact: string;
  currentPage = 1;
  totalItems = 48;
  RegId: string;
  userToken: string;
  details;
  clientList: any;
  profileData;
  profileDetails: any;
  candidatelist;
  candidate: any;
  interviewName;
  interviewNameList: any;
  selectedInterviewName: any;
  interviewListId: any;
  selectedClientName: any;
  clientListId: any;
  selectedPostingTitle: any;
  JobOpeningId: any;
  contact;
  contactDetails: any;
  selectedInterviewContact: any;
  contactId: any;
  users: any;
  usersData: any;
  selectedInterviewOwner: any;
  userId: any;
  selectedCandidate: any;
  candidateList: any[];
  selectedCandidateName: string;
  candidateData: any;
  selectedcandidateList = [];
  selectedcandidateListOjects = [];
  assessment;
  assessmentNameList: any;
  selectedAssessmentName: any;
  assessmentId: any;
  remainder;
  remainderList: any;
  selectedReaminder: any;
  remainderId: any;
  submitted: boolean;
  getById;
  getByIdList: any;
  minDate: any;
  maxDate: any;
  candidateNameList: any;
  candidateNames: any;
  userid: string;
  regId: string;
  candidateId: any;
  pageSize = 4;
  totalOwnerItems;
  currentPageOwner = 1;
  interivewround;

  interviewForm = this.formBuilder.group({
    interviewName: ['', Validators.required],
    round:['',Validators.required],
    candidateName: [''],
    clientName: [''],
    postingTitle: [''],
    From: ['', Validators.required],
    fromtime: [''],
    To: [''],
    totime: [''],
    interview: [''],
    interviewOwner: ['', Validators.required],
    Location: ['', Validators.required],
    scheduleComments: [''],
    assessmentName: ['', Validators.required],
    remainder: ['', Validators.required],
    browse: [''],
  });
  candidateid: any;
  flag = true;
  usersData1: any;
  role: string;
  pazeSize: any = 4;
  pazeNumber: any = 1;
  interviewList: any;
  interviewdata: any;
  selectedroundName: any;
  interviewroundId: any;
  interviewRoundName: any;

  constructor(private activatedRoute: ActivatedRoute, private formBuilder: FormBuilder, private service: SuperadminserviceService, private toaster: ToastrManager, private router: Router, private http: HttpClient) { }

  setPageOwner(paNo: number): void {
    this.currentPageOwner = paNo;
    this.getUsers();
  }

  ngOnInit() {
    this.interviewId = this.activatedRoute.snapshot.paramMap.get('interviewid');
    this.userToken = sessionStorage.getItem('Token');
    this.userid = sessionStorage.getItem('userId');
    this.RegId = sessionStorage.getItem('registrationId');
    this.role = sessionStorage.getItem('role');
    console.log(this.interviewForm.value, "form data");
    this.getInterviewById();
    this.getUsers();
    this.getUsers1();
    this.getAssessmentNameList();
    this.getRemainderList();
    this.getInterviewName();
    this. getinterviewround();
  }

  getInterviewById() {
    debugger
    this.service.getInterviewById(this.interviewId).subscribe(res => {
      this.getById = res;
      this.getByIdList = this.getById.result;
      this.interviewList = this.getByIdList.details;
      this.candidateNameList = this.interviewList.candidate;
    
      this.candidateNames = this.candidateNameList.firstName;
      this.candidateid = this.candidateNameList.id;
     
      console.log(this.getById, "getbyidinterview ------");
      this.interviewForm.controls.interviewName.setValue(this.interviewList.interviewName.modeofInterview);
      this.interviewForm.controls.round.setValue(this.interviewList.round.level);
      this.interviewForm.controls.candidateName.setValue(this.candidateNames);
      this.interviewForm.controls.clientName.setValue(this.interviewList.client.clientName);
      this.interviewForm.controls.postingTitle.setValue(this.interviewList.bdmReq.nameOfRequirement);
      this.interviewForm.controls.fromtime.setValue(this.interviewList.fromtime);
      this.interviewForm.controls.totime.setValue(this.interviewList.toTime);
      this.interviewForm.controls.interviewOwner.setValue(this.interviewList.interviewOwner.name);
      this.interviewForm.controls.Location.setValue(this.interviewList.interviewLocation);
      this.interviewForm.controls.scheduleComments.setValue(this.interviewList.scheduleComments);
      this.interviewForm.controls.assessmentName.setValue(this.interviewList.assesmentName.assessmentName);
      this.interviewForm.controls.remainder.setValue(this.interviewList.remainder.time);
      this.interviewForm.controls.interview.setValue(this.interviewList.interviewer.lastName);
      this.interviewForm.controls.From.setValue(formatDate(this.interviewList.interviewFrom, 'yyyy-MM-dd', 'en'));
      if (this.interviewList.interviewEnd != null) {
        this.interviewForm.controls.To.setValue(formatDate(this.interviewList.interviewEnd, 'yyyy-MM-dd', 'en'));
      }


      console.log(this.interviewForm.value, "form------ data");
    });
  }

  get f() {
    return this.interviewForm.controls;
  }

  getContactFile(contact) {
    this.files = contact.target.files;
    var reader = new FileReader();
    reader.onload = this.handleReaderLoad.bind(this);
    reader.readAsBinaryString(this.files[0]);
  }

  handleReaderLoad(readerEvt) {
    var binaryString = readerEvt.target.result;
    this.filestringContact = btoa(binaryString);
    console.log(this.filestringContact, "file string");
    // Converting binary string data.
  }

  getInterviewName() {
    this.service.getInterviewNameList(this.pazeNumber,this.pazeSize).subscribe(resp => {
      console.log(resp, "interview name----list");
      this.interviewName = resp;
      this.interviewNameList = this.interviewName.result;
    })
  }
  getinterviewround()
  {
 debugger
    this.service.getInterviewRound(this.pazeNumber,this.pazeSize).subscribe(resp=>
      {
        this.interivewround=resp;
        this.interviewdata=this.interivewround.result;
        console.log(this.interviewdata,"interviewdata");
        
      })
  }


  getUsers() {

    let postdata = {
      pageNo: this.currentPageOwner,
      pageSize: this.pageSize,
      flag: true,
      registrationId: this.RegId,
      role: this.role
    }
    this.service.getuserList(postdata).subscribe((res: any) => {
      this.users = res;


      this.totalItems = this.users.totalRecords;
      console.log(this.totalItems, "user total items");

    });
  }

  getUsers1() {

    let postdata = {
      pageNo: this.currentPageOwner,
      pageSize: this.totalItems,
      flag: true,
      registrationId: this.RegId,
      role: this.role
    }
    this.service.getuserList(postdata).subscribe((res: any) => {
      console.log('userlist', res);
      this.users = res;
      this.usersData1 = this.users.result;
      this.usersData = this.usersData1.list;

      console.log(this.usersData, "user getAll");
    });
  }

  getAssessmentNameList() {
    this.service.getAssessmentNameList().subscribe(resp => {
      console.log(resp, "assessment name----list");
      this.assessment = resp;
      this.assessmentNameList = this.assessment.result;
    })
  }

  getRemainderList() {
    this.service.getRemainderList().subscribe(resp => {
      console.log(resp, "remainder----list");
      this.remainder = resp;
      this.remainderList = this.remainder.result;
    })
  }

  onChange(email, isChecked: boolean) {
    if (isChecked) {
      this.selectedcandidateList.push(email);
      for (let i = 0; i < this.candidateData.length; i++) {
        if (email == this.candidateData[i].fullName) {
          this.selectedcandidateListOjects.push({ firstName: this.candidateData[i].fullName, id: this.candidateData[i].id });
          break;
        }
      }
    }
    else {
      let index = this.selectedcandidateList.indexOf(email);
      let index1 = this.selectedcandidateListOjects.indexOf(email);
      this.selectedcandidateList.splice(index, 1);
      this.selectedcandidateListOjects.splice(index1, 1);
    }
    this.selectedCandidateName = this.selectedcandidateList.toString();
    console.log(this.selectedcandidateListOjects, "object");
  }

  save() {
    this.submitted = true;
    console.log(this.interviewForm.value, "interviewForm data");

    var interviewName = this.selectedInterviewName;
    for (var i = 0; i < this.interviewNameList.length; i++) {
      if (this.interviewNameList[i].modeofInterview === interviewName) {
        this.interviewListId = this.interviewNameList[i].id;
        break;
      }

    }

    var interviewOwner = this.selectedInterviewOwner;
    for (var i = 0; i < this.usersData.length; i) {
      if (this.usersData[i].name === interviewOwner) {
        this.userId = this.usersData[i].id;
        break;
      }
      i++;
      console.log(i, "iteration .........................................");
      if (i == (this.usersData.length - 1)) {
        i = 0;
        if (this.currentPageOwner <= this.totalItems) {
          this.currentPageOwner += 1;
          this.setPageOwner(this.currentPageOwner);
        }
      }
    }

    var Assesment = this.selectedAssessmentName;
    for (var i = 0; i < this.assessmentNameList.length; i++) {
      if (this.assessmentNameList[i].assessmentName === Assesment) {
        this.assessmentId = this.assessmentNameList[i].id;
        break;
      }
    }

    var remainder = this.selectedReaminder;
    for (var i = 0; i < this.remainderList.length; i++) {
      if (this.remainderList[i].time === remainder) {
        this.remainderId = this.remainderList[i].id;
        break;
      }
    }

    var round=this.selectedroundName;
    for (var i = 0; i < this.interviewdata.length; i++) {
      if (this.interviewdata[i].level === round) {
        this.interviewroundId = this.interviewdata[i].id;
        this.interviewRoundName = this.interviewdata[i].level;
        break;
      }
    }


    let data = {
      details:
      {
        id: this.interviewId,
        registrationId: this.RegId,
        interviewName: {
          id: this.interviewListId
        },
        client: {
          id: this.interviewList.client.id
        },
        bdmReq: {
          id: this.interviewList.bdmReq.id
        },
        interviewer: {
          id: this.interviewList.interviewer.id
        },
        interviewOwner: {
          id: this.userId
        },
        assesmentName: {
          id: this.assessmentId
        },
        interviewLocation: this.interviewForm.value.Location,
        scheduleComments: this.interviewForm.value.scheduleComments,
        interviewFrom: this.interviewForm.value.From,
        fromtime: this.interviewForm.value.fromtime,
        interviewEnd: this.interviewForm.value.To,
        // formatDate(this.UserById.dob, 'yyyy-MM-dd', 'en')
        toTime: this.interviewForm.value.totime,
        remainder:
        {
          id: this.remainderId
        },
        candidate: {
          id: this.candidateid
        },
        round:{
          id:this.interviewroundId,
          level:this.interviewRoundName
        }
      },
      candidates: null,
      attachement: this.filestringContact
    }

    this.service.addInterview(data).subscribe(resp => {
      console.log(resp, "aedit interview");
      if (resp['status'] == "StatusSuccess") {
        this.toaster.successToastr('Interview edited successfully');
        this.router.navigate(['/superadmin/interview']);
      }
      else {
        this.toaster.warningToastr('Fill the fields !!', 'Warning!');
      }
    });
  }

}

