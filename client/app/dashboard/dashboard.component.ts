import {Component} from 'angular2/core';
import {DiaryEntrySearchRequest} from '../model/diary-entry-search-request';
import {DiaryEntrySearchResponse} from '../model/diary-entry-search-response';
import {RestService} from '../service/rest.service';

@Component({
    selector: 'dashboard',
    templateUrl: 'app/dashboard/dashboard.component.html'
})

export class DashboardComponent {

    request: DiaryEntrySearchRequest;
    searchResponse: DiaryEntrySearchResponse;

    constructor(private restService : RestService) {
      this.request = new DiaryEntrySearchRequest();
      this.request.text = "";
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
}
