import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SuperadminserviceService } from '../../superadminservice.service';
import { ToastrManager } from 'ng6-toastr-notifications';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { ExcelService } from 'src/app/excel.service';
declare var $;
@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  first: any;
  ContactNotedelete: any;
  notedeletedata: any;
  ShowHideClick: any;
  getdetails: any;
  status: any;
  resp;
  clientName;
  cont1;
  const;
  result;
  date = new Date();
  totalItems;
  pageSize = 10;
  currentPage = 1;
  selectedCientName: any;
  LeadList;
  LeadListData: any;
  leadId: any;
  leadName: any;
  AmList;
  amId: any;
  BdmList;
  BdmId: any;
  lead_id: void;
  accountManger_id: any;
  amName: any;
  bdmName: any;
  primaryContact_id: any;
  contact;
  contact1: any;
  contactOwner: any;
  ownerList;
  Owner: any;
  contactOwnerId: any;
  contactData: any;
  registrationId: any;
  contactDetails: any;
  ContactownerName: any;
  userToken: string;
  contactId: any;
  RegId: string;
  editcontactservice: any;
  editData: any;
  editDetails: any;
  addressDetails: any;
  firstName: any;
  id: any;
  contactid: any;
  AddressLine1: any;
  AddressLine2: any;
  country: any;
  state: any;
  pincode: any;
  city: any;
  gst: any;
  gstpercentage: any;
  isSez: any;
  typeofAddress: any;
  lastName: any;
  domain: any;
  contactss: any;
  email: any;
  mobile: any;
  jobTitle: any;
  phone: any;
  skypeId: any;
  twitterId: any;
  street1: any;
  address: any;
  street2: any;
  addressData: any;
  addressLane2: any;
  addressLane1: any;
  AddressLane1: any;
  selectedContactOwner: any;
  Contactowner: any;
  source: any;
  notes: any;
  contactResume: any;
  contactresume: any;
  getnoteData: any;
  postdata: any;
  others: any;
  getnotedata;
  noteId: any;
  getnoteDeletedata: any;
  // searchcode
  search_result: boolean = false;
  showNodata: boolean = false;
  search_Previous_result: boolean = false;
  dummy: boolean = true;
  asli: boolean = false;
  firstt: boolean = false;
  second: boolean = false;
  asd: boolean = false;
  qwe: boolean = false;
  lkj: boolean = false;
  searchTerm: FormControl = new FormControl();
  pageNum: any;
  searchvalue: any;
  data: [];
  obj: any;
  searchInput: any;
  cntactresumee: any;

  serchform = this.formBuilder.group({
    contactName: [''],
    phone: [''],
    email: ['']
  });

  printPage() {
    window.print();
  }

  ale() {
    return window.alert("The field is empty");
  }
  noteForm = this.formBuilder.group({
    note: ['', Validators.required],
    noteSelect: ['', Validators.required]
  })

  constructor(private route: Router, private serv: ExcelService, private toaster: ToastrManager, private service: SuperadminserviceService, private formBuilder: FormBuilder) { }

  setPage(pageNo: number): void {
    this.currentPage = pageNo;
    this.getContactsList();
  }
  search_values(event) {
    this.searchInput = event;
  }

  ngOnInit() {

    this.userToken = sessionStorage.getItem('Token');
    this.registrationId = sessionStorage.getItem("registrationId");

    $(document).click(function (e) {
      console.log(e);
      if (e.target.id == "usr") {
        $(".item").show();
      }
      else {
        // this.dummy=true;
        // this.asli=false;
        $(".item").hide();
        //alert("razak");
        // $(".dummyinp").hide();
        // $(".dummyclass").show();
      }

    });
    this.searchTerm.valueChanges.subscribe(
      term => {

        var post1 = {

          pageNum: this.currentPage,
          pageSize: this.pageSize,
          registrationId: JSON.parse(this.registrationId),
          flag: true,
          contactName: term,

        };
        console.log("post values", post1)

        if (term != '') {
          this.service.getContactList(post1).subscribe(
            resp => {
              this.contact = resp;
              this.contactDetails = this.contact.result;
              this.totalItems = this.contact.totalRecords;
              if (this.contact.status === "Accepted") {
                this.search_result = true;
                console.log("status", this.contact.status);
                this.contactDetails = this.contact.result;
                console.log("yoo", this.contactDetails);
                console.log(" data ", this.contact);
                console.log("gaaa", this.contact.result[0].fullName);
                console.log(" data - RESULT ", this.contact.totalRecords);
                this.showNodata = false;
              } else if (this.contact.status === "DataIsEmpty") {
                this.showNodata = true;
                if (this.showNodata == true) {
                  this.search_result = false;
                  this.search_Previous_result = true;
                }
              }

            })
        }
      });
    this.getContactsList();
    // this.getcontactnotes();
    this.Exel();
  }

  save() {

    const searchData = {
      pageNum: this.currentPage,
      pageSize: this.pageSize,
      registrationId: JSON.parse(this.registrationId),
      flag: true,
      contactName: this.serchform.value.contactName,
      phone: this.serchform.value.phone,
      email: this.serchform.value.email
    };
    this.service.getContactList(searchData).subscribe(res => {
      console.log(res, "totalserchhhhhhhhhhhhhhhhhhhhhhhinterviewlist----------");
      this.contact = res;
      this.totalItems = this.contact.totalRecords;
      console.log(this.totalItems, "totalItems");
      this.contactDetails = this.contact.result;
      console.log(this.contactDetails, "interviewist--------");
    });
  }
  clickContactName(id) {
    this.route.navigate(['superadmin/contact/clickContactName', id]);
  }

  Exel() {
    const post = {
      pageNum: this.currentPage,
      pageSize: this.pageSize,
      registrationId: this.RegId,
      flag: false
    };
    this.service.getClientList(post).subscribe(res => {
      this.contact = res;
      this.contactDetails = this.contact.result.json();
      console.log(this.totalItems, "totalItems");
      console.log(this.contactDetails, 'contactDetails...........');
    });
  }

  getcontactById(l) {
    this.contactid = l;
    var id = {
      id: this.contactid
    }
    this.service.getcontactById(this.contactid).subscribe(response => {
      this.editData = response;
      this.editDetails = this.editData.result;
      this.addressDetails = this.editDetails.address;
      this.contactss = this.editDetails.contact;
      this.address = this.addressDetails.contact;
      console.log(this.contactss, "id")
      console.log(this.editDetails, "dataa for candidate")
      console.log(this.addressDetails, "asdfsafdsf");
      this.firstName = this.contactss.firstName;
      this.lastName = this.contactss.lastName;
      this.domain = this.contactss.domain;
      this.clientName = this.contactss.client.clientName;
      this.email = this.contactss.email;
      this.mobile = this.contactss.mobile;
      this.jobTitle = this.contactss.jobTitle;
      this.phone = this.contactss.phone;
      this.skypeId = this.contactss.skypeId;
      this.twitterId = this.contactss.twitterId;
      this.source = this.contactss.source.sourceName;
      this.cntactresumee = this.contactss.others;
      console.log(this.cntactresumee, "sariiiii");

      for (let i = 0; i <= this.addressDetails.length; i++) {
        this.addressLane1 = this.addressDetails[i].addressLane1;
        this.addressLane2 = this.addressDetails[i].addressLane2;
        this.country = this.addressDetails[i].country;
        this.state = this.addressDetails[i].state;
        this.pincode = this.addressDetails[i].pincode;
        this.city = this.addressDetails[i].city;
      }
      for (let i = 0; i <= this.Owner.length; i++) {
        if (this.Owner[i].id == this.contactDetails.contactOwner) {
          this.Contactowner = this.Owner[i].name;
          this.selectedContactOwner = this.Owner[i].id;
          break;
        }
      }
    });
  }

  downloadExcel() {

    console.log(this.contactDetails, "fffffffff")
    this.serv.exportAsExcelFile(this.contactDetails, 'sample');
  }

  download() {
    var filepdf = 'data:application/pdf;base64,' + this.contactss.others;
    let a = document.createElement('a');
    a.href = filepdf;
    a.download = 'resume.pdf';
    a.click();
  }

  getContactsList() {
    var postData =
    {
      pageNum: this.currentPage,
      pageSize: this.pageSize,
      registrationId: JSON.parse(this.registrationId),
      flag: true
    }

    this.service.getContactList(postData).subscribe(resp => {
      console.log(resp, 'contactlist');
      this.contact = resp;
      this.totalItems = this.contact.totalRecords;
      console.log(this.totalItems, "totalItems");
      this.contactDetails = this.contact.result;
      console.log(this.contactDetails, "contact");
    });
  }


  // contactnotesaveee() {
  //   if (this.noteForm.invalid) {
  //     this.toaster.warningToastr('please enter note description');
  //   }
  //   else {
  //     let notesave = {
  //       registrationId: this.registrationId,
  //       contactId: this.contactid,
  //       noteData: this.noteForm.value.noteSelect,
  //       noteType: this.noteForm.value.note
  //     }

  //     this.service.contactnotesaveee(notesave).subscribe(res => {
  //       this.postdata = res
  //       if (this.postdata.status == "StatusSuccess") {
  //         this.toaster.successToastr('note added successfully');
  //         this.getcontactnotes();
  //         this.noteForm.reset();
  //       }
  //     });
  //   }
  //   }

  // getcontactnotes() {
  //   let getnotedata = {
  //     registrationId: this.registrationId,
  //     pageSize: 20,
  //     pageNum: 1,
  //     resourceId:this.contactid
  //   }
  //   this.service.getcontactnotes(getnotedata).subscribe(res => {
  //     this.getnotedata = res;
  //     this.getdetails = this.getnotedata.result;
  //     console.log(this.getdetails, "contact getnotedetails");
  //   })
  // }

  // ContactNotedelete(id) {

  //   this.service.deletecontactnotes(id).subscribe(res => {
  //     this.getnoteDeletedata = res;
  //     if (this.getnoteDeletedata.status == "StatusSuccess") {
  //       this.toaster.successToastr('Interview note deleted successfully');
  //       this.getcontactnotes();
  //     }
  //     else {
  //       this.toaster.warningToastr('error deleting the note', 'Warning!');
  //     }
  //     console.log(this.getnoteDeletedata, "note delete details");
  //   })
  // }
}
