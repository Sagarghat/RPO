import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ToastrManager } from 'ng6-toastr-notifications';
import { FormBuilder,Validators } from '@angular/forms';
import { SuperadminserviceService } from '../../superadminservice.service';

@Component({
  selector: 'app-roles',
  templateUrl: './roles.component.html',
  styleUrls: ['./roles.component.css']
})
export class RolesComponent implements OnInit {


  showtag:boolean= false;
  showtag1:boolean= false;

  rolesshow:boolean = false;



  

  rolesForm = this.formBuilder.group({
    name: ['', Validators.required],
  });
  data: any;
  datashow: any;
  total: any;
  
  obj: any;
  value: any;
  dataID: any;
  varaible: any;
  response: any;


  constructor(public toastr: ToastrManager, public ser: SuperadminserviceService,private formBuilder: FormBuilder) { }




  pages =4;
  currentPage = 1;
 
  setPages(pageNo: number): void {
    this.currentPage = pageNo;
    this.getmethod();
  }

  ngOnInit() {

 this.getmethod();
 
  }
 



  getId(id){
    console.log("id",id);
    this.value= id;
    this.ser.rolesgetId(id).subscribe(resp => {
      this.dataID = resp;
      console.log("data",resp);
       console.log("dataID",this.dataID.result.name);
       this.varaible = this.dataID.result.name;
       console.log(this.varaible ,"fffffffffff");
      this.rolesForm.controls.name.setValue(this.varaible);

    });
    this.rolesshow = true;
    this.showtag1 = true;
    this.showtag = false;
  }

  editroles(){
   
    let obj1={
      name:this.rolesForm.value.name
    }
    console.log("Edit data" ,obj1);
    console.log("Edit data" ,this.value);
      this.ser.editroles(obj1,this.value).subscribe(resp => {
      console.log("edit response",resp);
      if(resp){
        this.getmethod();
      }
  
      this.response= resp;
      if(this.response.status === 'StatusSuccess'){
        this.toastr.successToastr( 'Roles Modified');
      } else {
        this.toastr.infoToastr( 'Duplicate Data  (or)   Empty Set');
      }
    
    });
    this.rolesshow = false; 
  }






  getmethod(){
    this.ser.rolesget(this.pages,this.currentPage).subscribe(resp => {
      console.log("yooo due",resp);
      this.data = resp;
      this.datashow = this.data.result;
      
      console.log("totalRecords",this.data.totalRecords)
        this.total  =  this.data.totalRecords
    });
  }



  roles(){
    this.rolesshow = !this.rolesshow;
    this.rolesForm.reset();
    this.showtag = true;
    this.showtag1 = false;
  }


  addroles(){
    console.log(this.rolesForm.value);
    var role={
     name:this.rolesForm.value.name
    }

    console.log("role",role);
      this.ser.addroles(role).subscribe(resp => {
      console.log(resp);
      if(resp){
        this.getmethod();
      }
      this.obj= resp;
      if(this.obj.status === 'StatusSuccess' ){
      this.toastr.successToastr( 'Roles Added');
      this.rolesForm.reset();
      } else if(this.obj.status === 'DuplicateRecord' ){
        this.toastr.warningToastr('DuplicateRecord Or Add Again');
      }
    });
  
    this.rolesshow=false;
    
  }

}
