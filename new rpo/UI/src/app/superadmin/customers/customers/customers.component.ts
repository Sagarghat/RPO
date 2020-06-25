import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ToastrManager } from 'ng6-toastr-notifications';
import { FormBuilder, Validators } from '@angular/forms';
import { SuperadminserviceService } from '../../superadminservice.service';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {

  showtag: boolean = false;
  showtag1: boolean = false;
  customersshow: boolean = false;



  customersForm = this.formBuilder.group({
    customerType: ['', Validators.required],
    amount: ['', Validators.required],
  });


  data: any;
  datashow: any;
  total: any;

  obj: any;
  value: any;
  dataID: any;
  varaible: any;
  varaible1: any;
  response: any;


  constructor(public toastr: ToastrManager, public ser: SuperadminserviceService,
    private formBuilder: FormBuilder) { }




  pages = 3;
  currentPage = 1;

  setPages(pageNo: number): void {
    this.currentPage = pageNo;
    this.getmethod();
  }

  ngOnInit() {

    this.getmethod();
  }


  getId(id) {
    console.log("id", id);
    this.value = id;
    this.ser.customertypegetId(id).subscribe(resp => {
      this.dataID = resp;
      console.log("data", resp);
      console.log("dataID", this.dataID.result.customerType);
      this.varaible = this.dataID.result.customerType;
      this.varaible1 = this.dataID.result.amount;
      console.log(this.varaible, "fffffffffff");

      this.customersForm.controls.customerType.setValue(this.varaible);
      this.customersForm.controls.amount.setValue(this.varaible1);

    });
    this.customersshow = true;
    this.showtag1 = true;
    this.showtag = false;
  }

  editcustomerType() {

    let obj1 = {
      customerType: this.customersForm.value.customerType,
      amount: this.customersForm.value.amount
    }
    console.log("Edit data", obj1);
    console.log("Edit data", this.value);
    this.ser.editcustomerType(obj1, this.value).subscribe(resp => {
      console.log("edit response", resp);
      if (resp) {
        this.getmethod();
      }

      this.response = resp;
      if (this.response.status === 'StatusSuccess') {
        this.toastr.successToastr('Customer Type  Modified');
      } else {
        this.toastr.infoToastr('Duplicate Data  (or)   Empty Set');
      }


      //mention duplicate

    });
    this.customersshow = false;
  }





  getmethod() {
    this.ser.customertypeget(this.pages, this.currentPage).subscribe(resp => {
      console.log("yooo due", resp);
      this.data = resp;
      this.datashow = this.data.result;

      console.log("totalRecords", this.data.totalRecords)
      this.total = this.data.totalRecords
    });
  }



  customers() {
    this.customersshow = !this.customersshow;
    this.customersForm.reset();
    this.showtag = true;
    this.showtag1 = false;
  }


  addcustomers() {
    console.log(this.customersForm.value);
    var cust = {
      customerType: this.customersForm.value.customerType,
      amount: this.customersForm.value.amount
    }

    console.log("custtype", cust);
    this.ser.addcustomers(cust).subscribe(resp => {
      console.log(resp);
      if (resp) {
        this.getmethod();
      }
      this.obj = resp;
      if (this.obj.status === 'StatusSuccess') {
        this.toastr.successToastr('Customers Added');
        this.customersForm.reset();
      } else {
        this.toastr.warningToastr('DuplicateRecord Or Add Again');
      }
    });

    this.customersshow = false;

  }



}
