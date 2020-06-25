import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EmailComponent } from './email/email.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { routes } from './email.routing';
import { RichTextEditorAllModule } from '@syncfusion/ej2-angular-richtexteditor';



@NgModule({
  declarations: [EmailComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RichTextEditorAllModule,
   RouterModule.forChild(routes),
  ]
})
export class EmailModule { }
