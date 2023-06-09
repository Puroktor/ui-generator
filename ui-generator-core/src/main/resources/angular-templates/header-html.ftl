<#-- @ftlvariable name="components" type="java.util.List<ru.vsu.csf.skofenko.ui.generator.api.core.UIComponent>" -->
<mat-toolbar color="primary">
    <#list components as component>
    <a mat-button routerLink="/${component.getFileName()}"
       routerLinkActive="active-header-link"
       [routerLinkActiveOptions]="{exact: true}">${component.getDisplayName()}</a>
    </#list>
    <span class="header-spacer"></span>
    <a mat-button routerLink=""
       routerLinkActive="active-header-link"
       [routerLinkActiveOptions]="{exact: true}">Info</a>
</mat-toolbar>