import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DataService } from '../../services/data';
import { AuthorDTO } from '../../../dtos/author.dto';
import { BookDTO } from '../../../dtos/book.dto';

@Component({
  selector: 'app-book',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.scss']
})
export class BookComponent implements OnInit {
  bookForm: FormGroup;
  books: BookDTO[] = [];
  authors: AuthorDTO[] = [];

  constructor(
    private fb: FormBuilder,
    private dataService: DataService
  ) {
    this.bookForm = this.fb.group({
      title: ['', [Validators.required]],
      publicationDate: ['', [Validators.required]],
      isbn: ['', [Validators.required]],
      authorId: ['', [Validators.required]]
    });
  }

  ngOnInit() {
    this.loadBooks();
    this.loadAuthors();
  }

  loadBooks() {
    this.dataService.getBooks().subscribe({
      next: (books) => this.books = books,
      error: (err) => console.error('Failed to load books:', err)
    });
  }

  loadAuthors() {
    this.dataService.getAuthors().subscribe({
      next: (authors) => this.authors = authors,
      error: (err) => console.error('Failed to load authors:', err)
    });
  }

  getAuthorName(authorId: number): string {
    const author = this.authors.find(a => a.id === authorId);
    return author ? author.name : 'Unknown Author';
  }

  onSubmit() {
    if (this.bookForm.valid) {
      const formValue = this.bookForm.value;

      const newBook = {
        title: formValue.title,
        publicationDate: new Date(formValue.publicationDate),
        isbn: formValue.isbn,
        authorId: Number(formValue.authorId)  // safer parsing
      };

      console.log('📤 Adding book:', newBook);

      this.dataService.addBook(newBook).subscribe({
        next: (createdBook) => {
          console.log('✅ Book added:', createdBook);
          this.bookForm.reset();
          this.loadBooks(); // refresh the book list after successful add
        },
        error: (err) => {
          console.error('❌ Failed to add book:', err);
          alert('Failed to add book. Please try again.');
        }
      });
    } else {
      console.warn('⚠️ Book form is invalid, submission blocked');
    }
  }
}