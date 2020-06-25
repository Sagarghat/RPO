import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ServiceService } from 'src/app/services/service.service';
import { map } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ToastrManager } from 'ng6-toastr-notifications';
import { IfStmt } from '@angular/compiler';


@Component({
  selector: 'app-sign',
  templateUrl: './sign.component.html',
  styleUrls: ['./sign.component.css']
})
export class SignComponent implements OnInit {
  registerForm = this.formbuilder.group({
    emailId: ['', [Validators.required,Validators.email]],
    companyName: ['',[ Validators.required, Validators.pattern("[A-Za-z]+")]],
    phoneNo: ['',[ Validators.required,Validators.pattern("^(\([0-9]{3}\) |[6-9]{1})[0-9]{9}$")]],
    country: ['', [ Validators.required, Validators.pattern("[A-Za-z]+")]],
    zipCode: ['', [Validators.required,Validators.pattern("^[5]{1}[0-9]{5}$")]],
    box: ['', Validators.required]
  });
  planId;
  // public planId: Observable<string>;
  constructor(private formbuilder: FormBuilder, private ser: ServiceService,private route:Router,private toaster:ToastrManager) {



    // this.planId = this.activatedRoute.parent.params.pipe(map(opt => {
    //   return opt.planId;
    //  }));


  }
  ngOnInit() {
    this.ser.planID.subscribe(planId => {
      // alert(JSON.parse(planId));
      this.planId = planId;
      console.log(this.planId, "planid");
    })
  }

  submit() {
    if(this.registerForm.invalid)
    {
      this.toaster.errorToastr('Please fill the mandatory fields', 'Notification');
    }
    else{
    this.ser.planID.subscribe(planId => {
      // alert(planId);
      this.planId = planId;
      console.log(this.planId, "planid");
    });
    console.log(this.planId)
    let data = {
      emailId: this.registerForm.value.emailId,
     companyName: this.registerForm.value.companyName,
      phoneNo: this.registerForm.value.phoneNo,
      country: this.registerForm.value.country,
      compStatus: 0,
      zipCode: this.registerForm.value.zipCode,
      planId: JSON.stringify(this.planId),
      domain:"sts"
    
    }
      console.log(data);

    this.ser.signup(data).subscribe(resp => {

      console.log(resp , 'registation');
      if(resp['status'] === 'EmailExist')
      {
        this.toaster.errorToastr('Please fill the mandatory fields', 'Notification');
      } else {
        this.toaster.successToastr('registration successfull');
        this.route.navigateByUrl('');
      }
    }


    )
  }
  }
  // "user": {
      //   "companyName": this.registerForm.value.companyName,
      //   "email": this.registerForm.value.emailId,
      //   "name": this.registerForm.value.emailId,
      //   "status": "Inactive"
      // }
  // login()
  // {
  //   this.route.navigate(['']);
  // }

  get f() {
    return this.registerForm.controls;
  }
}
  // this.ht.post(data).subscribe(resp => {
  //   if (resp['status'] == 'StatusSuccess') {
  //     alert(resp['res'])
  //     this.customerResult = resp['result'];
  //     console.log(resp, "client details")
  //   }
  //   else {
  //     alert(resp['res']);
  //   }
  // })




