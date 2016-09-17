"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('angular2/core');
var diary_entry_1 = require('../model/diary-entry');
var rest_service_1 = require('../service/rest.service');
var router_1 = require('angular2/router');
var item_list_entry_component_1 = require('./item-list-entry.component');
var image_item_list_entry_component_1 = require('./image-item-list-entry.component');
var list_entry_1 = require('../model/list-entry');
var router_2 = require('angular2/router');
var EditorComponent = (function () {
    function EditorComponent(restService, routeParams, _router) {
        this.restService = restService;
        this.routeParams = routeParams;
        this._router = _router;
        this.entry = new diary_entry_1.DiaryEntry();
        this.entry.tags = [];
        this.entry.images = [];
    }
    EditorComponent.prototype.ngOnInit = function () {
        var _this = this;
        var id = this.routeParams.get('id');
        if (id) {
            this.caption = "Edit entry";
            this.restService.get(id).subscribe(function (e) { return _this.setEntry(e); });
        }
        else {
            this.caption = "Add entry";
            this.restService.save(this.entry).subscribe(function (e) { return _this.setEntry(e); });
        }
    };
    EditorComponent.prototype.setEntry = function (entry) {
        this.entry = entry;
    };
    EditorComponent.prototype.addTag = function () {
        var newListEntry = new list_entry_1.ListEntry();
        newListEntry.isEditing = true;
        this.entry.tags.push(newListEntry);
    };
    EditorComponent.prototype.addImage = function () {
        var newListEntry = new list_entry_1.ListEntry();
        newListEntry.isEditing = true;
        this.entry.images.push(newListEntry);
    };
    EditorComponent.prototype.saveEntry = function () {
        var _this = this;
        for (var i = 0; i < this.entry.images.length; i++) {
            var listEntry = this.entry.images[i];
            var uploader = listEntry.uploader;
            if (uploader) {
                var queue = uploader.queue;
                if (queue && queue.length == 1) {
                    listEntry.name = uploader.queue[0].file.name;
                    uploader.uploadAll();
                }
            }
            listEntry.uploader = null;
        }
        this.restService.save(this.entry).subscribe(function (e) { return _this.setEntry(e); });
    };
    EditorComponent.prototype.reload = function (savedEntry) {
        this._router.navigate(['Editor', { id: savedEntry.id }]);
    };
    EditorComponent.prototype.cancel = function () {
        var _this = this;
        this.restService.delete(this.entry.id).subscribe(function (resp) { return _this._router.navigate(['Dashboard']); });
    };
    EditorComponent = __decorate([
        core_1.Component({
            selector: 'editor',
            templateUrl: 'app/editor/editor.component.html',
            styleUrls: ['app/editor/editor.component.css'],
            directives: [item_list_entry_component_1.ItemListEntry, image_item_list_entry_component_1.ImageItemListEntry]
        }), 
        __metadata('design:paramtypes', [rest_service_1.RestService, (typeof (_a = typeof router_1.RouteParams !== 'undefined' && router_1.RouteParams) === 'function' && _a) || Object, (typeof (_b = typeof router_2.Router !== 'undefined' && router_2.Router) === 'function' && _b) || Object])
    ], EditorComponent);
    return EditorComponent;
    var _a, _b;
}());
exports.EditorComponent = EditorComponent;
//# sourceMappingURL=editor.component.js.map