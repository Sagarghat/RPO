import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ServiceService } from 'src/app/services/service.service';
import { HttpClient, HttpParams } from '@angular/common/http';
import { FormGroup, FormControl } from '@angular/forms';
import { ToastrManager } from 'ng6-toastr-notifications';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  id: any;
  num: any;

  loginForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    domain: new FormControl(''),

  });
  data: any;
  loginresp: any;


  constructor(private route: Router, public ser: ServiceService,
              private activatedRoute: ActivatedRoute, private http: HttpClient,
              private toaster: ToastrManager) {
  }

  ngOnInit() {

    this.activatedRoute.params.subscribe(params => {
      this.id = params['id'];
    })
    if (this.id !== undefined) {
      // tslint:disable-next-line:no-debugger
      debugger;
      this.num = parseInt(this.id);
      //  sessionStorage.setItem('userId',this.id);
      console.log(this.num);
      this.ser.activate_status(this.num).subscribe(resp => {
        console.log(resp);
      });
    } else {
      console.log('You need to Register');
    }
  }

  onSubmit() {
    const body = new HttpParams()
      .set('username', this.loginForm.value.username)
      .set('password', this.loginForm.value.password)
      .set('domain', this.loginForm.value.domain)
    this.ser.login(body.toString()).subscribe(resp => {
      console.log(resp, 'Login');
      this.loginresp = resp;


      if (this.loginresp.token === 'Bad credentials') {

        this.toaster.errorToastr('Please check the Credentials', 'WrongCredentials');

      } else {
        sessionStorage.setItem('userMail', this.loginForm.value.username);
        sessionStorage.setItem('Domain', this.loginForm.value.domain);
        sessionStorage.setItem('Token', this.loginresp.token);
        sessionStorage.setItem('userId', this.loginresp.user.id);
        sessionStorage.setItem('registrationId', this.loginresp.user.registrationId);
        sessionStorage.setItem('role', this.loginresp.user.role);
        sessionStorage.setItem('name', this.loginresp.user.name);
        sessionStorage.setItem('firstName',this.loginresp.user.firstName)
        sessionStorage.setItem('lastName',this.loginresp.user.lastName)


        console.log('uppercase', this.loginresp.user.role.toUpperCase());

        if (this.loginresp.user.role === 'Admin' ) {
          this.route.navigateByUrl('/superadmin');
          this.toaster.successToastr('Admin Login Success!');
        }

        if (this.loginresp.user.role === 'SUPERADMIN' ) {
          this.route.navigateByUrl('/superadmin');
          this.toaster.successToastr('SUPERADMIN Login Success!');
        }

        if ( this.loginresp.user.role === 'BDM' ) {
          this.route.navigateByUrl('/superadmin');
          this.toaster.successToastr('BDM Login Success!');
        }

        if ( this.loginresp.user.role === 'AM' ) {
          this.route.navigateByUrl('/superadmin');
          this.toaster.successToastr('AM Login Success!');
        }

        if ( this.loginresp.user.role === 'Lead' ) {
          this.route.navigateByUrl('/superadmin');
          this.toaster.successToastr('Lead Login Success!');
        }

        if ( this.loginresp.user.role === 'Recruiter' ) {
          this.route.navigateByUrl('/superadmin');
          this.toaster.successToastr('Lead Login Success!');
        }


        // else {
        //   this.toaster.infoToastr('Your Role is not Mentioned in the DataBase');
        // }


      }
    });
  }
}
