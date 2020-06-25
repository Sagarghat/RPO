import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { ToastrManager } from 'ng6-toastr-notifications';
import { formatDate } from '@angular/common';
import { SuperadminserviceService } from 'src/app/superadmin/superadminservice.service';
@Component({
  selector: 'app-editjobopening',
  templateUrl: './editjobopening.component.html',
  styleUrls: ['./editjobopening.component.css']
})
export class EditjobopeningComponent implements OnInit {

  selectedjobType: any;
  selectedclient: any;
  selectedSkillname: any;
  selectedCertificatename: any;
  selectedQualificationname: any;
  selectedlocationname: any;
  selectedWorkExperience: any;
  selectedNoticePeriod: any;
  selectedjobStatus: any;
  selectedrequirement: any;
  minDate: any;
  maxDate: any;
  setPage: any;
  totalItems: any;
  jobOpeningId;
  userToken;
  regId;
  jobOpening;
  jobOpeningListById: any;
  clientDetails: any;
  addContactDetails: any;
  ski: any;
  skills: any;
  qualifications: any;
  contact;
  contactDetails: any;
  client;
  clientData: any;
  skillDetails: any;
  qualification;
  qualificationDetails: any;
  certificate;
  certificateDetails: any;
  designation;
  designationDetails: any;
  locationData;
  skill;
  selectedClient: any;
  selectedCientName: any;
  selectedContact: any;
  selectedContactName: any;
  selectedSkill: any;
  selectedskillName: any;
  selectedDesignation: any;
  selectedDesignationName: any;
  selectedCertificate: any;
  selectedcertificateName: any;
  selectedqualification: any;
  selectedqualificationName: any;
  selectedLocation: any;
  selectedlocationName: any;
  jobopeningData;
  qual: any;
  loc: any;
  locations: any;
  designations: any;
  des: any;
  certificates: any;
  cer: any;
  userId: string;
  userRole: string;
  pageSize = 4;

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
      '1',
      '2',
      '3',
      '4',
      '4+'
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
    jobdescription: ['',Validators.required],
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
    daysMonths:[]
  })
  totalPages: any;
  locationDetails: any;
  hotcommemt: any;


  constructor(private formBuilder: FormBuilder, private toaster: ToastrManager, private router: Router, private addclientservice: SuperadminserviceService, private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.userId = sessionStorage.getItem('userId');
    this.userRole = sessionStorage.getItem('role');
    this.jobOpeningId = this.activatedRoute.snapshot.paramMap.get('id');
    this.userToken = sessionStorage.getItem('Token');
    this.regId = sessionStorage.getItem('registrationId');
    console.log("form data..........", this.requirementForm);
    this.getjobOpeningById();
    this.getSkills();
    this.getCertificates();
    this.getQualification();
    this.getDesignation();
    this.getLocation();
  }

  get f() {
    return this.requirementForm.controls;
  }

  getjobOpeningById() {
    var id = {
    bdmId: this.jobOpeningId,
      registrationId: this.regId
    }
    this.addclientservice.getjobById(id).subscribe(res => {

      this.jobOpening = res;
      this.jobOpeningListById = this.jobOpening.result;
      this.clientDetails = this.jobOpeningListById.client;
      this.addContactDetails = this.jobOpeningListById.addContact;
      this.skills = this.jobOpeningListById.skills;
      this.qualifications = this.jobOpeningListById.qualifications;
      this.locations = this.jobOpeningListById.locations;
      this.designations = this.jobOpeningListById.designations;
      this.certificates = this.jobOpeningListById.certificates;
      this.hotcommemt=this.jobOpeningListById.isHot;

      console.log(this.jobOpeningListById, "jobOpening list by id----");

      for (let i = 0; i < this.skills.length; i++) {
        this.ski = this.skills[i].skillName
      }

      for (let i = 0; i < this.qualifications.length; i++) {
        this.qual = this.qualifications[i].qualificationName
      }

      for (let i = 0; i < this.locations.length; i++) {
        this.loc = this.locations[i].locationName
      }

      for (let i = 0; i < this.designations.length; i++) {
        this.des = this.designations[i].designation
      }

      for (let i = 0; i < this.certificates.length; i++) {
        this.cer = this.certificates[i].certificationName
      }

      this.requirementForm.controls.clientName.setValue(this.clientDetails.clientName);
      this.requirementForm.controls.contactName.setValue(this.addContactDetails.firstName);
      this.requirementForm.controls.postingTitle.setValue(this.jobOpeningListById.nameOfRequirement);
      this.requirementForm.controls.noOfPositions.setValue(this.jobOpeningListById.noOfPositions);
      this.requirementForm.controls.openDate.setValue(formatDate(this.jobOpeningListById.requirementStartdate, 'yyyy-MM-dd', 'en'));
      this.requirementForm.controls.endDate.setValue(formatDate(this.jobOpeningListById.requirementEndDate, 'yyyy-MM-dd', 'en'));
      this.requirementForm.controls.experience.setValue(this.jobOpeningListById.totalExperience);
      this.requirementForm.controls.Country.setValue(this.jobOpeningListById.country);
      this.requirementForm.controls.skill.setValue(this.ski);
      this.requirementForm.controls.otherskill.setValue(this.jobOpeningListById.otherSkills);
      this.requirementForm.controls.qualification.setValue(this.qual);
      this.requirementForm.controls.location.setValue(this.loc);
      this.requirementForm.controls.jobType.setValue(this.jobOpeningListById.jobType);
      this.requirementForm.controls.designation.setValue(this.des);
      this.requirementForm.controls.certificate.setValue(this.cer);
      this.requirementForm.controls.jobdescription.setValue(this.jobOpeningListById.jobDesc);
      this.requirementForm.controls.otherqualification.setValue(this.jobOpeningListById.otherQualification);
      this.requirementForm.controls.openingStatus.setValue(this.jobOpeningListById.requirementStatus);
      this.requirementForm.controls.minBudget.setValue(this.jobOpeningListById.minBudget);
      this.requirementForm.controls.maxBudget.setValue(this.jobOpeningListById.maxBudget);
      this.requirementForm.controls.ExpectedRevenue.setValue(this.jobOpeningListById.expectedRevenue);
      this.requirementForm.controls.ActualRevenue.setValue(this.jobOpeningListById.actualRevenue);
      this.requirementForm.controls.MissedRevenue.setValue(this.jobOpeningListById.missedRevenue);
      this.requirementForm.controls.RevenueperPosition.setValue(this.jobOpeningListById.revenuePerPosition);
      this.requirementForm.controls.city.setValue(this.jobOpeningListById.city);
      this.requirementForm.controls.budgettype.setValue(this.jobOpeningListById.budgetTypeForPermanent);
      this.requirementForm.controls.amount.setValue(this.jobOpeningListById.budgetAmountForPermanent);
      this.requirementForm.controls.durationOfContract.setValue(this.jobOpeningListById.durationOfContract);
      this.requirementForm.controls.noticePeriod.setValue(this.jobOpeningListById.noticePeriod);
      this.requirementForm.controls.daysMonths.setValue(this.jobOpeningListById.noticePeriodType);
      this.requirementForm.controls.requirementType.setValue(this.jobOpeningListById.requirementType);
      this.requirementForm.controls.RevenueperPosition.setValue(this.jobOpeningListById.revenuePerPosition);
    });
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
      this.locationDetails=this.locationData.result;

      console.log(this.locationDetails, "get location data---------")
    })
  }

  save() {
    if (this.requirementForm.invalid) {
      console.log(this.requirementForm.value, "registorm data");
      this.toaster.errorToastr('Please Fill the Mandatory Fields', 'Notification');
    }
    else {
      // skills
      var skill = this.requirementForm.value.skill;
      for (let i = 0; i < this.skillDetails.length; i++) {
        if (this.skillDetails[i].skillName == skill) {
          this.selectedSkill = this.skillDetails[i].id;
          this.selectedskillName = this.skillDetails[i].skillName;
          console.log(this.selectedSkill, "this is id by selecting skills");
        }
      }

      // designation
      var designation = this.requirementForm.value.designation;
      for (let i = 0; i < this.designationDetails.length; i++) {
        if (this.designationDetails[i].designation == designation) {
          this.selectedDesignation = this.designationDetails[i].id;
          this.selectedDesignationName = this.designationDetails[i].designation;
          console.log(this.selectedDesignation, "this is id by selecting designation");
        }
      }

      // certificates
      var certificate = this.requirementForm.value.certificate;
      for (let i = 0; i < this.certificateDetails.length; i++) {
        if (this.certificateDetails[i].certificationName == certificate) {
          this.selectedCertificate = this.certificateDetails[i].id;
          this.selectedcertificateName = this.certificateDetails[i].certificationName;
          console.log(this.selectedCertificate, 'this is id by selecting certificates');
        }
      }

      // qualification
      var qualification = this.requirementForm.value.qualification;
      for (let i = 0; i < this.qualificationDetails.length; i++) {
        if (this.qualificationDetails[i].qualificationName === qualification) {
          this.selectedqualification = this.qualificationDetails[i].id;
          this.selectedqualificationName = this.qualificationDetails[i].qualificationName;
          console.log(this.selectedqualification, 'this is id by selecting qualification');
        }
      }

      // location
      var location = this.requirementForm.value.location;
      for (let i = 0; i < this.locationDetails.length; i++) {
        if (this.locationDetails[i].locationName === location) {
          this.selectedLocation = this.locationDetails[i].id;
          this.selectedlocationName = this.locationDetails[i].locationName;
          console.log(this.selectedLocation, 'this is id by selecting location');
        }
      }

      let data = {
        id: this.jobOpeningId,
        client: {
          id: this.clientDetails.id
        },
        addContact: {
          id: this.addContactDetails.id
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
        noticePeriodType:this.requirementForm.value.daysMonths,
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
        isHot:this.hotcommemt
      }

      this.addclientservice.addjobOpening(data).subscribe(res => {
        console.log(res, 'sucessflly jobProfile is edited');
        this.jobopeningData = res;
        if (this.jobopeningData.status === 'DuplicateRecord') {
          this.toaster.warningToastr('DuplicateRecord !!', 'Warning!');
        } else if (this.jobopeningData.status === 'StatusSuccess') {
          this.toaster.successToastr('Profile Edited successfully');
          this.router.navigate(['/superadmin/jobopening']);
        }
      });
    }
  }

}
