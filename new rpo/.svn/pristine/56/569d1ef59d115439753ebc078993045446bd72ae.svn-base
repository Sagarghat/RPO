import { Routes } from '@angular/router';
import { ClientComponent } from './client/client.component';


export const routes: Routes = [

  { path: '', component: ClientComponent },

  { path: 'addclient', loadChildren: () => import('./addclient/addclient.module').then(o => o.AddclientModule) },
  { path: 'editclient/:idd', loadChildren: () => import('./editclient/editclient.module').then(l => l.EditclientModule) },
  { path: 'clientnameclick/:idd', loadChildren: () => import('./clientnameclick/clientnameclick.module').then(l => l.ClientnameclickModule) },
  { path: 'clientmail/:clientmailid', loadChildren: () => import('./clientmail/clientmail.module').then(k => k.ClientmailModule) }

]