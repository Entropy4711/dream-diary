System.register(['angular2/core', '../model/entry', '../service/rest.service', 'angular2/router'], function(exports_1, context_1) {
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
    var core_1, entry_1, rest_service_1, router_1;
    var EditorComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (entry_1_1) {
                entry_1 = entry_1_1;
            },
            function (rest_service_1_1) {
                rest_service_1 = rest_service_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            }],
        execute: function() {
            EditorComponent = (function () {
                function EditorComponent(restService, _routeParams) {
                    this.restService = restService;
                    this._routeParams = _routeParams;
                    this.entry = new entry_1.Entry();
                }
                EditorComponent.prototype.ngOnInit = function () {
                    var _this = this;
                    var id = this._routeParams.get('id');
                    if (id) {
                        this.caption = "Edit entry";
                        this.restService.get(id).subscribe(function (e) { return _this.entry = e; });
                    }
                    else {
                        this.caption = "Add entry";
                    }
                };
                EditorComponent.prototype.saveEntry = function () {
                    var _this = this;
                    this.restService.save(this.entry).subscribe(function (e) { return _this.entry = e; });
                };
                EditorComponent = __decorate([
                    core_1.Component({
                        selector: 'editor',
                        templateUrl: 'app/editor/editor.component.html',
                        styleUrls: ['app/editor/editor.component.css']
                    }), 
                    __metadata('design:paramtypes', [rest_service_1.RestService, router_1.RouteParams])
                ], EditorComponent);
                return EditorComponent;
            }());
            exports_1("EditorComponent", EditorComponent);
        }
    }
});
//# sourceMappingURL=editor.component.js.map