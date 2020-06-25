import { Routes } from '@angular/router';
import { ContactComponent } from './contact/contact.component';



export const routes: Routes = [

  {path:'' , component:ContactComponent},

  
    {path:'addcontact',loadChildren: () => import('./addcontact/addcontact.module').then(o => o.AddcontactModule)},
   {path:'editcontact/:id',loadChildren: () => import('./editcontact/editcontact.module').then(l => l.EditcontactModule)},
   {path:'clickContactName/:id',loadChildren:() => import('./contactnameclick/contactnameclick.module').then(c=>c.ContactnameclickModule)},
   {path:'email/:contactid',loadChildren:() => import('./email/email.module').then(c=>c.EmailModule)}
  
]