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
var core_1 = require('@angular/core');
var diary_entry_search_request_1 = require('../model/diary-entry-search-request');
var rest_service_1 = require('../service/rest.service');
var router_1 = require('@angular/router');
var DashboardComponent = (function () {
    function DashboardComponent(restService, _router) {
        this.restService = restService;
        this._router = _router;
        this.request = new diary_entry_search_request_1.DiaryEntrySearchRequest();
        this.request.term = '';
        this.request.page = 0;
        this.request.pageSize = 25;
    }
    DashboardComponent.prototype.onKeyPressed = function (event) {
        if (event.keyCode == 13) {
            this.startSearch();
        }
    };
    DashboardComponent.prototype.startSearch = function () {
        var _this = this;
        this.restService.search(this.request).subscribe(function (resp) { return _this.searchResponse = resp; });
    };
    DashboardComponent.prototype.openEntry = function (id) {
        this._router.navigate(['Editor', { id: id }]);
    };
    DashboardComponent.prototype.deleteEntry = function (id) {
        var _this = this;
        this.restService.delete(id).subscribe(function (resp) { return _this.startSearch(); });
    };
    DashboardComponent = __decorate([
        core_1.Component({
            selector: 'dashboard',
            templateUrl: 'app/dashboard/dashboard.component.html',
            styleUrls: ['app/dashboard/dashboard.component.css']
        }), 
        __metadata('design:paramtypes', [rest_service_1.RestService, router_1.Router])
    ], DashboardComponent);
    return DashboardComponent;
}());
exports.DashboardComponent = DashboardComponent;
//# sourceMappingURL=dashboard.component.js.map