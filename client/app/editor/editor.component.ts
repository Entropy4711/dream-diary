import {Component} from 'angular2/core';
import {Entry} from '../model/entry';
import {RestService} from '../service/rest.service';
import {RouteParams} from 'angular2/router';

@Component({
  selector: 'editor',
  templateUrl: 'app/editor/editor.component.html',
  styleUrls: ['app/editor/editor.component.css']
})

export class EditorComponent {

  entry : Entry;
  caption : string;

  constructor(
      private restService : RestService,
      private _routeParams: RouteParams) {
    this.entry = new Entry();
  }

  ngOnInit() {
    var id = this._routeParams.get('id');

    if(id) {
      this.caption = "Edit entry";
      this.restService.get(id).subscribe(e => this.entry = e);
    } else  {
      this.caption = "Add entry";
    }
  }


  saveEntry() {
     this.restService.save(this.entry).subscribe(e => this.entry = e);
  }
}
