<#-- @ftlvariable name="component" type="ru.vsu.csf.skofenko.ui.generator.api.UIComponent" -->

<mat-accordion class="generated-forms-container" multi>
<#list component.endpoints as endpoint>
    <app-${component.getFileName()}-${endpoint.getFileName()}></app-${component.getFileName()}-${endpoint.getFileName()}>
</#list>
</mat-accordion>