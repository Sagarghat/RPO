import { Component, OnInit } from '@angular/core';
import { ToastrManager } from 'ng6-toastr-notifications';
import { FormBuilder,Validators } from '@angular/forms';
import { SuperadminserviceService } from '../../superadminservice.service';

@Component({
  selector: 'app-features',
  templateUrl: './features.component.html',
  styleUrls: ['./features.component.css']
})
export class FeaturesComponent implements OnInit {

  showtag:boolean= false;
  showtag1:boolean= false;
  featuresshow:boolean = false;

  featuresForm = this.formBuilder.group({
    featureName: ['', Validators.required],
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
      this.service.featuresgetId(id).subscribe(resp => {
        this.dataID = resp;
        console.log("data",resp);
         console.log("dataID",this.dataID.result.featureName);
         this.varaible = this.dataID.result.featureName;
         console.log(this.varaible ,"fffffffffff");
        this.featuresForm.controls.featureName.setValue(this.varaible);

      });
      this.featuresshow = true;
      this.showtag1 = true;
      this.showtag = false;
    }

    editfeature(){
     
      let obj1={
        featureName:this.featuresForm.value.featureName
      }
      console.log("Edit data" ,obj1);
      console.log("Edit data" ,this.value);
        this.service.editfeatures(obj1,this.value).subscribe(resp => {
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
      this.featuresshow = false; 
    }
  
  


     getmethod() {
      this.service.featuresget(this.pages,this.currentPage).subscribe(resp => {
        console.log("yooo due",resp);
        this.data = resp;
        this.datashow = this.data.result;
        console.log("totalRecords",this.data.totalRecords)
          this.total  =  this.data.totalRecords
      });
  }


  features(){
    this.featuresForm.reset();
    this.featuresshow = !this.featuresshow;
    this.showtag = true;
    this.showtag1 = false;
  }






  addfeature(){
    console.log(this.featuresForm.value);
    var mod={
      featureName:this.featuresForm.value.featureName
    }

    console.log("mode of Interview",mod);
      this.service.addfeatures(mod).subscribe(resp => {
      console.log(resp);
      if(resp){
        this.getmethod();
      }
      
      this.obj= resp;
      if(this.obj.status === 'StatusSuccess' ){
      this.toastr.successToastr( 'ModeofInterview Added');
      this.featuresForm.reset();
      } else {
        this.toastr.infoToastr( 'Duplicate Data  (or)  Empty Set');
      }

    });
    this.featuresshow=false;
    
  }


  
}
