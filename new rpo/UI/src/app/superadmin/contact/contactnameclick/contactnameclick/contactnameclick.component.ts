import { Component, OnInit, HostListener } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrManager } from 'ng6-toastr-notifications';
import { environment } from 'src/environments/environment';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';
@Component({
  selector: 'app-contactnameclick',
  templateUrl: './contactnameclick.component.html',
  styleUrls: ['./contactnameclick.component.css']
})
export class ContactnameclickComponent implements OnInit {
  baseUrl = `${environment.serviceUrl}`;

  email: any;
  status1: boolean = true;
  text: String = "ShowDetails";
  save: any;
  cus;
  AddressForm: any;
  saveAddress: any;
  UpdateAddressForm: any;
  ready: any;
  cancelReq: any;
  visable: any;
  AddressData: any;
  addresstypeselected: any;
  saveaddress: any;
  UpdateAddressFm: any;
  show: any;
  cus1: any;
  optionsSelect;
  submitted: boolean;
  LeadList;
  AmList;
  LeadListData: any;
  BdmList;
  AddressList: any;
  Address: any;
  Customer;
  CustomerDetails: any;
  BdmList1: any;
  ClientContact: any;
  typeOfAddress: any;
  amId: any;
  BdmId: any;
  leadId: any;
  date = new Date();
  id;
  editData: any;
  editDetails: any;
  leadName: any;
  selectedBdm: any;
  primaryId: any;
  accMId: any;
  primaryName: any;
  selectedLead: any;
  leadid: any;
  leadById: any;
  selectedSource: any;
  getById: any;
  selectedClient: any;
  AddressLine1: any;
  addressDetails: any;
  contactDetails: any;
  firstName: any;
  AddressLine2: any;
  country: any;
  state: any;
  pincode: any;
  city: any;
  gstpercentage: any;
  gst: any;
  isSez: any;
  typeofAddress: any;
  ownerList;
  Owner: any;
  selectedAddress: any;
  SourceList;
  source: any;
  diffOfDays: any;
  registrationId: string;
  ContactOwner: any;
  selectedUsername: any;
  Contactowner: any;
  clientDetails;
  clientList: any;
  currentPage: any = 1;
  clientId: any;
  selectedContactOwner: any;
  selectedContactSourceName: any;
  filestringContact: any;
  userToken: string | string[];
  isShow: boolean;
  topPosToStartShowing: 50;
  HideClick: any;
  addClientService: any;
  pageSize: any = 4;
  RegId: any;
  profileData: any;
  profileDetails: any;
  service: any;
  interviewdetails: any;
  interviewList: any;
  getnotedata;
  getdetails: any;
  noteId: any;
  getnoteDeletedata: any;
  clientid: any;
  contactid: any;
  postdata: any;
  getnoteData: any;
  flag: boolean;
  role;
  OwnerList;
  OwnerListUser: any;
  totalItems;
  amEmail: any;
  contactOwnerId: any;
  OwnerId: any;
  lastName: any;
  jobTitle: any;
  mobile: any;
  domain: any;

 registerForm = this.formBuilder.group({
    clientName: ['', Validators.required],
    contactOwner: ['', Validators.required],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    domain: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    jobTitle: ['', Validators.required],
    mobile: ['', [Validators.required, Validators.pattern("^(\([0-9]{3}\) |[6-9]{1})[0-9]{9}$")]],
    phone: ['', [Validators.required, Validators.pattern("^(\([0-9]{3}\) |[6-9]{1})[0-9]{9}$")]],
    primaryContact: ['', Validators.required],
    emailoptOut: ['', Validators.required],
    description: [''],
    skypeId: [''],
    twitterId: [''],
    addressLane1: [''],
    addressLane2: [''],
    country: [''],
    state: [''],
    pincode: ['', [Validators.pattern("^[5]{1}[0-9]{5}$")]],
    city: [''],
    gst: [''],
    gstpercent: [''],
    isSez: [''],
    addressType: [''],
    source: [''],
    browse: [''],
    diffOfDays: ['']
  });

 noteForm = this.formBuilder.group({
    note: ['', Validators.required],
    noteSelect: ['', Validators.required]
  })
  description: any;
  phone: any;
  twitterId: any;
  skypeId: any;
  isPrimaryContact: any;
  emailOptOut: any;
  
  constructor(private toaster: ToastrManager, private router: Router,
    private formBuilder: FormBuilder, private editcontactservice: SuperadminserviceService,
    private http: HttpClient, private activeRoute: ActivatedRoute) 
    {
       this.id = this.activeRoute.snapshot.paramMap.get("id");
        console.log(this.id, "idddddddddddddddddddd")
   }

  ngOnInit() 
  {
    this.registrationId = sessionStorage.getItem("registrationId");
    this.userToken = sessionStorage.getItem("Token");
    console.log(this.registerForm, "form data");
    this.getContactTypeAddress();
    this.getcontactById();
    this.getOwner();
    this.hidedd();
    this.profileGetData();
    this.getInterviewList();
    this.getcontactnotes();
  }

  getcontactById() 
  {
     this.editcontactservice.getcontactById(this.id).subscribe(response => {
      this.editData = response;
      this.editDetails = this.editData.result;
      this.addressDetails = this.editDetails.address;
      this.contactDetails = this.editDetails.contact;
      this.lastName = this.contactDetails.lastName;
      this.diffOfDays = this.contactDetails.diffOfDays;
      this.jobTitle = this.contactDetails.jobTitle;
      this.mobile = this.contactDetails.mobile;
      this.domain = this.contactDetails.domain
      this.description = this.contactDetails.description;
      this.contactOwnerId = this.editDetails.contact.contactOwner;
      this.phone = this.contactDetails.phone;
      this.isPrimaryContact = this.contactDetails.isPrimaryContact;
      this.emailOptOut = this.contactDetails.emailOptOut;
      this.twitterId = this.contactDetails.twitterId;
      this.skypeId = this.contactDetails.skypeId;

      this.UserRoleAmById();
      console.log(this.editDetails, "getById-------");
      console.log(this.clientid, "clientidddddddddddddddddd");
      this.registerForm.controls.firstName.setValue(this.contactDetails.firstName);
      this.registerForm.controls.lastName.setValue(this.contactDetails.lastName);
      this.registerForm.controls.domain.setValue(this.contactDetails.domain);
      this.registerForm.controls.clientName.setValue(this.editDetails.contact.client.clientName);
      this.registerForm.controls.mobile.setValue(this.contactDetails.mobile);
      this.registerForm.controls.email.setValue(this.contactDetails.email);
      this.registerForm.controls.jobTitle.setValue(this.contactDetails.jobTitle);
      this.registerForm.controls.phone.setValue(this.contactDetails.phone);
      this.registerForm.controls.twitterId.setValue(this.contactDetails.twitterId);
      this.registerForm.controls.skypeId.setValue(this.contactDetails.skypeId);
     for (let i = 0; i <= this.addressDetails.length; i++) {
        this.AddressLine1 = this.addressDetails[i].addressLane1;
        this.AddressLine2 = this.addressDetails[i].addressLane2;
        this.country = this.addressDetails[i].country;
        this.state = this.addressDetails[i].state;
        this.pincode = this.addressDetails[i].pincode;
        this.city = this.addressDetails[i].city;
        this.typeofAddress = this.addressDetails[i].typeOfAddress.typeOfAddress;
        break;
      }
     this.registerForm.controls.addressLane1.setValue(this.AddressLine1);
      this.registerForm.controls.addressLane2.setValue(this.AddressLine2);
      this.registerForm.controls.country.setValue(this.country);
      this.registerForm.controls.state.setValue(this.state);
      this.registerForm.controls.pincode.setValue(this.pincode);
      this.registerForm.controls.city.setValue(this.city);
      this.registerForm.controls.addressType.setValue(this.typeofAddress);
      this.registerForm.controls.source.setValue(this.contactDetails.source.sourceName);
      this.registerForm.controls.primaryContact.setValue(this.contactDetails.isPrimaryContact);
      this.registerForm.controls.emailoptOut.setValue(this.contactDetails.emailOptOut);
      this.clientid = this.editDetails.contact.client.id;
      for (let i = 0; i <= this.Owner.length; i++) {
        if (this.Owner[i].id == this.contactDetails.contactOwner) {
          this.Contactowner = this.Owner[i].name;
          this.selectedContactOwner = this.Owner[i].id;
          console.log("contactOwner..." + this.Contactowner);
          break;
        }
      }
      this.registerForm.controls.contactOwner.setValue(this.Contactowner);
     });
  }

  getOwner() 
  {
    this.role = 'am';
    this.flag = true;
    this.editcontactservice.getOwner(this.currentPage, this.pageSize, this.registrationId, this.role, this.flag).subscribe(resp => {
      this.OwnerList = resp;
      this.OwnerListUser = this.OwnerList.result;
      this.totalItems = this.OwnerList.totalRecords;
      console.log(this.OwnerList, 'ownerlist');
    })
  }

  UserRoleAmById() {
    this.editcontactservice.getUserById(this.contactOwnerId, this.registrationId).subscribe(resp => {
      this.ownerList = resp;
      this.Owner = this.ownerList.result;
      this.amEmail = this.Owner.email;
      this.registerForm.controls.contactOwner.setValue(this.amEmail);
    });
  }

  getContactTypeAddress() {
    this.editcontactservice.getContactTypeAddress().subscribe(resp => {
      console.log(resp);
      this.AddressList = resp;
      this.Address = this.AddressList.result;
      console.log(this.AddressList, 'this from responce Address')
    });
  }

  get f() {
    return this.registerForm.controls;
  }


  printPage() {
    window.print();
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

  status: Boolean = true;

  ShowHideClick() {
    this.status = false;
  }

  jobopening() {
    this.router.navigate(['superadmin/jobopening/addjobopening'], { queryParams: { val: this.id } });

  }

  interview() {
    this.router.navigate(['superadmin/interview/addinterview'], { queryParams: { val: this.id } });
  }

  viewmorejobopening() {
    this.router.navigate(['superadmin/jobopening']);
  }

  viewmoreinterview() {
    this.router.navigate(['superadmin/interview']);
  }

  setPage(pagNo: number): void {
    this.currentPage = pagNo;
    this.profileGetData();
    this.getInterviewList();
  }

  profileGetData() {
    var profileData = {
      pageNum: this.currentPage,
      pageSize: 4,
      registrationId: this.registrationId,
      flag: true,
      
    }
    this.editcontactservice.getAllJobOpenings(profileData).subscribe(resp => {
      this.profileData = resp;
      this.profileDetails = this.profileData.result;
      console.log(this.profileDetails, " Profile data---------")
    })
  }

  getInterviewList() {

    const post = {
      pageNo: this.currentPage,
      pageSize: this.pageSize,
      regId: this.registrationId,
      flag: true,

    };
    this.editcontactservice.getInterviewList(post).subscribe(res => {
      this.interviewdetails = res;
      this.interviewList = this.interviewdetails.result;
      console.log(this.interviewList, "interviewist--------");
    });
  }

   contactnotesaveee() {
    if (this.noteForm.invalid) {
      this.toaster.warningToastr('please enter note description');
    }
    else {
      let notesave = {
        registrationId: this.registrationId,
        contactId: this.id,
        noteData: this.noteForm.value.noteSelect,
        noteType: this.noteForm.value.note
      }

      this.editcontactservice.contactnotesaveee(notesave).subscribe(res => {
        this.postdata = res
        if (this.postdata.status == "StatusSuccess") {
          this.toaster.successToastr('note added successfully');
          this.getcontactnotes();
        }
      });
    }
    }

  getcontactnotes() {
    let getnotedata = {
      registrationId: this.registrationId,
      pageSize: 20,
      pageNum: 1,
      resourceId:this.id
    }
    this.editcontactservice.getcontactnotes(getnotedata).subscribe(res => {
      this.getnotedata = res;
      this.getdetails = this.getnotedata.result;
      this.noteId = this.getdetails.id;
      console.log(this.getdetails, "contact getnotedetails");

    })
  }

  ContactNotedelete(id) {

    this.editcontactservice.deletecontactnotes(id).subscribe(res => {
      this.getnoteDeletedata = res;
      if (this.getnoteDeletedata.status == "StatusSuccess") {
        this.toaster.successToastr('Interview note deleted successfully');
        this.getcontactnotes();
      }
      else {
        this.toaster.warningToastr('error deleting the note', 'Warning!');
      }
      console.log(this.getnoteDeletedata, "note delete details");
    })
  }
}
