import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class SuperadminserviceService {

 
  baseUrl = `${environment.serviceUrl}`;
 userToken;
 
  flag: boolean;
  currentpage=1;
  pagesize: any=4;
  // flag: boolean;


  constructor(public http:HttpClient) { 
    this.userToken = sessionStorage.getItem('Token');
   
    // console.log(this.userToken, 'Token -----');
   
  }

  // Behaviour Subject Starts

  private plansId = new BehaviorSubject<any>({});
  planID = this.plansId.asObservable();
  editplan(planObj) {
    this.plansId.next(planObj);
  }

  // Behaviour Subject Ends
  getPlans(pageNumber,pageSize) {
    return this.http.get(this.baseUrl + 'plans/findAll/'+pageNumber+'/'+pageSize);
  }
  signup(data) {
    return this.http.post(this.baseUrl + 'companyreg/registration', data, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
    }

    );
  }

  activate_status(id) {
    return this.http.post(this.baseUrl + 'companyreg/activate/' + id, null);
  }

  login(userdata) {
    return this.http.post(this.baseUrl + 'user/authenticate', userdata, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/x-www-form-urlencoded')
    }
    );   
  }
  getuserList(postUserdata){
    return this.http.post(this.baseUrl + 'Reg/search' , postUserdata,{
      headers: {
        'X-Access-Token': this.userToken,
      }
    });

  }

  saveUser(userData){
   return  this.http.post(this.baseUrl + 'Reg/adduser', userData, {
      headers: {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    });
  }
  
  getUserById(UserId,RegId){
   return this.http.get(this.baseUrl + 'Reg/getUserById/' + UserId + '/' + RegId, {
      headers:
      {
        'X-Access-Token': this.userToken,
      }
    });
  }

  editUser(UserId,RegId,userData){
   return  this.http.put(this.baseUrl + 'Reg/updateRegId/' + UserId + '/' + RegId + '/', userData, {
      headers: {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    });
  }

  getpermission(){
    return this.http.get(this.baseUrl + 'permissions/findAll/1/5' , {
      headers:
      {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    });
  }
  
  getFeatures(){    
    return this.http.get(this.baseUrl + 'featureResource/getAll/1/20' , {
      headers:
      {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    });
  }
//interview apis


getShortlistedCandidates(data){
  return this.http.post(this.baseUrl + 'candidatereqmapping/mappinglist',data,
   {
     headers: {
       'X-Access-Token': this.userToken,
     }
   });
 }

getInterviewNameList(pazeNo,pazeSize){
 return this.http.get(this.baseUrl + 'modeofInterview/findAll/'+pazeNo+'/'+pazeSize,
  {
    headers: {
      'X-Access-Token': this.userToken,
    }
  });
}
getInterviewRound(pazeNo,pageSize){
  return this.http.get(this.baseUrl + 'intervieweround/findAll/'+pazeNo+'/'+pageSize,
   {
     headers: {
       'X-Access-Token': this.userToken,
     }
   });
 }

getAssessmentNameList(){
  return this.http.get(this.baseUrl + 'assesmentNameResource/getAll',
  {
    headers: {
      'X-Access-Token': this.userToken,
    }
  });
}
getRemainderList(){
  return this.http.get(this.baseUrl + 'remainderResource',
  {
    headers: {
      'X-Access-Token': this.userToken,
    }
  });
}




addInterview(interviewdata){
  return  this.http.post(this.baseUrl + 'interviewDetails', interviewdata, {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  });
}
getInterviewList(interview){
  return  this.http.post(this.baseUrl + 'interviewDetails/search', interview, {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  })
}
getInterviewById(id){
  return this.http.get(this.baseUrl + 'interviewDetails/'+ id,
  {
    headers: {
      'X-Access-Token': this.userToken,
    }
  });
}
addCandidateStatusMaster(){
  return this.http.get(this.baseUrl + 'addCandidateStatus',
  {
    headers: {
      'X-Access-Token': this.userToken,
    }
  });
}

postInterviewFeedback(data){
  return  this.http.post(this.baseUrl + 'interviewfeedback',data, {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  });
}
saveInterviewnotes(notes){
  return  this.http.post(this.baseUrl + 'interviewnotes/save',notes, {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  });
}
getInterviewnotes(getnotes){
  return  this.http.post(this.baseUrl + 'interviewnotes/search',getnotes, {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  });
}

deleteInterviewnotes(deletenotesId){
  return  this.http.delete(this.baseUrl + 'interviewnotes/delete/' +deletenotesId, {
    headers: {
      'X-Access-Token': this.userToken,
      
    }
  });
}

  // client Apis


  getClientList(post){
  return  this.http.post(this.baseUrl + 'client/search', post, {
      headers: {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    })
  }

  getCustomertype(){
   return this.http.get(this.baseUrl + 'customerType/getdata/'+ this.currentpage +'/'+this.pagesize, {
      headers: {
        'X-Access-Token':this.userToken,
        'Content-Type': 'application/json'
      }
    })
  }

  getbillingmodel(){
    return this.http.get(this.baseUrl+'BillingModel/getdata/'+ this.currentpage +'/'+this.pagesize, {
      headers: {
        'X-Access-Token':  this.userToken,
        'Content-Type': 'application/json'
      }
    });
  }

  getPayment(){
    return this.http.get(this.baseUrl+'PaymentTerms/findAll/'+ this.currentpage +'/'+this.pagesize, {
      headers: {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    })
  }

  getservices(){
    return this.http.get(this.baseUrl+'services/findAll/'+ this.currentpage +'/'+this.pagesize,
    {
      headers: {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    })
  }

  getTypeAddress(){
    return this.http.get(this.baseUrl+'typeofaddress/findAll/1/1', {
      headers: {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    })
  }

  getAmList(RegId){
    this.flag=true;
    return this.http.get(this.baseUrl+'Reg/usersListByRegIdRole/1/10/'+ RegId+'/am'+'/'+this.flag, {
      headers: {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    })
  }


  getLead(regId){
      this.flag=true;
    return this.http.get(this.baseUrl+'Reg/usersListByRegIdRole/1/10/'+ regId+'/lead'+'/'+this.flag, {
      headers: {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    })
  }

  getSource(){
   return  this.http.get(this.baseUrl+'source/findAll/1/4', {
      headers: {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    })
  }

  getIndustry(){
    return this.http.get(this.baseUrl+'industry/search/'+ this.pagesize +'/'+this.currentpage, {
      headers: {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    })
  }

  saveClient(data){
  return this.http.post(this.baseUrl+'client/create', data, {
      headers: {
        'X-Access-Token':this.userToken,
        'Content-Type': 'application/json'
      }
    })
  }

  getClientById(clientId,RegId){
   return  this.http.get(this.baseUrl + 'client/findByIdandRegId/' + clientId + '/' + RegId, {
      headers: {
        'X-Access-Token': this.userToken
      }
    })
  }

  editClient(data){
    return  this.http.post(this.baseUrl + "client/create", data, {
      headers: {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    });
  }

  getclientnotes(getnotedata)
{
  return this.http.post(this.baseUrl+"clientNotes/search",getnotedata,{
      headers:{
        "X-Access-Token":this.userToken,
        "Content-Type":"application/json"
      }
    })
}

clientnotesave(saveclientnote){
  return this.http.post(this.baseUrl+"clientNotes/save",saveclientnote,{
    headers:{
      "X-Access-Token":this.userToken,
      "Content-Type":"application/json"
    }
  });
}

clientnotedelete(deletenotesId){
  return  this.http.delete(this.baseUrl + 'clientNotes/delete/' +deletenotesId, {
    headers: {
      'X-Access-Token': this.userToken,
      
    }
  });
}

sendClientMail(data){
  return this.http.post(this.baseUrl +'email/sendEmail' ,data,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  });
}

saveaddress(address){
  return this.http.post(this.baseUrl +'addressdetails' ,address,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  });
}

  deleteaddress(id){
    return this.http.delete(this.baseUrl+"addressdetails/delete/"+id,{
      headers:{
        "X-Access-Token":this.userToken,
        "Content-Type":"application/json"
      }
    });
  }

  editaddressId(id){
    return  this.http.get(this.baseUrl + 'addressdetails/' + id , {
      headers: {
        'X-Access-Token': this.userToken
      }
    });
  }

  updateaddress(id,details){
    return  this.http.post(this.baseUrl + 'addressdetails/' + id ,details, {
      headers: {
        'X-Access-Token': this.userToken
      }
    });
  }

// contact apis
  getContactList(post)
  {
   return this.http.post(this.baseUrl + 'addClientContact/search', post,
   {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
  }

 getOwner(currentpage,pagesize,RegId,role,flag)
 {
 this.flag=true;
  
   return this.http.get(this.baseUrl + 'Reg/usersListByRegIdRole/'+currentpage+'/'+pagesize+'/' +RegId+'/'+role +'/'+flag,
   {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
 }
 getContactSource(){
  return  this.http.get(this.baseUrl+'source/findAll/'+this.currentpage+'/'+this.pagesize,
  {
     headers: {
       'X-Access-Token': this.userToken,
       'Content-Type': 'application/json'
     }
   })
 }
 
 getContactTypeAddress(){
  return this.http.get(this.baseUrl+'typeofaddress/findAll/'+this.currentpage+'/'+this.pagesize, {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  })
}

 addcontactdata(data)
 {
  return this.http.post(this.baseUrl + 'addClientContact',data,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  });
 }

 contactnotesaveee(notesave)
{
  return this.http.post(this.baseUrl+"contactnotes/save",notesave,{
    headers:{
      "X-Access-Token":this.userToken,
      "Content-Type":"application/json"
    }
  })
}

getcontactnotes(getnotes){
  return  this.http.post(this.baseUrl + 'contactnotes/search',getnotes, {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  });
}
 
deletecontactnotes(id){
  return  this.http.delete(this.baseUrl + 'contactnotes/delete/' +id, {
    headers: {
      'X-Access-Token': this.userToken,
      
    }
  });
}

contactsaveaddress(address){
  return this.http.post(this.baseUrl +'Contact_address_map' ,address,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }
);
}


  contactdeleteaddress(id){
    return this.http.delete(this.baseUrl+"Contact_address_map/delete/"+id,{
      headers:{
        "X-Access-Token":this.userToken,
        "Content-Type":"application/json"
      }
    })
  }

  contactupdateaddress(id,details){
    return this.http.post(this.baseUrl+"Contact_address_map/"+id ,details,{
      headers:{
        "X-Access-Token":this.userToken,
        "Content-Type":"application/json"
      }
    })
  }


 getcontactById(id)
 {
 return this.http.get(this.baseUrl + 'addClientContact/' + id,
 {
   headers:{
    'X-Access-Token': this.userToken,
    'Content-Type': 'application/json'
   }
 });
 }

 getOwnerUserListById(registrationId)
 {
return this.http.get(this.baseUrl +'Reg/usersListByRegIdRole/1/5'+'/' + registrationId + '/AM',
{
  headers:{
    'X-Access-Token': this.userToken,
    'Content-Type': 'application/json'
  }
});
 }



 

// job opening apis

saveMarkAsHot(markData){
 return  this.http.post(this.baseUrl+'Bdmrequire/bdmupdate',markData,{
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  });
}


getAllJobOpenings(profileData)
{
  return this.http.post(this.baseUrl + 'Bdmrequire/getAllRequirementsByRegId', profileData, {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  })
}



// search code


search1(term) {
  return this.http.post('http://192.168.2.12:8080/rpo/rest/addcandidate/search' , term , {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
}  
// search code end



addjobOpening(data)
{
 return this.http.post(this.baseUrl + 'Bdmrequire/save',data,
 {
   headers:{
     'X-Access-Token': this.userToken,
     'Content-Type': 'application/json'
   }
 }); 
}

getjobById(id)
{
 return this.http.post(this.baseUrl + 'Bdmrequire/getRequirementById',id,
 {
   headers:{
     'X-Access-Token': this.userToken,
     'Content-Type': 'application/json'
   }
 }); 
}

count(data){
  return this.http.post(this.baseUrl+'candidatereqmapping/countbyreq',data,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
 }
 




skills(regId)
{
  return this.http.get(this.baseUrl + 'skill/findAll/1/5/'+regId, {
    headers: {
      'X-Access-Token':  this.userToken,
      'Content-Type': 'application/json'
    }
  })
}

Qualification()
{
  return this.http.get(this.baseUrl + 'Qualification/findAll/1/7', {
    headers: {
      'X-Access-Token':  this.userToken,
      'Content-Type': 'application/json'
    }
  })
}

Certificates()
{
  return this.http.get(this.baseUrl + 'certificates/findAll/2/2', {
    headers: {
      'X-Access-Token':  this.userToken,
      'Content-Type': 'application/json'
    }
  })
}

Designation()
{
  return this.http.get(this.baseUrl + 'designation', {
    headers: {
      'X-Access-Token':  this.userToken,
      'Content-Type': 'application/json'
    }
  })
}

Location(regId)
{
  return this.http.get(this.baseUrl + 'location/findAll/1/7/'+regId, {
    headers: {
      'X-Access-Token':  this.userToken,
      'Content-Type': 'application/json'
    }
  })
}

getRecruiter(regId){
  let body= new HttpParams()
  .set('role','recruiter')
  .set('regId',regId)
  

  return this.http.get(this.baseUrl+'Reg/getUserNamesByRole?'+body, {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  })
}

saveAssignRecruiter(recruiter)
{
  return this.http.post(this.baseUrl+'assignedRecruiters/create',recruiter, {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  })
}

notesave(notesave)
{
  return this.http.post(this.baseUrl+"reqnotes/save",notesave,{
    headers:{
      "X-Access-Token":this.userToken,
      "Content-Type":"application/json"
    }
  })
}



getnoteData(getnotedata)
{
  return this.http.post(this.baseUrl+"reqnotes/search",getnotedata,{
      headers:{
        "X-Access-Token":this.userToken,
        "Content-Type":"application/json"
      }
    })
}

notedeletedata(id)
{
  return this.http.delete(this.baseUrl+"reqnotes/delete/"+id,{
      headers:{
        "X-Access-Token":this.userToken,
        "Content-Type":"application/json"
      }
    })
}








getjobtitle()
 {
 return this.http.get(this.baseUrl + 'addCandidateJobTitle',
 {
   headers:{
    'X-Access-Token': this.userToken,
    'Content-Type': 'application/json'
   }
 })
 }

 primaryskill(){
  return  this.http.get(this.baseUrl+'/rest/skill/findAll/2/5', {
     headers: {
       'X-Access-Token':this.userToken,
       'Content-Type': 'application/json'
     }
   })
 }


 candidatestatus(){
  return  this.http.get(this.baseUrl+'addCandidateStatus', {
     headers: {
       'X-Access-Token': this.userToken,
       'Content-Type': 'application/json'
     }
   })
 }


 qualification(){
  return  this.http.get(this.baseUrl+'/Qualification/findAll/1/3', {
     headers: {
       'X-Access-Token': this.userToken,
       'Content-Type': 'application/json'
     }
   })
 }


 
 getuser(){
  return  this.http.get(this.baseUrl+'Reg/usersListByRegIdRole/1/10/35/AM', {
     headers: {
       'X-Access-Token': this.userToken,
       'Content-Type': 'application/json'
     }
   })
 }

 getallcandidateslist(post)
 {
  return  this.http.post(this.baseUrl+'addcandidate/search',post, {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  })
 }

 getRatingDetails(body)
 {
  return  this.http.post(this.baseUrl+'addcandidaterating/find',body, {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  })
 }
 
 getCandidateById(id){
  return  this.http.get(this.baseUrl + 'addcandidate/' +id , {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  });
}

candidatenotesave(notesave)
{
  return this.http.post(this.baseUrl+"candidatenotes/save",notesave,{
    headers:{
      "X-Access-Token":this.userToken,
      "Content-Type":"application/json"
    }
  })
}


addcanpost(data)
{
 return this.http.post(this.baseUrl + 'addcandidate',data,
 {
   headers:{
     'X-Access-Token': this.userToken,
     'Content-Type': 'application/json'
   }
 }); 
}



reports(data)
{

  return this.http.post(this.baseUrl + 'reports/reports' , data,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  })
}



candidateEmails(data)
{
  return this.http.post(this.baseUrl +'email/sendEmail' ,data,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }
  
);
}

contactEmails(data)
{
  return this.http.post(this.baseUrl +'email/sendEmail' ,data,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }
  
);
}
postmapping(data)
{
  return this.http.post(this.baseUrl +'candidatereqmapping',data,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  })
}



searchcandidate(term) {
  return this.http.post(this.baseUrl +'addcandidate/search' , term , {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
} 



 Reviewprofile(candidateId,bdmReqId,RegId)
 {

   return this.http.get(this.baseUrl + 'candidatereqmapping/reviewprofile' + "/"+candidateId+"/"+bdmReqId+"/"+RegId,
   {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
 
 }

 reviewstatus(data)
 {
   return this.http.post(this.baseUrl + 'candidatereqmapping/updatereviewprofilestatus',data,
   {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   })
 }


 getscreendetails(data){
  return this.http.post(this.baseUrl + 'candidatereqmapping/mappinglist' ,data, {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  });
}



mappings(data)
{

  return this.http.post(this.baseUrl +'candidatereqmapping/mappings' ,data,{
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  })
}

postcandidaterating(list){
  return  this.http.post(this.baseUrl + 'addcandidaterating',list, {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  });
}



getAssignedRequirements(recruitersData)
{
  return this.http.post(this.baseUrl+'assignedRecruiters/search', recruitersData, {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  })
}

deleteAssignedRequirements(recruitersDeleteData){
 return this.http.post(this.baseUrl+'assignedRecruiters/delete', recruitersDeleteData, {
      headers: {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    });
}

getCandidrating(ratiddd){
  return  this.http.get(this.baseUrl + 'addcandidaterating/' +ratiddd , {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  });
}

resumeparsing(data){
  return this.http.post(this.baseUrl+'addcandidate/uploadresume', data ,{
       headers: {
         'X-Access-Token': this.userToken,
        
        
       }
     });
 }

 submitcustomer(data)
 {
   return this.http.post(this.baseUrl +'candidatereqmapping/submittocustomer' ,data,{
    headers: {
      'X-Access-Token': this.userToken
     
    }
   });
 }

 shortlistclient(data)
 {
   return this.http.post(this.baseUrl +'candidatereqmapping/shortlistedbyclient' ,data,{
    headers: {
      'X-Access-Token': this.userToken
     
    }
   });
 }
 offerlist(data)
 {
   return this.http.post(this.baseUrl +'candidatereqmapping/offerrelease' ,data,{
    headers: {
      'X-Access-Token': this.userToken
     
    }
   });
 }

 interviewlist(data)
 {
   return this.http.post(this.baseUrl + 'candidatereqmapping/offerrelease' ,data,{
    headers: {
      'X-Access-Token': this.userToken
     
    }
   });
 }
confromoferstatus(data)
{
  return this.http.post(this.baseUrl +'candidatereqmapping/confirmofferstatus',data,{
    headers: {
      'X-Access-Token': this.userToken
     
    }
   })
}

Onboardstatus(data)
{
  return this.http.post(this.baseUrl +'candidatereqmapping/confirmonboardstatus',data,
  {
    headers: {
      'X-Access-Token': this.userToken
    }
  })
}













// Admin Serviceses



addSkills(skill) {
  return this.http.post(this.baseUrl + 'skill', skill,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
}


addModeofInterview(mod) {
  return this.http.post(this.baseUrl + 'modeofInterview', mod,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
}






addcustomers(cust) {
  return this.http.post(this.baseUrl + 'customerType', cust,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
}



addnoticePeriod(note) {
  return this.http.post(this.baseUrl + 'noticePeriod/save', note,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
}



 addqualifications(qualify) {
  return this.http.post(this.baseUrl + 'Qualification/save', qualify,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
 }



 addbillingModal(billing) {
  return this.http.post(this.baseUrl + 'BillingModel', billing,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
 }



 addserviceList(serve) {
  return this.http.post(this.baseUrl + 'services', serve,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
 }


 addpaymentTerms(pay) {
  return this.http.post(this.baseUrl + 'PaymentTerms', pay,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
 }



 addpermissions(permission){
  return this.http.post(this.baseUrl + 'permissions', permission ,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}

addroles(role){
  return this.http.post(this.baseUrl + 'roles', role,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}



addfeatures(features){
  return this.http.post(this.baseUrl + 'featureResource', features,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}

addcertificates(cer){
  return this.http.post(this.baseUrl + 'certificates', cer,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}


addjoblocation(loc){
  return this.http.post(this.baseUrl + 'location', loc,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}











// edit by ID Methods


editskills(skill,id) {
  return this.http.post(this.baseUrl + 'skill/' + id, skill,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
}

editModeofInterview(mod,id) {
  return this.http.post(this.baseUrl + 'modeofInterview/' + id, mod,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
}


editcustomerType(custype,id) {
  return this.http.post(this.baseUrl + 'customerType/' + id, custype,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
}


editnoticeperiod(note,id) {
  return this.http.post(this.baseUrl + 'noticePeriod/' + id, note,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
}

editqualifications(qualify,id) {
  return this.http.post(this.baseUrl + 'Qualification/' + id, qualify,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
}


editbillingModel(bill,id) {
  return this.http.post(this.baseUrl + 'BillingModel/' + id, bill,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
}



editservicelist(sl,id) {
  return this.http.post(this.baseUrl + 'services/' + id, sl,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
}


editpaymentTerms(pay,id) {
  return this.http.post(this.baseUrl + 'PaymentTerms/' + id, pay,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
}


editpermissions(perm,id) {
  return this.http.post(this.baseUrl + 'permissions/' + id, perm,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
}



editroles(role,id) {
  return this.http.post(this.baseUrl + 'roles/' + id, role,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
}

editfeatures(features,id) {
  return this.http.post(this.baseUrl + 'featureResource/' + id, features,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
}


editcertificates(cer,id) {
  return this.http.post(this.baseUrl + 'certificates/' + id, cer,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
}


editjoblocations(loc,id) {
  return this.http.post(this.baseUrl + 'location/' + id, loc,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
}




// get By Id Medhods


modeofInterviewgetId(id){
  return this.http.get(this.baseUrl + 'modeofInterview/' + id,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}


skillsgetId(id){
  return this.http.get(this.baseUrl + 'skill/' + id,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}

customertypegetId(id){
  return this.http.get(this.baseUrl + 'customerType/' + id,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}



noticeperiodgetId(id){
  return this.http.get(this.baseUrl + 'noticePeriod/' + id,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}


qualificationgetId(id){
  return this.http.get(this.baseUrl + 'Qualification/' + id,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}

billingmodelgetId(id){
  return this.http.get(this.baseUrl + 'BillingModel/'+ id,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}


servicelistgetId(id){
  return this.http.get(this.baseUrl + 'services/'+ id,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}



paymenttermsgetId(id){
  return this.http.get(this.baseUrl + 'PaymentTerms/' + id,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}


permissionsgetId(id){
  return this.http.get(this.baseUrl + 'permissions/getById/'+ id,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}


rolesgetId(id){
  return this.http.get(this.baseUrl + 'roles/' + id,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}


featuresgetId(id){
  return this.http.get(this.baseUrl + 'featureResource/' + id,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}


certificatesgetId(id){
  return this.http.get(this.baseUrl + 'certificates/' + id,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}



joblocationgetId(id){
  return this.http.get(this.baseUrl + 'location/' + id,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}






 // Masters GET Methods

 customertypeget(totalItems,currentPage) {
  return this.http.get(this.baseUrl + 'customerType/getdata/' + currentPage + '/'+ totalItems,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
 }


 billingmodalget(totalItems,currentPage) {
  return this.http.get(this.baseUrl + 'BillingModel/getdata/' + currentPage + '/'+ totalItems,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
 }


 serviceget(totalItems,currentPage) {
  return this.http.get(this.baseUrl + 'services/findAll/' + currentPage + '/'+ totalItems,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
 }





skillsget(totalItems,currentPage) {
  return this.http.get(this.baseUrl + 'skill/findAll/' + currentPage + '/'+ totalItems,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
 }



paymentTermsget(totalItems,currentPage){
  return this.http.get(this.baseUrl + 'PaymentTerms/findAll/' + currentPage + '/'+ totalItems,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}


locationsget(){
  return this.http.get(this.baseUrl + 'location/findAll/1/1',
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}



modeofInterviewget(totalItems,currentPage) {
  return this.http.get(this.baseUrl + 'modeofInterview/findAll/' + currentPage + '/'+ totalItems,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
 }





noticeperiodget(totalItems,currentPage) {
  return this.http.get(this.baseUrl + 'noticePeriod/search/' + currentPage + '/'+ totalItems,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
 }


qualificationsget(totalItems,currentPage){
  return this.http.get(this.baseUrl + 'Qualification/findAll/' + currentPage + '/'+ totalItems,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}


permissionsget(totalItems,currentPage){
  return this.http.get(this.baseUrl + 'permissions/findAll/' + currentPage + '/'+ totalItems,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
}


rolesget(totalItems,currentPage) {
  return this.http.get(this.baseUrl + 'roles/findAll/' + currentPage + '/'+ totalItems,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
 }




 featuresget(totalItems,currentPage) {
  return this.http.get(this.baseUrl + 'featureResource/getAll/' + currentPage + '/'+ totalItems,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
 }


 certificatesget(totalItems,currentPage) {
  return this.http.get(this.baseUrl + 'certificates/findAll/' + currentPage + '/'+ totalItems,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
 }




 joblocationsget(totalItems,currentPage) {
  return this.http.get(this.baseUrl + 'location/findAll/' + currentPage + '/'+ totalItems,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
   });
 }


}










