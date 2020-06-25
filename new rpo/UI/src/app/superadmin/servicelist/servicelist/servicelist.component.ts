import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ToastrManager } from 'ng6-toastr-notifications';
import { FormBuilder,Validators } from '@angular/forms';
import { SuperadminserviceService } from '../../superadminservice.service';
@Component({
  selector: 'app-servicelist',
  templateUrl: './servicelist.component.html',
  styleUrls: ['./servicelist.component.css']
})
export class ServicelistComponent implements OnInit {

  showtag:boolean= false;
  showtag1:boolean= false;
  serviceListshow:boolean = false;

  serviceListForm = this.formBuilder.group({
    serviceName: ['', Validators.required],
  });
  data: any;
  datashow: any;
  total: any;
  
  obj: any;
  value: any;
  varaible: any;
  dataID: any;
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
    this.service.servicelistgetId(id).subscribe(resp => {
      this.dataID = resp;
      console.log("data",resp);
       console.log("dataID",this.dataID.result.serviceName);
       this.varaible = this.dataID.result.serviceName;
       console.log(this.varaible ,"fffffffffff");
      this.serviceListForm.controls.serviceName.setValue(this.varaible);

    });
    this.serviceListshow = true;
    this.showtag1 = true;
    this.showtag = false;
  }

  editservicelist(){
   
    let obj1={
      serviceName:this.serviceListForm.value.serviceName
    }
    console.log("Edit data" ,obj1);
    console.log("Edit data" ,this.value);
      this.service.editservicelist(obj1,this.value).subscribe(resp => {
      console.log("edit response",resp);
      if(resp){
        this.getmethod();
      }
     
      this.response= resp;
      if(this.response.status === 'StatusSuccess'){
        this.toastr.successToastr( 'Service List Modified');
      } else {
        this.toastr.infoToastr( 'Duplicate Data  (or)   Empty Set');
      }
    
    });
    this.serviceListshow = false; 
  }





  
  getmethod(){
    this.service.serviceget(this.pages,this.currentPage).subscribe(resp => {
      console.log("yooo due",resp);
      this.data = resp;
      this.datashow = this.data.result;
      
      console.log("totalRecords",this.data.totalRecords)
        this.total  =  this.data.totalRecords
    });
  }





  serviceList(){
    this.serviceListshow = !this.serviceListshow;
    this.serviceListForm.reset();
    this.showtag = true;
    this.showtag1 = false;
  }



  addserviceList(){
    var serve={
    serviceName:this.serviceListForm.value.serviceName,
    date:''
    }
      console.log(this.serviceListForm.value);
           this.service.addserviceList(serve).subscribe(resp => {
        console.log(resp);
        if(resp){
          this.getmethod();
        }
      });
  
      this.toastr.successToastr( 'ServiceList Added');
      this.serviceListshow=false;
    }
  

 

}
