import { Component, OnInit, HostListener } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { ToastrManager } from 'ng6-toastr-notifications';
import { Router, ActivatedRoute } from '@angular/router';
// import { ServiceService } from 'src/app/services/service.service';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';
// import { invalid } from '@angular/compiler/src/render3/view/util';

@Component({
  selector: 'app-jobnameclick',
  templateUrl: './jobnameclick.component.html',
  styleUrls: ['./jobnameclick.component.css']
})
export class JobnameclickComponent implements OnInit {


  jobnameClick: any;
  save: any;
  openDate: any;
  status1: boolean = true;
  requirementStartdate: any;
  requirementEndDate: any;
  text: String = "HideDetails"
  selectedjobType: any;
  selectedclient: any;
  selectedSkillname: any;
  selectedCertificatename: any;
  selectedQualificationname: any;
  selectedlocationname: any;
  selectedWorkExperience: any;
  selectedNoticePeriod: any;
  selectedjobStatus: any;
  selectedrequirement: any;
  minDate: any;
  maxDate: any;
  setPage: any;
  totalItems: any;
  jobOpeningId;
  userToken;
  regId;
  jobOpening;
  jobOpeningListById: any;
  clientDetails: any;
  addContactDetails: any;
  ski: any;
  skills: any;
  qualifications: any;
  contact;
  contactDetails: any;
  client;
  clientData: any;
  skillDetails: any;
  qualification;
  qualificationDetails: any;
  certificate;
  certificateDetails: any;
  designation;
  designationDetails: any;
  locationData;
  skill;
  selectedClient: any;
  selectedCientName: any;
  selectedContact: any;
  selectedContactName: any;
  selectedSkill: any;
  selectedskillName: any;
  selectedDesignation: any;
  selectedDesignationName: any;
  selectedCertificate: any;
  selectedcertificateName: any;
  selectedqualification: any;
  selectedqualificationName: any;
  selectedLocation: any;
  selectedlocationName: any;
  jobopeningData;
  currentPage: any = 1;
  qual: any;
  loc: any;
  locations: any;
  designations: any;
  des: any;
  certificates: any;
  cer: any;
  postingTitle: any;
  isHot: void;
  isHotComment: any;
  status: boolean = true;
  clientName: any;
  getnotedata;
  getdetails: any;
  noteId: void;
  clientid;
  getnoteDeletedata;

  jobType =
    [
      'Contract to Hire', 'Contract', 'Permanent'
    ];

  WorkExperience =
    [
      '0-1year',
      '1-2 Years',
      '2-3 years',
      '3-4 Years',
      '4+ years'
    ];

  noticePeriod =
    [
      '1',
      '2',
      '3',
      '4',
      '4+'
    ];

  JobStatus =
    [
      'Open',
      'Closed',
      'Onhold'
    ];

  requirementType =
    [
      'Generic',
      'Niche',
      'Super Niche'
    ];

  requirementForm = this.formBuilder.group({
    clientName: ['', Validators.required],
    contactName: ['', Validators.required],
    postingTitle: ['', Validators.required],
    noOfPositions: ['', Validators.required],
    openDate: ['', Validators.required],
    endDate: ['', Validators.required],
    experience: ['', Validators.required],
    skill: ['', Validators.required],
    qualification: ['', Validators.required],
    location: ['', Validators.required],
    jobType: [''],
    Country: [''],
    otherskill: [''],
    jobdescription: [''],
    otherqualification: [''],
    openingStatus: [''],
    minBudget: [''],
    maxBudget: [''],
    designation: ['', Validators.required],
    certificate: ['', Validators.required],
    RevenueperPosition: [''],
    ExpectedRevenue: [''],
    ActualRevenue: [''],
    MissedRevenue: [''],
    city: [''],
    budgettype: [''],
    amount: [''],
    durationOfContract: [''],
    noticePeriod: [''],
    requirementType: [''],
    daysMonths:[]
  });

  noteForm = this.formBuilder.group({
    note: ['',Validators.required],
    noteSelect: ['',Validators.required]
  })
  postdata;
  country: any;
  otherSkills: any;
  jobdescription: any;
  otherqualification;
  openingStatus;
  minBudget;
  maxBudget;
  RevenueperPosition;
  ExpectedRevenue;
  ActualRevenue;
  MissedRevenue;
  city;
  budgettype;
  amount;
  durationOfContract;
  noticePe;
  requirement;
  noteType: any;
  noteData: any;
  interviewdetails: any;
  interviewList: any;
  clientList: any;
  details: any;
  
  clientidd;
  noticePeTy: any;
  constructor(private formBuilder: FormBuilder, private http: HttpClient,
     private toaster: ToastrManager, private router: Router, private service: SuperadminserviceService, private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.jobOpeningId = this.activatedRoute.snapshot.paramMap.get('id');
    this.userToken = sessionStorage.getItem('Token');
    this.regId = sessionStorage.getItem('registrationId');
   
    
    console.log("form data..........", this.requirementForm);
    this.getjobOpeningById();
    this.getnoteData();
    this.getClientList();
    this.getInterviewList();
    this.count();
  }

  get f() {
    return this.requirementForm.controls;
  }
  countby;


  list;
  screen;
  submis;
  inter;
  offer;
  hired;
  
 
  othercount;
count(){
let data = {
    regId: this.regId,
      bdmReq:{
        id:this.jobOpeningId
      }
    }

  
this.service.count(data).subscribe(res=>{
  
  this.countby = res;
  this.list= this.countby.result;
  console.log(this.countby,"countttttt")
  console.log(this.countby.result.screeningCount,"hhhhhhhhhhhhhh")
this.screen=this.list.screeningCount;
this.submis=this.list.submissionCount;
this.inter=this.list.interviewCount;
this.offer=this.list.offerCount;
this.hired=this.list.hiredCount;
this.othercount=this.list.otherCount;

})







}
  getjobOpeningById() {
    var id = {
      bdmId: this.jobOpeningId,
      registrationId: this.regId
    }
    this.service.getjobById(id).subscribe(res => {

      this.jobOpening = res;
      this.jobOpeningListById = this.jobOpening.result;
      this.clientDetails = this.jobOpeningListById.client;
    
      console.log( this.clientid,"ggg")
      this.addContactDetails = this.jobOpeningListById.addContact;
      this.postingTitle = this.jobOpeningListById.nameOfRequirement
      this.skills = this.jobOpeningListById.skills;
      this.qualifications = this.jobOpeningListById.qualifications;
      this.locations = this.jobOpeningListById.locations;
      this.designations = this.jobOpeningListById.designations;
      this.certificates = this.jobOpeningListById.certificates;
      this.isHot = this.jobOpeningListById.comment;
      this.requirementStartdate = this.jobOpeningListById.requirementStartdate;
      this.requirementEndDate = this.jobOpeningListById.requirementEndDate;
      this.country = this.jobOpeningListById.country;
      this.otherSkills = this.jobOpeningListById.otherSkills;
      this.jobdescription = this.jobOpeningListById.jobDesc;
      this.otherqualification = this.jobOpeningListById.otherQualification;
      this.openingStatus = this.jobOpeningListById.requirementStatus;
      this.minBudget = this.jobOpeningListById.minBudget;
      this.maxBudget = this.jobOpeningListById.maxBudget;
      this.RevenueperPosition = this.jobOpeningListById.revenuePerPosition;
      this.ExpectedRevenue = this.jobOpeningListById.expectedRevenue;
      this.ActualRevenue = this.jobOpeningListById.actualRevenue;
      this.MissedRevenue = this.jobOpeningListById.missedRevenue;
      this.city = this.jobOpeningListById.city;
      this.budgettype = this.jobOpeningListById.budgetTypeForPermanent;
      this.amount = this.jobOpeningListById.budgetAmountForPermanent;
      this.durationOfContract = this.jobOpeningListById.durationOfContract;
      this.noticePe = this.jobOpeningListById.noticePeriod;
      this.noticePeTy = this.jobOpeningListById.noticePeriodType;
      this.requirement = this.jobOpeningListById.requirementType;
      this.selectedjobStatus = this.jobOpeningListById.requirementStatus;
      // this.clientId = this.jobOpeningListById.
      if(this.selectedjobStatus == '' || this.selectedjobStatus == null)
      {
        this.selectedjobStatus = "Open";
      }
      else
      {
        this.selectedjobStatus = this.selectedjobStatus;
      }
      this.clientName = this.clientDetails.clientName;
      this.clientid = this.clientDetails.id;
      console.log(this.clientid,"clientid")
      sessionStorage.setItem('clientid', this.clientid);
      // if (this.isHot != null) {
      //   this.isHotComment = this.isHot;
      // }
      // else {
      //   this.isHotComment = "No Records found";
      // }

      console.log(this.jobOpeningListById, "jobOpening list by id----");

      for (let i = 0; i < this.skills.length; i++) {
        this.ski = this.skills[i].skillName
      }
      for (let i = 0; i < this.qualifications.length; i++) {
        this.qual = this.qualifications[i].qualificationName
      }
      for (let i = 0; i < this.locations.length; i++) {
        this.loc = this.locations[i].locationName
      }
      for (let i = 0; i < this.designations.length; i++) {
        this.des = this.designations[i].designation
      }
      for (let i = 0; i < this.certificates.length; i++) {
        this.cer = this.certificates[i].certificationName
      }

      this.requirementForm.controls.clientName.setValue(this.clientDetails.clientName);
      this.requirementForm.controls.contactName.setValue(this.addContactDetails.firstName);
      this.requirementForm.controls.postingTitle.setValue(this.jobOpeningListById.nameOfRequirement);
      this.requirementForm.controls.noOfPositions.setValue(this.jobOpeningListById.noOfPositions);
      this.requirementForm.controls.openDate.setValue(formatDate(this.jobOpeningListById.requirementStartdate, 'yyyy-MM-dd', 'en'));
      this.requirementForm.controls.endDate.setValue(formatDate(this.jobOpeningListById.requirementEndDate, 'yyyy-MM-dd', 'en'));
      this.requirementForm.controls.experience.setValue(this.jobOpeningListById.totalExperience);
      this.requirementForm.controls.Country.setValue(this.jobOpeningListById.country);
      this.requirementForm.controls.skill.setValue(this.ski);
      this.requirementForm.controls.otherskill.setValue(this.jobOpeningListById.otherSkills);
      this.requirementForm.controls.qualification.setValue(this.qual);
      this.requirementForm.controls.location.setValue(this.loc);
      this.requirementForm.controls.jobType.setValue(this.jobOpeningListById.jobType);
      this.requirementForm.controls.designation.setValue(this.des);
      this.requirementForm.controls.certificate.setValue(this.cer);
      this.requirementForm.controls.jobdescription.setValue(this.jobOpeningListById.jobDesc);
      this.requirementForm.controls.otherqualification.setValue(this.jobOpeningListById.otherQualification);
      this.requirementForm.controls.openingStatus.setValue(this.jobOpeningListById.requirementStatus);
      this.requirementForm.controls.minBudget.setValue(this.jobOpeningListById.minBudget);
      this.requirementForm.controls.maxBudget.setValue(this.jobOpeningListById.maxBudget);
      this.requirementForm.controls.ExpectedRevenue.setValue(this.jobOpeningListById.expectedRevenue);
      this.requirementForm.controls.ActualRevenue.setValue(this.jobOpeningListById.actualRevenue);
      this.requirementForm.controls.MissedRevenue.setValue(this.jobOpeningListById.missedRevenue);
      this.requirementForm.controls.city.setValue(this.jobOpeningListById.city);
      this.requirementForm.controls.budgettype.setValue(this.jobOpeningListById.budgetTypeForPermanent);
      this.requirementForm.controls.amount.setValue(this.jobOpeningListById.budgetAmountForPermanent);
      this.requirementForm.controls.durationOfContract.setValue(this.jobOpeningListById.durationOfContract);
      this.requirementForm.controls.noticePeriod.setValue(this.jobOpeningListById.noticePeriod);
      this.requirementForm.controls.daysMonths.setValue(this.jobOpeningListById.noticePeriodType);
      this.requirementForm.controls.requirementType.setValue(this.jobOpeningListById.requirementType);
      this.requirementForm.controls.RevenueperPosition.setValue(this.jobOpeningListById.revenuePerPosition);
    });
  }

  ShowHideClick() {
    this.status = false;
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
  notesave() {
    if(this.noteForm.invalid){
      this.toaster.warningToastr('Please enter note description');
    }
    else{
    let notesave = {
      registrationId: this.regId,
      // requirementId: this.jobOpeningId,
      noteData: this.noteForm.value.noteSelect,
      noteType: this.noteForm.value.note,
      recruiterId:this.jobOpeningId
    }
    this.service.notesave(notesave).subscribe(res => {
      this.postdata=res
      if (this.postdata.status == "StatusSuccess") {
        this.toaster.successToastr('Note added successfully');
        this.getnoteData();
      }
      console.log(res, "save note");
      this.getnoteData();
    })
  }
}


  getnoteData() {







    let getnotedata = {
      registrationId: this.regId,
      // requirementId: this.jobOpeningId,
      pageSize: 20,
      pageNum: 1,
      resourceId:this.jobOpeningId
    }
  
    this.service.getnoteData(getnotedata).subscribe(res => {
      this.getnotedata = res;
      this.getdetails = this.getnotedata.result;

      console.log(this.getdetails, "getnotedetails");
     
    })
  }
   

  //delete
  notedeletedata(id) {
    this.service.notedeletedata(id).subscribe(res => {
      this.getnoteDeletedata = res;
      if (this.getnoteDeletedata.status == "StatusSuccess") {
        this.toaster.successToastr('Note deleted successfully');
        this.getnoteData();
      }
      else {
        this.toaster.warningToastr('Error deleting the note', 'Warning!');
      }
      console.log(this.getnoteDeletedata, "note delete details");
    })
  }
  

  // interview

  ScheduleInterviews(){
    this.router.navigate(['superadmin/interview/addinterview'],{ queryParams: { val: this.jobOpeningId }});
  }

  getInterviewList() {

    this.clientid = sessionStorage.getItem('clientid');
   
    
    const post = {
      pageNo: this.currentPage,
      pageSize: 3,
      regId: this.regId,
      flag: true,
      clientId:this.clientid
     
    };
    this.service.getInterviewList(post).subscribe(res => {
      console.log(res, "totalinterviewlist----------");
      this.interviewdetails = res;
      this.interviewList = this.interviewdetails.result;
      console.log(this.interviewList, "interviewist--------");
    });

  }

  viewmoreinterviews(){
    this.router.navigate(['superadmin/interview']);
  }


  // client Submissions

  getClientList() {

    this.clientid = sessionStorage.getItem('clientid');
    const post = {
      pageNum: this.currentPage,
      pageSize: 3,
      registrationId: this.regId,
      flag: true,
      clientId:this.clientid
    };
    this.service.getClientList(post).subscribe(res => {
      this.details = res;
      this.clientList = this.details.result;
      console.log(this.clientList, 'clientlist...........');
    });
  }

  viewmoreclients(){
    this.router.navigate(['superadmin/client']);
  }

}
