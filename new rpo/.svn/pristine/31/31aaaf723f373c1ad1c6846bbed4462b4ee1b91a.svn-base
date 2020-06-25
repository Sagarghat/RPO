import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MapComponent } from './map/map.component';
import { RouterModule } from '@angular/router';
import { routes } from './map.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import {NgxPaginationModule} from 'ngx-pagination';
import { RichTextEditorAllModule } from '@syncfusion/ej2-angular-richtexteditor';
@NgModule({
  declarations: [MapComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    PdfViewerModule,

    RouterModule.forChild(routes),
    NgxPaginationModule,
    RichTextEditorAllModule,
  ]
})
export class MapModule { }
