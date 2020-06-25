import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { ServiceService } from 'src/app/services/service.service';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';
import { ToastrManager } from 'ng6-toastr-notifications';

@Component({
  selector: 'app-clientmail',
  templateUrl: './clientmail.component.html',
  styleUrls: ['./clientmail.component.css']
})
export class ClientmailComponent implements OnInit {

  
  emaildata;
  userId: string;
  userMail: string;

  contactlist: any;
  mail: any;
  clientid: any;
  clientList: any;
  RegId: string;
  userToken: string;
  ClientEmailId: any;
  clientmailid: string;
  leadId: any;
  accountManagerId: any;
  userlistById;
  userListByAmId: any;
  amEmail: any;
  userlistByIdLead;
  userListByLeadId: any;
  leadEmail: any;
  
  registerForm = this.formBuilder.group({
    // from: ['', Validators.required, Validators.email],
    // password: ['', Validators.required],
    to: ['',[Validators.required, Validators.email]],
    cc: ['',Validators.required],
    bcc: ['',Validators.required],
    messageBody: ['', Validators.required],
    messageSubject: [''],
    // logoImagePath: [''],
    // startDate: ['', Validators.required],
    // endDate: ['', Validators.required],
    // location: ['', Validators.required]
  });
  
  constructor(private route:Router, private toaster:ToastrManager, private formBuilder: FormBuilder, private router: ActivatedRoute, private service: SuperadminserviceService, private http: HttpClient) {

  }

  ngOnInit() {
    this.RegId = sessionStorage.getItem('registrationId');
    this.userToken = sessionStorage.getItem('Token');
    this.clientmailid = this.router.snapshot.paramMap.get('clientmailid');
    this.clientid = this.router.snapshot.paramMap.get('id');
    console.log(this.clientid, "id");
    this.getClientById();


  }
  UserRoleAmById() {
    this.service.getUserById(this.accountManagerId, this.RegId).subscribe(resp => {
      this.userlistById = resp;
      this.userListByAmId = this.userlistById.result;
      this.amEmail = this.userListByAmId.email;
      this.registerForm.controls.cc.setValue(this.amEmail);
    });
  }
  UserRoleLeadById() {
    this.service.getUserById(this.leadId, this.RegId).subscribe(resp => {
      this.userlistByIdLead = resp;
      this.userListByLeadId = this.userlistByIdLead.result;
      this.leadEmail = this.userListByLeadId.email;
      this.registerForm.controls.bcc.setValue(this.leadEmail);

    });
  }
  getClientById() {
    this.service.getClientById(this.clientmailid, this.RegId).subscribe(resp => {
      this.emaildata = resp;
      this.clientList = this.emaildata.result;
      console.log(this.clientList, "data");
      this.mail = this.clientList.client.email;
      this.leadId = this.clientList.client.leadId;
      this.accountManagerId = this.clientList.client.accountManagerId;
      this.UserRoleAmById();
      this.UserRoleLeadById();
      this.registerForm.controls.to.setValue(this.mail);
    });
  }

  save() {
    if(this.registerForm.invalid){
      this.toaster.warningToastr('Please fill the message body');
    }
    else{
    let data = {
      // from: this.registerForm.value.from,
      // password: this.registerForm.value.password,
      to: this.registerForm.value.to,
      cc: this.registerForm.value.cc,
      bcc: this.registerForm.value.bcc,
      messageBody: this.registerForm.value.messageBody,
      messagesubject: this.registerForm.value.messageSubject,
      fileAttachement:'',
      fileType:''
      // logoImagePath: this.registerForm.value.logoImagePath,
      // startDate:this.registerForm.value.startDate,
      // endDate:this.registerForm.value.endDate,
      // location:this.registerForm.value.location
    }

    this.service.sendClientMail(data).subscribe(resp => {
      this.emaildata = resp;
      console.log(this.emaildata, "emaildata");
      if(resp['status']=="StatusSuccess"){
        this.toaster.successToastr('Email sent successfully');
        this.route.navigate(['/superadmin/client']);
      }

    });
  }
  }
}
