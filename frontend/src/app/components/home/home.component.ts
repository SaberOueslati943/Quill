import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DataService } from '../../services/data';
import { AuthorDTO } from '../../../dtos/author.dto';
import { PublicationDTO } from '../../../dtos/publication.dto';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  publications: PublicationDTO[] = [];
  authors: AuthorDTO[] = [];
  totalPublications = 0;

  constructor(private dataService: DataService) {}

  ngOnInit() {
    this.loadPublications();
    this.loadAuthors();
  }

  loadPublications() {
    this.dataService.getPublications().subscribe({
      next: (res) => {
        console.log('Publications loaded:', this.publications);
        this.publications = res.content;
        this.totalPublications = res.totalElements;
      },
      error: (err) => console.error('Failed to load publications:', err)
    });
  }

  loadAuthors() {
    this.dataService.getAuthors().subscribe({
      next: (authors) => this.authors = authors,
      error: (err) => console.error('Failed to load authors:', err)
    });
  }

  getBookCount(): number {
    return this.publications.filter(p => p.type === 'BOOK').length;
  }

  getMagazineCount(): number {
    return this.publications.filter(p => p.type === 'MAGAZINE').length;
  }
}
