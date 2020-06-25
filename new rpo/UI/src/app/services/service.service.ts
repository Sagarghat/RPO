import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {
 baseUrl = `${environment.serviceUrl}`;
 userToken;
  getOwnerUserListByIdforAm: any;
  getInterviewList: any;

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
  getuserList(currentPage,pazeSize,RegId){
    return this.http.get(this.baseUrl + 'Reg/usersListByRegId/' + currentPage + '/' + pazeSize + '/' + RegId, {
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
   return this.http.get(this.baseUrl + 'user/' + UserId + '/' + RegId, {
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
   return this.http.get(this.baseUrl + 'customerType', {
      headers: {
        'X-Access-Token':this.userToken,
        'Content-Type': 'application/json'
      }
    })
  }
  getbillingmodel(){
    return this.http.get(this.baseUrl+'BillingModel', {
      headers: {
        'X-Access-Token':  this.userToken,
        'Content-Type': 'application/json'
      }
    });
  }

  getPayment(){
    return this.http.get(this.baseUrl+'PaymentTerms', {
      headers: {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    })
  }
  getservices(){
    return this.http.get(this.baseUrl+'services', {
      headers: {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    })
  }
  getTypeAddress(){
    return this.http.get(this.baseUrl+'typeofaddress', {
      headers: {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    })
  }
  getAmList(RegId){
    return this.http.get(this.baseUrl+'Reg/listAM/'+ RegId, {
      headers: {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    })
  }


  // getLead(){
  //   return this.http.get(this.baseUrl+'Reg/getUserNamesByRole?role=Lead', {
  //     headers: {
  //       'X-Access-Token': this.userToken,
  //       'Content-Type': 'application/json'
  //     }
  //   })
  // }





  getLead(regId){
    let body= new HttpParams()
    .set('role','lead')
    .set('regId',regId)
    
 
    return this.http.get(this.baseUrl+'Reg/getUserNamesByRole?'+body, {
      headers: {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    })
  }

  getSource(){
   return  this.http.get(this.baseUrl+'source', {
      headers: {
        'X-Access-Token': this.userToken,
        'Content-Type': 'application/json'
      }
    })
  }
  getIndustry(){
    return this.http.get(this.baseUrl+'industry', {
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

 getOwner(RegId)
 {
   return this.http.get(this.baseUrl + 'Reg/listAM/' +RegId,
   {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }); 
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
return this.http.get(this.baseUrl +'Reg/usersListByRegIdRole/1/5/' + registrationId + '/AM',
{
  headers:{
    'X-Access-Token': this.userToken,
    'Content-Type': 'application/json'
  }
});
 }



 

// job opening apis
getAllJobOpenings(profileData)
{
  return this.http.post(this.baseUrl + 'Bdmrequire/getAllRequirementsByRegId', profileData, {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  })
}

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

skills()
{
  return this.http.get(this.baseUrl + 'skill', {
    headers: {
      'X-Access-Token':  this.userToken,
      'Content-Type': 'application/json'
    }
  })
}

Qualification()
{
  return this.http.get(this.baseUrl + 'Qualification', {
    headers: {
      'X-Access-Token':  this.userToken,
      'Content-Type': 'application/json'
    }
  })
}

Certificates()
{
  return this.http.get(this.baseUrl + 'certificates', {
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

Location()
{
  return this.http.get(this.baseUrl + 'location', {
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
  return  this.http.get(this.baseUrl+'skill', {
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
  return  this.http.get(this.baseUrl+'Qualification', {
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


 
 getCandidateById(id){
  return  this.http.get(this.baseUrl + 'addcandidate/' +id , {
    headers: {
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  });
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

getemail(data)
{
  return this.http.post(this.baseUrl +'email/scheduleMail' ,data,
  {
    headers:{
      'X-Access-Token': this.userToken,
      'Content-Type': 'application/json'
    }
  }
  
);
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

candidatenoteData(getnotedata)
{
  return this.http.post(this.baseUrl+"candidatenotes/search",getnotedata,{
      headers:{
        "X-Access-Token":this.userToken,
        "Content-Type":"application/json"
      }
    })
}

candidatedeletedata(id)
{
  return this.http.delete(this.baseUrl+"candidatenotes/delete/"+id,{
      headers:{
        "X-Access-Token":this.userToken,
        "Content-Type":"application/json"
      }
    })
}


}
