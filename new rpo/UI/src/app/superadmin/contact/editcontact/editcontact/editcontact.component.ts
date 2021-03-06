import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrManager } from 'ng6-toastr-notifications';
import { environment } from 'src/environments/environment';
import { ServiceService } from 'src/app/services/service.service';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';
@Component({
  selector: 'app-editcontact',
  templateUrl: './editcontact.component.html',
  styleUrls: ['./editcontact.component.css']
})
export class EditcontactComponent implements OnInit {
  baseUrl = `${environment.serviceUrl}`;

  isEdit: any;
  currentpageOwner: any;
  setPageOwner: any;
saveaddress: any;
  EditcontactComponent: any;
  cus;
  cus1: any;
  ready: any;
  bc: any;
  theCheckbox: any;
  visable: any;
  buttonName: any;
  toggle: any;
  show: any;
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
  addressArray: any = [];
  addressArrayid: any;
  SourceList;
  source: any;
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
  userId: string;
  clientid: any;
  role;
  flag: boolean;
  pageSize: any = 4;
  contactOwnerId: any;
  amEmail: any;
  OwnerId: any;
  OwnerList;
  OwnerListUser: any;
 totalItems;
  AddressData: any;
  marked: any;
  deleteaddressList: any;
  contactupdateaddressList;
  showdata: any = false;
  updateid: any;
  typeAdd: any;
  ownerDetails;

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
    addressType: ['', Validators.required],
    source: ['', Validators.required],
    browse: [''],
    createdBy: [],
  });

AddressForm = new FormGroup({
    addressType: new FormControl('', Validators.required),
    addressLane1: new FormControl('', Validators.required),
    addressLane2: new FormControl('', Validators.required),
    state: new FormControl('', Validators.required),
    pincode: new FormControl('', Validators.required),
    city: new FormControl('', Validators.required),
    country: new FormControl('', Validators.required),
  });
 

  constructor(private toaster: ToastrManager, private router: Router,
    private formBuilder: FormBuilder, private editcontactservice: SuperadminserviceService,
    private http: HttpClient, private activeRoute: ActivatedRoute) {
    this.id = this.activeRoute.snapshot.paramMap.get("id");
    console.log(this.id, "idddddddddddddddddddd")
  }
  setPage(pageNo: number): void {
    this.currentPage = pageNo;
    this.getUsers()
  }

 ngOnInit() {

   this.registrationId = sessionStorage.getItem("registrationId");
    this.userToken = sessionStorage.getItem("Token");
    this.role=sessionStorage.getItem('role');
    this.userId = sessionStorage.getItem('userId');
    console.log(this.registerForm, "form data");
    this.getContactTypeAddress();
    this.getContactSource();
    this.getcontactById();
this.getUsers();
    this.saveAddress();
  }

  getcontactById() {

    this.editcontactservice.getcontactById(this.id).subscribe(response => {
      this.editData = response;
      this.editDetails = this.editData.result;
      this.addressDetails = this.editDetails.address;
      this.contactDetails = this.editDetails.contact;
      this.contactOwnerId = this.editDetails.contact.contactOwner;
      this.UserRoleAmById();
      console.log(this.editDetails, "getById-------");
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
      this.registerForm.controls.addressLane1.setValue(this.AddressLine1);
      this.registerForm.controls.addressLane2.setValue(this.AddressLine2);
      this.registerForm.controls.country.setValue(this.country);
      this.registerForm.controls.state.setValue(this.state);
      this.registerForm.controls.pincode.setValue(this.pincode);
      this.registerForm.controls.city.setValue(this.city);
      this.registerForm.controls.addressType.setValue(this.typeofAddress);
      this.registerForm.controls.source.setValue(this.contactDetails.source.sourceName);
      this.registerForm.controls.browse.setValue(this.contactDetails.others);
      this.registerForm.controls.primaryContact.setValue(this.contactDetails.isPrimaryContact);
      this.registerForm.controls.emailoptOut.setValue(this.contactDetails.emailOptOut);
      this.clientid = this.editDetails.contact.client.id;
      console.log(this.clientid, "clientidddddddddddddddddd");
      // for (let i = 0; i <= this.addressDetails.length; i++) {
      //   this.AddressLine1 = this.addressDetails[i].addressLane1;
      //   this.AddressLine2 = this.addressDetails[i].addressLane2;
      //   this.country = this.addressDetails[i].country;
      //   this.state = this.addressDetails[i].state;
      //   this.pincode = this.addressDetails[i].pincode;
      //   this.city = this.addressDetails[i].city;
      //   this.typeofAddress = this.addressDetails[i].typeOfAddress.typeOfAddress;
      //   break;
      // }
    });
  }

//  getOwner() {
//     this.role = 'am';
//     this.flag = true;
//     this.editcontactservice.getOwner(this.currentPage, this.pageSize, this.registrationId, this.role, this.flag).subscribe(resp => {
//       this.OwnerList = resp;
//       this.OwnerListUser = this.OwnerList.result;
//       this.totalItems = this.OwnerList.totalRecords;
//       console.log(this.OwnerList, 'ownerlist');
//     })
//   }

getUsers() {
  debugger
  let postdata = {
    pageNo: this.currentPage,
    pageSize: this.pageSize,
    flag: true,
    registrationId: this.registrationId,
    role:this.role
    
  }
  this.editcontactservice.getuserList(postdata).subscribe((resp: any)=>{

    this.ownerDetails=resp.result.list;
    // this.OwnerListUser=resp.result.list;
    console.log(this.OwnerListUser,"gggggggggg")
  })


}

  getContactTypeAddress() {

    this.editcontactservice.getContactTypeAddress().subscribe(resp => {
      console.log(resp);
      this.AddressList = resp;
      this.AddressData = this.AddressList.result;
      console.log(this.AddressData, 'this from responce Address')
    });
  }

  UserRoleAmById() {
    this.editcontactservice.getUserById(this.contactOwnerId, this.registrationId).subscribe(resp => {
      this.ownerList = resp;
      this.Owner = this.ownerList.result;
      this.amEmail = this.Owner.email;
      this.registerForm.controls.contactOwner.setValue(this.amEmail);
    });
  }

  getContactSource() {
    this.editcontactservice.getContactSource().subscribe(resp => {
      console.log(resp);
      this.SourceList = resp;
      this.source = this.SourceList.result;
      console.log(this.source, "sourcelist");
    })
  }

  save() {
     
    this.submitted = true;
    // console.log(this.AddressData);
    // var add = this.bc
    // if (add == undefined) {
    //   this.typeOfAddress = this.addressDetails.typeOfAddress
    // }
    // else {
    //   for (var i = 0; i < this.AddressData.length; i++) {
    //     if (this.AddressData[i].typeOfAddress == add) {
    //       this.typeOfAddress = this.AddressData.result[i];
    //     }
    //   }
    // }
    var contactowner = this.selectedUsername;
      for (let i = 0; i < this.ownerDetails.length; i++) {
        if (this.ownerDetails[i].email == contactowner) {
          this.OwnerId = this.ownerDetails[i].id;
          break;
        }
      }
    var source = this.selectedSource;
    for (let i = 0; i <= this.source.length; i++) {
      if (this.source[i].sourceName == source) {
        this.selectedContactSourceName = this.source[i].id;
        break;
      }
    }
    let data =
    {
      contact: {
        // createdBy:this.userId,
        id: this.id,
        firstName: this.registerForm.value.firstName,
        lastName: this.registerForm.value.lastName,
        domain: this.registerForm.value.domain,
        mobile: this.registerForm.value.mobile,
        email: this.registerForm.value.email,
        client:
        {
          id: this.clientid
        },
        phone: this.registerForm.value.phone,
        jobTitle: this.registerForm.value.jobTitle,
        twitterId: this.registerForm.value.twitterId,
        skypeId: this.registerForm.value.skypeId,
        registrationId: this.registrationId,
        contactOwner: this.OwnerId,
        description: this.registerForm.value.description,
        isPrimaryContact: this.registerForm.value.primaryContact,
        emailOptOut: this.registerForm.value.emailoptOut,
        source:
        {
          id: this.selectedContactSourceName
        }

      },
      // address: this.addressDetails
    }

    this.editcontactservice.addcontactdata(data).subscribe(resp => {
      console.log("post of edit......." + resp);
      if (resp['status'] == "StatusSuccess") {
        this.toaster.successToastr('contact edited successfully');
        this.router.navigate(['/superadmin/contact']);
      }
      else {
        this.toaster.warningToastr('DuplicateRecord.', 'Alert!');
      }
    })
  }

  get f() {
    return this.registerForm.controls;
  }

  toggleVisibility(e) {
    this.marked = e.target.checked;
  }

  addresstypeselected() {
    console.log("address ------>  ", this.AddressData);
    var selectedData = this.AddressData.find(d => d.addressType == this.selectedAddress)
    this.AddressForm.controls.addressType.setValue(selectedData.addressType)
   }

  toggleT() {
    this.show = !this.show;
    this.showdata = false
  }

  deleteaddress(e) {
    this.addressArray.splice(e, 1);
  }

 contactdeleteaddress(id) {
    this.editcontactservice.contactdeleteaddress(id).subscribe(res => {
    this.deleteaddressList = res;
      if (this.deleteaddressList.status == "StatusSuccess") {
        this.toaster.successToastr('Address deleted successfully');
        this.getcontactById();
      }
      else {
        this.toaster.warningToastr('error deleting the address', 'Warning!');
      }
    })
  }

 saveAddress() {

    var add = this.selectedAddress;
    if (add == undefined) {
      this.typeOfAddress = this.addressDetails.typeOfAddress;
      this.getContactTypeAddress();
    } else {
      for (var i = 0; i < this.AddressData.length; i++) {
        if (this.AddressData[i].typeOfAddress == add) {
          this.typeOfAddress = this.AddressData[i];
        }
      }
      this.getContactTypeAddress();
    }

    let addressdata = {
     
      date: '',
      typeOfAddress: this.typeOfAddress,
      addressLane1: this.AddressForm.value.addressLane1,
      addressLane2: this.AddressForm.value.addressLane2,
      city: this.AddressForm.value.city,
      country: this.AddressForm.value.country,
      pincode: this.AddressForm.value.pincode,
      state: this.AddressForm.value.state,
      cid: this.id
    }
    this.getContactTypeAddress();
    console.log('save address data.....', addressdata);
    this.getContactTypeAddress();
    this.editcontactservice.contactsaveaddress(addressdata).subscribe(resp => {
      if (resp['status'] == 'StatusSuccess') {
        this.toaster.successToastr('Address added successfully');
        this.toggleT();
        this.getcontactById();
      } else if (resp['status'] == 'DuplicateRecord') {
        this.toaster.warningToastr('DuplicateRecord.', 'Alert!');
      }
    })
  }

  edit(p) {
    console.log(p);
    this.show = true;
    this.showdata = true;
    this.updateid = p.id;
    this.typeAdd = p.typeOfAddress.typeOfAddress;
    console.log("hbvjgfhgfhgv", p.typeOfAddress.typeOfAddress);
    console.log("gggggggg", this.AddressForm);
    this.AddressForm.controls.addressType.setValue(p.typeOfAddress.typeOfAddress);
    this.AddressForm.controls.addressLane1.setValue(p.addressLane1);
    this.AddressForm.controls.addressLane2.setValue(p.addressLane2);
    this.AddressForm.controls.city.setValue(p.city);
    this.AddressForm.controls.pincode.setValue(p.pincode);
    this.AddressForm.controls.state.setValue(p.state);
    this.AddressForm.controls.country.setValue(p.country);
  }

   contactupdateaddress() {
    console.log(this.AddressForm, this.updateid);
    var selectedData = this.AddressData.find(d => d.typeOfAddress == this.AddressForm.value.addressType)
    let addressupadation = {
      cid: this.id,
      typeOfAddress: selectedData,
      addressLane1: this.AddressForm.value.addressLane1,
      addressLane2: this.AddressForm.value.addressLane2,
      city: this.AddressForm.value.city,
      pincode: this.AddressForm.value.pincode,
      state: this.AddressForm.value.state,
      country: this.AddressForm.value.country,
    }
    console.log("updation", addressupadation)
    this.editcontactservice.contactupdateaddress(this.updateid, addressupadation).subscribe(resp => {
      if (resp['status'] == 'StatusSuccess') {
        this.toaster.successToastr('address updated successfully');
        this.getcontactById();
        this.AddressForm.reset();

      } else if (resp['status'] == 'DuplicateRecord') {
        this.toaster.warningToastr('DuplicateRecord.', 'Alert!');
      }
    });
   }

 cancelReq() {
    this.AddressForm.reset()
    this.AddressForm.clearAsyncValidators()
    this.show = false
  }

}