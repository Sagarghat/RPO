import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ToastrManager } from 'ng6-toastr-notifications';
import { Router, ActivatedRoute } from '@angular/router';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';

@Component({
  selector: 'app-addinterview',
  templateUrl: './addinterview.component.html',
  styleUrls: ['./addinterview.component.css']
})
export class AddinterviewComponent implements OnInit {

  selectedroundName: any;
  files: any;
  filestringContact: string;
  RegId: string;
  userToken: string;
  details;
  clientList: any;
  profileData;
  profileDetails: any;
  candidatelist;
  candidate: any;
  interviewNameData;
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
  minDate: any;
  maxDate: any;
  setPage: any;

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
  pageSize = 4;
  totalCandidateItems;
  currentPageCandid = 1;
  totalClientItems;
  currentPageClient = 1;
  totalProfileItems;
  currentPageJob = 1;
  totalContactItems;
  currentPageContact = 1;
  totalOwnerItems;
  currentPageOwner = 1;
  divStatus = true;
  divStatus1 = true;
  ContactNameClickid: any;
  editConactData;
  editConactDetails: any;
  contactInternalDetails: any;
  ClientNameClickid: any;
  clientinterviewList: any;
  clientinterviewListById: any;
  CandidateNameClickid: any;
  status:any;


  interviewForm = this.formBuilder.group({
    interviewName: ['', Validators.required],
    round:['',Validators.required],
    candidateName: ['', Validators.required],
    clientName: ['', [Validators.required]],
    postingTitle: ['', [Validators.required]],
    From: ['', Validators.required],
    fromtime: ['', Validators.required],
    To: [''],
    totime: [],
    interview: ['', Validators.required],
    interviewOwner: ['', Validators.required],
    Location: ['', Validators.required],
    scheduleComments: [''],
    assessmentName: ['', Validators.required],
    remainder: ['', Validators.required],
    browse: ['']
    // fileName: ['']
  });

  JobNameClickid: any;
  jobOpening;
  jobOpeningListById: any;

  flag = true;
  usersData1: any;
  role: string;
  interviewName: any;
  pazeSize: any = 4;
  pazeNumber: any = 1;
  interivewround;
  interviewdata: any;
  interviewroundId: any;
  interviewRoundName: any;

  constructor(private activeRoute: ActivatedRoute, private formBuilder: FormBuilder, private service: SuperadminserviceService, private toaster: ToastrManager, private router: Router) {

    this.activeRoute.queryParams.subscribe(params => {
      this.ContactNameClickid = params['val'];
      console.log(this.ContactNameClickid, "ContactNameClickid");
    });

    this.activeRoute.queryParams.subscribe(params => {
      this.ClientNameClickid = params['val'];
      console.log(this.ClientNameClickid, "ClientNameClickid");
    });


    this.activeRoute.queryParams.subscribe(params => {
      this.JobNameClickid = params['val'];
      console.log(this.JobNameClickid, "JobNameClickid");
    });

    this.activeRoute.queryParams.subscribe(params => {
      this.CandidateNameClickid = params['val'];
      console.log(this.CandidateNameClickid, "CandidateNameClickid");
    });
  }

  onChanges() {
    this.interviewForm.get('clientName').valueChanges
      .subscribe(selectedCountry => {
        if (selectedCountry == undefined) {
          this.divStatus = true;
        }
        else {
          this.divStatus = false;
        }
      });

    this.interviewForm.get('postingTitle').valueChanges
      .subscribe(selectedPost => {
        if (selectedPost == undefined) {
          this.divStatus1 = true;
        }
        else {
          this.divStatus1 = false;
        }
      });
  }

  setPageJob(pagNo: number): void {
    this.currentPageJob = pagNo;
    this.profileGetData();
  }

  setPageCandidate(page: number): void {
    this.currentPageCandid = page;
    this.getallcandidateslist();
  }

  setPageClient(pages: number): void {
    this.currentPageClient = pages;
    this.getClientList();
  }

  setPageContact(pagesNo: number): void {
    this.currentPageContact = pagesNo;
    this.getContactsList();

  }

  setPageOwner(paNo: number): void {
    this.currentPageOwner = paNo;
    this.getUsers();
  }

  ngOnInit() {
    this.RegId = sessionStorage.getItem('registrationId');
    this.userToken = sessionStorage.getItem('Token');
    this.role = sessionStorage.getItem('role');
    console.log("form data..........", this.interviewForm);
    this.getUsers();
    this.getClientList();
    this.getInterviewName();
    this.getAssessmentNameList();
    this.getRemainderList();
    this.getClientById();
    this.getallcandidateslist();
    this.getcontactById();
    this.getjobOpeningById();
    console.log(this.clientListId, "clientId=====");
    this.onChanges();
    this.getCandidateById();
    this.getinterviewround();
  }
  get f() {
    return this.interviewForm.controls;
  }

  getClientById() {
    this.service.getClientById(this.ClientNameClickid, this.RegId).subscribe(res => {
      this.clientinterviewList = res;
      this.clientinterviewListById = this.clientinterviewList.result;
      console.log(this.clientinterviewListById, "clientcontactByid----");
      this.interviewForm.controls.clientName.setValue(this.clientinterviewListById.client.clientName);
      console.log(this.clientinterviewListById.client.clientName, "clientNameById");
    });
  }

  getcontactById() {
    this.service.getcontactById(this.ContactNameClickid).subscribe(response => {
      this.editConactData = response;
      this.editConactDetails = this.editConactData.result;
      console.log(this.editConactDetails, "CONTACT============================================");
      this.contactInternalDetails = this.editConactDetails.contact;

      console.log(this.editConactDetails, "CONTACT============================================");
      this.contactInternalDetails = this.editConactDetails.contact;
      console.log(this.contactInternalDetails.lastName, "sdgfdgd");
      this.interviewForm.controls.interview.setValue(this.contactInternalDetails.lastName);
      console.log(this.contactInternalDetails.lastName, "mahdbsaa");

      this.interviewForm.controls.clientName.setValue(this.contactInternalDetails.client.clientName);
      this.interviewForm.controls.interview.setValue(this.contactInternalDetails.lastName);
      console.log(this.contactInternalDetails.lastName, "sdgfdgd");
      console.log(this.contactInternalDetails, "contactgetById-------");
    });
  }
  getjobOpeningById() {
    let jobIdRequest = {
      id: this.JobNameClickid,
      registrationId: this.RegId
    }
    this.service.getjobById(jobIdRequest).subscribe(res => {

      this.jobOpening = res;
      this.jobOpeningListById = this.jobOpening.result;


      console.log(this.jobOpeningListById, "jobbb=----------------------");
      this.interviewForm.controls.clientName.setValue(this.jobOpeningListById.client.clientName);
      this.interviewForm.controls.postingTitle.setValue(this.jobOpeningListById.nameOfRequirement);
      // addContact
      console.log(this.jobOpeningListById.client.clientName, "hgdfsdgjfsvdjfsvd");

      // this.interviewForm.controls.postingTitle.setValue(this.jobOpeningListById.bdmReq.nameOfRequirement);

    });
  }




  candidatedata: any;
  candidatelistts: any;
  getCandidateById() {


    this.service.getCandidateById(this.CandidateNameClickid).subscribe(resp => {
      this.candidatedata = resp;
      this.candidatelistts = this.candidatedata.result;
      console.log(this.candidatelistts, "candidatesssssssssssssssssssssssssssss");
      this.interviewForm.controls.candidateName.setValue(this.candidatelistts.firstName);
      console.log(this.candidatelistts.firstName, "fulllllllllnameee");



    });
  }
  modelChanged(clientname) {
    console.log(clientname, "lahari========");
    for (var i = 0; i < this.clientList.length; i++) {
      if (this.clientList[i].clientName === clientname) {

        this.clientListId = this.clientList[i].id;
        this.profileGetData();
        this.getContactsList();
        console.log(this.clientListId, "clientId=====");
        break;
      }
    }
  }

  bdmRequirementId(bdmRequirement) {
    for (var i = 0; i < this.profileDetails.length; i++) {
      if (this.profileDetails[i].nameOfRequirement === bdmRequirement) {
        this.JobOpeningId = this.profileDetails[i].id;
        console.log( this.JobOpeningId," this.JobOpeningId");
        
        this.getallcandidateslist();
        break;
      }
    }
  }

  getClientList() {
    const post = {
      pageNum: this.currentPageClient,
      pageSize: this.pageSize,
      registrationId: this.RegId,
      flag: true
    };
    this.service.getClientList(post).subscribe(res => {
      this.details = res;
      this.totalClientItems = this.details.totalRecords;
      console.log(this.totalClientItems, "total client Items");
      this.clientList = this.details.result;
      var clientName = this.selectedClientName;
      console.log(clientName, "clientname==========");
    });
  }

  profileGetData() {
    var profileData = {
      pageNum: this.currentPageJob,
      pageSize: this.pageSize,
      registrationId: this.RegId,
      clientId: this.clientListId,
      flag: true
    }
    this.service.getAllJobOpenings(profileData).subscribe(resp => {
      this.profileData = resp;
      this.profileDetails = this.profileData.result;
      this.totalProfileItems = this.profileData.totalRecords;


      console.log(this.totalProfileItems, "total job Items");
      console.log(this.profileDetails, " Profile data---------")
    })
  }

  getallcandidateslist() {
    const post = {
      pageNum: this.currentPageCandid,
      pageSize: this.pageSize,
      regId: this.RegId,
      candidateStatus: "Shortlisted",
      stage: "Submission",
      bdmReq: {
        id: this.JobOpeningId
      },
      flag:true
    };
    debugger;
    this.service.getShortlistedCandidates(post).subscribe(res => {
      this.candidatelist = res;
      // this.totalCandidateItems = this.candidatelist.totalRecords;
      // console.log(this.totalCandidateItems, "total candidate Items");
      this.candidateData = this.candidatelist.result;
      console.log(this.candidateData, 'candgetall...........');
    });
  }

  getContactsList() {
    var postData =
    {
      pageNum: this.currentPageContact,
      pageSize: this.pageSize,
      clientId: this.clientListId,
      registrationId: JSON.parse(this.RegId),
      flag: true
    }
    this.service.getContactList(postData).subscribe(resp => {
      console.log(resp, 'contactlist');
      this.contact = resp;
      this.totalContactItems = this.contact.totalRecords;
      console.log(this.totalContactItems, "total contact Items");
      this.contactDetails = this.contact.result;
      console.log(this.contactDetails, "contact");
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
    this.service.getInterviewNameList(this.pazeNumber,this.pazeSize).subscribe(resp => {
      console.log(resp, "interview name----list");
      this.interviewNameData = resp;
      this.interviewNameList = this.interviewNameData.result;
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
      role: this.role,

    }
    this.service.getuserList(postdata).subscribe((res: any) => {
      console.log('userlist', res);
      this.users = res;
      this.totalOwnerItems = this.users.totalRecords;
      console.log(this.totalOwnerItems, "total owner Items");
      this.usersData1 = this.users.result;
      this.usersData = this.usersData1.list;
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
        if (email == this.candidateData[i].candidateName) {
          this.selectedcandidateListOjects.push({ firstName: this.candidateData[i].candidateName, id: this.candidateData[i].candidateId });
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

    if (this.interviewForm.invalid) {
      console.log(this.interviewForm, "invalid interviewForm data");
      this.toaster.warningToastr('fill the mandatory fields !!', 'Warning!');
    }
    else {
      console.log(this.interviewForm.value, "interviewForm data");
      var interviewName = this.selectedInterviewName;
      for (var i = 0; i < this.interviewNameList.length; i++) {
        if (this.interviewNameList[i].modeofInterview === interviewName) {
          this.interviewListId = this.interviewNameList[i].id;
          this.interviewName = this.interviewNameList[i].interviewName;
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
      let data = {
        details:
        {
          interviewName: {
            id: this.interviewListId,
            modeofInterview: this.interviewName
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
          interviewFrom:this.interviewForm.value.From,
          fromtime: this.interviewForm.value.fromtime,
          interviewEnd:this.interviewForm.value.To,
          toTime: this.interviewForm.value.totime,
          remainder:
          {
            id: this.remainderId
          },
          round:
          {
            id:this.interviewroundId,
            level:this.interviewRoundName
          },
          registrationId: this.RegId,
          attachement: this.filestringContact,
          status: "Interview scheduled",
          candidate:null ,
          // this.selectedcandidateListOjects
        },
        // fileName: this.files[0].name,
        candidates: this.selectedcandidateListOjects
      }
       console.log("status",this.status)
      this.service.addInterview(data).subscribe(resp => {
        console.log(resp, "add interview");
        if (resp['status'] == "StatusSuccess") {
          this.toaster.successToastr('Interview scheduled successfully');
          this.router.navigate(['/superadmin/interview']);
        }
        else {
          this.toaster.warningToastr('Something went wrong!', 'Warning');
        }
      });
    }
  }


}
