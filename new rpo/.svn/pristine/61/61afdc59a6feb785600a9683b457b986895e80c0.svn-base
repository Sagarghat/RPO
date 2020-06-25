import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ToastrManager } from 'ng6-toastr-notifications';
import { FormBuilder,Validators } from '@angular/forms';
import { SuperadminserviceService } from '../../superadminservice.service';
@Component({
  selector: 'app-skills',
  templateUrl: './skills.component.html',
  styleUrls: ['./skills.component.css']
})
export class SkillsComponent implements OnInit {


  showtag:boolean= false;
  showtag1:boolean= false;
  skillshow:boolean = false;
  skillForm = this.formBuilder.group({
    skillName: ['', Validators.required],
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

  page = 3;
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
      this.service.skillsgetId(id).subscribe(resp => {
        this.dataID = resp;
        console.log("data",resp);
         console.log("dataID",this.dataID.result.skillName);
         this.varaible = this.dataID.result.skillName;
         console.log(this.varaible ,"fffffffffff");
        this.skillForm.controls.skillName.setValue(this.varaible);

      });
      this.skillshow = true;
      this.showtag1 = true;
      this.showtag = false;
    }

    editskills(){
     
      let obj1={
        skillName:this.skillForm.value.skillName
      }
      console.log("Edit data" ,obj1);
      console.log("Edit data" ,this.value);
        this.service.editskills(obj1,this.value).subscribe(resp => {
        console.log("edit response",resp);
        if(resp){
          this.getmethod();
        }
       
        this.response= resp;
        if(this.response.status === 'StatusSuccess'){
          this.toastr.successToastr('Skills Modified');
        } else {
          this.toastr.infoToastr( 'Duplicate Data  (or)  Empty Set');
        }
      
      
      });
      this.skillshow = false; 
    }
  



  getmethod(){
    this.service.skillsget(this.page,this.currentPage).subscribe(resp => {
      console.log("yooo due",resp);
      this.data = resp;
      this.datashow = this.data.result;
      console.log("totalRecords",this.data.totalRecords)
        this.total  =  this.data.totalRecords;
    });
  }

  skills(){
    this.skillshow= !this.skillshow;
    this.skillForm.reset();
    this.showtag = true;
    this.showtag1 = false;
  }


  


  addSkills(){
    console.log(this.skillForm.value);
    var skill={
      skillName: this.skillForm.value.skillName
    };

    console.log('skills', skill);
    this.service.addSkills(skill).subscribe(resp => {
      console.log(resp);
      if(resp){
        this.getmethod();
      }
      this.obj = resp;
      if (this.obj.status === 'StatusSuccess' ) {
        this.toastr.successToastr( 'Skills Added');
        this.skillForm.reset();
      }
    });
    this.skillshow = false;
  }



}
