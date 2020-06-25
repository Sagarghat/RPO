import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Validators, FormBuilder } from '@angular/forms';
import { ToastrManager } from 'ng6-toastr-notifications';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
@Component({
  selector: 'app-addjobopening',
  templateUrl: './addjobopening.component.html',
  styleUrls: ['./addjobopening.component.css']
})
export class AddjobopeningComponent implements OnInit {

  selectedjobType: any;
  selectedclient: any;
  selectedCertificatename: any;
  selectedQualificationname: any;
  selectedlocationname: any;
  selectedWorkExperience: any;
  selectedNoticePeriod: any;
  selectedjobStatus: any = 'Open';
  selectedrequirement: any = "Generic";
  minDate: any;
  maxDate: any;
  totalItems: any;
  submitted = false;
  currentPage: any = 1;
  contact: any;
  contactdata: any;
  contactDetails: any;
  client;
  clientDetails: any;
  regId: any;
  jobopeningData: any;
  selectedClient: any;
  selectedCientName: any;
  selectedContact: any;
  selectedContactName: any;
  skill;
  skillDetails: any;
  designation;
  designationDetails: any;
  certificate;
  certificateDetails: any;
  qualification;
  qualificationDetails: any;
  userToken: string;
  selectedSkill: any;
  selectedskillName: any = [];
  selectedDesignation: any;
  selectedDesignationName: any;
  selectedCertificate: any;
  selectedcertificateName: any;
  selectedqualification: any;
  selectedqualificationName: any;
  locationData;
  selectedLocation: any;
  selectedlocationName: any;
  checkedSkillList: any[];
  selectedSkillName: any;
  checkedCertificateList: any[];
  selectedCertificateName: string;
  checkedQualificationList: any[];
  selectedQualificationName: string;
  userId: string;
  userRole: string;
  files: any;
  filestringContact: string;
  pageSize = 4;
  totalClientItems;
  currentPageClient = 1;
  locationDetails: any;
  totalContactItems;
  currentPageContact = 1;
  ContactNameClickid: any;
  editConactData;
  editConactDetails: any;
  contactInternalDetails: any;
  clientListId: any;
  divStatus = true;
  ClientNameClickid: any;
  clientinterviewList;
  clientinterviewListById: any;
  RegId: any;
  jobType =
    [
      'Contract to Hire', 'Contract', 'Permanent'
    ];

  WorkExperience =
    [
      '0-1year',
      '1-2 Years',
      '2-3 years',
      '3-4 Years',
      '4+ years'
    ];

  noticePeriod =
    [
      'days',
      'months'

    ];

  JobStatus =
    [
      'Open',
      'Closed',
      'Onhold'
    ];

  requirementType =
    [
      'Generic',
      'Niche',
      'Super Niche'
    ]

  clientSearch = this.formBuilder.group({
    clientname: ['']
  })

  requirementForm = this.formBuilder.group({
    clientName: ['', Validators.required],
    contactName: ['', Validators.required],
    postingTitle: ['', Validators.required],
    noOfPositions: ['', Validators.required],
    openDate: ['', Validators.required],
    endDate: ['', Validators.required],
    experience: ['', Validators.required],
    skill: ['', Validators.required],
    qualification: ['', Validators.required],
    location: ['', Validators.required],
    jobType: ['', Validators.required],
    Country: [''],
    otherskill: [''],
    jobdescription: ['', Validators.required],
    otherqualification: [''],
    openingStatus: [''],
    minBudget: [''],
    maxBudget: [''],
    designation: ['', Validators.required],
    certificate: ['', Validators.required],
    RevenueperPosition: [''],
    ExpectedRevenue: ['',],
    ActualRevenue: [''],
    MissedRevenue: [''],
    city: [''],
    budgettype: [''],
    amount: [''],
    durationOfContract: [''],
    noticePeriod: [''],
    requirementType: [''],
    browse: [''],
    daysMonths: []
  })

  constructor(private formBuilder: FormBuilder, private toaster: ToastrManager, private activeRoute: ActivatedRoute, private router: Router, private addclientservice: SuperadminserviceService) {

    this.activeRoute.queryParams.subscribe(params => {
      this.ClientNameClickid = params['val'];
      console.log(this.ClientNameClickid, "ClientNameClickid");
    });
    this.activeRoute.queryParams.subscribe(params => {
      this.ContactNameClickid = params['val'];
      console.log(this.ContactNameClickid, "ContactNameClickid");
    });
  }

  onChanges() {
    this.requirementForm.get('clientName').valueChanges
      .subscribe(selectedCountry => {
        if (selectedCountry == undefined) {
          this.divStatus = true;
        }
        else {
          this.divStatus = false;
        }
      });
  }

  ngOnInit() {
    this.regId = sessionStorage.getItem('registrationId');
    this.userId = sessionStorage.getItem('userId');
    this.userRole = sessionStorage.getItem('role');
    console.log("form data..........", this.requirementForm);
    this.clientPostData();
    this.getSkills();
    this.getCertificates();
    this.getQualification();
    this.getDesignation();
    this.getLocation();
    this.getClientById();
    this.getcontactById();
    this.onChanges();
  }

  get contactNameReset() {
    return this.requirementForm.get('contactName');
  }

  modelChanged(clientname) {
    this.contactNameReset.reset();
    console.log(clientname, "Cient Name from client getAll API========");
    for (var i = 0; i < this.clientDetails.length; i++) {
      if (this.clientDetails[i].clientName === clientname) {
        this.clientListId = this.clientDetails[i].id;
        this.contactPostData();
        console.log(this.clientListId, "clientId from client getAll API=====");
        break;
      }
    }
  }

  getcontactById() {
    this.addclientservice.getcontactById(this.ContactNameClickid).subscribe(response => {
      this.editConactData = response;
      this.editConactDetails = this.editConactData.result;
      this.contactInternalDetails = this.editConactDetails.contact;
      this.requirementForm.controls.contactName.setValue(this.contactInternalDetails.lastName);
      this.requirementForm.controls.clientName.setValue(this.contactInternalDetails.client.clientName);
      console.log(this.contactInternalDetails, "contactjobgetById-------");
    });
  }

  get f() {
    return this.requirementForm.controls;
  }

  setPageClient(pages: number): void {
    this.currentPageClient = pages;
    this.clientPostData();
  }

  setPageContact(pagesNo: number): void {
    this.currentPageContact = pagesNo;
    this.contactPostData();
  }

  getClientById() {
    this.addclientservice.getClientById(this.ClientNameClickid, this.regId).subscribe(res => {
      this.clientinterviewList = res;
      this.clientinterviewListById = this.clientinterviewList.result;
      console.log(this.clientinterviewListById, "clientcontactByid----");
      this.requirementForm.controls.clientName.setValue(this.clientinterviewListById.client.clientName);
    });
  }

  contactPostData() {
    var post =
    {
      pageNum: this.currentPageContact,
      pageSize: this.pageSize,
      clientId: this.clientListId,
      registrationId: this.regId,
      flag: true
    }
    this.addclientservice.getContactList(post).subscribe(resp => {
      console.log(resp, "contact data---");
      this.contact = resp;
      this.totalContactItems = this.contact.totalRecords;
      console.log("total contact records", this.totalContactItems);
      this.contactDetails = this.contact.result;
      console.log(this.contactDetails, "post contactdata-----------");
    });
  }

  clientPostData() {
    var post = {
      pageNum: this.currentPageClient,
      pageSize: this.pageSize,
      registrationId: this.regId,
      flag: true
    }
    this.addclientservice.getClientList(post).subscribe(resp => {
      console.log(resp, "client data---------");
      this.client = resp;
      this.totalClientItems = this.client.totalRecords;
      console.log("total client records", this.totalClientItems);
      this.clientDetails = this.client.result;
      console.log(this.clientDetails, "post clientdata---------")

    })
  }

  getSkills() {
    this.addclientservice.skills(this.regId).subscribe(resp => {
      console.log(resp, "skills data response---------");
      this.skill = resp;
      this.skillDetails = this.skill.result;
      console.log(this.skillDetails, "get skills data---------")
    })
  }

  getQualification() {
    this.addclientservice.Qualification().subscribe(resp => {
      console.log(resp, "qualification data response---------");
      this.qualification = resp;
      this.qualificationDetails = this.qualification.result;
      console.log(this.skillDetails, "get qualification data---------")
    })
  }

  getCertificates() {
    this.addclientservice.Certificates().subscribe(resp => {
      console.log(resp, "certificate data response---------");
      this.certificate = resp;
      this.certificateDetails = this.certificate.result;
      console.log(this.certificateDetails, "get certificate data---------")
    })
  }

  getDesignation() {
    this.addclientservice.Designation().subscribe(resp => {
      console.log(resp, "designation data response---------");
      this.designation = resp;
      this.designationDetails = this.designation.result;
      console.log(this.designationDetails, "get designation data---------")
    })
  }

  getLocation() {
    this.addclientservice.Location(this.regId).subscribe(resp => {
      console.log(resp, "location data response---------");

      this.locationData = resp;
      this.locationDetails = this.locationData.result;
      console.log(this.locationDetails, "get location data---------")
    })
  }

  save() {
    if (this.requirementForm.invalid) {
      console.log(this.requirementForm.value, "registorm data");
      this.toaster.errorToastr('Please Fill the Mandatory Fields', 'Notification');
    }
    else {

      // client
      var client = this.requirementForm.value.clientName;
      for (let i = 0; i < this.clientDetails.length; i++) {
        if (this.clientDetails[i].clientName == client) {
          this.selectedClient = this.clientDetails[i].id;
          this.selectedCientName = this.clientDetails[i].clientName;
          console.log(this.selectedClient, "this is id by clicking client");
        }
      }

      // contact
      var contact = this.requirementForm.value.contactName;
      for (let i = 0; i < this.contactDetails.length; i++) {
        if (this.contactDetails[i].lastName == contact) {
          this.selectedContact = this.contactDetails[i].id;
          this.selectedContactName = this.contactDetails[i].lastName;
          console.log(this.selectedContact, "this is id by clicking client");
        }
      }

      // skills
      var skill = this.requirementForm.value.skill;
      for (let i = 0; i < this.skillDetails.length; i++) {
        if (this.skillDetails[i].skillName == skill) {
          this.selectedSkill = this.skillDetails[i].id;
          this.selectedskillName = this.skillDetails[i].skillName;
          console.log(this.selectedSkill, "this is id by clicking client");
        }
      }

      // designation
      var designation = this.requirementForm.value.designation;
      for (let i = 0; i < this.designationDetails.length; i++) {
        if (this.designationDetails[i].designation == designation) {
          this.selectedDesignation = this.designationDetails[i].id;
          this.selectedDesignationName = this.designationDetails[i].designation;
          console.log(this.selectedDesignation, "this is id by clicking client");
        }
      }

      // certificates
      var certificate = this.requirementForm.value.certificate;
      for (let i = 0; i < this.certificateDetails.length; i++) {
        if (this.certificateDetails[i].certificationName == certificate) {
          this.selectedCertificate = this.certificateDetails[i].id;
          this.selectedcertificateName = this.certificateDetails[i].certificationName;
          console.log(this.selectedCertificate, "this is id by clicking client");
        }
      }

      // qualification
      var qualification = this.requirementForm.value.qualification;
      for (let i = 0; i < this.qualificationDetails.length; i++) {
        if (this.qualificationDetails[i].qualificationName == qualification) {
          this.selectedqualification = this.qualificationDetails[i].id;
          this.selectedqualificationName = this.qualificationDetails[i].qualificationName;
          console.log(this.selectedqualification, "this is id by clicking client");
        }
      }

      // Location
      var location = this.requirementForm.value.location;
      for (let i = 0; i < this.locationDetails.length; i++) {
        if (this.locationDetails[i].locationName == location) {
          this.selectedLocation = this.locationDetails[i].id;
          this.selectedlocationName = this.locationDetails[i].locationName;
          console.log(this.selectedqualification, "this is id by clicking client");
        }
      }

      let data = {
        client: {
          id: this.selectedClient
        },
        addContact: {
          id: this.selectedContact
        },
        nameOfRequirement: this.requirementForm.value.postingTitle,
        noOfPositions: this.requirementForm.value.noOfPositions,
        requirementStartdate: this.requirementForm.value.openDate,
        requirementEndDate: this.requirementForm.value.endDate,
        totalExperience: this.requirementForm.value.experience,
        country: this.requirementForm.value.Country,
        skills: [{
          id: this.selectedSkill
        }],
        otherSkills: this.requirementForm.value.otherskill,
        qualifications: [{
          id: this.selectedqualification
        }],
        otherQualification: this.requirementForm.value.otherqualification,
        registrationId: this.regId,
        createdBy: {
          id: this.userId
        },
        modifiedBy: null,
        industry: null,
        city: this.requirementForm.value.city,
        lastUpdatedDate: null,
        requirementStatus: this.requirementForm.value.openingStatus,
        noticePeriod: this.requirementForm.value.noticePeriod,
        noticePeriodType: this.requirementForm.value.daysMonths,
        budgetTypeForPermanent: this.requirementForm.value.budgettype,
        budgetAmountForPermanent: this.requirementForm.value.amount,
        minBudget: this.requirementForm.value.minBudget,
        maxBudget: this.requirementForm.value.maxBudget,
        jobType: this.requirementForm.value.jobType,
        requirementType: this.requirementForm.value.requirementType,
        durationOfContract: this.requirementForm.value.durationOfContract,
        revenuePerPosition: this.requirementForm.value.RevenueperPosition,
        expectedRevenue: this.requirementForm.value.ExpectedRevenue,
        actualRevenue: this.requirementForm.value.ActualRevenue,
        missedRevenue: this.requirementForm.value.MissedRevenue,
        jobDesc: this.requirementForm.value.jobdescription,
        locations: [{
          id: this.selectedLocation
        }],
        designations: [{
          id: this.selectedDesignation
        }],
        certificates: [{
          id: this.selectedCertificate
        }],
        fileAttachements: this.filestringContact
      }

      this.addclientservice.addjobOpening(data).subscribe(res => {
        console.log(res, 'Sucessflly jobProfile is added');
        this.jobopeningData = res;
        if (this.jobopeningData.status === 'DuplicateRecord') {
          this.toaster.warningToastr('Profile Already Exist !!', 'Warning!');
        } else {
          this.toaster.successToastr('Profile Added Successfully');
          this.router.navigate(['/superadmin/jobopening']);
        }
      });
    }
  }



  onChange(skill: string, isChecked: boolean) {
    this.checkedSkillList = [];
    if (isChecked) {
      this.checkedSkillList.push(skill);
    }
    else {
      let index = this.checkedSkillList.indexOf(skill);
      this.checkedSkillList.splice(index, 1);
    }
    console.log(this.checkedSkillList);
    this.selectedSkillName = this.checkedSkillList.toString();
  }

  onChangeCertificates(certificate: string, isChecked: boolean) {
    this.checkedCertificateList = [];
    if (isChecked) {
      this.checkedCertificateList.push(certificate);
    }
    else {
      let index = this.checkedCertificateList.indexOf(certificate);
      this.checkedCertificateList.splice(index, 1);
    }
    console.log(this.checkedCertificateList);
    this.selectedCertificateName = this.checkedCertificateList.toString();
  }

  onChangeQualification(qualification: string, isChecked: boolean) {
    this.checkedQualificationList = [];
    if (isChecked) {
      this.checkedQualificationList.push(qualification);
    }
    else {
      let index = this.checkedQualificationList.indexOf(qualification);
      this.checkedQualificationList.splice(index, 1);
    }
    console.log(this.checkedQualificationList);
    this.selectedQualificationName = this.checkedQualificationList.toString();
  }


  //file
  getJobAttachmentFile(contact) {
    this.files = contact.target.files;
    var reader = new FileReader();
    reader.onload = this.handleReaderLoad.bind(this);
    reader.readAsBinaryString(this.files[0]);
  }

  handleReaderLoad(readerEvt) {
    var binaryString = readerEvt.target.result;
    this.filestringContact = btoa(binaryString);
    console.log(this.filestringContact, "file string");
    // Converting binary string data.
  }

}



