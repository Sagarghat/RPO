import { Routes } from '@angular/router';
import { InterviewComponent } from './interview/interview.component';




export const routes: Routes = [

  {path:'' , component:InterviewComponent},

  
    {path:'addinterview',loadChildren: () => import('./addinterview/addinterview.module').then(addinterview => addinterview.AddinterviewModule)},
   {path:'editinterview/:interviewid',loadChildren: () => import('./editinterview/editinterview.module').then(editinterview => editinterview.EditinterviewModule)},
   {path:'interviewname/:interviewname',loadChildren: () => import('./interviewnameclick/interviewnameclick.module').then(interviewname => interviewname.InterviewnameclickModule)}
 
]