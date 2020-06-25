import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { ToastrManager } from 'ng6-toastr-notifications';
import { Router, ActivatedRoute } from '@angular/router';
import { environment } from 'src/environments/environment';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';
import * as CryptoJS from 'crypto-js';


// import {NgxPaginationModule} from 'ngx-pagination';

@Component({
  selector: 'app-addclient',
  templateUrl: './addclient.component.html',
  styleUrls: ['./addclient.component.css']
})
export class AddclientComponent implements OnInit {
  baseUrl = `${environment.serviceUrl}`;
  Type = null;
  isEdit: any;
  pageSize: any = 10;
  totalClientItems: any;
  setPageClient: any;
  selectedAddress: any;
  token: string;
  optionsSelect;
  bill;
  array;
  dropdownList;
  selectedItems: any[];
  pays;
  customerResult: any;
  customerType: any;
  billingModel: any;
  serviceName: any;
  paymentType: any;
  typeOfAddress: any[];
  customers;
  CustomerData: any;
  billingData: any;
  payment;
  paymentData: any;
  service;
  serviceData: any;
  Address;
  AddressData: any;
  selectedPaymentType: any;
  selectedBillingModel: any;
  selectedCustometType: any;
  selectedServiceName: any;
  bc: any;
  servicename: any;
  customertype: any;
  billingmodel: any;
  paymenttype: any;
  marked: any;
  AmList: any;
  amId: any;
  amdata;
  am: any;
  lead;
  leaddata: any;
  source;
  sourcedata: any;
  selectedaccmanager: any;
  accmanager: any;
  accData: any;
  selectedlead: any;
  leadData: any;
  leading: any;
  selectedindustry: any;
  industrydata: any;
  industry: any;
  selectedsource: any;
  selectedLead: any;
  selectedSource: any;
  file: any;
  files: any;
  filestring: string;
  filestringclient: string;
  RegId: string;
  Domain: string;
  userToken: string;


  addressArray: any = []
  addressArrayid: any
  theCheckbox: any;

  registerForm = this.formBuilder.group({
    createdBy: [''],
    clientName: ['', Validators.required],
    customerType: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    phone: ['', [Validators.required, Validators.pattern('^(\([0-9]{3}\) |[6-9]{1})[0-9]{9}$')]],
    tdspercentage: ['', Validators.required],
    billingModel: ['', Validators.required],
    services: ['', Validators.required],
    startDate: ['', Validators.required],
    leavesAllowed: ['', Validators.required],
    paymentTerms: ['', Validators.required],
    accmanager: ['', Validators.required],
    lead: ['', Validators.required],
    source: ['', Validators.required],
    industry: ['', Validators.required],
    endDate: [''],
    city: [''],
    typeOfAddress: [''],
    website: [''],
    fax: [''],
    about: [''],
    addressLane1: [''],
    addressLane2: [''],
    state: [''],
    country: [''],
    pincode: ['', [Validators.pattern('^[5]{1}[0-9]{5}$')]],
    gstpercentage: [''],
    gst: [''],
    // isSez: [''],
    clientContract: [''],
    others: ['']
  });
  fileTypeWithName: any;

  encrpt: any;
  decrpt: string;
  value: any;
  obj1 = [];
  // tslint:disable-next-line:no-inferrable-types
  divshow: boolean = false;
  obj5: any[];
  ClientNameClickid: any;
  userId: string;
  currentPage: number = 1;
  currentPageClient: number;
  currentPageContact: number;
  role: string;
  flag: boolean;
  usersAmData: any;
  usersAmDataList: any;
  usersLeadData: any;
  usersLeadDataList: any;
  LoginRole: string;
  leadID: any;
  accmanagerID: any;

  constructor(private activeRoute: ActivatedRoute, private formBuilder: FormBuilder,
    private toaster: ToastrManager, private router: Router, private clientService: SuperadminserviceService) {
    localStorage.setItem('count', '0');
  }
  // setPage(pagNo: number): void {
  //   this.currentPage = pagNo;
  //   // this.getClientList();
  // }



  ngOnInit() {
    this.RegId = sessionStorage.getItem('registrationId');
    this.Domain = sessionStorage.getItem('Domain');
    this.userToken = sessionStorage.getItem('Token');
    this.userId = sessionStorage.getItem('userId');
    this.LoginRole = sessionStorage.getItem('role');
    

    // alert(this.userToken);
    console.log(this.registerForm);
    this.getCustomertype();
    this.getbillingmodel();
    this.getPayment();
    this.getTypeAddress();
    this.getservice();
    this.getAmList();
    this.getLead();
    this.getSource();
    this.getIndustry();

  }

  setPageCustomerType(pages: number): void {
    this.currentPageClient = pages;
    this.getCustomertype();
    this.getbillingmodel();
    this.getPayment();
    this.getservice();
    // this.getAmList();
    // this.getLead();
    this.getSource();
    this.getIndustry();
  }

  getCustomertype() {
    this.clientService.getCustomertype().subscribe(res => {
      this.customers = res;
      this.CustomerData = this.customers.result;
      console.log(this.CustomerData);
    });
  }

  getbillingmodel() {
    this.clientService.getbillingmodel().subscribe(res => {
      this.bill = res;
      this.billingData = this.bill.result;
      console.log(this.billingData);
    });
  }

  getPayment() {
    this.clientService.getPayment().subscribe(res => {
      this.payment = res,
        this.paymentData = this.payment.result,
        console.log(this.paymentData);
    });
  }

  getservice() {
    this.clientService.getservices().subscribe(res => {
      this.service = res,
        this.serviceData = this.service.result,
        console.log(this.serviceData);
    });
  }

  getTypeAddress() {
    this.clientService.getTypeAddress().subscribe(res => {
      this.Address = res,
        this.AddressData = this.Address.result,
        console.log(this.AddressData);
    });
  }

  getAmList() {
    let postdata = {
      pageNo: this.currentPage,
      pageSize: this.pageSize,
      flag: true,
      registrationId: this.RegId,
      role: this.LoginRole,
      roleSearchParam:'am'
    }
    this.clientService.getuserList(postdata).subscribe(res => {
      console.log(res, 'amlist');
      this.am = res;
      this.usersAmData = this.am.result;
      this.usersAmDataList =  this.usersAmData.list;
    });
  }

  getLead() {
    let data = {
      pageNo: this.currentPage,
      pageSize: this.pageSize,
      flag: true,
      registrationId: this.RegId,
      role: this.LoginRole,
      roleSearchParam:'lead'
    }
    this.clientService.getuserList(data).subscribe(res => {
      console.log(res, 'leadlist');
      this.lead = res;
      this.usersLeadData = this.lead.result;
      this.usersLeadDataList =  this.usersLeadData.list;
    });
  }

  getSource() {
    this.clientService.getSource().subscribe(res => {
      this.source = res,
        this.sourcedata = this.source.result,
        console.log(this.sourcedata);
    });
  }

  getIndustry() {
    this.clientService.getIndustry().subscribe(res => {
      this.industry = res,
        this.industrydata = this.industry.result,
        console.log(this.industrydata);
    });
  }



  getclientContract(client) {
    this.files = client.target.files;
    let reader = new FileReader();
    reader.onload = this.handleReaderLoad.bind(this);
    reader.readAsBinaryString(this.files[0]);
  }

  handleReaderLoad(readerEvt) {
    let binaryString = readerEvt.target.result;
    this.filestringclient = btoa(binaryString);
    console.log(this.filestringclient, 'file string');
    // Converting binary string data.
  }

  getFiles(event) {
    this.files = event.target.files;
    var reader = new FileReader();
    reader.onload = this.handleReaderLoaded.bind(this);
    reader.readAsBinaryString(this.files[0]);
  }

  handleReaderLoaded(readerEvt) {
    var binaryString = readerEvt.target.result;
    this.filestring = btoa(binaryString);
    console.log(this.filestring, 'file string');
    // Converting binary string data.
  }

  submit() {
    if (this.registerForm.invalid) {
      console.log(this.registerForm.value, 'registorm data');
      this.toaster.errorToastr('Please fill the mandatory fields', 'Notification');
    }
    else {

      var add = this.bc;
      for (var i = 0; i < this.AddressData.length; i++) {
        if (this.AddressData[i].typeOfAddress === add) {
          this.typeOfAddress = this.AddressData[i];
          break;
        }
      }

      var ser = this.selectedServiceName;
      for (var i = 0; i < this.serviceData.length; i++) {
        if (this.serviceData[i].serviceName === ser) {
          this.servicename = this.serviceData[i];
          break;
        }
      }

      var cus = this.selectedCustometType;
      for (var i = 0; i < this.CustomerData.length; i++) {
        if (this.CustomerData[i].customerType === cus) {
          this.customertype = this.CustomerData[i];
          break;
        }
      }

      var bill = this.selectedBillingModel;
      for (var i = 0; i < this.billingData.length; i++) {
        if (this.billingData[i].billingModel === bill) {
          this.billingmodel = this.billingData[i];
          break;
        }
      }

      var pay = this.selectedPaymentType;
      for (var i = 0; i < this.paymentData.length; i++) {
        if (this.paymentData[i].paymentType === pay) {
          this.paymenttype = this.paymentData[i];
          break;
        }
      }

    var acmanager = this.selectedaccmanager;
    for (var i = 0; i < this.usersAmDataList.length; i++) {
      if (this.usersAmDataList[i].name == acmanager) {
        this.accmanagerID = this.usersAmDataList[i].id;
        break;
      }
    }
    var leading = this.selectedLead;
    for (var i = 0; i < this.usersLeadDataList.length; i++) {
      if (this.usersLeadDataList[i].name == leading) {
        this.leadID= this.usersLeadDataList[i].id;
        break;
      }
    }

      var indus = this.selectedindustry;
      for (var i = 0; i < this.industrydata.length; i++) {
        if (this.industrydata[i].industryType === indus) {
          this.industry = this.industrydata[i];
          break;
        }
      }

      var sou = this.selectedSource;
      for (var i = 0; i < this.sourcedata.length; i++) {
        if (this.sourcedata[i].sourceName === sou) {
          this.source = this.sourcedata[i];
          break;
        }
      }

      let data = {
        client: {
          createdBy: this.userId,
          clientName: this.registerForm.value.clientName,
          customerType: this.customertype,
          email: this.registerForm.value.email,
          phone: this.registerForm.value.phone,
          fax: this.registerForm.value.fax,
          website: this.registerForm.value.website,
          tdspercentage: this.registerForm.value.tdspercentage,

          billingModel: this.billingmodel,
          services: [this.servicename],
          startDate: Date.parse(this.registerForm.value.startDate),
          endDate: Date.parse(this.registerForm.value.endDate),
          paymentTerms: this.paymenttype,
          leavesAllowed: this.registerForm.value.leavesAllowed,
          contractFile: this.filestringclient,
          others: this.filestring,

          accountManagerId: this.accmanagerID,
          leadId: this.leadID,
          industry: this.industry,
          source: this.source,
          about: this.registerForm.value.about,
          lastUpdatedDate: '',
          registrationId: this.RegId
        },
        addressDetails: this.addressArray
      };

      console.log('dfsd', data);
      debugger;

      this.clientService.saveClient(data).subscribe(resp => {
        // tslint:disable-next-line:no-string-literal
        if (resp['status'] === 'StatusSuccess') {
          this.toaster.successToastr('client added successfully');
          this.router.navigate(['/superadmin/client']);
        }
        // tslint:disable-next-line:no-string-literal
        if (resp['status'] === 'Customer already exist !!') {
          this.toaster.warningToastr('Customer already exist !!', 'Warning!');
        }
        // else {
        //   this.toaster.warningToastr('Please Change the clientname.', 'Alert!');
        //   alert(resp['status']);
        // }
      });
    }
  }

  // tslint:disable-next-line:member-ordering
  encryptSecretKey = 'OJAS-Rpo';

  profileForm = new FormGroup({
    typeOfAddress: new FormControl('',Validators.required),
    addressLane1: new FormControl('',Validators.required),
    addressLane2: new FormControl('',Validators.required),
    state: new FormControl('',Validators.required),
    pincode: new FormControl('',Validators.required),
    city: new FormControl('',Validators.required),
    country: new FormControl('',Validators.required),
    gst: new FormControl(''),
    gstpercentage: new FormControl(''),
  });


  count: any;

  onSubmit() {
    if (this.profileForm.invalid) {
      console.log(this.profileForm.value, 'registorm data');
      this.toaster.errorToastr('Please fill the mandatory fields', 'Notification');
    }
    else {
    var data = this.AddressData.find(d => d.typeOfAddress == this.profileForm.value.typeOfAddress)
    this.profileForm.controls.typeOfAddress.setValue(data);
    this.addressArray.push(this.profileForm.value)
    this.divshow = true
    this.profileForm.reset()
    this.selectedAddress = null;
  }
}
get f1() {
  return this.profileForm.controls;
}


  encryptData(profileForm) {
    try {
      return CryptoJS.AES.encrypt(JSON.stringify(profileForm), this.encryptSecretKey).toString();
    } catch (e) {
      console.log(e);
    }

  }

  decryptData(obj) {
    try {
      const bytes = CryptoJS.AES.decrypt(obj, this.encryptSecretKey);
      if (bytes.toString()) {
        return JSON.parse(bytes.toString(CryptoJS.enc.Utf8));
      }
      return obj;
    } catch (e) {
      console.log(e);
    }
  }

  decryptData1(decrpt) {
    try {
      const bytes = CryptoJS.AES.decrypt(decrpt, this.encryptSecretKey);
      if (bytes.toString()) {
        return JSON.parse(bytes.toString(CryptoJS.enc.Utf8));
      }
      return decrpt;
    } catch (e) {
      console.log(e);
    }
  }

  view() {
    this.divshow = true;
  }

  get f() {
    return this.registerForm.controls;
  }

  toggleVisibility(e) {
    this.marked = e.target.checked;
  }


  addresstypeselected() {
    console.log("address", this.AddressData);
    var selectedData = this.AddressData.find(d => d.typeOfAddress == this.selectedAddress)
    this.profileForm.controls.typeOfAddress.setValue(selectedData.typeOfAddress)
  }


  delete(e) {
    this.addressArray.splice(e, 1);
  }

  editAddress(e, d) {
    console.log(e);
    this.addressArrayid = d
    this.profileForm.controls.typeOfAddress.setValue(e.typeOfAddress.typeOfAddress)
    this.profileForm.controls.addressLane1.setValue(e.addressLane1)
    this.profileForm.controls.addressLane2.setValue(e.addressLane2)
    this.profileForm.controls.state.setValue(e.state)
    this.profileForm.controls.city.setValue(e.city)
    this.profileForm.controls.country.setValue(e.country)
    this.profileForm.controls.gst.setValue(e.gst)
    this.profileForm.controls.gstpercentage.setValue(e.gstpercentage)
    this.profileForm.controls.pincode.setValue(e.pincode)
  }
  
  cancelReq() {
    this.profileForm.reset()
    this.profileForm.clearAsyncValidators()
  }

  updateAddress() {
    console.log("array", this.addressArray[this.addressArrayid]);
    var data = this.AddressData.find(d => d.typeOfAddress == this.profileForm.value.typeOfAddress)
    this.addressArray[this.addressArrayid].typeOfAddress = data
    this.addressArray[this.addressArrayid].addressLane1 = this.profileForm.value.addressLane1
    this.addressArray[this.addressArrayid].addressLane2 = this.profileForm.value.addressLane2
    this.addressArray[this.addressArrayid].state = this.profileForm.value.state
    this.addressArray[this.addressArrayid].city = this.profileForm.value.city
    this.addressArray[this.addressArrayid].country = this.profileForm.value.country
    this.addressArray[this.addressArrayid].gst = this.profileForm.value.gst
    this.addressArray[this.addressArrayid].gstpercentage = this.profileForm.value.gstpercentage
    this.addressArray[this.addressArrayid].pincode = this.profileForm.value.pincode
    console.log("hello", this.addressArray);
  }
}
