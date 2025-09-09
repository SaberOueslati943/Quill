import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthorDTO } from '../../../dtos/author.dto';
import { MagazineDTO } from '../../../dtos/magazine.dto';
import { DataService } from '../../services/data';

@Component({
  selector: 'app-magazine',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './magazine.component.html',
  styleUrls: ['./magazine.component.scss']
})
export class MagazineComponent implements OnInit {
  magazineForm: FormGroup;
  magazines: MagazineDTO[] = [];
  authors: AuthorDTO[] = [];
  selectedAuthorIds: number[] = [];

  constructor(
    private fb: FormBuilder,
    private dataService: DataService
  ) {
    this.magazineForm = this.fb.group({
      title: ['', Validators.required],
      publicationDate: ['', Validators.required],
      issueNumber: ['', [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit() {
    this.loadMagazines();
    this.loadAuthors();
  }

  loadMagazines() {
    this.dataService.getMagazines().subscribe({
      next: (magazines) => (this.magazines = magazines),
      error: (err) => console.error('Failed to load magazines:', err)
    });
  }

  loadAuthors() {
    this.dataService.getAuthors().subscribe({
      next: (authors) => (this.authors = authors),
      error: (err) => console.error('Failed to load authors:', err)
    });
  }

  onAuthorChange(authorId: number, event: Event) {
    const checked = (event.target as HTMLInputElement).checked;
    if (checked) {
      this.selectedAuthorIds.push(authorId);
    } else {
      this.selectedAuthorIds = this.selectedAuthorIds.filter(id => id !== authorId);
    }
  }

  getAuthorNames(authorIds: number[]): string {
    return authorIds
      .map(id => this.authors.find(a => a.id === id)?.name || 'Unknown')
      .join(', ');
  }

  onSubmit() {
    if (this.magazineForm.valid && this.selectedAuthorIds.length > 0) {
      const formValue = this.magazineForm.value;

      const magazinePayload: Omit<MagazineDTO, 'id'> = {
        title: formValue.title,
        publicationDate: new Date(formValue.publicationDate),
        issueNumber: Number(formValue.issueNumber),
        authorIds: this.selectedAuthorIds
      };

      console.log('📤 Adding magazine:', magazinePayload);

      this.dataService.addMagazine(magazinePayload).subscribe({
        next: (createdMagazine) => {
          console.log('✅ Magazine added:', createdMagazine);
          this.magazineForm.reset();
          this.selectedAuthorIds = [];
          this.loadMagazines();

          // Manually clear checkbox selections in the DOM
          const checkboxes = document.querySelectorAll('.checkbox-input') as NodeListOf<HTMLInputElement>;
          checkboxes.forEach(cb => cb.checked = false);
        },
        error: (err) => {
          console.error('❌ Failed to add magazine:', err);
          alert('Failed to add magazine. Please try again.');
        }
      });
    } else {
      console.warn('⚠️ Form invalid or no authors selected');
    }
  }
}
