import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './components/navbar/navbar.component';

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
export class AppComponent {
  title = 'books-magazines-app';
}