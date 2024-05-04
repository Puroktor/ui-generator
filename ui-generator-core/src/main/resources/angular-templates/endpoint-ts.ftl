<#-- @ftlvariable name="endpoint" type="ru.vsu.csf.skofenko.ui.generator.api.UIEndpoint" -->
<#-- @ftlvariable name="componentName" type="java.lang.String" -->
import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormGroup, FormControl, FormArray, Validators} from '@angular/forms';
import {HttpResponse, HttpErrorResponse} from "@angular/common/http";
import {AppService} from '../../service/app.service';

@Component({
    selector: 'app-${componentName}-${endpoint.getFileName()}',
    templateUrl: './${endpoint.getFileName()}.component.html',
    styleUrls: ['./${endpoint.getFileName()}.component.css']
})
export class ${endpoint.getScriptName()}Component implements OnInit, OnDestroy {

<#macro renderFormCotrol uiField prefix>
    <#if uiField.getFieldType().name() == "CLASS">
        <#list uiField.getInnerFields() as innerField>
            <@renderFormCotrol uiField=innerField prefix="${prefix}${uiField.getCodeName()}_"/>
        </#list>
    <#elseif uiField.getFieldType().name() == "LIST">
        "${prefix}${uiField.getCodeName()}" : new FormArray([]),
    <#else>
        "${prefix}${uiField.getCodeName()}" : new FormControl(<#if uiField.getFieldType().name() == "BOOL">false<#else>null</#if>, [
            <#if uiField.getFieldType().name() == "NUMBER">
                <#if uiField.getMin()??>Validators.min(${uiField.getMin()?c}),</#if>
                <#if uiField.getMax()??>Validators.max(${uiField.getMax()?c}),</#if>
            </#if>
            <#if uiField.getFieldType().name() == "TEXT">
                <#if uiField.getPattern()??>Validators.pattern("${uiField.getPattern()}"),</#if>
                <#if uiField.getMinLength()??>Validators.minLength(${uiField.getMinLength()?c}),</#if>
                <#if uiField.getMaxLength()??>Validators.maxLength(${uiField.getMaxLength()?c}),</#if>
            </#if>
            <#if uiField.isRequired()>Validators.required,</#if>
        ]),
    </#if>
</#macro>
<#macro renderFormArray uiField prefix>
    <#if uiField.getFieldType().name() == "LIST">
    ${prefix}${uiField.getCodeName()} = this.formGroup.get('${prefix}${uiField.getCodeName()}') as FormArray;
    </#if>
</#macro>
<#assign pathParamPrefix = "path$">
<#assign queryParamPrefix = "query$">
<#assign bodyPrefix = "body$">

    private mapping: string = '${endpoint.getMapping()}';
    private requestType: string = '${endpoint.getRequestType().name()}';
    response: any|null = null;

    displayedResponseColumns: string[] = [<#list endpoint.getResponseBody() as responseField>'${responseField.getCodeName()}',</#list>];
    responseTableData: any|null = null;
    jsonResponseType = false;

    formGroup = new FormGroup({
    <#list endpoint.getPathParams() as pathParam>
        <@renderFormCotrol uiField=pathParam prefix="${pathParamPrefix}"/>
    </#list>
    <#list endpoint.getQueryParams() as queryParam>
        <@renderFormCotrol uiField=queryParam prefix="${queryParamPrefix}"/>
    </#list>
    <#if endpoint.getRequestBody()??>
        <#list endpoint.getRequestBody().getFields() as bodyField>
            <@renderFormCotrol uiField=bodyField prefix="${bodyPrefix}"/>
        </#list>
    </#if>
    });

<#list endpoint.getPathParams() as pathParam>
    <@renderFormArray uiField=pathParam prefix="${pathParamPrefix}"/>
</#list>
<#list endpoint.getQueryParams() as queryParam>
    <@renderFormArray uiField=queryParam prefix="${queryParamPrefix}"/>
</#list>
<#if endpoint.getRequestBody()??>
    <#list endpoint.getRequestBody().getFields() as bodyField>
    <@renderFormArray uiField=bodyField prefix="${bodyPrefix}"/>
    </#list>
</#if>

    constructor(private appService: AppService) {
    }

    ngOnInit(): void {
    }

    ngOnDestroy(): void {
    }

    addFormArrayItem(formArray: FormArray, isRequired: boolean) {
        formArray.push(new FormControl(null, isRequired ? Validators.required : []));
    }

    removeFormArrayItem(formArray: FormArray, index: number) {
        formArray.removeAt(index);
    }

    submitForm(): void {
        if (this.formGroup.invalid) {
            return;
        }
        this.formGroup.disable();
        this.appService.submitForm(this.requestType, this.mapping, this.formGroup.value).subscribe({
            next: (response: HttpResponse<any>) => {
                this.response = response;
                this.responseTableData = [response.body];
                this.formGroup.enable();
            },
            error: (err: HttpErrorResponse) => {
                this.response = err;
                this.responseTableData = null;
                this.formGroup.enable();
            }
        })
    }
}