import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrManager } from 'ng6-toastr-notifications';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';

@Component({
  selector: 'app-editcandidate',
  templateUrl: './editcandidate.component.html',
  styleUrls: ['./editcandidate.component.css']
})
export class EditcandidateComponent implements OnInit {
  candidateid;
  selecteddegree: any;
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
  Owne: any;
  sourc: any;
  primar: any;
  jo: any;
  Statu: any;
  hiQua: any;
  degreeQua: any;
  userMail: string;
  nameOfSchool: any;

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
    resume: [''],
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

  constructor(private formBuilder: FormBuilder, private activeRoute: ActivatedRoute,
    private ser: SuperadminserviceService, private router: Router, private toaster: ToastrManager) { }

  ngOnInit() {
    this.RegId = sessionStorage.getItem('registrationId'),
      this.candidateid = this.activeRoute.snapshot.paramMap.get("id");
    this.userMail = sessionStorage.getItem('userMail');
    this.userId = sessionStorage.getItem('userId');
    this.getSource();
    this.jobtitle();
    this.getSkills()
    this.candidatestatus();
    this.getQualification()
    this.getCandidateById();



  }

  get f() {
    return this.registerForm.controls;
  }
  educa;
  getCandidateById() {
    var id = {
      id: this.candidateid,
    }
    debugger;
    this.ser.getCandidateById(this.candidateid).subscribe(resp => {
      this.candidatedata = resp;
      this.candidatelist = this.candidatedata.result;
      console.log(this.candidatelist, "candidate get by id");
      this.addhighqualification = this.candidatelist.addQualificationHeld;
      console.log(this.addhighqualification, "highest qualification in candidate get by id")
      this.jobDetailss = this.candidatelist.candidateJobTitle;
      this.sourcedetails = this.candidatelist.source;
      this.statusmaster = this.candidatelist.candidateStatusMaster;
      this.educationaldetail = this.candidatelist.educationalDetails;
      this.experience = this.candidatelist.experiencedetails;
      this.skill = this.candidatelist.primarySkills[0].skillName;
      console.log(this.skill,"rrrrrrrrrrrr")
      console.log(this.skill),"gggg"
      this.educa=this.candidatelist.educationAndTraining;
      

      for (let i = 0; i < this.skill.length; i++) { //primaryskills
        this.ski = this.skill[i].skillName
      }

    



      for (let i = 0; i < this.educationaldetail.length; i++) { //primaryskills
        this.deg = this.educationaldetail[i];
        this.nameOfSchool = this.educationaldetail[i].nameOfSchool;
      }

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
      this.registerForm.controls.candidateJobTitle.setValue(this.jobDetailss.currentJobTittle); //api
      this.registerForm.controls.candidateStatusMaster.setValue(this.statusmaster.status); //api
      this.registerForm.controls.primarySkills.setValue(this.skill);  //api
      this.registerForm.controls.totalExperience.setValue(this.candidatelist.totalExperience);
      this.registerForm.controls.currentCTC.setValue(this.candidatelist.currentCTC);
      this.registerForm.controls.expectedCTC.setValue(this.candidatelist.expectedCTC);
      this.registerForm.controls.currentCompanyName.setValue(this.candidatelist.currentCompanyName);
      this.registerForm.controls.skypeID.setValue(this.candidatelist.skypeID);
      this.registerForm.controls.twitterId.setValue(this.candidatelist.twitterId);
      this.registerForm.controls.noticePeriod.setValue(this.candidatelist.noticePeriod);
      this.registerForm.controls.nameOfSchool.setValue(this.nameOfSchool);
      this.registerForm.controls.durationFormDate.setValue(this.educationaldetail.durationFormDate);
      this.registerForm.controls.degree.setValue(this.deg.degree.qualificationName);
      this.registerForm.controls.durationToDate.setValue(this.educationaldetail.durationToDate);
      this.registerForm.controls.companyname.setValue(this.candidatelist.companyname);
      this.registerForm.controls.occupation.setValue(this.candidatelist.occupation);
      this.registerForm.controls.summary.setValue(this.candidatelist.summary);
      this.registerForm.controls.durationFormDate.setValue(this.experience.durationFormDate);
      this.registerForm.controls.durationToDate.setValue(this.experience.durationToDate);
      this.registerForm.controls.educationAndTraining.setValue(this.educa);
    })
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


  save() {
    if (this.registerForm.invalid) {
      console.log(this.registerForm.value, "candidate edit form data");
      this.toaster.errorToastr('Please fill the mandatory fields', 'Notification');
    }
    else {
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
        id: this.candidateid,
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
        user: {
          id: this.userId
        },
        expectedCTC: this.registerForm.value.expectedCTC,
        currentCTC: this.registerForm.value.currentCTC,
        noticePeriod: this.registerForm.value.noticePeriod,//api
        skillset: this.registerForm.value.skillset,
        additionalInfo: this.registerForm.value.additionalInfo,
        skypeID: this.registerForm.value.skypeID,
        twitterId: this.registerForm.value.twitterId,
        candidateStatusMaster: this.Statu,
        source: this.sourc,
        registrationId: this.RegId,
        resume: this.filestringContact,
        // fileExtension: this.files[0].name,
        // fileName: this.files[0].name,
        // educationalDetails: [{
        //   nameOfSchool: this.registerForm.value.nameOfSchool,
        //   degree: this.degreeQua,
        //   durationFormDate: this.registerForm.value.durationFormDate,
        //   durationToDate: this.registerForm.value.durationToDate,
        // }],
        // experiencedetails: [{
        //   companyname: this.registerForm.value.companyname,
        //   occupation: this.registerForm.value.occupation,
        //   summary: this.registerForm.value.summary,
        //   durationFormDate: this.registerForm.value.durationFormDate,
        //   durationToDate: this.registerForm.value.durationToDate,
        // }],
        primarySkills: this.primarydata,
        modifiedOwner:{
          id: this.userId
        }
      };
console.log(data,"ffff")
      this.ser.addcanpost(data).subscribe(resp => {
        console.log('edit candidate response from API', resp);
        this.candidatedata = resp;
        if (this.candidatedata['status'] === 'StatusSuccess') {
          this.toaster.successToastr('candidate edited successfully');
          this.router.navigate(['/superadmin/candidate']);
        }
      });
    }
  }

}