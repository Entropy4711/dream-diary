import {Component} from 'angular2/core';
import {DiaryEntry} from '../model/diary-entry';
import {RestService} from '../service/rest.service';
import {RouteParams} from 'angular2/router';
import {ItemListEntry} from './item-list-entry.component';
import {ImageItemListEntry} from './image-item-list-entry.component';
import {ListEntry} from '../model/list-entry';
import { Router } from 'angular2/router';

@Component({
  selector: 'editor',
  templateUrl: 'app/editor/editor.component.html',
  styleUrls: ['app/editor/editor.component.css'],
  directives: [ItemListEntry, ImageItemListEntry]
})

export class EditorComponent {

  entry : DiaryEntry;
  caption : string;

  constructor(
      private restService : RestService,
      private routeParams: RouteParams,
      private _router: Router) {
    this.entry = new DiaryEntry();
    this.entry.tags = [];
    this.entry.images = [];
  }

  ngOnInit() {
    var id = this.routeParams.get('id');

    if(id) {
      this.caption = "Edit entry";
      this.restService.get(id).subscribe(e => this.setEntry(e));
    } else  {
      this.caption = "Add entry";
      this.restService.save(this.entry).subscribe(e => this.setEntry(e));
    }
  }

  setEntry(entry : DiaryEntry) {
    this.entry = entry;
  }

  addTag() {
    var newListEntry : ListEntry = new ListEntry();
    newListEntry.isEditing = true;
    this.entry.tags.push(newListEntry);
  }

  addImage() {
    var newListEntry : ListEntry = new ListEntry();
    newListEntry.isEditing = true;
    this.entry.images.push(newListEntry);
  }

  saveEntry() {
    for(var i = 0; i < this.entry.images.length; i++) {
      var listEntry : ListEntry = this.entry.images[i];
      var uploader = listEntry.uploader;

      if(uploader) {
        var queue = uploader.queue;

        if(queue && queue.length == 1) {
          listEntry.name = uploader.queue[0].file.name;
          uploader.uploadAll();
        }
      }

      listEntry.uploader = null;
    }

    this.restService.save(this.entry).subscribe(e => this.setEntry(e));
  }

  reload(savedEntry : DiaryEntry) {
    this._router.navigate(['Editor', { id: savedEntry.id }]);
  }

  cancel() {
    this.restService.delete(this.entry.id).subscribe(resp => this._router.navigate(['Dashboard']));
  }
}
