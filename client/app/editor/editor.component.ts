import {Component} from 'angular2/core';
import {Entry} from '../model/entry';
import {RestService} from '../service/rest.service';

@Component({
  selector: 'editor',
  templateUrl: 'app/editor/editor.component.html',
  styleUrls: ['app/editor/editor.component.css']
})

export class EditorComponent {

  entry : Entry;

  constructor(private restService : RestService) {
    this.entry = new Entry();
  }

  saveEntry() {
     this.restService.save(this.entry).subscribe(e => this.entry = e);
  }
}
