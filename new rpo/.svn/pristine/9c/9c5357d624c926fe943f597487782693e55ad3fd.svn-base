import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrManager } from 'ng6-toastr-notifications';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';

import { ServiceService } from 'src/app/services/service.service';
import { NgxSpinnerService } from 'ngx-spinner';


@Component({
  selector: 'app-addcandidate',
  templateUrl: './addcandidate.component.html',
  styleUrls: ['./addcandidate.component.css']
})

export class AddcandidateComponent implements OnInit {
  RegId: string;

  selectedSource: any;
  files: any;
  filestringContact: string;
  selectedskilss: any;
  selectedjob: any;
  selectedstatus: any;
  selectedqual: any;
  candidatedata: Object;
  selecteduser: any;
  selectedClientOwnerId: any;
  Domain: string;
  selecteddegree: any;
  selectedPostingTitle: any;
  selectedjobopenings: any;
  profileData;
  profileDetails: any;
  userToken;
  totalItems: any;
  currentPage: any = 1;
  id: any;
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
  degrer: any;
  hiQua: any;
  degreeQua: any;
  sourc: any;
  Statu: any;
  Owne: any;
  primar: any;
  jo: any;
  profile;
  totaljob: any;
  submitted: boolean;
  fileTypeWithName: any;
  userMail: string;
  filelist;

  registerForm = this.formBuilder.group({
    firstName: ['', [Validators.required, Validators.pattern("[A-Za-z]+")]],
    lastName: ['', [Validators.required, , Validators.pattern("[A-Za-z]+")]],
    email: ['', [Validators.required, Validators.email]],
    mobile: ['', [Validators.required, Validators.pattern("^(\([0-9]{3}\) |[6-9]{1})[0-9]{9}$")]],
    addQualificationHeld: ['', Validators.required],
    candidateJobTitle: ['', Validators.required],
    currentCompanyName: ['', Validators.required],
    expectedCTC: ['', Validators.required],
    noticePeriod: ['', Validators.required],
    primarySkills: ['', Validators.required],
    candidateStatusMaster: ['', Validators.required],
    source: ['', Validators.required],
    user: ['', Validators.required],
    nameOfSchool: [''],
    resume: ['', Validators.required],
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
    currentCTC: [''],
    skillset: [''],
    workExp: [''],
    skypeID: [''],
    twitterId: [''],
    department: [''],
    degree: [''],
    durationFormDate: [''],
    durationToDate: [''],
    occupation: [''],
    companyname: [''],
    summary: [''],
    fileExtension: [''],
    fileName: [''],
    modifiedOwner:[''],
    awards:[''],
    panCard:[''],
    aadharCard:[''],
    educationAndTraining:['']
    
  }
  )



  resumeForm = this.formBuilder.group({



    resumeparse:['',Validators.required]



  })



  filesss: any;
  skillName: any;
  skills: any;
  qualifications: any;
  qualificationName: any;
  skill: any;
  candd: any;
  // constructor(private formBuilder: FormBuilder, private ser: SuperadminserviceService, private router: Router, private toaster: ToastrManager, private service: ServiceService, private SpinnerService: NgxSpinnerService) {
   
  //  }


  // constructor(private formBuilder: FormBuilder, private ser: SuperadminserviceService, private router: Router, private toaster: ToastrManager, private service: ServiceService, private SpinnerService: NgxSpinnerService) { }


  constructor(private formBuilder: FormBuilder, private ser: SuperadminserviceService,
     private router: Router, private toaster: ToastrManager, private service: ServiceService, private SpinnerService: NgxSpinnerService) { }


  ngOnInit() {
    
    this.RegId = sessionStorage.getItem('registrationId');
    this.Domain = sessionStorage.getItem('Domain');
    this.userToken = sessionStorage.getItem('Token');
    this.userMail = sessionStorage.getItem('userMail');
    this.userId = sessionStorage.getItem('userId');
    this.candd="New"
    this.getSource();
    this.jobtitle();
    this.getSkills()
    this.candidatestatus();
    this.getQualification()
    this.getAllJobOpenings();
  }

  // myFunc(){
  //   $("#login-modal").modal('show');
  //  }

  jobtitle() {
    this.ser.getjobtitle().subscribe(resp => {
      console.log(resp, "jobdetails");
      this.job = resp;
      this.jobdetails = this.job.result;
    })
  };

  getSource() {
    this.ser.getSource().subscribe(res => {
      this.source = res,
        this.sourcedata = this.source.result,
        console.log(this.sourcedata);
    });
  }

 

 
  getSkills() {
    this.ser.skills().subscribe(resp => {
      console.log(resp, "skills data response---------");
      this.primary = resp;
      this.primarydata = this.primary.result;
      console.log(this.primarydata, "get skills data---------")
    })
  }


  

  // getQualification() {
  //   this.addclientservice.Qualification().subscribe(resp => {
  //     console.log(resp, "qualification data response---------");
  //     this.qualification = resp;
  //     this.qualificationDetails = this.qualification.result;
  //     console.log(this.skillDetails, "get qualification data---------")
  //   })
  // }

  candidatestatus() {
    this.ser.candidatestatus().subscribe(res => {
      console.log(res, "candidate status");
      this.Statuss = res;
      this.canditstatus = this.Statuss.result;
    })
  }

  Qualification() {
    this.ser.Qualification().subscribe(res => {
      console.log(res, "qualification getAll");
      this.highq = res;
      this.highquali = this.highq.result;
      this.degreelist = this.highq.result;
    })
  }




  getQualification() {
    this.ser.Qualification().subscribe(resp => {
      console.log(resp, "qualification data response---------");
      this.highq = resp;
      this.highquali = this.highq.result;
      this.degreelist = this.highq.result;
      console.log(this.highquali, "get qualification data---------")
    })
  }

  getAllJobOpenings() {
    var profileData = {
      pageNum: this.currentPage,
      pageSize: 4,
      registrationId: this.RegId
    }
    this.ser.getAllJobOpenings(profileData).subscribe(resp => {
      this.profile = resp;
      this.totaljob = this.profile.result;
      console.log(this.profile, "job get all");
    })
  }

  save() {
    if(this.registerForm.invalid)
    {
      console.log(this.registerForm.value, "candidate add form data");
      this.toaster.errorToastr('Please fill the mandatory fields', 'Notification');
    }
    else
    {
    var sou = this.selectedSource;

    for (var i = 0; i < this.sourcedata.length; i++) {
      if (this.sourcedata[i].sourceName === sou) {
        this.sourc = this.sourcedata[i];
        break;
      }
    }

    var prim = this.selectedskilss;
    for (var i = 0; i < this.primarydata.length; i++) {
      if (this.primarydata[i].skillName === prim) {
        this.primar = this.primarydata[i];
        break;
      }
    }


    // for (let i = 0; i < this.qualifications.length; i++) {
    //   this.qualificationName = this.qualifications[i].qualificationName;
    // }
    // for (let i = 0; i < this.skills.length; i++) {
    //   this.skillName = this.skills[i].skillName;
    // }

    var jobs = this.selectedjob;
    for (var i = 0; i < this.jobdetails.length; i++) {
      if (this.jobdetails[i].currentJobTittle === jobs) {
        this.jo = this.jobdetails[i];
        console.log(this.jo,"hhhhhhhhhhhhhhhh");
        
        break;
      }
    }

    var stat = this.selectedstatus;
    for (var i = 0; i < this.canditstatus.length; i++) {
      if (this.canditstatus[i].status === stat) {
        this.Statu = this.canditstatus[i];
        break;
      }
    }

    var hq = this.selectedqual;
    for (var i = 0; i < this.highquali.length; i++) {
      if (this.highquali[i].qualificationName === hq) {
        this.hiQua = this.highquali[i];
        break;
      }
    }

   

    let data = {
      firstName: this.registerForm.value.firstName,
      lastName: this.registerForm.value.lastName,
      email: this.registerForm.value.email[0],
      mobile: this.registerForm.value.mobile,
      alternateMobile: this.registerForm.value.alternateMobile,
      fax: this.registerForm.value.fax,
      website: this.registerForm.value.website,
      altenateEmail: this.registerForm.value.altenateEmail,
      street1: this.registerForm.value.street1,
      city: this.registerForm.value.city,
      state: this.registerForm.value.state,
      pincode: this.registerForm.value.pincode,
      country: this.registerForm.value.country,
      totalExperience: this.registerForm.value.totalExperience,
      addQualificationHeld: this.hiQua,
      //api
      candidateJobTitle: this.jo,
      //api
      currentCompanyName: this.registerForm.value.currentCompanyName,
      user:
      {
        id: this.userId,
      },
      expectedCTC: this.registerForm.value.expectedCTC,
      currentCTC: this.registerForm.value.currentCTC,
      noticePeriod: this.registerForm.value.noticePeriod,//api
      skillset: this.registerForm.value.skillset,
      workExp: this.registerForm.value.workExp,
      skypeID: this.registerForm.value.skypeID,
      twitterId: this.registerForm.value.twitterId,
      candidateStatusMaster: this.Statu,
      summary:this.registerForm.value.summary,
      aadharCard:this.registerForm.value.aadharCard,
      source: this.sourc,
      registrationId: this.RegId,
      resume: this.filestringContact,
      fileExtension: this.files[0].name,
      fileName: this.files[0].name,
      educationAndTraining:this.registerForm.value.educationAndTraining,
    
      awards:this.registerForm.value.awards,
      primarySkills: [this.primar],
      modifiedOwner:{
        id: this.userId
      }
    };

    this.ser.addcanpost(data).subscribe(resp => {
      console.log('add candidate response from API', resp);
      this.candidatedata = resp;
      if (this.candidatedata['status'] === 'StatusSuccess') {
        this.toaster.successToastr('Candidate added successfully');
        this.router.navigate(['/superadmin/candidate']);
      }
    });
  }
  }




  
  profileGetData() {
    var profileData = {
      pageNum: this.currentPage,
      pageSize: 4,
      registrationId: this.RegId
    }

    this.ser.getAllJobOpenings(profileData).subscribe(resp => {
      this.profileData = resp;
      this.profileDetails = this.profileData.result;
      console.log(this.profileDetails, " Profile getAll---------")
    })
  }



  getContactFile(contact) {
    this.files = contact.target.files;
    console.log(this.files, "files========");
    var reader = new FileReader();
    reader.onload = this.handleReaderLoad.bind(this);
    reader.readAsBinaryString(this.files[0]);
  }

  handleReaderLoad(readerEvt) {
    var binaryString = readerEvt.target.result;
    this.filestringContact = btoa(binaryString);
  console.log(this.filestringContact, "file string");
   }


  onFileSelect(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.resumeForm.get('resumeparse').setValue(file);
    }
  }

  resumeeeee;
  
  resumep:File;
  jl;

  format;
  fileToUpload;
  file:any;
  fffirstname;
  emaill;
  mobill;
  skilllset;
  summmary;
  additionn;
  aawards;
  edudet;
  applied;
  workExp;
  workExpe;
  train;
  resumeparsing(){
    this.SpinnerService.show();  
const formData = new FormData();
formData.append('file', this.resumeForm.get('resumeparse').value);



 console.log(this.filesss,"ddddddddddddddddddddddd")


  this.ser.resumeparsing(formData).subscribe(resp=>{

this.resumeeeee=resp;
this.format=this.resumeeeee.result;
this.SpinnerService.hide();
  
 
console.log("ddddd",this.format)
// console.log(this.fffirstname,"nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn")
this.fffirstname=this.format.data.basics.name.surname;
this.emaill=this.format.data.basics.email;
this.mobill=this.format.data.mobile;
this.summmary=this.format.data.summary;
this.workExpe=this.format.data.work_experience;
this.aawards=this.format.data.awards;
this.train=this.format.data.education_and_training;

// this.applied=this.format.data.work_experience[3].jobtitle;
console.log(this.applied,"hhhhhhhhhhhhhhh")
this.registerForm.controls.lastName.setValue(this.fffirstname);
// this.registerForm.controls.lastName.setValue(this.candidatelist.lastName);
this.registerForm.controls.email.setValue(this.emaill);
this.registerForm.controls.mobile.setValue(this.mobill);



 let skillsobj='';


 this.skilllset=this.format.data.skills

 this.skilllset.forEach(ele=>{
  let keys= Object.keys(ele);
    keys.forEach(el=>{
      skillsobj +=ele[el]
    })

 })
 this.registerForm.controls.skillset.setValue(skillsobj);




 let summaryobj='';

 this.summmary=this.format.data.summary



 this.summmary.forEach(ele=>{

  let keys= Object.keys(ele);
  keys.forEach(el=>{

    summaryobj +=ele[el]
    
  })
console.log(summaryobj);
  
 })

 this.registerForm.controls.summary.setValue(summaryobj);
//  if (myArray && myArray.length > 0) {
//   console.log('myArray is not empty.');
// }else{
//   console.log('myArray is empty.');
// }

 



 let workexpobj='';
 this.workExpe=this.format.data.work_experience


 this.workExpe.forEach(ele=>{
   let keys=Object.keys(ele);
   keys.forEach(el=>{
    workexpobj +=ele[el]
   
   })
   
  ;  
   console.log(workexpobj)
   
 })

 this.registerForm.controls.workExp.setValue(workexpobj);



 let eduobj='';
 this.train=this.format.data.education_and_training


 this.train.forEach(ele=>{
   let keys=Object.keys(ele);
   keys.forEach(el=>{
    eduobj +=ele[el]
   })
   console.log(eduobj)
 })
 this.registerForm.controls.educationAndTraining.setValue(eduobj);

 let awwwobj='';
 this.aawards=this.format.data.awards
 
 
 this.aawards.forEach(ele=>{
   let keys=Object.keys(ele);
   keys.forEach(el=>{
     awwwobj +=ele[el]
   })
   console.log(awwwobj)
 })
 this.registerForm.controls.awards.setValue(awwwobj);









 
 














  })



   }










  get f() {
    return this.registerForm.controls;
  }






 

   



  

}