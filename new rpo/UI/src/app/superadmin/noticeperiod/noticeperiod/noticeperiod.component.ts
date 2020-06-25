import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ToastrManager } from 'ng6-toastr-notifications';
import { FormBuilder,Validators } from '@angular/forms';
import { SuperadminserviceService } from '../../superadminservice.service';
@Component({
  selector: 'app-noticeperiod',
  templateUrl: './noticeperiod.component.html',
  styleUrls: ['./noticeperiod.component.css']
})
export class NoticeperiodComponent implements OnInit {

  noticePeriodshow:boolean = false;
  showtag:boolean= false;
  showtag1:boolean= false;

  noticePeriodForm = this.formBuilder.group({
    noticePeriod: ['', Validators.required],
  });

  data: any;
  datashow: any;
  total: any;
  
  obj: any;
  value: any;
  dataID: any;
  varaible: any;
  response: any;

  constructor(public toastr: ToastrManager, public ser: SuperadminserviceService,
    private formBuilder: FormBuilder) { }


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
    this.ser.noticeperiodgetId(id).subscribe(resp => {
      this.dataID = resp;
      console.log("data",resp);
       console.log("dataID",this.dataID.result.noticePeriod);
       this.varaible = this.dataID.result.noticePeriod;
       console.log(this.varaible ,"fffffffffff");
      this.noticePeriodForm.controls.noticePeriod.setValue(this.varaible);

    });
    this.noticePeriodshow = true;
    this.showtag1 = true;
    this.showtag = false;
  }

  editNoticeperiod(){
   
    let obj1={
      noticePeriod:this.noticePeriodForm.value.noticePeriod
    }
    console.log("Edit data" ,obj1);
    console.log("Edit data" ,this.value);
      this.ser.editnoticeperiod(obj1,this.value).subscribe(resp => {
      console.log("edit response",resp);
      if(resp){
        this.getmethod();
      }
   
      this.response= resp;
      if(this.response.status === 'StatusSuccess'){
        this.toastr.successToastr( 'Notice Period  Modified');
      } else {
        this.toastr.infoToastr( 'Duplicate Data  (or)   Empty Set');
      }
      
    
    });
    this.noticePeriodshow = false; 
  }









  
  getmethod(){
    this.ser.noticeperiodget(this.pages,this.currentPage).subscribe(resp => {
      console.log("yooo due",resp);
      this.data = resp;
      this.datashow = this.data.result;
      
      console.log("totalRecords",this.data.totalRecords)
        this.total  =  this.data.totalRecords
    });
  }



  noticePeriod(){
    this.noticePeriodshow = !this.noticePeriodshow;
    this.noticePeriodForm.reset();
    this.showtag = true;
    this.showtag1 = false;
  }



  addnoticePeriod(){
    var note={
    noticePeriod:this.noticePeriodForm.value.noticePeriod
  }
      console.log(this.noticePeriodForm.value);
        this.ser.addnoticePeriod(note).subscribe(resp => {
        console.log(resp);
        if(resp){
          this.getmethod();
        }
      });
      this.toastr.successToastr( 'NoticePeriod Added');
      this.noticePeriodshow=false;
    }
  

}
