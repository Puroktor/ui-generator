<#-- @ftlvariable name="endpoint" type="ru.vsu.csf.skofenko.ui.generator.api.UIEndpoint" -->

<#macro renderField uiField prefix>
    <#if uiField.getFieldType().name() == "TEXT" || uiField.getFieldType().name() == "NUMBER" ||  uiField.getFieldType().name() == "ENUM">
        <mat-form-field class="long-field">
            <mat-label i18n>${uiField.getDisplayName()}</mat-label>
            <#if uiField.getFieldType().name() == "ENUM">
            <mat-select formControlName="${prefix}${uiField.getCodeName()}">
                <#list uiField.getSubmitToDisplayValues() as submitName, displayName>
                    <mat-option value="${submitName}" i18n>${displayName}</mat-option>
                </#list>
            </mat-select>
            <#else>
            <input matInput formControlName="${prefix}${uiField.getCodeName()}" <#if uiField.getFieldType().name() == "NUMBER">type="number"</#if>>
            </#if>
            <#if uiField.getFieldType().name() == "NUMBER">
                 <#if uiField.getMin()??>
                     <mat-error *ngIf="formGroup.controls['${prefix}${uiField.getCodeName()}'].errors?.['min']" i18n>
                         ${uiField.getDisplayName()} must be greater or equal to ${uiField.getMin()?c}
                     </mat-error>
                 </#if>
                <#if uiField.getMax()??>
                    <mat-error *ngIf="formGroup.controls['${prefix}${uiField.getCodeName()}'].errors?.['max']" i18n>
                        ${uiField.getDisplayName()} must be less or equal to ${uiField.getMax()?c}
                    </mat-error>
                </#if>
            </#if>
            <#if uiField.getFieldType().name() == "TEXT">
                <#if uiField.getMinLength()??>
                    <mat-error *ngIf="formGroup.controls['${prefix}${uiField.getCodeName()}'].errors?.['minLength']" i18n>
                        ${uiField.getDisplayName()} must be no shorter than ${uiField.getMinLength()?c} characters
                    </mat-error>
                </#if>
                <#if uiField.getMaxLength()??>
                    <mat-error *ngIf="formGroup.controls['${prefix}${uiField.getCodeName()}'].errors?.['maxLength']" i18n>
                        ${uiField.getDisplayName()} must be no longer than ${uiField.getMaxLength()?c} characters
                    </mat-error>
                </#if>
                <#if uiField.getPattern()??>
                    <mat-error *ngIf="formGroup.controls['${prefix}${uiField.getCodeName()}'].errors?.['pattern']" i18n>
                        ${uiField.getDisplayName()} is invalid
                    </mat-error>
                </#if>
            </#if>
                <mat-error *ngIf="formGroup.controls['${prefix}${uiField.getCodeName()}'].errors?.['required']" i18n>
                    ${uiField.getDisplayName()} is required
                </mat-error>
        </mat-form-field>
    <#elseif uiField.getFieldType().name() == "BOOL">
        <mat-checkbox class="checkbox" formControlName="${prefix}${uiField.getCodeName()}">${uiField.getDisplayName()}</mat-checkbox>
    <#elseif uiField.getFieldType().name() == "LIST">
        <div class="flex-container">
            <p class="label">${uiField.getDisplayName()}:</p>
            <div class="array-container" formArrayName="${prefix}${uiField.getCodeName()}">
                <div class="list-form-container"
                     *ngFor="let item of ${prefix}${uiField.getCodeName()}.controls; index as i">
                    <mat-form-field class="list-form-field">
                        <mat-label i18n>
                            <span>Element</span>
                            <span>{{i+1}}</span>
                        </mat-label>
                        <#if uiField.getElement().getFieldType().name() == "ENUM">
                            <mat-select [formControlName]="i">
                                <#list uiField.getElement().getSubmitToDisplayValues() as submitName, displayName>
                                    <mat-option value="${submitName}" i18n>${displayName}</mat-option>
                                </#list>
                            </mat-select>
                        <#else>
                            <input matInput [formControlName]="i" <#if uiField.getElement().getFieldType().name() == "NUMBER">type="number"</#if>>
                        </#if>
                        <mat-error *ngIf="item.hasError('required')" i18n>
                            Element is required
                        </mat-error>
                    </mat-form-field>
                    <button mat-mini-fab class="remove-element-btn" color="accent" type="button" (click)="removeFormArrayItem(${prefix}${uiField.getCodeName()}, i)">
                        <mat-icon>delete</mat-icon>
                    </button>
                </div>
                <button mat-mini-fab color="primary" class="add-element-btn" type="button" (click)="addFormArrayItem(${prefix}${uiField.getCodeName()}, ${uiField.isRequired()?string('true', 'false')})">
                    <mat-icon>add</mat-icon>
                </button>
            </div>
        </div>
    <#elseif uiField.getFieldType().name() == "CLASS">
        <div class="flex-container">
            <p class="label" i18n>${uiField.getDisplayName()}:</p>
            <div class="class-container">
                <#list uiField.getInnerFields() as innerField>
                    <@renderField uiField=innerField prefix="${prefix}${uiField.getCodeName()}_"/>
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
            <mat-panel-description i18n>
                ${endpoint.getDisplayName()}
            </mat-panel-description>
        </mat-expansion-panel-header>
        <div class="path-params">
            <#if endpoint.getQueryParams()?has_content>
                <h3 class="text-center" i18n>Path Params</h3>
            </#if>
            <#list endpoint.getPathParams() as pathParam>
                <@renderField uiField=pathParam prefix="path$"/>
            </#list>
        </div>
        <div class="query-params">
            <#if endpoint.getQueryParams()?has_content>
                <h3 class="text-center" i18n>Query Params</h3>
            </#if>
            <#list endpoint.getQueryParams() as queryParam>
                <@renderField uiField=queryParam prefix="query$"/>
            </#list>
        </div>
        <div class="request-body">
            <#if endpoint.getRequestBody()??>
                <h3 class="text-center" i18n>Request Body - ${endpoint.getRequestBody().getEntityName()}</h3>
                <#list endpoint.getRequestBody().getFields() as requestField>
                   <@renderField uiField=requestField prefix="body$"/>
                </#list>
            </#if>
        </div>
        <div class="request-footer">
            <button mat-raised-button [disabled]="formGroup.disabled" color="primary" type="submit" class="float-right" i18n>
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
        <div class="response-container">
            <div class="response-header-container">
                <h3 i18n>Response</h3>
                <div class="response-type-switch">
                    <mat-label class="mat-slide-toggle-content response-type-toggle-label" i18n>Table</mat-label>
                    <mat-slide-toggle [(ngModel)]="jsonResponseType" [ngModelOptions]="{standalone: true}" i18n>Json</mat-slide-toggle>
                </div>
            </div>

            <#if endpoint.getResponseBody()?has_content>
                <table mat-table [dataSource]="responseTableData" [ngClass]="{'disabled': jsonResponseType}" class="mat-elevation-z4">
                    <#list endpoint.getResponseBody() as responseField>
                        <ng-container matColumnDef="${responseField.getCodeName()}">
                            <th mat-header-cell *matHeaderCellDef i18n>${responseField.getDisplayName()}</th>
                            <td mat-cell *matCellDef="let element">{{element.${responseField.getCodeName()}}}</td>
                        </ng-container>
                    </#list>
                    <tr mat-header-row *matHeaderRowDef="displayedResponseColumns"></tr>
                    <tr mat-row *matRowDef="let row; columns: displayedResponseColumns;"></tr>
                </table>
            <#else>
                <h3 class="text-center" [ngClass]="{'disabled': jsonResponseType}" i18n>No response body</h3>
            </#if>
            <app-response [response]="response" [ngClass]="{'disabled': jsonResponseType === false}">
            </app-response>
        </div>
    </mat-expansion-panel>
</form>