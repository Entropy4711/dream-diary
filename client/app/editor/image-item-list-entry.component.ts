import { Component, OnInit } from '@angular/core';
import {Input} from '@angular/core';
import {ListEntry} from '../model/list-entry';
import {DiaryEntry} from '../model/diary-entry';
import {ItemListEntry} from './item-list-entry.component';
import {FILE_UPLOAD_DIRECTIVES, FileUploader} from '../../node_modules/ng2-file-upload/ng2-file-upload';

@Component({
  selector: 'image-item-list-entry',
  templateUrl: 'app/editor/image-item-list-entry.component.html',
  styleUrls: ['app/editor/image-item-list-entry.component.css'],
  directives: [FILE_UPLOAD_DIRECTIVES]
})


export class ImageItemListEntry extends ItemListEntry implements OnInit {

  @Input() listEntry : ListEntry;
  @Input() listContainer : ListEntry[];
  @Input() diaryEntry : DiaryEntry;


  baseUrl : string;
  imageUrl : string;
  uploader : FileUploader;

  ngOnInit() {
    var imageSize = '100';
    this.baseUrl = 'http://localhost:8080/images/' + this.diaryEntry.id;
    this.imageUrl = this.baseUrl + '/' + this.listEntry.name + '/?width=' + imageSize + '&height=' + imageSize;
    this.uploader = new FileUploader({ url : this.baseUrl });
    this.uploader.queueLimit = 99;
    this.listEntry.uploader = this.uploader;
  }

  showFullImage() {
      window.open(this.baseUrl + '/' + this.listEntry.name + '/');
  }
}
