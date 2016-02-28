import {Component} from 'angular2/core';
import { RouteConfig, ROUTER_DIRECTIVES, ROUTER_PROVIDERS } from 'angular2/router';
import {DashboardComponent} from './dashboard/dashboard.component';
import {EditorComponent} from './editor/editor.component';
import {RestService} from './service/rest.service';
import {HTTP_PROVIDERS} from 'angular2/http';

@Component({
    selector: 'dream-diary',
    templateUrl: 'app/app.component.html',
    styleUrls: ['app/app.component.css'],
    directives: [ROUTER_DIRECTIVES],
    providers: [
      RestService,
      HTTP_PROVIDERS,
      ROUTER_PROVIDERS
    ]
})

@RouteConfig([
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: DashboardComponent,
    useAsDefault: true
  }, {
    path: '/editor',
    name: 'Editor',
    component: EditorComponent
  }
])

export class AppComponent {

}
