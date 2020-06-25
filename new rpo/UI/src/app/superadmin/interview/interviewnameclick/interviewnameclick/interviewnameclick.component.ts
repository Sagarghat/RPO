import { Component, OnInit, HostListener } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { formatDate } from '@angular/common';
import { FormBuilder, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ToastrManager } from 'ng6-toastr-notifications';
import { ServiceService } from 'src/app/services/service.service';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';

@Component({
  selector: 'app-interviewnameclick',
  templateUrl: './interviewnameclick.component.html',
  styleUrls: ['./interviewnameclick.component.css']
})
export class InterviewnameclickComponent implements OnInit {
  interviewnameId;
  status1: boolean = true;
  text: String = "HideDetails";

  minDate: any;
  maxDate: any;
  postingTitle: any;
  openDate: any;

  status: boolean = true;
  userToken: string;
  regId: string;

  files: any;
  filestringContact: string;
  pageSize = 10;
  currentPage = 1;
  totalItems = 48;
  RegId: string;
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

  interviewForm = this.formBuilder.group({
    interviewName: ['', Validators.required],
    round:['',Validators.required],
    candidateName: ['', Validators.required],
    clientName: ['', [Validators.required]],
    postingTitle: ['', [Validators.required]],
    From: ['', Validators.required],
    fromtime: ['', Validators.required],
    To: ['', Validators.required],
    totime: ['', Validators.required],
    interview: ['', Validators.required],
    interviewOwner: ['', Validators.required],
    Location: ['', Validators.required],
    scheduleComments: [''],
    assessmentName: ['', Validators.required],
    remainder: ['', Validators.required],
    browse: [''],
  });

  noteForm = this.formBuilder.group({
    note: ['',Validators.required],
    noteSelect: ['',Validators.required]
  })
  userid: string;
  candidateNameList: any;
  candidateNames: any;
  candidateid: any;
  interviewId: any;
  InterviewName: any;
  bdmReqId: any;
  bdmReq: any;
  comments: any;
  flag = true;
  toTime: any;
  todate: any;
  fromdate: any;
  postdata;
  getnotedata;
  getdetails: any;
  noteId: any;
  getnoteDeletedata;
  pazeSize: any;
  pazeNumber: any;
  interivewround;
  interviewList: any;
  interviewdata: any;
  constructor(private service: SuperadminserviceService, private activatedRoute: ActivatedRoute, private formBuilder: FormBuilder, private http: HttpClient, private toaster: ToastrManager, private router: Router, private addclientservice: SuperadminserviceService) { }
  setPage(pagNo: number): void {
    this.currentPage = pagNo;
   
    this.getClientList();
    this.profileGetData();
    this.getallcandidateslist();

    this.getContactsList();
    this.getAssessmentNameList();
    this.getRemainderList();
    this.getUsers();
    this.getInterviewName();
 
  }
  ngOnInit() {
    this.interviewnameId = this.activatedRoute.snapshot.paramMap.get('interviewname');
    this.RegId = sessionStorage.getItem('registrationId');
    this.userid = this.activatedRoute.snapshot.paramMap.get('userId');
    this.userToken = sessionStorage.getItem('Token');
    this.regId = sessionStorage.getItem('registrationId');
    this.getInterviewById();
    this.getClientList();
    this.profileGetData();
    this.getallcandidateslist();
    this.getUsers();
    this.getContactsList();
    this.getAssessmentNameList();
    this.getRemainderList();
    this.getInterviewsnoteData();
    // this.getinterviewround();
  }
  ShowHideClick() {
    this.status = false;
  }

  get f() {
    return this.interviewForm.controls;
  }

  hidedd() {
    this.text = (this.text === "ShowDetails") ? "HideDetails" : "ShowDetails"
    if (this.text === "ShowDetails") {
      this.status1 = false;
    }
    else {
      this.status1 = true;
    }
  }

  isShow: boolean;
  topPosToStartShowing = 50;
  @HostListener('window:scroll')
  checkScroll() {
    const scrollPosition = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
    // console.log('[scroll]', scrollPosition);
    if (scrollPosition >= this.topPosToStartShowing) {
      this.isShow = true;
    } else {
      this.isShow = false;
    }
  }

  gotoTop() {
    window.scroll({
      top: 0,
      left: 0,
      behavior: 'smooth'
    });


  }

//   getinterviewround()
//   {
//  debugger
//     this.service.getInterviewRound(this.pazeNumber,this.pazeSize).subscribe(resp=>
//       {
//         this.interivewround=resp;
//         this.interviewdata=this.interivewround.result;
//         console.log(this.interviewdata,"interviewdata");
        
//       })
//   }

  getInterviewById() {
    debugger
    this.service.getInterviewById(this.interviewnameId).subscribe(res => {
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





  getClientList() {
    const post = {
      pageNum: this.currentPage,
      pageSize: this.pageSize,
      registrationId: this.RegId
    };
    this.service.getClientList(post).subscribe(res => {
      this.details = res;
      this.clientList = this.details.result;
      console.log(this.clientList, 'clientlist...........');
    });
  }


  profileGetData() {
    var profileData = {
      pageNum: this.currentPage,
      pageSize: 4,
      registrationId: this.RegId
    }
    this.service.getAllJobOpenings(profileData).subscribe(resp => {
      this.profileData = resp;
      this.profileDetails = this.profileData.result;
      // console.log(this.profileDetails.id,"id")
      console.log(this.profileDetails, " Profile data---------")

    })
  }

  getallcandidateslist() {

    const post = {
      pageNum: this.currentPage,
      pageSize: this.pageSize,
      registrationId: this.RegId
    };
    this.service.getallcandidateslist(post).subscribe(res => {
      this.candidatelist = res;
      this.candidateData = this.candidatelist.result;
      console.log(this.candidateData, 'candgetall...........');
    });
  }

  getContactsList() {

    var postData =
    {
      pageNum: this.currentPage,
      pageSize: this.pageSize,
      // pageSize: this.pageSize,
      registrationId: JSON.parse(this.RegId),

    }


    this.service.getContactList(postData).subscribe(resp => {
      console.log(resp, 'contactlist');

      this.contact = resp;

      this.contactDetails = this.contact.result;
      // this.contactOwner = this.contact1.contactOwner;
      console.log(this.contactDetails, "contact");
      this.totalItems = this.contact.totalRecords;
      this.totalItems = this.contact.totalRecords;
      console.log("totalRecords:" + this.totalItems);
    });
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
    this.service.getInterviewNameList(this.pazeSize,this.pazeNumber).subscribe(resp => {
      console.log(resp, "interview name----list");
      this.interviewName = resp;
      this.interviewNameList = this.interviewName.result;
    })
  }

  getUsers() {
    let postdata={
      pageNo:this.currentPage,
      pageSize:this.pageSize,
     flag:true,
 registrationId: this.RegId,
    }
    this.service.getuserList(postdata).subscribe((res: any) => {
      console.log('userlist', res);
      this.users = res;
      this.usersData = this.users.result;
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


    var clientName = this.selectedClientName;
    for (var i = 0; i < this.clientList.length; i++) {
      if (this.clientList[i].clientName === clientName) {
        this.clientListId = this.clientList[i].id;
        break;
      }
    }


    var jobOpening = this.selectedPostingTitle;
    for (var i = 0; i < this.profileDetails.length; i++) {
      if (this.profileDetails[i].nameOfRequirement === jobOpening) {
        this.JobOpeningId = this.profileDetails[i].id;
        break;
      }
    }
    var interviewContact = this.selectedInterviewContact;
    for (var i = 0; i < this.contactDetails.length; i++) {
      if (this.contactDetails[i].lastName === interviewContact) {
        this.contactId = this.contactDetails[i].id;
        break;
      }
    }


    var interviewOwner = this.selectedInterviewOwner;
    for (var i = 0; i < this.usersData.length; i++) {
      if (this.usersData[i].name === interviewOwner) {
        this.userId = this.usersData[i].id;
        break;
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

    // var candidate = this.selectedReaminder;

    //   if (this.remainderList[i].time === Assesment) {
    //     this.remainderId = this.remainderList[i].id;

    //   }


    let data = {
      id: this.interviewnameId,
      registrationId: this.RegId,
      interviewName: {
        id: this.interviewListId
      },
      client: {
        id: this.clientListId
      },

      bdmReq: {
        id: this.JobOpeningId
      },

      interviewer: {
        id: this.contactId
      },
      interviewOwner: {
        id: this.userId
      },
      assesmentName: {
        id: this.assessmentId
      },
      interviewLocation: this.interviewForm.value.Location,
 
      scheduleComments: this.interviewForm.value.scheduleComments,

      interviewFrom: Date.parse(this.interviewForm.value.From),

      fromtime: this.interviewForm.value.fromtime,

      interviewEnd: Date.parse(this.interviewForm.value.To),
      toTime: this.interviewForm.value.totime,
      remainder:
      {
        id: this.remainderId
      },
      candidate: this.selectedcandidateListOjects,
      attachement: this.filestringContact
    }


    this.service.addInterview(data).subscribe(resp => {

      console.log(resp, "aedit interview");

      if (resp['status'] == "StatusSuccess") {
        this.toaster.successToastr('Interview edited successfully');
        this.router.navigate(['/superadmin/interview']);
      }
      else {
        this.toaster.warningToastr('fill the fields !!', 'Warning!');
      }


    });

  }

  getInterviewsnoteData() {
    let getnotedata = {
      registrationId:  this.RegId,
      pageSize: 20,
      pageNum: 1
    }
    this.service.getInterviewnotes(getnotedata).subscribe(res => {
      this.getnotedata = res;
      this.getdetails = this.getnotedata.result;

      this.noteId = this.getdetails.id;
      console.log(this.getdetails, "interview getnotedetails");
 
    })
  }

   //delete
   interviewNotedelete(id) {
    // let deletenotedata = {
    //   id: id,
    //   registrationId: this.regId,
    //   requirementId: this.interviewnameId,
    //   noteData: noteData,
    //   noteType: noteType
    // }
    this.service.deleteInterviewnotes(id).subscribe(res => {
      this.getnoteDeletedata = res;
      if (this.getnoteDeletedata.status == "StatusSuccess") {
        this.toaster.successToastr('Interview note deleted successfully');
        this.getInterviewsnoteData();
      }
      else {
        this.toaster.warningToastr('Error in  deleting the note', 'Warning!');
      }
      console.log(this.getnoteDeletedata, "note delete details");
    })
  }
  
  saveInterviewnotes(){
    if(this.noteForm.invalid){
      this.toaster.warningToastr('please enter note description');
    }
    else{
    let interviewnotesave = {
      registrationId: this.RegId,
      interviewId: this.interviewnameId,
      noteData: this.noteForm.value.noteSelect,
      noteType: this.noteForm.value.note
    }
  
    this.service.saveInterviewnotes(interviewnotesave).subscribe(resp => {
      console.log(resp, " interview notes");
      this.postdata=resp;
      if (this.postdata.status == "StatusSuccess") {
      
        this.toaster.successToastr('Interview note added successfully');
        this.getInterviewsnoteData();
      }
});
    }
}

}