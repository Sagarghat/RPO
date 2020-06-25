import { Routes } from '@angular/router';

export const routes: Routes = [

   { path: '', loadChildren: './plans/plans.module#PlansModule' },
   { path: 'signup', loadChildren: './signup/signup.module#SignupModule'}
]

