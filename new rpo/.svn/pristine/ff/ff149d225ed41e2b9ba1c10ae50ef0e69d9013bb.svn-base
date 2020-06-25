import { Component, OnInit } from '@angular/core';
import { SuperadminserviceService } from '../../superadminservice.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

import { ToastrManager } from 'ng6-toastr-notifications';
import { ExcelService } from 'src/app/excel.service';



declare var $;
@Component({
  selector: 'app-candidate',
  templateUrl: './candidate.component.html',
  styleUrls: ['./candidate.component.css']
})
export class CandidateComponent implements OnInit {
  setPageClient: any;
  first: any;
  selectedclient: any;
  currentPageClient: any;
  totalClientItems: any;
  exprencee: any;
  selectedRadio: any;
  tlexpp: any;
  SKILLSrq: any;
  commetform: any;
  cmtsave: any;
  SKILLS: any;
  exprence: any;
  tlexp: any;
  view: boolean = false;
  view1: boolean = false;
  view2: boolean = false;
  view3: boolean = false;
  viewcheck: boolean = false;
  currentCompanyName: any;
  skills: any;
  currentRate: any;
  RegId: string;
  candlist;
  candgetall: any;
  candidateid: any;
  candidatedata: any;
  candidatelist: any;
  addhighqualification: any;
  jobDetailss: any;
  sourcedetails: any;
  statusmaster: any;
  educationaldetail: any;
  Skill: any;
  experience: any;
  usserr: any;
  ski: any;
  deg: any;
  getCandidateid: any;
  firstName: any;
  lastName: any;
  email: any;
  mobile: any;
  alternateMobile: any;
  fullName: any;
  totalExperience: any;
  currentCTC: any;
  expectedCTC: any;
  fax: any;
  website: any;
  street1: any;
  altenateEmail: any;
  city: any;
  state: any;
  country: any;
  pincode: any;
  skillName: any;
  qualificationName: any;
  nameOfSchool: any;
  skypeID: any;
  twitterId: any;
  resume;
  candid: any;
  eml: any;
  docPath: string;
  lahari: string;
  candidateResume: any;
  totalItems;
  pageSize = 10;
  currentPage = 1;
  profileData;
  profileDetails;
  bdmqId: any;
  selectedcandidateId: any;
  userId: string;
  mappdata: Object;
  totalContactItems;
  currentPageContact = 1;
  status: boolean = true;
  btnname: any;
  skill: any;
  s: any;
  profile;
  bd: any;
  expec: any;
  mappingId: any;
  ratingresp;
  ratingres;
  ratid
  totalItemsjob: any;
  excell: any;
  candidateeedetails;
  candexcel: any;
  idddd;
  bdmid;
  ssss;
  ratingresult;
  ratiddd;
  reg;
  startiddd
  excelll;
  candistatusmaster;
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
  candidateIDD;
  searchTerm: FormControl = new FormControl();
  candidatepage = 1;
  pageNum: any;
  registrationId: any;
  searchvalue: any;
  data: [];
  obj: any;
  searchInput: any;

  searchCandidateForm = this.formBuilder.group({
    email: [''],
    mobile: [''],
    highestQualification: [''],
    candidateId:['']
  });

  ratingForm = this.formBuilder.group({
    Comments: ['', Validators.required],
    nameOfRequirement: ['', Validators.required],
    rating: ['', Validators.required]
  });

  rating: any;
  ratingDetails: any;
  ratingData;
  bdmReqId: any;
  bdmReqName: any;
  candidateID: any;
  fileextension:any;
  search_values(event) {
    this.searchInput = event;
  }

  constructor(private ser: SuperadminserviceService, private toaster: ToastrManager,
    private rout: Router, private formBuilder: FormBuilder, private http: HttpClient, private router: ActivatedRoute, private serv: ExcelService
  ) { }

  setPage(pagNo: number): void {
    this.currentPage = pagNo;
    this.getallcandidateslist();
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
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          registrationId: this.RegId,
          flag: true,
          candidateName: term,
        };
        if (term != '') {
          this.ser.searchcandidate(post1).subscribe(
            resp => {
              this.candlist = resp;
               console.log("excellllllllllll", this.excell)
              this.candgetall = this.candlist.result;
              this.candidateIDD=this.candgetall.id;
              console.log(this.candidateIDD,"this.candidateIDD");
              
              this.totalItems = this.candlist.totalRecords;
              if (this.candlist.status === "Accepted") {
                this.search_result = true;
                console.log("status", this.candlist.status);
                this.data = this.candlist.result;
                this.showNodata = false;
              } else if (this.candgetall.status === "DataIsEmpty") {
                this.showNodata = true;
                if (this.showNodata == true) {
                  this.search_result = false;
                  this.search_Previous_result = true;
                }
              }
            });
        }
      });
    this.RegId = sessionStorage.getItem('registrationId');
    this.userId = sessionStorage.getItem('userId');
    this.getallcandidateslist();
    this.profileGetData();
    this.saveCandidateSearch();
    // this.getRatingDetails();
    this.exel();
  }

  saveCandidateSearch() {
    // this.candid=this.candidateid
    const searchData = {
      candidateId: this.searchCandidateForm.value.candidateId,
      pageNum: this.currentPage,
      pageSize: this.pageSize,
      registrationId: this.RegId,
      flag: true,
      email: this.searchCandidateForm.value.email,
      highestQualificationHeld: this.searchCandidateForm.value.highestQualification,
      mobile: this.searchCandidateForm.value.mobile
      
    }
    this.ser.getallcandidateslist(searchData).subscribe(res => {
      console.log(res, "totalserchhhhhhhhhhhhhhhhhhhhhhhinterviewlist----------");
      this.candlist = res;
      this.totalItems = this.candlist.totalRecords;
      console.log(this.totalItems, "totalItems");
      this.candgetall = this.candlist.result;
      console.log(this.candgetall, "interviewist--------");
    });
  }

  setPageContact(pagesNo: number): void {
    this.currentPageContact = pagesNo;
    this.profileGetData();
  }

  getallcandidateslist() {
    const post = {
      pageNum: this.currentPage,
      pageSize: this.pageSize,
      registrationId: this.RegId,
      flag: true
    };
    this.ser.getallcandidateslist(post).subscribe(res => {
      this.candlist = res;
      this.totalContactItems = this.candlist.totalRecords;
      console.log(this.totalItems, "totalItems");
      this.candgetall = this.candlist.result;
      console.log(this.candgetall, "candgetall");
      console.log(this.candgetall.mappingId, "mappingid")
      console.log(this.candgetall.candidateMappingStatus, "statusssssss");
      for (let i = 0; i < this.candgetall.length; i++) {
        this.Skill = this.candgetall[i].skills
        console.log(this.Skill, "skillsssssssssss");
      }
      console.log(this.candgetall, 'candgetall...........');
    });
  }

  setPagejob(page) {
    this.candidatepage = page;
    this.profileGetData();
  }


  profileGetData() {
    
    var profileData = {
      pageNum: this.candidatepage,
      pageSize: this.pageSize,
      registrationId: this.RegId,
      flag: true
    }

    this.ser.getAllJobOpenings(profileData).subscribe(resp => {
      this.profileData = resp;
      this.profileDetails = this.profileData.result;
      this.totalItemsjob = this.profileData.totalRecords;


      console.log(this.totalItemsjob, "totaljobbbbb");








      console.log(this.profileDetails, "bdmqqqqqqqqq");

      // this.id = this.profileData.result;

      this.totalItems = this.profileData.totalRecords;



    })
  }
  candidateIDDDDD;
  getId(id) {
    this.bdmid = id;

    console.log(this.bdmid, "BDMIDDDDD");

  }
  downloadExcel() {

    console.log(this.candgetall, "fffffffff")
    this.serv.exportAsExcelFile(this.candgetall, 'sample');
  }
  id: any;
  byte;
  byte1;
  getCandidateById(c) {

    this.candidateid = c;
    this.ser.getCandidateById(this.candidateid).subscribe(resp => {
      this.candidatedata = resp;
      this.candidatelist = this.candidatedata.result;
      this.candidateResume = this.candidatelist.resume;
      console.log("resume",this.candidateResume)
     
      this.addhighqualification = this.candidatelist.addQualificationHeld;
      console.log(this.addhighqualification, "dfggfd")

      
      this.jobDetailss = this.candidatelist.candidateJobTitle;
      this.sourcedetails = this.candidatelist.source;
      this.statusmaster = this.candidatelist.candidateStatusMaster;
      this.educationaldetail = this.candidatelist.educationalDetails;
      this.experience = this.candidatelist.experiencedetails;
      this.skill = this.candidatelist.primarySkills;
      this.usserr = this.candidatelist.user;

      for (let i = 0; i < this.skill.length; i++) { //primaryskills
        this.s = this.skill[i].skillName
      }
      for (let i = 0; i < this.educationaldetail.length; i++) { //primaryskills
        this.nameOfSchool = this.educationaldetail[i].nameOfSchool
      }
      this.firstName = this.candidatelist.firstName;
      this.lastName = this.candidatelist.lastName;
      this.email = this.candidatelist.email;
      this.mobile = this.candidatelist.mobile;
      this.alternateMobile = this.candidatelist.alternateMobile;
      this.totalExperience = this.candidatelist.totalExperience;
      this.currentCTC = this.candidatelist.currentCTC;
      this.expectedCTC = this.candidatelist.expectedCTC;
      this.fax = this.candidatelist.fax;
      this.website = this.candidatelist.website;
      this.street1 = this.candidatelist.street1;
      this.altenateEmail = this.candidatelist.altenateEmail;
      this.city = this.candidatelist.city;
      this.state = this.candidatelist.state;
      this.country = this.candidatelist.country;
      this.pincode = this.candidatelist.pincode;
      this.skypeID = this.candidatelist.skypeID;
      this.twitterId = this.candidatelist.twitterId;
      this.statusmaster = this.candidatelist.statusmaster
      this.resume = this.candidatelist.resume;
      console.log(this.resume,"rrrrrrrrrrrrr")
      this.fileextension=this.candidatelist.fileExtension;
      console.log(this.fileextension,"eeeeeeeeeeeeeeeeee")
      console.log(this.resume,"jjjjjjjjjjjjj");
      // File.WriteAllBytes("<file path>",ByteArray);
      // var str = String.fromCharCode.apply(String,  this.resume);
      // let TYPED_ARRAY = new Uint8Array(this.resume);
      // const STRING_CHAR = TYPED_ARRAY.reduce((data, byte)=> {
      //   return data + String.fromCharCode(byte);
      //   })
    
      
    
    })
  }
  saveid;
  getCandidrating(canid,rating) {
    debugger;
    this.candidateID = canid;
    this.rating = rating;
    console.log("cccccanddddd", this.id);

    if(this.rating != null)
    {
    let ratingBody = {
      rating: this.rating,
      registrationId: this.RegId,
      candidateId: this.candidateID,
      userId: this.userId,
    }
    this.ser.getRatingDetails(ratingBody).subscribe(resp => {
      this.ratingData = resp;
        this.ratingDetails = this.ratingData.result;
        this.saveid=this.ratingDetails.id;
        console.log(this.saveid,"ratingid")
        console.log(this.ratingDetails,"ratig get by id from api");
        this.bdmReqId = this.ratingDetails.bdmId;
        this.ratingForm.controls.Comments.setValue(this.ratingDetails.comments);
        this.ratingForm.controls.rating.setValue(this.ratingDetails.rating);
        for (let i = 0; i < this.profileDetails.length; i++) {
          if (this.profileDetails[i].id === this.bdmReqId) {
            this.bdmReqName = this.profileDetails[i].nameOfRequirement;
            console.log(this.bdmReqName, "this is bdm name from rating get by id");
            break;
            
          }
        }
        this.ratingForm.controls.nameOfRequirement.setValue(this.bdmReqName);
    })
  }
  else{
    this.ratingForm.reset();
  }
  }

  exel() {
    const post = {
      pageNum: this.currentPage,
      pageSize: this.pageSize,
      registrationId: this.RegId,
      flag: false
    };
    this.ser.getallcandidateslist(post).subscribe(res => {
      this.candlist = res;
      this.candgetall = this.candlist.result.json();
      console.log(res, "cangetalll");
    });
  }

  emailf(id,candistatus) {
    this.candid = id;
    this.candistatusmaster=candistatus;
  }

  candidatef(id) {
    this.rout.navigate(['superadmin/candidate/candidatedetails', id]);
  }

  bdmId(bdmiid) {
    this.bdmqId = bdmiid;
    console.log(bdmiid);
  };

  candidateId(canid) {
    this.selectedcandidateId = canid;
    console.log(canid, "iiddddddddddddddd========");
  }

  getCandida(id) {
    this.startiddd = id;
    console.log(this.startiddd, "candidddddddddddddd");
  }

  mappingsubmit() {
    let data = {
      bdmReq: [{
        id: this.bdmqId
      }],
      candidate: [{
        id: this.selectedcandidateId
      }],
      mappedUser: {
        id: this.userId
      },
      regId: this.RegId,
    }
    this.ser.postmapping(data).subscribe(resp => {
      this.mappdata = resp;
      console.log(this.mappdata);
      if (this.mappdata['status'] === 'OK') {
        $("#mapping").modal("hide");
        this.toaster.successToastr('Candidate mapped successfully');
      }
      if(this.mappdata['status'] === 'NameExist')
      {
        this.toaster.warningToastr(this.mappdata['res']);
      }
    });
  }

  download() {
    var filepdf = 'data:application/*;base64,' + this.candidatelist.resume ;
    let a = document.createElement('a');
    a.href = filepdf;
    a.download = 'resume.pdf';
    
    a.click();
  }


docx(){

  var filepdf = 'data:application/*;base64,' + this.candidatelist.resume;
  let a = document.createElement('a');
  a.href = filepdf;
  a.download = 'resume.docx';
  a.click();
  var actual = JSON.parse(this.candidatelist.resume)
  //var objectURL = URL.createObjectURL(actual);
  console.log(this.candidatelist.resume);
  console.log("url",actual);
  

}
candidate;
reesumee(c) {
  

    this.candidateid = c;
    this.ser.getCandidateById(this.candidateid).subscribe(resp => {
      this.candidatedata = resp;
      // .map(res => res )
     
    })
    
  }
  postcandidaterating() {
    let list = {
      rating: this.ratingForm.value.rating,
      comments: this.ratingForm.value.Comments,
      registrationId: this.RegId,
      candidateId: this.startiddd,
      userId: this.userId,
      bdmId: this.bdmid,
      id:this.saveid
    }
    this.ser.postcandidaterating(list).subscribe(resp => {
      this.ratingresp = resp;
      if (this.ratingresp.status === "Accepted") {
        this.ratingres = this.ratingresp.result;
       
        console.log(this.ratingres, "postinggggggggggggggggg");
        this.toaster.successToastr('Candidate rated successfully');
        this.getallcandidateslist();
      }
    })
  }

  // getRatingDetails()
  // {
  //   let ratingBody = {
  //     rating: this.rating,
  //     registrationId: this.RegId,
  //     candidateId: this.candidateid,
  //     userId: this.userId,
  //   }
  //   this.ser.getRatingDetails(ratingBody).subscribe(resp => {
  //     this.ratingData = resp;
  //       this.ratingDetails = this.ratingData.result;
  //       console.log(this.ratingDetails,"ratig get by id from api");
  //       this.bdmReqId = this.ratingDetails.bdmId;
  //       this.ratingForm.controls.Comments.setValue(this.ratingDetails.comments);
  //       this.ratingForm.controls.rating.setValue(this.ratingDetails.rating);
  //       this.profileDetails
  //       for (let i = 0; i < this.profileDetails.length; i++) {
  //         if (this.profileDetails[i].id === this.bdmReqId) {
  //           this.bdmReqName = this.profileDetails[i].nameOfRequirement;
  //           console.log(this.bdmReqName, "this is bdm name from rating get by id");
  //         }
  //       }
  //       this.ratingForm.controls.nameOfRequirement.setValue(this.ratingDetails.revenuePerPosition);
  //   })
  // }



  addresume(){
this.rout.navigate(['superadmin/candidate/addcanresume']);
  }

}










  // review;
  // candt;
  // exprence;
  // tlexp;
  // SKILLS;
  // expectecctc;
  // requirmt;
  // SKILLSrq;
  // exprencee;
  // tlexpp;
  // mappid;
  // mapping;
  // candstatusss;
  // candidatereview(candidateId:any,bdmReqId:any,status)
  //   {  
  //     console.log(this.candstatusss,"candidateMappingStatus");
  // console.log(this.mappingId,"mappingId");

  // this.candstatusss=status;

  //  this.ser.Reviewprofile(candidateId, bdmReqId,this.RegId).subscribe(resp=>
  //     {
  //       this.profile=resp;
  //       this.review=this.profile.result;



  //       console.log("experience",this.review.relavantExperience);
  //       console.log("Total-experience",this.review.totalExperience);
  //       console.log("skills",this.review.skills);
  //       console.log("locations",this.review.locations);
  //       console.log("expectedCTC",this.review.expectedCTC);


  //       if( this.review.relavantExperience === false){
  //         this.view= true;
  //       }

  //       if( this.review.totalExperience === false){
  //         this.view1= true;
  //       }


  //       if( this.review.skills === true){
  //         this.view3= false;
  //        this.viewcheck = true;
  //       }else{
  //         this.view3= true;
  //         this.viewcheck = false;

  //       }


  //       if( this.review.expectedCTC === false){
  //         this.view2 = true;
  //       }




  //       console.log(this.review,"dssdsfdfsd"); 

  //       this.exprence=this.review.candidateDetails.relavantExperience;
  //       console.log(this.exprence,"exp");

  //       this.tlexp=this.review.candidateDetails.totalExperience;
  //       this.SKILLS=this.review.candidateDetails.skills;
  //        for (let i = 0; i < this.SKILLS.length; i++) { 
  //         this.s = this.SKILLS[i].skillName

  //       }
  //       this.expectecctc=this.review.candidateDetails.expectedCTC;
  //       this.requirmt=this.review.requirementDetails;
  //       this.SKILLSrq=this.review.requirementDetails.skills;
  //        for (let i = 0; i < this.SKILLSrq.length; i++) { 
  //         this.s = this.SKILLSrq[i].skillName
  //       }
  //       this.exprencee=this.review.requirementDetails.relavantExperience;
  //       this.tlexpp=this.review.requirementDetails.totalExperience;
  //       this.expec=this.review.requirementDetails.expectedCTC;
















  //     })
  //   }

  // commetform=this.formBuilder.group(
  //   {
  //     remars:['',Validators.required]
  //   }
  // )

  // reviewstatus;
  // cmtsave()
  // {
  //   debugger;
  //   let data={
  //     remarks:this.commetform.value.remars,
  //     id:this.mappingId,
  //     candidateStatus:this.status,
  //     regId:JSON.parse(this.RegId),


  // }
  // this.ser.reviewstatus(data).subscribe(resp=>
  //   {
  //     this.reviewstatus=resp;
  //     console.log(this.reviewstatus,"Review status")
  //   })
  // }