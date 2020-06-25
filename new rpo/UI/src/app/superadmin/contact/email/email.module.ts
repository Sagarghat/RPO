import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmailComponent } from './email/email.component';
import { RouterModule } from '@angular/router';
import { RichTextEditorAllModule } from '@syncfusion/ej2-angular-richtexteditor';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { routes } from './email.router';




@NgModule({
  declarations: [EmailComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RichTextEditorAllModule,
    HttpClientModule,
    RouterModule.forChild(routes),
  ]
})
export class EmailModule { }
