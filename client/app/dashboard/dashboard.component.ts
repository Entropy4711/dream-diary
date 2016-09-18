import {Component} from '@angular/core';
import {DiaryEntrySearchRequest} from '../model/diary-entry-search-request';
import {DiaryEntrySearchResponse} from '../model/diary-entry-search-response';
import {RestService} from '../service/rest.service';
import { Router } from '@angular/router';

@Component({
    selector: 'dashboard',
    templateUrl: 'app/dashboard/dashboard.component.html',
    styleUrls: ['app/dashboard/dashboard.component.css']
})

export class DashboardComponent {

    request: DiaryEntrySearchRequest;
    searchResponse: DiaryEntrySearchResponse;

    constructor(
        private restService : RestService,
        private _router: Router) {
      this.request = new DiaryEntrySearchRequest();
      this.request.term = '';
      this.request.page = 0;
      this.request.pageSize = 25;
    }

    onKeyPressed(event) {
      if(event.keyCode == 13) {
        this.startSearch();
      }
    }

    startSearch() {
      this.restService.search(this.request).subscribe(resp => this.searchResponse = resp);
    }

    openEntry(id : string) {
      this._router.navigate(['Editor', { id: id }]);
    }

    deleteEntry(id : string) {
      this.restService.delete(id).subscribe(resp => this.startSearch());
    }
}
