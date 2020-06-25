import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ToastrManager } from 'ng6-toastr-notifications';
import { FormBuilder,Validators } from '@angular/forms';
import { SuperadminserviceService } from '../../superadminservice.service';

@Component({
  selector: 'app-paymentterms',
  templateUrl: './paymentterms.component.html',
  styleUrls: ['./paymentterms.component.css']
})
export class PaymenttermsComponent implements OnInit {


  showtag:boolean= false;
  showtag1:boolean= false;
  paymentTermsshow:boolean = false;


  paymentTermsForm = this.formBuilder.group({
    paymentType: ['', Validators.required],
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
    this.service.paymenttermsgetId(id).subscribe(resp => {
      this.dataID = resp;
      console.log("data",resp);
       console.log("dataID",this.dataID.result.paymentType);
       this.varaible = this.dataID.result.paymentType;
       console.log(this.varaible ,"fffffffffff");
      this.paymentTermsForm.controls.paymentType.setValue(this.varaible);

    });
    this.paymentTermsshow = true;
    this.showtag1 = true;
    this.showtag = false;
  }

  editpaymentterm(){
   
    let obj1={
      paymentType:this.paymentTermsForm.value.paymentType
    }
    console.log("Edit data" ,obj1);
    console.log("Edit data" ,this.value);
      this.service.editpaymentTerms(obj1,this.value).subscribe(resp => {
      console.log("edit response",resp);
      if(resp){
        this.getmethod();
      }
     
      this.response= resp;
      if(this.response.status === 'StatusSuccess'){
        this.toastr.successToastr( 'Payment Terms  Modified');
      } else {
        this.toastr.infoToastr( 'Duplicate Data  (or)   Empty Set');
      }
    
    });
    this.paymentTermsshow = false; 
  }






  getmethod(){
    this.service.paymentTermsget(this.pages,this.currentPage).subscribe(resp => {
      console.log("yooo due",resp);
      this.data = resp;
      this.datashow = this.data.result;
      
      console.log("totalRecords",this.data.totalRecords)
        this.total  =  this.data.totalRecords
    });
  }



  paymentTerms(){
    this.paymentTermsshow = !this.paymentTermsshow;
    this.paymentTermsForm.reset();
    this.showtag = true;
    this.showtag1 = false;
 
  }

  addpaymentTerms(){
    var pay={
    paymentType:this.paymentTermsForm.value.paymentType
  }
      console.log(this.paymentTermsForm.value);
  
           this.service.addpaymentTerms(pay).subscribe(resp => {
        console.log(resp);
        if(resp){
          this.getmethod();
        }
      });
      this.toastr.successToastr( 'PaymentTerms Added');
      this.paymentTermsshow=false;
    }

}
