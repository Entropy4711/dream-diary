System.register(['angular2/core', '../model/diary-entry', '../service/rest.service', 'angular2/router', './item-list-entry.component', './image-item-list-entry.component', '../model/list-entry'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, diary_entry_1, rest_service_1, router_1, item_list_entry_component_1, image_item_list_entry_component_1, list_entry_1, router_2;
    var EditorComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (diary_entry_1_1) {
                diary_entry_1 = diary_entry_1_1;
            },
            function (rest_service_1_1) {
                rest_service_1 = rest_service_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
                router_2 = router_1_1;
            },
            function (item_list_entry_component_1_1) {
                item_list_entry_component_1 = item_list_entry_component_1_1;
            },
            function (image_item_list_entry_component_1_1) {
                image_item_list_entry_component_1 = image_item_list_entry_component_1_1;
            },
            function (list_entry_1_1) {
                list_entry_1 = list_entry_1_1;
            }],
        execute: function() {
            EditorComponent = (function () {
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
                    __metadata('design:paramtypes', [rest_service_1.RestService, router_1.RouteParams, router_2.Router])
                ], EditorComponent);
                return EditorComponent;
            }());
            exports_1("EditorComponent", EditorComponent);
        }
    }
});
//# sourceMappingURL=editor.component.js.map