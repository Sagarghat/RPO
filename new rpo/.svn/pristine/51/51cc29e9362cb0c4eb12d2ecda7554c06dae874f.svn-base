import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';


export const routes: Routes = [

  {path:'' , component:DashboardComponent},
  { path: 'map/:id', loadChildren: () => import('./map/map.module').then(o => o.MapModule) },
   { path: 'submissions/:id', loadChildren: () => import('./submissions/submissions.module').then(o => o.SubmissionsModule) },
   { path: 'interv/:id', loadChildren: () => import('./interv/interv.module').then(o => o.IntervModule) },
  { path: 'offered/:id', loadChildren: () => import('./offered/offered.module').then(o => o.OfferedModule) },
  { path: 'hired/:id', loadChildren: () => import('./hired/hired.module').then(o => o.HiredModule) }

];