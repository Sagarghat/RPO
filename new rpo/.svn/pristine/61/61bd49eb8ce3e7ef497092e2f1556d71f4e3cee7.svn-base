import { Component, OnInit } from '@angular/core';
import { ToastrManager } from 'ng6-toastr-notifications';
import { FormBuilder,Validators } from '@angular/forms';
import { SuperadminserviceService } from '../../superadminservice.service';

@Component({
  selector: 'app-joblocation',
  templateUrl: './joblocation.component.html',
  styleUrls: ['./joblocation.component.css']
})
export class JoblocationComponent implements OnInit {


  showtag:boolean= false;
  showtag1:boolean= false;
  joblocationshow:boolean = false;

  joblocationForm = this.formBuilder.group({
    locationName: ['', Validators.required],
  });


  data: any;
  datashow: any;
  total: any;
  
  obj: any;
  dataID: any;
  varaible: any;
  value: any;
  response: any;
 
  constructor(public toastr: ToastrManager, public service: SuperadminserviceService,private formBuilder: FormBuilder) {}


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
      this.service.joblocationgetId(id).subscribe(resp => {
        this.dataID = resp;
        console.log("data",resp);
         console.log("dataID",this.dataID.result.locationName);
         this.varaible = this.dataID.result.locationName;
         console.log(this.varaible ,"fffffffffff");
        this.joblocationForm.controls.locationName.setValue(this.varaible);

      });
      this.joblocationshow = true;
      this.showtag1 = true;
      this.showtag = false;
    }

    editjoblocation(){
     
      let obj1={
        locationName:this.joblocationForm.value.locationName
      }
      console.log("Edit data" ,obj1);
      console.log("Edit data" ,this.value);
        this.service.editjoblocations(obj1,this.value).subscribe(resp => {
        console.log("edit response",resp);
        if(resp){
          this.getmethod();
        }
        this.response= resp;
        if(this.response.status === 'StatusSuccess'){
          this.toastr.successToastr( 'ModeofInterview Modified');
        } else {
          this.toastr.infoToastr( 'Duplicate Data  (or)  Empty Set');
        }
       

      });
      this.joblocationshow = false; 
    }
  
  




     getmethod() {
      this.service.joblocationsget(this.pages,this.currentPage).subscribe(resp => {
        console.log("yooo due",resp);
        this.data = resp;
        this.datashow = this.data.result;
        console.log("totalRecords",this.data.totalRecords)
          this.total  =  this.data.totalRecords
      });
  }


  joblocation(){
    this.joblocationForm.reset();
    this.joblocationshow = !this.joblocationshow;
    this.showtag = true;
    this.showtag1 = false;
  }






  addjoblocation(){
    console.log(this.joblocationForm.value);
    var mod={
      locationName:this.joblocationForm.value.locationName
    }

    console.log("mode of Interview",mod);
      this.service.addjoblocation(mod).subscribe(resp => {
      console.log(resp);
      if(resp){
        this.getmethod();
      }
      
      this.obj= resp;
      if(this.obj.status === 'StatusSuccess' ){
      this.toastr.successToastr( 'ModeofInterview Added');
      this.joblocationForm.reset();
      } else {
        this.toastr.infoToastr( 'Duplicate Data  (or)  Empty Set');
      }

    });
    this.joblocationshow=false;
    
  }

}
