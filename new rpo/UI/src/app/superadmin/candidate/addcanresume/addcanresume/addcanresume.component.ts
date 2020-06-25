import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrManager } from 'ng6-toastr-notifications';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';

import { ServiceService } from 'src/app/services/service.service';
import { NgxSpinnerService } from 'ngx-spinner';
@Component({
  selector: 'app-addcanresume',
  templateUrl: './addcanresume.component.html',
  styleUrls: ['./addcanresume.component.css']
})
export class AddcanresumeComponent implements OnInit {

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
  status1: string = "Experienced";


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
    nameOfSchool: ['', Validators.required],
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
    workExperience: [''],
    skypeID: [''],
    twitterId: [''],
    department: [''],
    degree: [],
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
  candd:any;
  Statuu:any;
  constructor(private formBuilder: FormBuilder, private ser: SuperadminserviceService, private router: Router, private toaster: ToastrManager, private service: ServiceService) { }

  ngOnInit() {
  Statuu:"New";
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
    console.log(this.status1,"99999999999");
  }

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

    var dq = this.selecteddegree;
    for (var i = 0; i < this.degreelist.length; i++) {
      if (this.degreelist[i].qualificationName === dq) {
        this.degreeQua = this.degreelist[i];
        break;
      }
    }



    let data = {
      firstName: this.registerForm.value.firstName,
      lastName: this.registerForm.value.lastName,
      email: this.registerForm.value.email,
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
      // workExperience: this.registerForm.value.additionalInfo,
      skypeID: this.registerForm.value.skypeID,
      twitterId: this.registerForm.value.twitterId,
      candidateStatusMaster: this.Statuu,
      source: this.sourc,
      registrationId: this.RegId,
      resume: this.filestringContact,
      fileExtension: this.files[0].name,
      fileName: this.files[0].name,
      // educationdet:this.registerForm.value.educationdet,
      educationalDetails: [{
        nameOfSchool: this.registerForm.value.nameOfSchool,
        degree: this.degreeQua,
        durationFormDate: this.registerForm.value.durationFormDate,
        durationToDate: this.registerForm.value.durationToDate,
      }],
      experiencedetails: [{
        companyname: this.registerForm.value.companyname,
        occupation: this.registerForm.value.occupation,
        // summary: this.registerForm.value.summary,
        durationFormDate: this.registerForm.value.durationFormDate,
        durationToDate: this.registerForm.value.durationToDate,
       
      }],

      // awards:this.registerForm.value.awards,
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
  resumeparsing(){
  
const formData = new FormData();
formData.append('file', this.resumeForm.get('resumeparse').value);



 console.log(this.filesss,"ddddddddddddddddddddddd")


  this.ser.resumeparsing(formData).subscribe(resp=>{

this.resumeeeee=resp;
this.format=this.resumeeeee.result;

  
 
console.log("ddddd",this.format)
// console.log(this.fffirstname,"nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn")
this.fffirstname=this.format.data.basics.name.surname;
this.emaill=this.format.data.basics.email;
this.mobill=this.format.data.mobile;
this.summmary=this.format.data.summary;
this.additionn=this.format.data.work_experience;
this.aawards=this.format.data.awards;
this.edudet=this.format.data.education_and_training;
this.applied=this.format.data.work_experience[3].jobtitle;
console.log(this.applied,"hhhhhhhhhhhhhhh")
this.registerForm.controls.lastName.setValue(this.fffirstname);
// this.registerForm.controls.lastName.setValue(this.candidatelist.lastName);
this.registerForm.controls.email.setValue(this.emaill);
this.registerForm.controls.mobile.setValue(this.mobill);

this.registerForm.controls.candidateJobTitle.setValue(this.applied);

 let skillsobj='';


 this.skilllset=this.format.data.skills

 this.skilllset.forEach(ele=>{
  let keys= Object.keys(ele);
    keys.forEach(el=>{
      skillsobj +=ele[el]
    })

 })


 let summaryobj='';

 this.summmary=this.format.data.summary



 this.summmary.forEach(ele=>{

  let keys= Object.keys(ele);
  keys.forEach(el=>{

    summaryobj +=ele[el]
    
  })

  
 })



let adddobj='';
this.additionn=this.format.data.work_experience

this.additionn.forEach(ele=>{


  let keys = Object.keys(ele);

  keys.forEach(el=>{

    adddobj +=ele[el]


  })

})


let awwwobj='';
this.aawards=this.format.data.awards
this.aawards.forEach(ele=>{

  let keys = Object.keys(ele);
  keys.forEach(el=>{
    awwwobj +=ele[el]
  })
  
})




let eduobj='';
this.edudet=this.format.data.education_and_training
this.edudet.forEach(ele=>{

let keys = Object.keys(ele);
keys.forEach(ele=>{
  eduobj +=ele[ele]
})

console.log(eduobj);

})

this.registerForm.controls.skillset.setValue(skillsobj);
this.registerForm.controls.workExperience.setValue(adddobj);
this.registerForm.controls.additionalInfo.setValue(summaryobj);
this.registerForm.controls.awards.setValue(awwwobj);
this.registerForm.controls.educationdet.setValue(eduobj);


console.log(this.skilllset,"languages")










  })



   }










  get f() {
    return this.registerForm.controls;
  }






 

   



  

}
