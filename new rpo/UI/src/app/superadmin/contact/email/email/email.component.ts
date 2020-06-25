import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';
import { ActivatedRoute } from '@angular/router';
import { ToastrManager } from 'ng6-toastr-notifications';

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
  userId: string;
  userMail: string;
  contactlist: any;
  route: any;
  RegId: string;
  contactOwnerId: any;
  registrationId: any;
  ownerList;
  Owner: any;
  accountManagerId: any;
  userlistById;
  userListByAmId: any;
  amEmail: any;
  userlistByIdLead;
  userListByLeadId: any;
  leadEmail: any;
  leadId: any;

  registerForm = this.formBuilder.group({
   
    to: ['', Validators.required],
    cc: [''],
    bcc: [''],
    messageBody: ['', Validators.required],
    Subject: [''],
  })
 

  constructor(private toaster:ToastrManager,private formBuilder: FormBuilder, private service: SuperadminserviceService, private http: HttpClient, private rout: ActivatedRoute) { }

  ngOnInit() {
    this.RegId = sessionStorage.getItem('registrationId');
    this.userId = sessionStorage.getItem('userId');
    this.userMail = sessionStorage.getItem('userMail');
    this.id = this.rout.snapshot.paramMap.get('contactid');
    console.log(this.id, "id");
    this.getcontactById();
    this.save();

  }

   UserRoleAmById() {
  this.service.getUserById(this.contactOwnerId, this.RegId).subscribe(resp => {
    this.ownerList = resp;
    console.log(this.ownerList,"ownerlist------ ");
    this.Owner = this.ownerList.result; 
    this.amEmail = this.Owner.email;
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

 getcontactById() {
    this.service.getcontactById(this.id).subscribe(resp => {
      this.emaildata = resp;
      this.contactlist = this.emaildata.result;
     this.contactOwnerId= this.contactlist.contact.contactOwner;
      console.log(this.contactlist, "data")
      this.mail = this.contactlist.contact.email;
      this.registerForm.controls.to.setValue(this.mail)
      console.log(this.mail, "dfdfgdsfsd");
      this.UserRoleAmById();
    });
  }

  save() {

    let data = 
    {
      to: this.registerForm.value.to,
      cc: this.registerForm.value.cc,
      bcc: this.registerForm.value.bcc,
      messageBody: this.registerForm.value.messageBody,
      messagesubject: this.registerForm.value.messagesubject,
    }
      this.service.contactEmails(data).subscribe(resp => {
      this.emaildata = resp;
      console.log(this.emaildata, "emaildata");
      if(resp['status']=="StatusSuccess"){
        this.toaster.successToastr('Email sent successfully');
        this.route.navigate(['/superadmin/client']);
      }
    })
  }
}