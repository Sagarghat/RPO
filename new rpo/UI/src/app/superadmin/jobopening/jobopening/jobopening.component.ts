import { OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { ToastrManager } from 'ng6-toastr-notifications';
import { environment } from 'src/environments/environment';
import { SuperadminserviceService } from '../../superadminservice.service';
import { Component, ElementRef, HostListener } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ExcelService } from 'src/app/excel.service';
declare var $;

@Component({
  selector: 'app-jobopening',
  templateUrl: './jobopening.component.html',
  styleUrls: ['./jobopening.component.css']
})
export class JobopeningComponent implements OnInit {



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
  data: [];
  obj: any;
  searchInput: any;
  pageNumsearch: number;
  pageSizesearch: number;
  registrationIdsearch: number;
  postdata;
  getnoteDeletedata;
  profileExcel: any;
  ishotVar: number;
  jobDesc: any;
  search_values(event) {
    this.searchInput = event;
  }

  // search code end

  // tslint:disable-next-line:member-ordering
  first: any;
  profileData;
  createdById: any;
  profileDetails: any;
  RegId: any;
  userToken;
  jobOpeningId: any;
  jobOpening;
  jobOpeningListById: any;
  clientDetails: any;
  addContactDetails: any;
  skills: any;
  qualifications: any;
  locations: any;
  certificates: any;
  designations: any;
  clientName: any;
  phone: any;
  postingTitle: any;
  lead;
  leaddata: any;
  recruiterdata: any;
  recruiter;
  checkedList: any[];
  marked: any;
  selectedCandidate: any;
  id: any;
  q: any;
  clientId: any;
  clientid: any;
  m: any;
  parsedJson = [];
  checkedlist: [];
  abc: string;
  checkedlista: any = [];
  createdId: any;
  assignedRecruiterData;
  date: any;
  email: any;
  startDate: any;
  endDate: any;
  jobType: any;
  totalExperience: any;
  minBudget: any;
  maxBudget: any;
  designationName: any;
  certificationName: any;
  skillName: any;
  qualificationName: any;
  jobDescp: any;
  ishot: any;
  baseUrl = `${environment.serviceUrl}`;
  userId: string;
  userRole: string;
  totalItems;
  pageSize = 10;
  currentPage = 1;
  markAsHotForm = this.formBuilder.group({
    comment: ['', Validators.required],
  });
  markedHot: any;
  checked: any;
  selectedRadio: any;
  isHot: void;
  isHotComment: any;
  jobopeningstatus: any;
  getnotedata: any;
  getdetails: any;
  fileattachment: any;
  fileattachmentData: any;
  serchform = this.formBuilder.group({
    amName: [''],
    reqEndDate: [''],
    reqstartDate: [''],
    id: [''],
    requirementStatus: [''],
    isHotcomment: ['']

  });
  setPage(pagNo: number): any {
    this.currentPage = pagNo;
    this.profileGetData();
  }


  noteForm = this.formBuilder.group({
    note: ['', Validators.required],
    noteSelect: ['', Validators.required]
  })
  constructor(private excel: ExcelService, private formBuilder: FormBuilder, private route: Router, private addClientService: SuperadminserviceService, private toaster: ToastrManager) {
    this.pageNumsearch = 1;
    this.pageSizesearch = 5;
    this.registrationIdsearch = 43;
  }

  addprop1(lah) {
    console.log(lah, "lah");

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
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          registrationId: this.RegId,
          flag: true,
          jobOpeningTitle: term
        };
        console.log("post values", post1)
        if (term != '') {
          this.addClientService.getAllJobOpenings(post1).subscribe(
            resp => {
              this.profileData = resp;
              this.profileDetails = this.profileData.result;
              this.totalItems = this.profileData.totalRecords;
              if (this.profileData.status === "Accepted") {
                this.search_result = true;
                this.data = this.profileData.result;
                this.showNodata = false;
              } else if (this.profileData.status === "DataIsEmpty") {
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
    this.userRole = sessionStorage.getItem('role');
    this.RegId = sessionStorage.getItem('registrationId');
    this.userToken = sessionStorage.getItem('Token');

    this.profileGetData();
    this.Excel();
    this.getRecruiter();

    // this.getnoteData();
    this.saveAssignRecruiter();


  }

  downloadExcel() {

    console.log(this.profileDetails, "fffffffff")
    this.excel.exportAsExcelFile(this.profileDetails, 'Job Openings');
  }


  status = true;
  ShowHideClick() {
    this.status = false;
  }


  save() {
    if (this.serchform.value.isHotcomment === true)
      this.ishotVar = 1;
    else if (this.serchform.value.isHotcomment === false)
      this.ishotVar = 0;
    else
      this.ishotVar = null;



    const searchData = {
      pageNum: this.currentPage,
      pageSize: this.pageSize,
      registrationId: this.RegId,
      flag: true,
      amName: this.serchform.value.amName,
      reqEndDate: Date.parse(this.serchform.value.reqEndDate),
      reqstartDate: Date.parse(this.serchform.value.reqstartDate),
      bdmId: this.serchform.value.id,
      status: this.serchform.value.requirementStatus,
      isHot: this.ishotVar

    };




    this.addClientService.getAllJobOpenings(searchData).subscribe(res => {
      this.profileData = res;
      this.profileDetails = this.profileData.result;
      this.totalItems = this.profileData.totalRecords;
      console.log(this.totalItems, "totalItems");
      console.log(this.profileDetails, " Profile data---------")
      for (let i in this.profileDetails) {
        console.log(this.profileDetails[i].isHot, "is hot");
      }
    });
  }

  clickCandidateFunction() {
    this.route.navigate(['./clickCandidate']);
  }

  profileGetData() {
    var profileData = {
      pageNum: this.currentPage,
      pageSize: this.pageSize,
      registrationId: this.RegId,
      flag: true
    }

    this.addClientService.getAllJobOpenings(profileData).subscribe(resp => {
      this.profileData = resp;
      this.profileDetails = this.profileData.result;
      this.totalItems = this.profileData.totalRecords;
      console.log(this.totalItems, "totalItems");
      this.clientId = this.profileDetails.clientId;
      console.log(this.profileDetails, " Profile data---------")
      for (let i in this.profileDetails) {
        console.log(this.profileDetails[i].isHot, "is hot");
      }
    })
  }

  Excel() {
    var profileData = {
      pageNum: this.currentPage,
      pageSize: this.pageSize,
      registrationId: this.RegId,
      flag: false
    }

    this.addClientService.getAllJobOpenings(profileData).subscribe(resp => {
      this.profileData = resp;
      this.profileDetails = this.profileData.result.json();

    })
  }

  getjobOpeningById(p) {
    this.jobOpeningId = p;
    var id = {
      bdmId: this.jobOpeningId,
      registrationId: this.RegId
    }
    this.addClientService.getjobById(id).subscribe(res => {


      this.jobOpening = res;
      this.jobOpeningListById = this.jobOpening.result;
      this.clientDetails = this.jobOpeningListById.client;
      this.addContactDetails = this.jobOpeningListById.addContact;
      this.clientName = this.clientDetails.clientName;
      this.email = this.clientDetails.email;
      this.startDate = this.jobOpeningListById.requirementStartdate;
      this.endDate = this.jobOpeningListById.requirementEndDate;
      this.jobType = this.jobOpeningListById.jobType;
      this.totalExperience = this.jobOpeningListById.totalExperience;
      this.minBudget = this.jobOpeningListById.minBudget;
      this.maxBudget = this.jobOpeningListById.maxBudget;
      this.phone = this.addContactDetails.phone;
      this.postingTitle = this.jobOpeningListById.nameOfRequirement;
      this.skills = this.jobOpeningListById.skills;
      this.qualifications = this.jobOpeningListById.qualifications;
      this.locations = this.jobOpeningListById.locations;
      this.designations = this.jobOpeningListById.designations;
      this.certificates = this.jobOpeningListById.certificates;
      this.jobDescp = this.jobOpeningListById.jobDesc;
      console.log(this.jobDescp, "fgfg");
      this.fileattachment = this.jobOpeningListById.fileAttachements;
      if (this.fileattachment != null) {
        this.fileattachmentData = this.fileattachment
      }
      else {
        this.fileattachmentData = "No Attachment Found"
      }
      console.log("file", this.fileattachment)
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
      this.isHot = this.jobOpeningListById.comment;
      if (this.isHot != null) {
        this.isHotComment = this.isHot
      }
      else {
        this.isHotComment = "Not Marked As Hot"
      }


      if (this.jobDescp === null || this.jobDescp === '' || this.jobDescp === undefined) {
        this.jobDesc = "No Description found"
      }
      else {
        this.jobDesc = this.jobDescp;

      }

      this.jobopeningstatus = this.jobOpeningListById.requirementStatus;
      if (this.jobopeningstatus != null) {
        this.jobopeningstatus = this.jobopeningstatus;
      }
      else {
        this.jobopeningstatus = "Open"
      }
    });
    this.getnoteData();

  }

  getRecruiter() {
    this.addClientService.getRecruiter(this.RegId).subscribe(res => {
      console.log(res, 'recruiter list');
      this.recruiter = res,
        this.recruiterdata = this.recruiter.result,
        console.log(this.recruiterdata, "recruiter result");
    });
  }

  getId(clientId, bdmId, createdId) {
    this.clientid = clientId;
    this.id = bdmId;
    this.createdId = createdId;
  }

  onChange(email: number, isChecked: boolean) {
    if (isChecked) {
      this.checkedlista.push(email);
    }
    else {
      let index = this.checkedlista.indexOf(email);
      this.checkedlista.splice(index, 1);
    }
    this.abc = this.checkedlista;
    console.log("multi checked list with id for assigned recruiter(s)", this.checkedlista);
  }

  saveAssignRecruiter() {
    var recruiterData =
    {
      recruiterIdsList: this.checkedlista,
      registrationId: JSON.parse(this.RegId),
      clientId: JSON.parse(this.clientid),
      userId: this.userId,
      assignedDate: Date.now(),
      requirementId: JSON.parse(this.id)
    }
    this.addClientService.saveAssignRecruiter(recruiterData).subscribe(res => {
      console.log(res, 'Sucessfully recruiter is assigned');
      this.assignedRecruiterData = res;
      if (this.assignedRecruiterData.status === 'StatusSuccess') {
        $("#assignedRecruiter").modal("hide");
        this.toaster.successToastr('Recruiter assigned successfully');
      } else {
        this.toaster.warningToastr('Failed to assign recruiter !!', 'Warning!');
      }
    });
  }

  saveMarkAsHot() {
    this.q = this.id;
    this.m = this.clientid;
    console.log(this.q);
    console.log(this.m);
    if (this.markAsHotForm.invalid) {
      this.ishot = 0;
    }
    else {
      this.ishot = 1;
    }
    var markData = {
      bdmId: JSON.parse(this.q),
      registrationId: JSON.parse(this.RegId),
      clientId: this.m,
      isHot: this.ishot,
      comment: this.markAsHotForm.value.comment
    }
    this.addClientService.saveMarkAsHot(markData).subscribe(resp => {
      this.markedHot = resp;
      if (this.markedHot.status == "StatusSuccess") {
        this.profileGetData();
        this.toaster.successToastr('Id: ' + this.q + ' is  marked as hot');
        this.markAsHotForm.reset();
      }
      if (this.markedHot.status == "Bad_Request") {
        this.toaster.warningToastr('Please enter comment!!!');
      }
      console.log(this.markedHot, "markHot");
    })
  }

  jobnameClick(id) {
    this.route.navigate(['superadmin/jobopening/jobnameclick', id]);
  }

  download() {
    var filepdf = 'data:application/pdf;base64,' + this.fileattachment;
    let a = document.createElement('a');
    a.href = filepdf;
    a.download = 'jobDescription.pdf';
    a.click();
  }




  notesave() {
    if (this.noteForm.invalid) {
      this.toaster.warningToastr('Please enter note description');
    }
    else {
      let notesave = {
        registrationId: this.RegId,
        // requirementId: this.jobOpeningId,
        noteData: this.noteForm.value.noteSelect,
        noteType: this.noteForm.value.note,
        recruiterId: this.jobOpeningId
      }
      this.addClientService.notesave(notesave).subscribe(res => {
        this.postdata = res
        if (this.postdata.status == "StatusSuccess") {
          this.toaster.successToastr('Note added successfully');
          this.getnoteData();
          this.noteForm.reset();
        }
        console.log(res, "save note");
        // this.getnoteData();
      })
    }

  }


  getnoteData() {
    debugger;
    let getnotedata = {
      registrationId: this.RegId,
      // requirementId: this.jobOpeningId,
      pageSize: 20,
      pageNum: 1,
      resourceId: this.jobOpeningId
    }

    this.addClientService.getnoteData(getnotedata).subscribe(res => {
      this.getnotedata = res;
      this.getdetails = this.getnotedata.result;

      console.log(this.getdetails, "getnotedetails");

    })
  }

  notedeletedata(id) {
    this.addClientService.notedeletedata(id).subscribe(res => {
      this.getnoteDeletedata = res;
      if (this.getnoteDeletedata.status == "StatusSuccess") {
        this.toaster.successToastr('Note deleted successfully');
        this.getnoteData();
      }
      else {
        this.toaster.warningToastr('Error deleting the note', 'Warning!');
      }
      console.log(this.getnoteDeletedata, "note delete details");
    })
  }

}