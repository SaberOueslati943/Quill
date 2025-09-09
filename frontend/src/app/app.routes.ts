import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { AuthorComponent } from './components/author/author.component';
import { BookComponent } from './components/book/book.component';
import { MagazineComponent } from './components/magazine/magazine.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'authors', component: AuthorComponent },
  { path: 'books', component: BookComponent },
  { path: 'magazines', component: MagazineComponent },
  { path: '**', redirectTo: '' }
];