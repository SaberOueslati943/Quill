import { Component } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { RouterOutlet } from '@angular/router';
import { routes } from './app/app.routes';
import { NavbarComponent } from './app/components/navbar/navbar.component';
import 'zone.js';
import { provideHttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent],
  template: `
    <div class="app">
      <app-navbar></app-navbar>
      <main class="main-content">
        <router-outlet></router-outlet>
      </main>
    </div>
  `,
  styles: [`
    .app {
      min-height: 100vh;
      background: #f9fafb;
    }

    .main-content {
      min-height: calc(100vh - 64px);
    }
  `]
})
export class App {
  name = 'Quill';
}

bootstrapApplication(App, {
  providers: [
    provideRouter(routes),
    provideHttpClient()
  ]
});