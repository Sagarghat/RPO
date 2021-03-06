import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ToastrManager } from 'ng6-toastr-notifications';
import { FormBuilder,Validators } from '@angular/forms';
import { SuperadminserviceService } from '../../superadminservice.service';
@Component({
  selector: 'app-permissions',
  templateUrl: './permissions.component.html',
  styleUrls: ['./permissions.component.css']
})
export class PermissionsComponent implements OnInit {

  
  showtag:boolean= false;
  showtag1:boolean= false;
  permissionsshow:boolean = false;

  permissionsForm = this.formBuilder.group({
    permissionName: ['', Validators.required],
  });

  

  data: any;
  datashow: any;

  total: any;
  
  obj: any;
  value: any;
  dataID: any;
  varaible: any;
  response: any;


  constructor(public toastr: ToastrManager, public service: SuperadminserviceService,private formBuilder: FormBuilder) { }


  pages =3;
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
    this.service.permissionsgetId(id).subscribe(resp => {
      this.dataID = resp;
      console.log("data",resp);
       console.log("dataID",this.dataID.result.permissionName);
       this.varaible = this.dataID.result.permissionName;
       console.log(this.varaible ,"fffffffffff");
      this.permissionsForm.controls.permissionName.setValue(this.varaible);

    });
    this.permissionsshow = true;
    this.showtag1 = true;
    this.showtag = false;
  }

  editpermissions(){
   
    let obj1={
      permissionName:this.permissionsForm.value.permissionName
    }
    console.log("Edit data" ,obj1);
    console.log("Edit data" ,this.value);
      this.service.editpermissions(obj1,this.value).subscribe(resp => {
      console.log("edit response",resp);
      if(resp){
        this.getmethod();
      }
      
      this.response= resp;
      if(this.response.status === 'StatusSuccess'){
        this.toastr.successToastr( 'Permissions  Modified');
      } else {
        this.toastr.infoToastr( 'Duplicate Data  (or)   Empty Set');
      }
    
    });
    this.permissionsshow = false; 
  }










  getmethod(){
    this.service.permissionsget(this.pages,this.currentPage).subscribe(resp => {
      console.log("yooo due",resp);
      this.data = resp;
      this.datashow = this.data.result;
      console.log("totalRecords",this.data.totalRecords)
        this.total  =  this.data.totalRecords
    });
  }


  



  

  permissions(){
    this.permissionsshow = !this.permissionsshow;
    this.permissionsForm.reset();
    this.showtag = true;
    this.showtag1 = false;
  }


  addpermissions(){
    console.log(this.permissionsForm.value);
    var permission={
      permissionName:this.permissionsForm.value.permissionName
    }
    this.service.addpermissions(permission).subscribe(resp => {
      console.log(resp);
      if(resp){
        this.getmethod();
      }
    });
    this.toastr.successToastr( 'Permissions Added');
    this.permissionsshow=false;
  }





}
