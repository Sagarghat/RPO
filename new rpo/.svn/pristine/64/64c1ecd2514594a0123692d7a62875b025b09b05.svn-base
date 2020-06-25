import { Routes } from '@angular/router';
import { CandidateComponent } from './candidate/candidate.component';



export const routes: Routes = [

  { path: '', component: CandidateComponent },

  { path: 'addcandidate', loadChildren: () => import('./addcandidate/addcandidate.module').then(o => o.AddcandidateModule) },
  { path: 'editcandidate/:id', loadChildren: () => import('./editcandidate/editcandidate.module').then(l => l.EditcandidateModule) },
  { path: 'candidatedetails/:id', loadChildren: () => import('./candidatedetails/candidatedetails.module').then(l => l.CandidatedetailsModule) },
  { path: 'email/:candid', loadChildren: () => import('./email/email.module').then(s => s.EmailModule) },
  { path: 'subclient/:candid', loadChildren: () => import('./subclient/subclient.module').then(c => c.SubclientModule) },
  { path: 'addcanresume', loadChildren: () => import('./addcanresume/addcanresume.module').then(k => k.AddcanresumeModule) }


]