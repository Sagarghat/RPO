import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientmailComponent } from './clientmail/clientmail.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { routes } from './clientmail.router';
import { RichTextEditorAllModule } from '@syncfusion/ej2-angular-richtexteditor';



@NgModule({
  declarations: [ClientmailComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    ReactiveFormsModule,
    RichTextEditorAllModule,
  
 
  ]
})
export class ClientmailModule { }
