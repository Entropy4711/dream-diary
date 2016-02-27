System.register(['angular2/core', 'angular2/http', 'rxjs/Rx', 'rxjs/Observable'], function(exports_1, context_1) {
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
    var core_1, http_1, Observable_1, http_2;
    var RestService;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
                http_2 = http_1_1;
            },
            function (_1) {},
            function (Observable_1_1) {
                Observable_1 = Observable_1_1;
            }],
        execute: function() {
            RestService = (function () {
                function RestService(http) {
                    this.http = http;
                    this.searchEntriesUrl = 'http://localhost:8080/entries';
                }
                RestService.prototype.save = function (entry) {
                    var body = JSON.stringify(entry);
                    var headers = new http_1.Headers({ 'Content-Type': 'application/json' });
                    var options = new http_1.RequestOptions({ headers: headers });
                    return this.http.post(this.searchEntriesUrl, body, options)
                        .map(function (res) { return res.json(); })
                        .catch(this.handleError);
                };
                RestService.prototype.get = function (id) {
                    var url = this.searchEntriesUrl + '/' + id;
                    return this.http.get(url)
                        .map(function (res) { return res.json(); })
                        .catch(this.handleError);
                };
                RestService.prototype.search = function (request) {
                    console.info(request);
                    var params = new http_2.URLSearchParams();
                    params.set('term', request.term);
                    params.set('page', request.page + '');
                    params.set('pageSize', request.pageSize + '');
                    if (request.sortField) {
                        params.set('sortField', request.sortField);
                    }
                    if (request.sortAscending) {
                        params.set('sortAscending', request.sortAscending + '');
                    }
                    return this.http.get(this.searchEntriesUrl, { search: params })
                        .map(function (res) { return res.json(); })
                        .catch(this.handleError);
                };
                RestService.prototype.handleError = function (error) {
                    console.error(error);
                    return Observable_1.Observable.throw(error.json().error || 'Server error');
                };
                RestService = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [http_1.Http])
                ], RestService);
                return RestService;
            }());
            exports_1("RestService", RestService);
        }
    }
});
//# sourceMappingURL=rest.service.js.map