import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError, throwError } from 'rxjs';
import { PublicationDTO } from '../../dtos/publication.dto';
import { AuthorDTO } from '../../dtos/author.dto';
import { MagazineDTO } from '../../dtos/magazine.dto';
import { BookDTO } from '../../dtos/book.dto';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  private baseUrl = 'http://localhost:8080/api'

  constructor(private http: HttpClient) { }

  private handleError(operation: string) {
    return (error: any) => {
      console.error(`❌ Error during ${operation}:`, error);
      return throwError(() => error); // or customize with user-friendly messages
    };
  }

  // 🔹 PUBLICATIONS
  getPublications(): Observable<{ content: PublicationDTO[], totalElements: number }> {
    return this.http.get<{ content: PublicationDTO[], totalElements: number }>(
      `${this.baseUrl}/publications?page=0&size=10`
    ).pipe(
      catchError(this.handleError('getPublications'))
    );
  }

  searchPublications(title: string): Observable<PublicationDTO[]> {
    return this.http.get<PublicationDTO[]>(
      `${this.baseUrl}/publications?title=${encodeURIComponent(title)}`
    ).pipe(
      catchError(this.handleError('searchPublications'))
    );
  }

  // 🔹 AUTHORS
  getAuthors(): Observable<AuthorDTO[]> {
    return this.http.get<AuthorDTO[]>(`${this.baseUrl}/authors`).pipe(
      catchError(this.handleError('getAuthors'))
    );
  }

  addAuthor(author: Omit<AuthorDTO, 'id'>): Observable<AuthorDTO> {
    console.log('Adding author:', author);
    return this.http.post<AuthorDTO>(`${this.baseUrl}/authors`, author).pipe(
      catchError(this.handleError('addAuthor'))
    );
    console.log('Adding author:2', author);
  }

  // 🔹 BOOKS
  getBooks(): Observable<BookDTO[]> {
    return this.http.get<BookDTO[]>(`${this.baseUrl}/books`).pipe(
      catchError(this.handleError('getBooks'))
    );
  }

  addBook(book: Omit<BookDTO, 'id'>): Observable<BookDTO> {
    return this.http.post<BookDTO>(`${this.baseUrl}/books`, book).pipe(
      catchError(this.handleError('addBook'))
    );
  }

  // 🔹 MAGAZINES
  getMagazines(): Observable<MagazineDTO[]> {
    return this.http.get<MagazineDTO[]>(`${this.baseUrl}/magazines`).pipe(
      catchError(this.handleError('getMagazines'))
    );
  }

  addMagazine(magazine: Omit<MagazineDTO, 'id'>): Observable<MagazineDTO> {
    return this.http.post<MagazineDTO>(`${this.baseUrl}/magazines`, magazine).pipe(
      catchError(this.handleError('addMagazine'))
    );
  }
}
