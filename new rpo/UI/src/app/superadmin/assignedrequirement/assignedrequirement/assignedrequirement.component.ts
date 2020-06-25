import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormControl, FormBuilder } from '@angular/forms';
import { ToastrManager } from 'ng6-toastr-notifications';
import { SuperadminserviceService } from '../../superadminservice.service';
import { ExcelService } from 'src/app/excel.service';
declare var $;

@Component({
  selector: 'app-assignedrequirement',
  templateUrl: './assignedrequirement.component.html',
  styleUrls: ['./assignedrequirement.component.css']
})
export class AssignedrequirementComponent implements OnInit {
  first: any;
  RegId: any;
  assignedRequirementDetails: any;
  assignedData;
  userId: string;
  assignedRequirementDelete: any;
  recruiterid: any;
  totalItems;
  pageSize = 10;
  currentPage = 1;
  jobOpeningId: any;
  jobOpening;
  jobOpeningListById: any;
  clientDetails: any;
  addContactDetails: any;
  clientName: any;
  email: any;
  startDate: any;
  jobType: any;
  totalExperience: any;
  endDate: any;
  maxBudget: any;
  minBudget: any;
  phone: any;
  skills: any;
  postingTitle: any;
  qualifications: any;
  locations: any;
  certificates: any;
  designations: any;
  fileattachment: any;
  jobDesc: any;
  certificationName: any;
  isHot: any;
  isHotComment: any;
  skillName: any;
  qualificationName: any;
  designationName: any;
  jobopeningstatus: any;
  requirementType: any;
  id: any;
  clientId: any;
  assignedBy: any;

  // serach code
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
  registrationId: any;
  searchvalue: any;
  data1: [];
  obj: any;
  searchInput: any;
  pageNumsearch: number;
  pageSizesearch: number;
  registrationIdsearch: number;
  details;
  search_values(event) {
    this.searchInput = event;
  }
  // search code end

  serchform = this.formBuilder.group({
    requirementName: [''],
    clientName: [''],
    clientId: [''],
    leadName: [''],
    assignedDate: ['']
  });


  setPage(pagNo: number): void {
    this.currentPage = pagNo;
    this.assignedRequirementsData();
  }

  constructor(private excel: ExcelService, private formBuilder: FormBuilder, private route: Router, private http: HttpClient, private addClientService: SuperadminserviceService, private activatedRoute: ActivatedRoute, private toaster: ToastrManager) {

  }

  ngOnInit() {
    $(document).click(function (e) {
      console.log(e);
      if (e.target.id == "usr") {
        $(".item").show();
      }
      else {

        $(".item").hide();
      }
    });
    this.searchTerm.valueChanges.subscribe(
      term => {
        var post1 = {
          registrationId: this.RegId,
          pageSize: this.pageSize,
          pageNum: this.currentPage,
          userId: this.userId,
          flag: true,
          requirementName: term
        };
        console.log("post values", post1)
        if (term != '') {
          this.addClientService.getAssignedRequirements(post1).subscribe(
            resp => {
              this.details = resp;
              this.assignedRequirementDetails = this.details.result;
              this.totalItems = this.assignedRequirementDetails.totalRecords;
              if (this.details.status === "Accepted") {
                this.search_result = true;
                this.showNodata = false;
              } else if (this.details.status === "DataIsEmpty") {
                this.showNodata = true;
                if (this.showNodata == true) {
                  this.search_result = false;
                  this.search_Previous_result = true;
                }
              }
            })
        }
      });

    this.userId = sessionStorage.getItem('userId');
    this.RegId = sessionStorage.getItem('registrationId');
    this.assignedRequirementsData();
    this.getExcel();
  }

  save() {
    var recruitersData = {
      registrationId: JSON.parse(this.RegId),
      pageSize: this.pageSize,
      pageNum: this.currentPage,
      userId: JSON.parse(this.userId),
      flag: true,
      requirementName: this.serchform.value.requirementName,
      recruiterName: this.serchform.value.leadName,
      assignedDate: Date.parse(this.serchform.value.assignedDate),
      clientName: this.serchform.value.clientName,
      clientId: this.serchform.value.clientId,

    }
    this.addClientService.getAssignedRequirements(recruitersData).subscribe(res => {
      console.log(res, "total searched assigned requirements list----------");
      this.assignedData = res;
      this.totalItems = this.assignedData.totalRecords;
      console.log(this.totalItems, "total assigned requirements by searching ");
      this.assignedRequirementDetails = this.assignedData.result;
      console.log(this.assignedRequirementDetails, "Assigned Requirements data---------");
    });
  }

  assignedRequirementsData() {
    var recruitersData = {
      registrationId: this.RegId,
      pageSize: this.pageSize,
      pageNum: this.currentPage,
      userId: this.userId,
      flag: true
    }
    this.addClientService.getAssignedRequirements(recruitersData).subscribe(resp => {
      this.assignedData = resp;
      this.totalItems = this.assignedData.totalRecords;
      console.log(this.totalItems, "total assigned requirements from getAll ");
      this.assignedRequirementDetails = this.assignedData.result;
      console.log(this.assignedRequirementDetails, "Assigned Requirements data---------");
    })
  }

  getExcel() {
    const post = {
      registrationId: this.RegId,
      pageSize: this.pageSize,
      pageNum: this.currentPage,
      userId: this.userId,
      flag: false
    };
    this.addClientService.getAssignedRequirements(post).subscribe(resp => {
      this.assignedData = resp;
      this.assignedRequirementDetails = this.assignedData.result.json();
      console.log(this.assignedRequirementDetails, "Assigned Requirements data---------");
    })
  }

  downloadExcel() {
    console.log(this.assignedRequirementDetails, "assigned Requirement Details")
    this.excel.exportAsExcelFile(this.assignedRequirementDetails, 'assigned Requirements');
  }

  deleteAssignedRequirements(clientId, requirementId, recruiterid) {
    var recruitersDeleteData = {
      recruiterIdsList: [recruiterid],
      registrationId: this.RegId,
      userId: this.userId,
      clientId: clientId,
      requirementId: requirementId
    }
    this.addClientService.deleteAssignedRequirements(recruitersDeleteData).subscribe(resp => {
      this.assignedRequirementDelete = resp;
      console.log(this.assignedRequirementDelete, "Assigned Requirements delete---------");
      if (this.assignedRequirementDelete.status === 'StatusSuccess') {
        this.toaster.successToastr('Requirement deleted successfully');
      }
      this.assignedRequirementsData();
    })
  }

  getRequirementById(p, clientId, assignedBy) {
    this.clientId = clientId;
    this.assignedBy = assignedBy;
    this.jobOpeningId = p;
    var id = {
      bdmId: this.jobOpeningId,
      registrationId: this.RegId
    }
    this.addClientService.getjobById(id).subscribe(res => {
      this.jobOpening = res;
      this.jobOpeningListById = this.jobOpening.result;
      console.log(this.jobOpeningListById, "job Opening List by ID for description modal");
      this.requirementType = this.jobOpeningListById.requirementType
      this.id = this.jobOpeningListById.id;
      this.clientDetails = this.jobOpeningListById.client;
      this.addContactDetails = this.jobOpeningListById.addContact;
      this.clientName = this.clientDetails.clientName;
      this.email = this.clientDetails.email;
      this.startDate = this.jobOpeningListById.requirementStartdate;
      this.endDate = this.jobOpeningListById.requirementEndDate;
      this.jobType = this.jobOpeningListById.jobType;
      this.totalExperience = this.jobOpeningListById.totalExperience;
      this.minBudget = this.jobOpeningListById.minBudget;
      this.postingTitle = this.jobOpeningListById.nameOfRequirement;
      this.skills = this.jobOpeningListById.skills;
      this.qualifications = this.jobOpeningListById.qualifications;
      this.locations = this.jobOpeningListById.locations;
      this.designations = this.jobOpeningListById.designations;
      this.certificates = this.jobOpeningListById.certificates;
      this.jobDesc = this.jobOpeningListById.jobDesc;
      this.maxBudget = this.jobOpeningListById.maxBudget;
      this.phone = this.addContactDetails.phone;
      this.isHot = this.jobOpeningListById.comment;

      if (this.minBudget == null || this.minBudget == '') {
        this.minBudget = " -";
      }
      else {
        this.minBudget = this.minBudget;
      }

      if (this.maxBudget == null || this.maxBudget == '') {
        this.maxBudget = " -";
      }
      else {
        this.maxBudget = this.maxBudget;
      }

      if (this.phone == null || this.phone == '') {
        this.phone = " -";
      }
      else {
        this.phone = this.phone;
      }

      for (let i = 0; i < this.designations.length; i++) {
        this.designationName = this.designations[i].designation;
      }
      for (let i = 0; i < this.qualifications.length; i++) {
        this.qualificationName = this.qualifications[i].qualificationName;
      }
      for (let i = 0; i < this.skills.length; i++) {
        this.skillName = this.skills[i].skillName;
      }
      for (let i = 0; i < this.certificates.length; i++) {
        this.certificationName = this.certificates[i].certificationName;
      }

      if (this.isHot != null) {
        this.isHotComment = this.isHot;
        console.log("status comment", this.isHotComment);
      }
      else {
        this.isHotComment = "No Records found";
      }

      if (this.jobDesc! = null) {
        this.jobDesc = this.jobDesc;
      }
      else {
        this.jobDesc = "No Description found"
      }
    });
  }

}
