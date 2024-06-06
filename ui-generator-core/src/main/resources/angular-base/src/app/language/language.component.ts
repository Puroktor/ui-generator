import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {MatSelectChange} from '@angular/material/select';

@Component({
    selector: 'app-language',
    templateUrl: './language.component.html',
    styleUrls: ['./language.component.css']
})
export class LanguageComponent {
    constructor(private router: Router) {}

    switchLanguage(event: MatSelectChange) {
        const currentUrl = this.router.url.split('/').slice(2).join('/');
        this.router.navigate([`/${event.value}/${currentUrl}`]);
    }
}