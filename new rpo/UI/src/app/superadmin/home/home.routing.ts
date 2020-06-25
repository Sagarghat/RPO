import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { SkillsComponent } from '../skills/skills/skills.component';
import { ModeofinterviewComponent } from '../modeofinterview/modeofinterview/modeofinterview.component';
import { NoticeperiodComponent } from '../noticeperiod/noticeperiod/noticeperiod.component';
import { QualificationsComponent } from '../qualifications/qualifications/qualifications.component';
import { PaymenttermsComponent } from '../paymentterms/paymentterms/paymentterms.component';
import { ServicelistComponent } from '../servicelist/servicelist/servicelist.component';
import { PermissionsComponent } from '../permissions/permissions/permissions.component';
import { RolesComponent } from '../roles/roles/roles.component';
import { FeaturesComponent } from '../features/features/features.component';
import { CertificatesComponent } from '../certificates/certificates/certificates.component';
import { CustomersComponent } from '../customers/customers/customers.component';
import { BillingmodalComponent } from '../billingmodal/billingmodal/billingmodal.component';
import { JoblocationComponent } from '../joblocation/joblocation/joblocation.component';




export const routes: Routes = [
  {
    path: '', component: HomeComponent,

    children: [
      {
        path: '', children: [
          { path: '', loadChildren: '../dashboard/dashboard.module#DashboardModule' }
        ]
      },
      {
        path: 'dashboard', children: [
          { path: '', loadChildren: '../dashboard/dashboard.module#DashboardModule' }
        ]
      },
      {
        path: 'user', children: [
          { path: '', loadChildren: '../user/user.module#UserModule' }
        ]
      },
      {
        path: 'client', children: [
          { path: '', loadChildren: '../client/client.module#ClientModule' }
        ]
      },
      {
        path: 'contact', children: [
          { path: '', loadChildren: '../contact/contact.module#ContactModule' }
        ]
      },
      {
        path: 'jobopening', children: [
          { path: '', loadChildren: '../jobopening/jobopening.module#JobopeningModule' }
        ]
      },
      {
        path: 'interview', children: [
          { path: '', loadChildren: '../interview/interview.module#InterviewModule' }
        ]
      },
      {
        path: 'candidate', children: [
          { path: '', loadChildren: '../candidate/candidate.module#CandidateModule' }
        ]
      },
      {
        path: 'assignedrequirement', children: [
          { path: '', loadChildren: '../assignedrequirement/assignedrequirement.module#AssignedrequirementModule' }
        ]
      },
      {
        path: 'reports', children: [
          { path: '', loadChildren: '../reports/reports.module#ReportsModule' }
        ]
      },
      {
        path: 'skills', children: [
          { path: '', loadChildren: '../skills/skills.module#SkillsModule' }
        ]
      },
      {
        path: 'modeofinterview', children: [
          { path: '', loadChildren: '../modeofinterview/modeofinterview.module#ModeofinterviewModule' }
        ]
      },
      {
        path: 'noticeperiod', children: [
          { path: '', loadChildren: '../noticeperiod/noticeperiod.module#NoticeperiodModule' }
        ]
      },
      {
        path: 'qualifications', children: [
          { path: '', loadChildren: '../qualifications/qualifications.module#QualificationsModule' }
        ]
      },
      {
        path: 'servicelist', children: [
          { path: '', loadChildren: '../servicelist/servicelist.module#ServicelistModule' }
        ]
      },
      {
        path: 'paymentterms', children: [
          { path: '', loadChildren: '../paymentterms/paymentterms.module#PaymenttermsModule' }
        ]
      },
      {
        path: 'permissions', children: [
          { path: '', loadChildren: '../permissions/permissions.module#PermissionsModule' }
        ]
      },
      {
        path: 'roles', children: [
          { path: '', loadChildren: '../roles/roles.module#RolesModule' }
        ]
      },
      {
        path: 'features', children: [
          { path: '', loadChildren: '../features/features.module#FeaturesModule' }
        ]
      },
      {
        path: 'certificates', children: [
          { path: '', loadChildren: '../certificates/certificates.module#CertificatesModule' }
        ]
      },
      {
        path: 'joblocation', children: [
          { path: '', loadChildren: '../joblocation/joblocation.module#JoblocationModule' }
        ]
      },
      {
        path: 'billingmodal', children: [
          { path: '', loadChildren: '../billingmodal/billingmodal.module#BillingmodalModule' }
        ]
      },
      {
        path: 'customertype', children: [
          { path: '', loadChildren: '../customers/customers.module#CustomersModule' }
        ]
      }
    ]
  }

];
