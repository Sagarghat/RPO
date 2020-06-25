import { Routes } from '@angular/router';
import { JobopeningComponent } from './jobopening/jobopening.component';

export const routes: Routes = [

    {path:'' , component:JobopeningComponent},
  
    
     {path:'addjobopening',loadChildren: () => import('./addjobopening/addjobopening.module').then(o => o.AddjobopeningModule)},
     {path:'editjobopening/:id',loadChildren: () => import('./editjobopening/editjobopening.module').then(l => l.EditjobopeningModule)},
     {path:'jobnameclick/:id',loadChildren: () => import('./jobnameclick/jobnameclick.module').then(l => l.JobnameclickModule)}
  ]
