<#-- @ftlvariable name="endpoint" type="ru.vsu.csf.skofenko.ui.generator.api.core.UIEndpoint" -->

<#macro renderField uiField prefix>
    <#if uiField.getFieldType().name() == "TEXT" || uiField.getFieldType().name() == "NUMBER" ||  uiField.getFieldType().name() == "ENUM">
        <mat-form-field class="long-field">
            <mat-label>${uiField.getDisplayName()}</mat-label>
            <#if uiField.getFieldType().name() == "ENUM">
            <mat-select formControlName="${prefix}${uiField.getSubmitName()}">
                <#list uiField.getSubmitToDisplayValues() as submitName, displayName>
                    <mat-option value="${submitName}">${displayName}</mat-option>
                </#list>
            </mat-select>
            <#else>
            <input matInput formControlName="${prefix}${uiField.getSubmitName()}" <#if uiField.getFieldType().name() == "NUMBER">type="number"</#if>>
            </#if>
            <mat-error *ngIf="formGroup.controls['${prefix}${uiField.getSubmitName()}'].errors?.['required']">${uiField.getDisplayName()} is required</mat-error>
        </mat-form-field>
    <#elseif uiField.getFieldType().name() == "BOOL">
        <mat-checkbox class="checkbox" formControlName="${prefix}${uiField.getSubmitName()}">${uiField.getDisplayName()}</mat-checkbox>
    <#elseif uiField.getFieldType().name() == "LIST">
        <div class="flex-container">
            <p class="label">${uiField.getDisplayName()}:</p>
            <div class="array-container" formArrayName="${prefix}${uiField.getSubmitName()}">
                <div class="list-form-container"
                     *ngFor="let item of ${prefix}${uiField.getSubmitName()}.controls; index as i">
                    <mat-form-field class="list-form-field">
                        <mat-label>Element {{i+1}}</mat-label>
                        <#if uiField.getFieldType().name() == "ENUM">
                            <mat-select [formControlName]="i">
                                <#list uiField.getSubmitToDisplayValues() as submitName, displayName>
                                    <mat-option value="${submitName}">${displayName}</mat-option>
                                </#list>
                            </mat-select>
                        <#else>
                            <input matInput [formControlName]="i" <#if uiField.getFieldType().name() == "NUMBER">type="number"</#if>>
                        </#if>
                        <mat-error *ngIf="item.hasError('required')">
                            Element {{i+1}} is required
                        </mat-error>
                    </mat-form-field>
                    <button mat-mini-fab class="remove-element-btn" color="accent" type="button" (click)="removeFormArrayItem(${prefix}${uiField.getSubmitName()}, i)">
                        <mat-icon>delete</mat-icon>
                    </button>
                </div>
                <button mat-mini-fab color="primary" class="add-element-btn" type="button" (click)="addFormArrayItem(${prefix}${uiField.getSubmitName()}, ${uiField.isRequired()?string('true', 'false')})">
                    <mat-icon>add</mat-icon>
                </button>
            </div>
        </div>
    <#elseif uiField.getFieldType().name() == "CLASS">
        <div class="flex-container">
            <p class="label">${uiField.getDisplayName()}:</p>
            <div class="class-container">
                <#list uiField.getInnerFields() as innerField>
                    <@renderField uiField=innerField prefix="${prefix}${uiField.getSubmitName()}_"/>
                </#list>
            </div>
        </div>
    </#if>
</#macro>

<form [formGroup]="formGroup"
      (ngSubmit)="submitForm()">
    <mat-expansion-panel>
        <mat-expansion-panel-header>
            <mat-panel-title>
                ${endpoint.getRequestType()}
            </mat-panel-title>
            <mat-panel-description>
                ${endpoint.getDisplayName()}
            </mat-panel-description>
        </mat-expansion-panel-header>
        <div class="query-params">
            <#if endpoint.getQueryParams()?has_content>
                <h3 class="text-center">Query Params</h3>
            </#if>
            <#list endpoint.getQueryParams() as queryParam>
                <@renderField uiField=queryParam prefix="query_"/>
            </#list>
        </div>
        <div class="request-body">
            <#if endpoint.getRequestBody()??>
                <h3 class="text-center">Request Body - ${endpoint.getRequestBody().getEntityName()}</h3>
                <#list endpoint.getRequestBody().getFields() as requestField>
                   <@renderField uiField=requestField prefix="body_"/>
                </#list>
            </#if>
        </div>
        <div class="request-footer">
            <button mat-raised-button [disabled]="formGroup.disabled" color="primary" type="submit" class="float-right">
                <#if endpoint.getRequestType().name() == "POST">
                    Save
                <#elseif endpoint.getRequestType().name() == "GET">
                    Get
                <#elseif endpoint.getRequestType().name() == "PUT">
                    Change
                <#else>
                    Delete
                </#if>
            </button>
        </div>
        <app-response [response]="response">
        </app-response>
    </mat-expansion-panel>
</form>