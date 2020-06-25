import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { formatDate } from '@angular/common';
import { ToastrManager } from 'ng6-toastr-notifications';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';
// import { loadavg } from 'os';

@Component({
  selector: 'app-editclient',
  templateUrl: './editclient.component.html',
  styleUrls: ['./editclient.component.css']
})
export class EditclientComponent implements OnInit {

  // tslint:disable-next-line:variable-name
  baseUrl = `${environment.serviceUrl}`;
  // token = '8e9f2573-5ae9-4f8d-9181-8b14bb7e135c';


  isEdit: any;
  EditAddress: any;
  save: any;
  show: boolean = false;
  selectedAddress: any;
  pageSize: any=10;
  totalClientItems: any;
  setPageClient: any;
  clientId;
  clientList;
  clientListById;
  customers;
  CustomerData: any;
  delete: any;
  buttonName: any;
  toggle: any;
  bill;
  billingData: any;
  payment;
  paymentData: any;
  service;
  serviceData: any;
  Address;
  AddressData: any;
  services: any;
  customerResult: any;
  customerType: any;
  billingModel: any;
  serviceName: any;
  paymentType: any;
  typeOfAddress: any;
  bc: any;
  servicename: any;
  selectedServiceName: any;
  customertype: any;
  selectedCustometType: any;
  billingmodel: any;
  selectedBillingModel: any;
  paymenttype: any;
  selectedPaymentType: any;
  ser: any;
  addressDetails: any;
  clientDetails: any;
  typeAddress: any;
  type: any;
  marked: any;
  addressLine1: any;
  addressLine2: any;
  city: any;
  pincode: any;
  state: any;
  country: any;
  gst: any;
  gstpercentage: any;
  amdata: any;
  lead: any;
  leaddata: any;
  source: any;
  sourcedata: any;
  industry: any;
  industrydata: any;
  amName: any;
  leadName: any;
  files: any;
  filestringclient: string;
  filestring: string;
  accmanager: any;
  selectedaccmanager: any;
  selectedLead: any;
  selectedindustry: any;
  selectedSource: any;
  selectedUsername: any;
  theCheckbox: any;
  showdata:any=false

  updateid:any
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
    accManager: ['', Validators.required],
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
    gst: ['']
  });

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

  RegId: string;
  Domain: string;
  userToken: string;
  attachment: any;
  currentPageClient: number;
  userId;
  am;
  leadId: any;
  accountManagerId: any;
  userlistById: any;
  userListByAmId: any;
  userlistByIdLead: any;
  userListByLeadId: any;
  leadEmail: any;
  amEmail: any;
  deleteaddressList;
  usersAmDataList: any;
  usersAmData: any;
  LoginRole: string;
  currentPage: any=1;
  usersLeadData: any;
  usersLeadDataList: any;
  typeAdd: any;
  addressupadation: any;


  constructor(private route: ActivatedRoute, private formBuilder: FormBuilder, private router: Router,
    private toaster: ToastrManager, private clientService: SuperadminserviceService) {
  }

  ngOnInit() {
    this.RegId = sessionStorage.getItem('registrationId');
    this.Domain = sessionStorage.getItem('Domain');
    this.userToken = sessionStorage.getItem('Token');
    this.clientId = this.route.snapshot.paramMap.get('idd');
    this.userId = sessionStorage.getItem('userId');
    this.LoginRole = sessionStorage.getItem('role');
    console.log(this.clientId, 'idddddddddddddddddddd');
    this.getClientById();
    this.getCustomertype();
    this.getbillingmodel();
    this.getservice();
    this.getPayment();
    this.getAmList();
    this.getLead();
    this.getSource();
    this.getIndustry();
    this.getTypeAddress();
  this.updateaddress();
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
        console.log("apsana",this.AddressData);
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
    var reader = new FileReader();
    reader.onload = this.handleReaderLoad.bind(this);
    reader.readAsBinaryString(this.files[0]);
  }

  handleReaderLoad(readerEvt) {
    var binaryString = readerEvt.target.result;
    this.filestringclient = btoa(binaryString);
    console.log(this.filestringclient, "file string");
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
    console.log(this.filestring, "file string");
    // Converting binary string data.
  }

  UserRoleAmById() {
    this.clientService.getUserById(this.accountManagerId, this.RegId).subscribe(resp => {
      this.userlistById = resp;
      this.userListByAmId = this.userlistById.result;
      this.amEmail = this.userListByAmId.email;
      this.registerForm.controls.accManager.setValue(this.amEmail);
      this.registerForm.controls.cc.setValue(this.amEmail);
    });
  }

  UserRoleLeadById() {
    this.clientService.getUserById(this.leadId, this.RegId).subscribe(resp => {
      console.log(resp, 'kgb');

      this.userlistByIdLead = resp;
      this.userListByLeadId = this.userlistByIdLead.result;
      this.leadEmail = this.userListByLeadId.email;
      this.registerForm.controls.lead.setValue(this.leadEmail);
      this.registerForm.controls.bcc.setValue(this.leadEmail);

    });
  }

  getClientById() {
    this.clientService.getClientById(this.clientId, this.RegId).subscribe(res => {
      this.clientList = res;
      this.clientListById = this.clientList.result;
      console.log(this.clientListById, "clientByid----");
      this.addressDetails = this.clientListById.addressDetails;
      this.clientDetails = this.clientListById.client;
      this.leadId = this.clientListById.client.leadId;
      this.accountManagerId = this.clientListById.client.accountManagerId;
      this.attachment = this.clientListById.client.others;
      console.log("resume", JSON.stringify(this.attachment));
      this.services = this.clientDetails.services;
      this.clientListById.amDetails;

      for (let i = 0; i < this.services.length; i++) {
        this.ser = this.services[i].serviceName
      }
      this.registerForm.controls.clientName.setValue(this.clientDetails.clientName);
      this.registerForm.controls.customerType.setValue(this.clientDetails.customerType.customerType);
      this.registerForm.controls.email.setValue(this.clientDetails.email);
      this.registerForm.controls.phone.setValue(this.clientDetails.phone);
      this.registerForm.controls.fax.setValue(this.clientDetails.fax);
      this.registerForm.controls.website.setValue(this.clientDetails.website);
      this.registerForm.controls.tdspercentage.setValue(this.clientDetails.tdspercentage);
      this.registerForm.controls.billingModel.setValue(this.clientDetails.billingModel.billingModel);
      this.registerForm.controls.services.setValue(this.ser);
      this.registerForm.controls.startDate.setValue(formatDate(this.clientDetails.startDate, 'yyyy-MM-dd', 'en'));
      this.registerForm.controls.endDate.setValue(formatDate(this.clientDetails.endDate, 'yyyy-MM-dd', 'en'));
      this.registerForm.controls.leavesAllowed.setValue(this.clientDetails.leavesAllowed);
      this.registerForm.controls.paymentTerms.setValue(this.clientDetails.paymentTerms.paymentType);
      this.registerForm.controls.industry.setValue(this.clientDetails.industry.industryType);
      this.registerForm.controls.source.setValue(this.clientDetails.source.sourceName);
      this.registerForm.controls.about.setValue(this.clientDetails.about);
      this.registerForm.controls.addressLane1.setValue(this.addressLine1);
      this.registerForm.controls.addressLane2.setValue(this.addressLine2);
      this.registerForm.controls.city.setValue(this.city);
      this.registerForm.controls.pincode.setValue(this.pincode);
      this.registerForm.controls.state.setValue(this.state);
      this.registerForm.controls.country.setValue(this.country);
      this.registerForm.controls.gst.setValue(this.gst);
      this.registerForm.controls.gstpercentage.setValue(this.gstpercentage);
      this.registerForm.controls.typeOfAddress.setValue(this.type);
      this.UserRoleAmById();
      this.UserRoleLeadById();
    });
  }

  submit() {
    // console.log(this.registerForm.value, "registorm data")
    if (this.registerForm.invalid) {
      console.log(this.registerForm.value, 'registorm data');
      this.toaster.errorToastr('Please fill the mandatory fields', 'Notification');
    }
    else{
    var add = this.bc;
    if (add == undefined) {
      this.typeOfAddress = this.addressDetails.typeOfAddress
    } else {
      for (var i = 0; i < this.AddressData.length; i++) {
        if (this.AddressData[i].typeOfAddress == add) {
          this.typeOfAddress = this.AddressData[i];
        }
      }
    }

    var ser = this.selectedServiceName;
    for (var i = 0; i < this.serviceData.length; i++) {
      if (this.serviceData[i].serviceName == ser) {
        this.servicename = this.serviceData[i];
      }
    }

    var cus = this.selectedCustometType;
    for (var i = 0; i < this.CustomerData.length; i++) {
      if (this.CustomerData[i].customerType == cus) {
        this.customertype = this.CustomerData[i];
      }
    }

    var bill = this.selectedBillingModel;
    for (var i = 0; i < this.billingData.length; i++) {
      if (this.billingData[i].billingModel == bill) {
        this.billingmodel = this.billingData[i];
      }
    }

    var pay = this.selectedPaymentType;
    if (pay == undefined) {
      this.paymenttype = this.clientListById.paymentTerms
    }
    else {
      for (var i = 0; i < this.paymentData.length; i++) {
        if (this.paymentData[i].paymentType == pay) {
          this.paymenttype = this.paymentData[i];
        }
      }
    }

    var acmanager = this.selectedaccmanager;
    for (var i = 0; i < this.usersAmDataList.length; i++) {
      if (this.usersAmDataList[i].name == acmanager) {
        this.accmanager = this.usersAmDataList[i].id;
        break;
      }
    }
    var leading = this.selectedLead;
    for (var i = 0; i < this.usersLeadDataList.length; i++) {
      if (this.usersLeadDataList[i].email == leading) {
        this.lead = this.usersLeadDataList[i].id;
        break;
      }
    }

    var indus = this.selectedindustry;
    for (var i = 0; i < this.industrydata.length; i++) {
      if (this.industrydata[i].industryType == indus) {
        this.industry = this.industrydata[i];
        break;
      }
    }

    var sou = this.selectedSource;
    for (var i = 0; i < this.sourcedata.length; i++) {
      if (this.sourcedata[i].sourceName == sou) {
        this.source = this.sourcedata[i];
        break;
      }
    }

    let data = {
      client: {
        id: this.clientId,
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
        accountManagerId: this.accountManagerId,
        leadId: this.leadId,
        industry: this.industry,
        source: this.source,
        about: this.registerForm.value.about,
        lastUpdatedDate: '',
        registrationId: this.RegId
      },
       addressDetails: this.addressDetails
    }
    console.log('edit post data.....', data);
    this.clientService.editClient(data).subscribe(resp => {

      if (resp['status'] == 'StatusSuccess') {
        this.toaster.successToastr('client edited successfully');
        this.router.navigate(['/superadmin/client']);

      } else if (resp['status'] == 'DuplicateRecord') {
        this.toaster.warningToastr('DuplicateRecord.', 'Alert!');
      }
      else{
        this.toaster.warningToastr('Unable to update client.', 'Alert!');
      }
    });
  }
  }

  get f() {
    return this.registerForm.controls;
  }

  toggleVisibility(e) {
    this.marked = e.target.checked;
  }


  toggleT() {
    this.show = !this.show;
    this.showdata=false
  }

  deleteaddress(id) {
    this.clientService.deleteaddress(id).subscribe(res => {

      this.deleteaddressList = res;
      if (this.deleteaddressList.status == "StatusSuccess") {
        this.toaster.successToastr('Address deleted successfully');
        this.getClientById();
      }
      else {
        this.toaster.warningToastr('error deleting the address', 'Warning!');
      }
    })
  }


  addresstypeselected() {
    console.log("address", this.AddressData);
    var selectedData = this.AddressData.find(d => d.typeOfAddress == this.selectedAddress)
    this.profileForm.controls.typeOfAddress.setValue(selectedData.typeOfAddress)
  }

  saveaddress() {
    if (this.profileForm.invalid) {
      console.log(this.profileForm.value, 'registorm data');
      this.toaster.errorToastr('Please mandatory fields', 'Notification');
    }
    else {
    var response
    var selectedData = this.AddressData.find(d => d.typeOfAddress == this.profileForm.value.typeOfAddress)
    let addressdata = {
      cid:this.clientId,
      date: '',
      typeOfAddress: selectedData,
      addressLane1: this.profileForm.value.addressLane1,
      addressLane2: this.profileForm.value.addressLane2,
      city: this.profileForm.value.city,
      country: this.profileForm.value.country,
      pincode: this.profileForm.value.pincode,
      state: this.profileForm.value.state,
      gst: this.profileForm.value.gst,
      gstpercentage: this.profileForm.value.gstpercentage,
      isSez: this.profileForm.value.isSez,
    }  
    this.clientService.saveaddress(addressdata).subscribe(resp => {
      response = resp;
      this.addressDetails.push(response.result);
      console.log(this.addressDetails,"address");      
    });
  }
  }

  edit(p) {
    console.log(p);
    this.show=true;
    this.showdata=true;
    this.updateid=p.id;
    this.typeAdd=p.typeOfAddress.typeOfAddress;
    console.log("(p.typeOfAddress.typeOfAddress",p.typeOfAddress.typeOfAddress);   
    this.profileForm.controls.addressLane1.setValue(p.addressLane1);
    this.profileForm.controls.addressLane2.setValue(p.addressLane2);
    this.profileForm.controls.city.setValue(p.city);
    this.profileForm.controls.pincode.setValue(p.pincode);
    this.profileForm.controls.state.setValue(p.state);
    this.profileForm.controls.country.setValue(p.country);
    this.profileForm.controls.gst.setValue(p.gst);
    this.profileForm.controls.gstpercentage.setValue(p.gstpercentage);
    this.profileForm.controls.typeOfAddress.setValue(this.typeAdd);    
  }

  updateaddress(){
  console.log(this.profileForm,this.updateid);
  var selectedData = this.AddressData.find(d => d.typeOfAddress == this.profileForm.value.typeOfAddress)
  let addressupadation={
    cid: this.clientId,
    date:'',
    typeOfAddress: selectedData,
    addressLane1: this.profileForm.value.addressLane1,
    addressLane2: this.profileForm.value.addressLane2,
    city: this.profileForm.value.city,
    pincode: this.profileForm.value.pincode,
    state: this.profileForm.value.state,
    country: this.profileForm.value.country,
    gst: this.profileForm.value.gst,
    gstpercentage: this.profileForm.value.gstpercentage,
    isSez: this.profileForm.value.isSez,
}
  console.log("updation",addressupadation)
  this.clientService.updateaddress(this.updateid,addressupadation).subscribe(resp => {
  if (resp['status'] == 'StatusSuccess') {
    this.toaster.successToastr('address updated successfully');
    this.getClientById();
    // this.router.navigate(['/superadmin/editclient']);

  } else if (resp['status'] == 'DuplicateRecord') {
    this.toaster.warningToastr('DuplicateRecord.', 'Alert!');
  }
});
}

  cancelReq() {
    this.profileForm.reset()
    this.profileForm.clearAsyncValidators()
    this.show=false
  }


}