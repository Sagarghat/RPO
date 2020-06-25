import { Component, OnInit } from '@angular/core';
import { ToastrManager } from 'ng6-toastr-notifications';
import { FormBuilder,Validators } from '@angular/forms';
import { SuperadminserviceService } from '../../superadminservice.service';
@Component({
  selector: 'app-certificates',
  templateUrl: './certificates.component.html',
  styleUrls: ['./certificates.component.css']
})
export class CertificatesComponent implements OnInit {


  showtag:boolean= false;
  showtag1:boolean= false;
  certificatesshow:boolean = false;

  certificatesForm = this.formBuilder.group({
    certificationName: ['', Validators.required],
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
      this.service.certificatesgetId(id).subscribe(resp => {
        this.dataID = resp;
        console.log("data",resp);
         console.log("dataID",this.dataID.result.certificationName);
         this.varaible = this.dataID.result.certificationName;
         console.log(this.varaible ,"fffffffffff");
        this.certificatesForm.controls.certificationName.setValue(this.varaible);

      });
      this.certificatesshow = true;
      this.showtag1 = true;
      this.showtag = false;
    }

    editcertificates(){
     
      let obj1={
        certificationName:this.certificatesForm.value.certificationName
      }
      console.log("Edit data" ,obj1);
      console.log("Edit data" ,this.value);
        this.service.editcertificates(obj1,this.value).subscribe(resp => {
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
      this.certificatesshow = false; 
    }
  
  




     getmethod() {
      this.service.certificatesget(this.pages,this.currentPage).subscribe(resp => {
        console.log("yooo due",resp);
        this.data = resp;
        this.datashow = this.data.result;
        console.log("totalRecords",this.data.totalRecords)
          this.total  =  this.data.totalRecords
      });
  }


  certificates(){
    this.certificatesForm.reset();
    this.certificatesshow = !this.certificatesshow;
    this.showtag = true;
    this.showtag1 = false;
  }






  addcertificates(){
    console.log(this.certificatesForm.value);
    var mod={
      certificationName:this.certificatesForm.value.certificationName
    }

    console.log("mode of Interview",mod);
      this.service.addcertificates(mod).subscribe(resp => {
      console.log(resp);
      if(resp){
        this.getmethod();
      }
      
      this.obj= resp;
      if(this.obj.status === 'StatusSuccess' ){
      this.toastr.successToastr( 'ModeofInterview Added');
      this.certificatesForm.reset();
      } else {
        this.toastr.infoToastr( 'Duplicate Data  (or)  Empty Set');
      }

    });
    this.certificatesshow=false;
    
  }


}
