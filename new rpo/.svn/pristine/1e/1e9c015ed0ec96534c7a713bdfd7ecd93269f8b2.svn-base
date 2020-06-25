import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-subclient',
  templateUrl: './subclient.component.html',
  styleUrls: ['./subclient.component.css']
})
export class SubclientComponent implements OnInit {

  emaildata;
 
  email: string;
  candidateid: any;
  candlist: any;
  mail: any;
  id: string;
  selectedClientName;

  registerForm = this.formBuilder.group({
  
    to:['',Validators.required],
    cc:[''],
    bcc:[''],
    messageBody:['',Validators.required],
    messagesubject:['',Validators.required],
    logoImagePath:[''],
    startDate:['',Validators.required],
    endDate:['',Validators.required],
    location:['',Validators.required]
  })
  userId: string;
  userMail: string;


  totalItems;
  pageSize = 10;
  currentPage = 1;
  RegId: string;
  userToken: string;
  details;
  clientList: any;
  clientListId: any;
  profileData;
  profileDetails: any;
  totalProfileItems: any;
  constructor(private formBuilder:FormBuilder,private service:SuperadminserviceService,private rout:ActivatedRoute) { }

  setPage(pagNo: number): void {
    this.currentPage = pagNo;
    this.getClientList();
  }
  ngOnInit() {
    this.RegId = sessionStorage.getItem('registrationId');
    this.userToken = sessionStorage.getItem('Token');
    this.userId = sessionStorage.getItem('userId');
    this.userMail = sessionStorage.getItem('userMail');
    this.id=this.rout.snapshot.paramMap.get('candid');
 
  this.getCandidateById();
    this.save();
    this.getClientList();
  
  }
  modelChanged(clientname) {
    console.log(clientname, "lahari========");
    for (var i = 0; i < this.clientList.length; i++) {
      if (this.clientList[i].clientName === clientname) {
 
        this.clientListId = this.clientList[i].id;
        this.profileGetData();
        console.log(this.clientListId, "clientId=====");
        break;
      }
    }
 
  }

  profileGetData() {
    var profileData = {
      pageNum: 1,
      pageSize: this.pageSize,
      registrationId: this.RegId,
      clientId: this.clientListId
    }
    
    this.service.getAllJobOpenings(profileData).subscribe(resp => {
      this.profileData = resp;
      this.profileDetails = this.profileData.result;
      this.totalProfileItems = this.profileData.totalRecords;
      console.log(this.totalProfileItems, "total job Items");
      console.log(this.profileDetails, " Profile data---------")
    })
  }
  getCandidateById(){
    
    debugger;
    this.service.getCandidateById(this.id).subscribe(resp=>
      {
        this.emaildata=resp;
        this.candlist=this.emaildata.result;
        console.log(this.candlist,"data")


        // this.mail=this.candlist.email;
        // console.log(this.mail,"email");
        


      })

  }

  save()
  {

    let data = {
      
      
      to: this.registerForm.value.to,
      cc: this.registerForm.value.cc,
      bcc: this.registerForm.value.bcc,
      messageBody: this.registerForm.value.messageBody,
      messagesubject: this.registerForm.value.messagesubject,
      logoImagePath: this.registerForm.value.logoImagePath,
      startDate:this.registerForm.value.startDate,
      endDate:this.registerForm.value.endDate,
      location:this.registerForm.value.location
     }
 
   this.service.candidateEmails(data).subscribe(resp=>
    {
      this.emaildata=resp;
      console.log(this.emaildata,"emaildata");
      
    })
  }


  getClientList() {
    const post = {
      pageNum: this.currentPage,
      pageSize: this.pageSize,
      registrationId: this.RegId
    };
    this.service.getClientList(post).subscribe(res => {
      this.details = res;
      this.totalItems = this.details.totalRecords;
      this.clientList = this.details.result;
      console.log(this.totalItems, "totalItems");
      console.log(this.clientList, 'clientlist...........');
    });
  }


}
