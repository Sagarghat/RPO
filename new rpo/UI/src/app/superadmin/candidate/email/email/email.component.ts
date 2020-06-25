import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { HttpClient } from '@angular/common/http';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent implements OnInit {
  emaildata;
  email: string;
  candidateid: any;
  candlist: any;
  mail: any;
  id: string;

  registerForm = this.formBuilder.group({
  
    to:['',Validators.required],
    cc:[''],
    bcc:[''],
   
    messagesubject:[''],
    messageBody:['',Validators.required]
    // logoImagePath:[''],
    // startDate:['',Validators.required],
    // endDate:['',Validators.required],
    // location:['',Validators.required]
  })
  userId: string;
  userMail: string;


  constructor(private formBuilder:FormBuilder,
    private service:SuperadminserviceService,private http:HttpClient,private rout:ActivatedRoute) { }

  ngOnInit() {
    this.userId = sessionStorage.getItem('userId');
    this.userMail = sessionStorage.getItem('userMail');
    this.id=this.rout.snapshot.paramMap.get('candid');
 
  this.getCandidateById();
    this.save();
  
  }

  getCandidateById(){
    
    debugger;
    this.service.getCandidateById(this.id).subscribe(resp=>
      {
        this.emaildata=resp;
        this.candlist=this.emaildata.result;
        console.log(this.candlist,"data")


        this.mail=this.candlist.email;
        console.log(this.mail,"email");
        


      })

  }

  save()
  {

    let data = {
      
      to: this.registerForm.value.to,
      cc: this.registerForm.value.cc,
      bcc: this.registerForm.value.bcc,
      
      messageSubject: this.registerForm.value.messageSubject,
      messageBody: this.registerForm.value.messageBody,
      // logoImagePath: this.registerForm.value.logoImagePath,
      // startDate:this.registerForm.value.startDate,
      // endDate:this.registerForm.value.endDate,
      // location:this.registerForm.value.location
     }
 
   this.service.candidateEmails(data).subscribe(resp=>
    {
      this.emaildata=resp;
      console.log(this.emaildata,"emaildata");
      
    })
  }
}
