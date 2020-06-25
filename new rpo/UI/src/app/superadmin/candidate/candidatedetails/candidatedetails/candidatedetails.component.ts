import { Component, OnInit, HostListener } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { ServiceService } from 'src/app/services/service.service';
import { ToastrManager } from 'ng6-toastr-notifications';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';

@Component({
  selector: 'app-candidatedetails',
  templateUrl: './candidatedetails.component.html',
  styleUrls: ['./candidatedetails.component.css']
})
export class CandidatedetailsComponent implements OnInit {


  save: any;
  status1: boolean = true;
  selecteddegree: any;
  jobopening: any;
  currentRate: any;
  text: String = "HideDetails";
  candidateid;
  profileData;
  profileDetails: any;
  RegId: string;
  Domain: string;
  userToken: string;
  filestring: string;
  files: any;
  degree: any;
  deg: any;
  selectedSource: any;
  selectedskilss: any;
  selectedjob: any;
  selectedstatus: any;
  selectedqual: any;
  selecteduser: any;
  filestringContact: string;
  currentPage: any = 1;
  firstname: any;
  lastname: any;
  id;
  job: any;
  source;
  primary;
  Statuss: any;
  highq: any;
  sourcedata: any;
  primarydata: any;
  jobdetails: any;
  canditstatus: any;
  highquali: any;
  userId: any;
  Owner;
  ownerlistt: any;
  degreelist: any;
  candidatedata;
  candidatelist: any;
  addhighqualification;
  sourcedetails;
  jobDetailss;
  statusmaster;
  educationaldetail;
  skill;
  experience;
  ski;
  qual;
  stat;
  jobd;
  sorce;
  usserr;
  usr;
  getnoteDeletedata;
  status = true;
  postdata;
  getnotedata;
  getdetails;
  noteId;
  alternateMobile: any;
  website: any;
  fax: any;
  altenateEmail: any;
  street1: any;
  city: any;
  pincode: any;
  state: any;
  country: any;
  totalExperience: any;
  skillset: any;
  currentCTC: any;
  additionalInfo: any;
  skypeID: any;
  twitterId: any;
  department: any;
  degreeee: any;
  durationFormDate: any;
  occupation: any;
  companyname: any;
  summary: any;
  fileExtension: any;
  fileName: any;
  modifiedOwner: any;
  durationToDate: any;
  school: any;
  isShow: boolean;
  topPosToStartShowing = 100;

  registerForm = this.formBuilder.group({
    firstName: [''],
    lastName: [''],
    email: [''],
    mobile: [''],
    alternateMobile: [''],
    fax: [''],
    website: [''],
    altenateEmail: [''],
    street1: [''],
    city: [''],
    state: [''],
    pincode: [''],
    country: [''],
    totalExperience: [''],
    addQualificationHeld: [''],
    candidateJobTitle: [''],
    currentCompanyName: [''],
    expectedCTC: [''],
    currentCTC: [''],
    noticePeriod: [''],
    primarySkills: [''],
    skillset: [''],
    additionalInfo: [''],
    skypeID: [''],
    twitterId: [''],
    candidateStatusMaster: [''],
    source: [''],
    user: [''],
    nameOfSchool: [''],
    department: [''],
    degree: [''],
    durationFormDate: [''],
    durationToDate: [''],
    occupation: [''],
    companyname: [''],
    summary: [''],
    browse: ['']
  }
  )

  noteForm = this.formBuilder.group({
    note: ['', Validators.required],
    noteSelect: ['', Validators.required]
  })

  constructor(private formBuilder: FormBuilder, private http: HttpClient,
    private activeRoute: ActivatedRoute, private ser: ServiceService, private router: Router, private toaster: ToastrManager) { }

  ngOnInit() {
    this.RegId = sessionStorage.getItem('registrationId'),
    this.candidateid = this.activeRoute.snapshot.paramMap.get("id");
    this.getuser();
    this.getCandidateById();
    this.candidatenoteData();
    this. getCandidateById()
  }

  get f() {
    return this.registerForm.controls;
  }

  getCandidateById() {
    var id = {
      id: this.candidateid,
    }
      this.ser.getCandidateById(this.candidateid).subscribe(resp => {
      this.candidatedata = resp;
      this.candidatelist = this.candidatedata.result;
      console.log(this.candidatelist,"candidate list by id");
      
      this.addhighqualification = this.candidatelist.addQualificationHeld;
      this.jobDetailss = this.candidatelist.candidateJobTitle;
      this.sourcedetails = this.candidatelist.source;
      this.statusmaster = this.candidatelist.candidateStatusMaster;
      this.educationaldetail = this.candidatelist.educationalDetails;
      this.experience = this.candidatelist.experiencedetails;
      this.skill = this.candidatelist.primarySkills;
      this.usserr = this.candidatelist.user;
      this.firstname = this.candidatelist.firstName;
      this.lastname = this.candidatelist.lastName;

      for (let i = 0; i < this.skill.length; i++) { //primaryskills
        this.ski = this.skill[i].skillName
      }

      for (let i = 0; i < this.educationaldetail.length; i++) { //primaryskills
        this.deg = this.educationaldetail[i]
      }
    
      this.alternateMobile = this.candidatelist.alternateMobile;
      this.fax = this.candidatelist.fax;
      this.website = this.candidatelist.website;
      this.altenateEmail = this.candidatelist.altenateEmail;
      this.street1 = this.candidatelist.street1;
      this.city = this.candidatelist.city;
      this.state = this.candidatelist.state;
      this.pincode = this.candidatelist.pincode;
      this.country = this.candidatelist.country;
      this.totalExperience = this.candidatelist.totalExperience;
      this.currentCTC = this.candidatelist.currentCTC;
      this.skypeID = this.candidatelist.skypeID;
      this.twitterId = this.candidatelist.twitterId;
      this.department
      this.degreeee = this.deg.degree.qualificationName;
      this.durationFormDate = this.educationaldetail.durationFormDate;
      this.durationToDate = this.educationaldetail.durationToDate;
      this.occupation = this.candidatelist.occupation;
      this.companyname = this.candidatelist.companyname;
      this.summary = this.candidatelist.summary;
      this.school = this.educationaldetail.nameOfSchool;
      this.skillset = this.candidatelist.skillset;
      this.additionalInfo = this.candidatelist.additionalInfo;


      this.registerForm.controls.firstName.setValue(this.candidatelist.firstName);
      this.registerForm.controls.lastName.setValue(this.candidatelist.lastName);
      this.registerForm.controls.email.setValue(this.candidatelist.email);
      this.registerForm.controls.mobile.setValue(this.candidatelist.mobile);
      this.registerForm.controls.alternateMobile.setValue(this.candidatelist.alternateMobile);
      this.registerForm.controls.fax.setValue(this.candidatelist.fax);
      this.registerForm.controls.website.setValue(this.candidatelist.website);
      this.registerForm.controls.street1.setValue(this.candidatelist.street1);
      this.registerForm.controls.altenateEmail.setValue(this.candidatelist.altenateEmail);
      this.registerForm.controls.city.setValue(this.candidatelist.city);
      this.registerForm.controls.state.setValue(this.candidatelist.state);
      this.registerForm.controls.country.setValue(this.candidatelist.country);
      this.registerForm.controls.pincode.setValue(this.candidatelist.pincode);
      this.registerForm.controls.addQualificationHeld.setValue(this.addhighqualification.qualificationName); //api
      this.registerForm.controls.source.setValue(this.sourcedetails.sourceName); //api
      this.registerForm.controls.user.setValue(this.usserr.name); //api
      this.registerForm.controls.candidateJobTitle.setValue(this.jobDetailss.currentJobTittle); //api
      this.registerForm.controls.candidateStatusMaster.setValue(this.statusmaster.status); //api
      this.registerForm.controls.primarySkills.setValue(this.ski);  //api
      this.registerForm.controls.totalExperience.setValue(this.candidatelist.totalExperience);
      this.registerForm.controls.currentCTC.setValue(this.candidatelist.currentCTC);
      this.registerForm.controls.expectedCTC.setValue(this.candidatelist.expectedCTC);
      this.registerForm.controls.currentCompanyName.setValue(this.candidatelist.currentCompanyName);
      this.registerForm.controls.skypeID.setValue(this.candidatelist.skypeID);
      this.registerForm.controls.twitterId.setValue(this.candidatelist.twitterId);
      this.registerForm.controls.noticePeriod.setValue(this.candidatelist.noticePeriod);
      this.registerForm.controls.nameOfSchool.setValue(this.educationaldetail.nameOfSchool);
      this.registerForm.controls.durationFormDate.setValue(this.educationaldetail.durationFormDate);
      this.registerForm.controls.degree.setValue(this.deg.degree.qualificationName);
      this.registerForm.controls.durationToDate.setValue(this.educationaldetail.durationToDate);
      this.registerForm.controls.companyname.setValue(this.candidatelist.companyname);
      this.registerForm.controls.occupation.setValue(this.candidatelist.occupation);
      this.registerForm.controls.summary.setValue(this.candidatelist.summary);
      this.registerForm.controls.skillset.setValue(this.candidatelist.skillset);
      this.registerForm.controls.durationToDate.setValue(this.experience.durationToDate)
    })
  }
  
  getuser() {
    this.ser.getuser().subscribe(resp => {
      this.Owner = resp;
      this.ownerlistt = this.Owner.result;
      console.log(this.ownerlistt, 'ownerlist');
    })
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

  @HostListener('window:scroll')
  checkScroll() {
    const scrollPosition = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
    console.log('[scroll]', scrollPosition);
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

  submitclient() {
    this.router.navigate(['superadmin/candidate/subclient', this.candidateid]);
  }
  emalnav() {
    this.router.navigate(['superadmin/candidate/email', this.candidateid])
  }

  interview() {
    this.router.navigate(['superadmin/interview/addinterview'], { queryParams: { val: this.candidateid } });
  }

  notesave() {
    if (this.noteForm.invalid) {
      this.toaster.warningToastr('please enter note description');
    }
    else {
      let notesave = {
        registrationId: this.RegId,
        candidateId: this.candidateid,
        noteData: this.noteForm.value.noteSelect,
        noteType: this.noteForm.value.note
      }
      this.ser.candidatenotesave(notesave).subscribe(res => {
        this.postdata = res
        if (this.postdata.status == "StatusSuccess") {
          this.toaster.successToastr('note added successfully');
          this.candidatenoteData();
        }
        console.log(res, "save note");
      })
    }
  }

  candidatenoteData() {
    let getnotedata = {
      registrationId: this.RegId,
      pageSize: 20,
      pageNum: 1
    }
    this.ser.candidatenoteData(getnotedata).subscribe(res => {
      this.getnotedata = res;
      this.getdetails = this.getnotedata.result;
      console.log(this.getdetails, "getnotedetails");
    })
  }

  //delete
  candidatedeletedata(id) {
    this.ser.candidatedeletedata(id).subscribe(res => {
      this.getnoteDeletedata = res;
      if (this.getnoteDeletedata.status == "StatusSuccess") {
        this.toaster.successToastr('Candidate note deleted successfully');
        this.candidatenoteData();
      }
      else {
        this.toaster.warningToastr('error deleting the note', 'Warning!');
      }
      console.log(this.getnoteDeletedata, "note delete details");
    })
  }

}
