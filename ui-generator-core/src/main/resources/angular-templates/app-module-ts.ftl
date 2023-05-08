<#-- @ftlvariable name="components" type="java.util.List<ru.vsu.csf.skofenko.ui.generator.api.core.UIComponent>" -->
import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule}   from '@angular/forms';
import {ReactiveFormsModule} from '@angular/forms';

import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatTableModule} from '@angular/material/table';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatSelectModule} from '@angular/material/select';
import {MatIconModule} from '@angular/material/icon';
import {NgxJsonViewerModule} from 'ngx-json-viewer';
import {HttpClientModule} from '@angular/common/http';

import {AppRoutingModule, routingComponents} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './header/header.component';
import {ResponseComponent} from './response/response.component';

<#list components as component>
<#list component.getEndpoints() as endpoint>
import {${endpoint.getScriptName()}Component} from './${component.getFileName()}/${endpoint.getFileName()}/${endpoint.getFileName()}.component';
</#list>
</#list>

@NgModule({
    declarations: [
        AppComponent,
        HeaderComponent,
        ResponseComponent,
        routingComponents,

        <#list components as component>
        <#list component.getEndpoints() as endpoint>
        ${endpoint.getScriptName()}Component,
        </#list>
        </#list>
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        BrowserAnimationsModule,
        FormsModule,
        ReactiveFormsModule,

        MatSlideToggleModule,
        MatTableModule,
        MatToolbarModule,
        MatButtonModule,
        MatInputModule,
        MatFormFieldModule,
        MatExpansionModule,
        MatCheckboxModule,
        MatSelectModule,
        MatIconModule,
        NgxJsonViewerModule,
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}