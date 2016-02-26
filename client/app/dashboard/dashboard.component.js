System.register(['angular2/core', '../model/diary-entry-search-request', '../service/rest.service'], function(exports_1, context_1) {
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
    var core_1, diary_entry_search_request_1, rest_service_1;
    var DashboardComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (diary_entry_search_request_1_1) {
                diary_entry_search_request_1 = diary_entry_search_request_1_1;
            },
            function (rest_service_1_1) {
                rest_service_1 = rest_service_1_1;
            }],
        execute: function() {
            DashboardComponent = (function () {
                function DashboardComponent(restService) {
                    this.restService = restService;
                    this.request = new diary_entry_search_request_1.DiaryEntrySearchRequest();
                    this.request.text = "";
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
                DashboardComponent = __decorate([
                    core_1.Component({
                        selector: 'dashboard',
                        templateUrl: 'app/dashboard/dashboard.component.html'
                    }), 
                    __metadata('design:paramtypes', [rest_service_1.RestService])
                ], DashboardComponent);
                return DashboardComponent;
            }());
            exports_1("DashboardComponent", DashboardComponent);
        }
    }
});
//# sourceMappingURL=dashboard.component.js.map