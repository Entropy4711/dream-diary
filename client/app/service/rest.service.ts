import {Injectable} from 'angular2/core';
import {Entry} from '../model/entry';
import {Http, Response, Headers, RequestOptions} from 'angular2/http';
import 'rxjs/Rx';
import {Observable} from 'rxjs/Observable';
import {DiaryEntrySearchRequest} from '../model/diary-entry-search-request';
import {DiaryEntrySearchResponse} from '../model/diary-entry-search-response';

@Injectable()
export class RestService {

  private saveEntryUrl = 'http://localhost:8080/entry';
  private searchEntriesUrl = 'http://localhost:8080/entries';

  constructor(private http: Http) {}

  save(entry: Entry) : Observable<Entry> {
    console.info(entry);
    let body = JSON.stringify(entry);
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    return this.http.post(this.saveEntryUrl, body, options)
                    .map(res =>  <Entry> res.json())
                    .catch(this.handleError);
  }

  search(request: DiaryEntrySearchRequest) : Observable<DiaryEntrySearchResponse> {
    console.info(request);
    let body = JSON.stringify(request);
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    return this.http.post(this.searchEntriesUrl, body, options)
                    .map(res =>  <DiaryEntrySearchResponse> res.json())
                    .catch(this.handleError);
  }

  private handleError (error: Response) {
    console.error(error);
    return Observable.throw(error.json().error || 'Server error');
  }
}
