import { Routes } from '@angular/router';
import { UserComponent } from './user/user.component';

export const routes: Routes = [

  {path:'' , component:UserComponent},

  
    {path:'adduser',loadChildren: () => import('./adduser/adduser.module').then(o => o.AdduserModule)},
   {path:'edituser/:id',loadChildren: () => import('./edituser/edituser.module').then(l => l.EdituserModule)}
   
]