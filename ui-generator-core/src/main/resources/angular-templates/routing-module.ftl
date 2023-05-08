<#-- @ftlvariable name="components" type="java.util.List<ru.vsu.csf.skofenko.ui.generator.api.core.UIComponent>" -->
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {InfoComponent} from './info/info.component';
<#list components as component>
import {${component.getScriptName()}Component} from "./${component.getFileName()}/${component.getFileName()}.component";
</#list>

const routes: Routes = [
    {path: '', component: InfoComponent},
<#list components as component>
    {path: '${component.getFileName()}', component: ${component.getScriptName()}Component},
</#list>
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}

export const routingComponents = [
    InfoComponent, <#list components as component>${component.getScriptName()}Component, </#list>
]