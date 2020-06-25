import { Component, OnInit } from '@angular/core';
import { ServiceService } from 'src/app/services/service.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-plans',
  templateUrl: './plans.component.html',
  styleUrls: ['./plans.component.css']
})
export class PlansComponent implements OnInit {
  result;
  plans: any;
  plansId: any;
  planObj:any={
    'id':''
  }
  currentPage=1;
  pageSize= 10;

  setPage(pageNo: number): void {
    this.currentPage = pageNo;
    this.getList();
  }
  message: string;

  constructor(public ser:ServiceService,private activatedRoute :ActivatedRoute,private route:Router) {
     this.plansId =this.activatedRoute.snapshot.paramMap.get("id");
   }

  ngOnInit() {
    this.getList();
    this.ser.planID.subscribe(planList => {
      planList=this.plans;
      console.log(planList);
    });
  }
  getList(){
    this.ser.getPlans(this.currentPage,this.pageSize).subscribe((res:any)=>{
      console.log(res);


      this.result =res;

      this.plans=this.result.result;
      this.plansId=this.plans[0].planId
      this.plans.forEach(plansList => {
        
        this.plansId = plansList['planId'];
        this.planObj= {
          'id':this.plansId
        }
        console.log(JSON.stringify(this.planObj))
       
      });
     
      });
     
     
  }
 
  register(planObj){
    
    this.ser.editplan(planObj);
    this.route.navigateByUrl("/register/signup");
    console.log(this.route)
  }

}
 