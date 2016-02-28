import {Component} from 'angular2/core';
import {Input} from 'angular2/core';
import {ListEntry} from '../model/list-entry';
import {DiaryEntry} from '../model/diary-entry';

@Component({
  selector: 'item-list-entry',
  templateUrl: 'app/editor/item-list-entry.component.html',
  styleUrls: ['app/editor/item-list-entry.component.css']
})

export class ItemListEntry {

  @Input() listEntry : ListEntry;
  @Input() listContainer : ListEntry[];

  toggleEditing() {
    this.listEntry.isEditing = !this.listEntry.isEditing;
  }

  onKeyPressed(event) {
    if(event.keyCode == 13) {
      this.listEntry.isEditing = false;
    }
  }

  removeListEntry() {
    var index = this.listContainer.indexOf(this.listEntry);
    this.listContainer.splice(index, 1);
  }
}
