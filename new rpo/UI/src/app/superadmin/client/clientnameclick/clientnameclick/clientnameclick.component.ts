import { Component, OnInit, HostListener } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ToastrManager } from 'ng6-toastr-notifications';
import { Router, ActivatedRoute } from '@angular/router';
import { ServiceService } from 'src/app/services/service.service';
import { formatDate } from '@angular/common';
import { environment } from 'src/environments/environment';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';

@Component({
  selector: 'app-clientnameclick',
  templateUrl: './clientnameclick.component.html',
  styleUrls: ['./clientnameclick.component.css']
})
export class ClientnameclickComponent implements OnInit {



  baseUrl = `${environment.serviceUrl}`;
  // token = '8e9f2573-5ae9-4f8d-9181-8b14bb7e135c';
  clientName: any;
  fax: any;
  startDate: any;
  leavesAllowed: any;
  accManager: any;
  paymentTerms: any;
  website: any;
  endDate: any;
  about: any;

  clientId;
  clientList;
  clientListById;
  customers;
  CustomerData: any;
  save: any;
  jobnameClick: any;
  isHotComment: any;
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
  id: string;
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

  registerForm = this.formBuilder.group({
    clientName: ['', Validators.required],
    customerType: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    phone: ['', [Validators.required, Validators.pattern('^(\([0-9]{3}\) |[6-9]{1})[0-9]{9}$')]],
    fax: ['', Validators.required],
    website: ['', Validators.required],
    tdspercentage: ['', Validators.required],
    billingModel: ['', Validators.required],
    services: ['', Validators.required],
    startDate: ['', Validators.required],
    endDate: ['', Validators.required],
    leavesAllowed: ['', Validators.required],
    paymentTerms: ['', Validators.required],
    accManager: ['', Validators.required],
    lead: ['', Validators.required],
    industry: ['', Validators.required],
    source: ['', Validators.required],
    about: ['', Validators.required],
    addressLane1: ['', Validators.required],
    addressLane2: ['', Validators.required],
    city: ['', Validators.required],
    state: ['', Validators.required],
    typeOfAddress: ['', Validators.required],
    country: ['', Validators.required],
    pincode: ['', Validators.required],
    gstpercentage: ['', Validators.required],
    gst: ['', Validators.required],
    isSez: [''],
  });
  RegId: string;
  Domain: string;
  userToken: string;
  text: String = "HideDetails"
  status1: boolean = true;
  currentPage: any = 1;
  profileData: any;
  profileDetails: any;
  pageSize: any;
  contactDetails: any;
  totalItems: any;
  status: boolean;
  interviewdetails: any;
  interviewList: any;
  registrationId: string;
  Contact;
  getnoteDeletedata: any;
  getdetails: any;
  noteId: any;
  getnotedata;

  noteForm = this.formBuilder.group({
    note: ['', Validators.required],
    noteSelect: ['', Validators.required]
  });
  status2: boolean = true;
  leadname: any;


  constructor(private http: HttpClient, private route: ActivatedRoute,
    private formBuilder: FormBuilder, private router: Router, private toaster: ToastrManager,
    private clientService: SuperadminserviceService) {
  }
  setPage(pageNo: number): void {
    this.currentPage = pageNo;
    this.profileGetData();
    this.getInterviewList();
    this.getContactsList();
  }
  ngOnInit() {
    this.RegId = sessionStorage.getItem('registrationId');
    this.Domain = sessionStorage.getItem('Domain');
    this.userToken = sessionStorage.getItem('Token');
    this.clientId = this.route.snapshot.paramMap.get('idd');
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
    this.profileGetData();
    this.getInterviewList();
    this.getContactsList();
    this.getnoteData();
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
    this.clientService.getAmList(this.RegId).subscribe(res => {
      this.amdata = res;
      for (let i = 0; i <= this.amdata.length; i++) {
        if (this.amdata[i].id === this.clientDetails.accountManagerId) {
          this.registerForm.controls.accManager.setValue(this.amdata[i].name);

        }
      }
      console.log(res, "AmList");
    });

  }

  getLead() {
    this.clientService.getLead(this.RegId).subscribe(res => {
      this.lead = res,
        this.leaddata = this.lead.result;
      for (let i = 0; i <= this.leaddata.length; i++) {
        if (this.leaddata[i].id === this.clientDetails.leadId) {
          this.leadname = this.leaddata[i].fullNameOfUser;
          this.registerForm.controls.lead.setValue(this.leaddata[i].fullNameOfUser);

        }
      }
      console.log(this.leaddata);
    });
  }

  getSource() {
    this.clientService.getSource().subscribe(res => {
      this.source = res,
        this.sourcedata = this.source.result;
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

  getClientById() {
    this.clientService.getClientById(this.clientId, this.RegId).subscribe(res => {

      this.clientList = res;
      this.clientListById = this.clientList.result;
      console.log(this.clientListById, "clientByid----");

      this.addressDetails = this.clientListById.addressDetails;
      this.clientDetails = this.clientListById.client;
      this.services = this.clientDetails.services;
      this.clientListById.amDetails;

      for (let i = 0; i < this.services.length; i++) {
        this.ser = this.services[i].serviceName
      }

      this.typeAddress = this.addressDetails.typeOfAddress;
      for (let j = 0; j <= this.addressDetails.length; j++) {
        if (this.addressDetails.length != 0) {
          this.city = this.addressDetails[j].city;
          this.pincode = this.addressDetails[j].pincode;
          this.state = this.addressDetails[j].state;
          this.addressLine2 = this.addressDetails[j].addressLane2;
          this.country = this.addressDetails[j].country;
          this.gstpercentage = this.addressDetails[j].gstpercentage;
          this.gst = this.addressDetails[j].gst;
          this.addressLine1 = this.addressDetails[j].addressLane1;
          this.addressLine2 = this.addressDetails[j].addressLane2;
          this.typeAddress = this.addressDetails[j].typeOfAddress;
          this.type = this.typeAddress.typeOfAddress;
          break;
        }
      }

      this.registerForm.controls.clientName.setValue(this.clientDetails.clientName);
      this.registerForm.controls.customerType.setValue(this.clientDetails.customerType.customerType);
      this.registerForm.controls.email.setValue(this.clientDetails.email);
      this.registerForm.controls.phone.setValue(this.clientDetails.phone);
      this.registerForm.controls.fax.setValue(this.clientDetails.fax);
      this.registerForm.controls.website.setValue(this.clientDetails.website);
      this.registerForm.controls.tdspercentage.setValue(this.clientDetails.tdspercentage);
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
      this.registerForm.controls.typeOfAddress.setValue(this.typeOfAddress);
      this.registerForm.controls.services.setValue(this.ser);

    });

  }

  back()
  {
    this.router.navigate(['superadmin/client']);
  }
  hidedd() {
    this.text = (this.text === "ShowDetails") ? "HideDetails" : "ShowDetails"
    if (this.text === "ShowDetails") {
      this.status1 = false;
    }
    else {
      this.status1 = true;
    }
  }
  isShow: boolean;
  topPosToStartShowing = 100;
  @HostListener('window:scroll')
  checkScroll() {
    const scrollPosition = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
    // console.log('[scroll]', scrollPosition);
    if (scrollPosition >= this.topPosToStartShowing) {
      this.isShow = true;
    } else {
      this.isShow = false;
    }
  }
  gotoTop() {
    window.scroll({
      top: 0,
      left: 0,
      behavior: 'smooth'
    });
  }
  client() {
    this.router.navigate(['superadmin/client/addclient'], { queryParams: { val: this.clientId } });
  }
  jobopening() {
    this.router.navigate(['superadmin/jobopening/addjobopening'], { queryParams: { val: this.clientId } });
  }
  interview() {
    this.router.navigate(['superadmin/interview/addinterview'], { queryParams: { val: this.clientId } });
  }
  contact() {
    this.router.navigate(['superadmin/contact/addcontact'], { queryParams: { val: this.clientId } })
  }


  profileGetData() {
    var profileData = {
      pageNum: this.currentPage,
      pageSize: 4,
      registrationId: this.RegId,
      clientId: this.clientId,
      flag: true
    }
    this.clientService.getAllJobOpenings(profileData).subscribe(resp => {
      this.profileData = resp;
      this.profileDetails = this.profileData.result;
      console.log(this.profileDetails, "ssssssss")
      console.log(this.profileDetails, " Profile data---------")
    })
  }


  getInterviewList() {
    const post = {
      pageNo: this.currentPage,
      pageSize: 4,
      regId: this.RegId,
      clientId: this.clientId,
      flag: true
    };
    this.clientService.getInterviewList(post).subscribe(res => {
      this.interviewdetails = res;
      this.interviewList = this.interviewdetails.result;
      console.log(this.interviewList, "interviewlist============");
    });

  }



  getContactsList() {
    const postData = {
      pageNum: this.currentPage,
      pageSize: 4,
      registrationId: this.RegId,
      clientId: this.clientId,
      flag: true
    }
    this.clientService.getContactList(postData).subscribe(resp => {
      this.Contact = resp;
      this.contactDetails = this.Contact.result;
      console.log(this.contactDetails, "contact");
    });
  }


  ShowHideClick() {
    this.status2 = false;
  }

  notesave() {
    let notesave = {
      registrationId: this.RegId,
      clientId: this.clientId,
      noteData: this.noteForm.value.note,
      noteType: this.noteForm.value.noteSelect
    }
    this.clientService.clientnotesave(notesave).subscribe(res => {
      console.log(res, "save note");
      this.getnoteData();
    })
  }


  getnoteData() {
    let getnotedata = {
      registrationId: this.RegId,
      resourceId: this.clientId,
      pageSize: 20,
      pageNum: 1
    }
    this.clientService.getclientnotes(getnotedata).subscribe(res => {
      this.getnotedata = res;
      this.getdetails = this.getnotedata.result;
      this.noteId = this.getdetails.id;
      console.log(this.getdetails, "getnotedetails");

    })
  }


  //delete
  notedeletedata(id) {
    this.clientService.clientnotedelete(id).subscribe(res => {
      this.getnoteDeletedata = res;
      if (this.getnoteDeletedata.status == "StatusSuccess") {
        this.toaster.successToastr('note deleted successfully');
        this.getnoteData();
      }
      else {
        this.toaster.warningToastr('error deleting the note', 'Warning!');
      }
      console.log(this.getnoteDeletedata, "note delete details");
    })
  }



  viewmorejobopening() {
    this.router.navigate(['superadmin/jobopening']);
  }
  viewmoreinterview() {
    this.router.navigate(['superadmin/interview']);
  }
  viewcontact() {
    this.router.navigate(['superadmin/contact']);

  }
}
