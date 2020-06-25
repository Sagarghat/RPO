import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubclientComponent } from './subclient/subclient.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { routes } from './subclient.routing';
import { RichTextEditorAllModule } from '@syncfusion/ej2-angular-richtexteditor';




@NgModule({
  declarations: [SubclientComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    RichTextEditorAllModule,
    
  ]
})
export class SubclientModule { }
