System.register(['angular2/core', '../model/list-entry'], function(exports_1, context_1) {
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
    var core_1, core_2, list_entry_1;
    var ItemListEntry;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
                core_2 = core_1_1;
            },
            function (list_entry_1_1) {
                list_entry_1 = list_entry_1_1;
            }],
        execute: function() {
            ItemListEntry = (function () {
                function ItemListEntry() {
                }
                ItemListEntry.prototype.toggleEditing = function () {
                    this.listEntry.isEditing = !this.listEntry.isEditing;
                };
                ItemListEntry.prototype.onKeyPressed = function (event) {
                    if (event.keyCode == 13) {
                        this.listEntry.isEditing = false;
                    }
                };
                ItemListEntry.prototype.removeListEntry = function () {
                    var index = this.listContainer.indexOf(this.listEntry);
                    this.listContainer.splice(index, 1);
                };
                __decorate([
                    core_2.Input(), 
                    __metadata('design:type', list_entry_1.ListEntry)
                ], ItemListEntry.prototype, "listEntry", void 0);
                __decorate([
                    core_2.Input(), 
                    __metadata('design:type', Array)
                ], ItemListEntry.prototype, "listContainer", void 0);
                ItemListEntry = __decorate([
                    core_1.Component({
                        selector: 'item-list-entry',
                        templateUrl: 'app/editor/item-list-entry.component.html',
                        styleUrls: ['app/editor/item-list-entry.component.css']
                    }), 
                    __metadata('design:paramtypes', [])
                ], ItemListEntry);
                return ItemListEntry;
            }());
            exports_1("ItemListEntry", ItemListEntry);
        }
    }
});
//# sourceMappingURL=item-list-entry.component.js.map