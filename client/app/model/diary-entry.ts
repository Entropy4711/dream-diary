import {ListEntry} from './list-entry';

export class DiaryEntry {

  id: string;
  createdDate: string;
  title: string;
  content: string;
  tags: ListEntry[];
  images: ListEntry[];
}
