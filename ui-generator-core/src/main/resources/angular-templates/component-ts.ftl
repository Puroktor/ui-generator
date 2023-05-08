<#-- @ftlvariable name="component" type="ru.vsu.csf.skofenko.ui.generator.api.core.UIComponent" -->
import {Component, OnDestroy, OnInit} from '@angular/core';

@Component({
    selector: 'app-${component.getFileName()}',
    templateUrl: './${component.getFileName()}.component.html',
    styleUrls: ['./${component.getFileName()}.component.css']
})
export class ${component.getScriptName()}Component implements OnInit, OnDestroy {

    constructor() {
    }

    ngOnInit(): void {
    }

    ngOnDestroy(): void {
    }
}