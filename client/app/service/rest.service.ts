import {Injectable} from 'angular2/core';
import {Entry} from '../model/entry';
import {Http, Response, Headers, RequestOptions} from 'angular2/http';
import 'rxjs/Rx';
import {Observable} from 'rxjs/Observable';
import {DiaryEntrySearchRequest} from '../model/diary-entry-search-request';
import {DiaryEntrySearchResponse} from '../model/diary-entry-search-response';
import {URLSearchParams} from 'angular2/http';

@Injectable()
export class RestService {

  private searchEntriesUrl = 'http://localhost:8080/entries';

  constructor(private http: Http) {}

  save(entry: Entry) : Observable<Entry> {
    let body = JSON.stringify(entry);
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    return this.http.post(this.searchEntriesUrl, body, options)
                    .map(res =>  <Entry> res.json())
                    .catch(this.handleError);
  }

  get(id : string) : Observable<Entry> {
    var url = this.searchEntriesUrl + '/' + id;
    return this.http.get(url)
                    .map(res =>  <Entry> res.json())
                    .catch(this.handleError);
  }

  search(request: DiaryEntrySearchRequest) : Observable<DiaryEntrySearchResponse> {
    console.info(request);
    var params = new URLSearchParams();
    params.set('term', request.term);
    params.set('page', request.page + '');
    params.set('pageSize', request.pageSize + '');

    if(request.sortField) {
      params.set('sortField', request.sortField);
    }

    if(request.sortAscending) {
      params.set('sortAscending', request.sortAscending + '');
    }

    return this.http.get(this.searchEntriesUrl, { search: params })
                    .map(res =>  <DiaryEntrySearchResponse> res.json())
                    .catch(this.handleError);
  }

  private handleError (error: Response) {
    console.error(error);
    return Observable.throw(error.json().error || 'Server error');
  }
}
