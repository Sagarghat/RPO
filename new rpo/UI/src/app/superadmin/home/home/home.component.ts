import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  // tslint:disable-next-line:variable-name
  user_name: string;
  firstName: any;
  lastName: any;
  constructor( private route:Router) { }

  ngOnInit() {

         this.user_name = sessionStorage.getItem('name');
         this.firstName = sessionStorage.getItem('firstName')
         this.lastName = sessionStorage.getItem('lastName')
  }



 signOut(){
  sessionStorage.clear(); 
  this.route.navigateByUrl('');
 }

  

}
