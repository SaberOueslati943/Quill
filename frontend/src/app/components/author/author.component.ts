import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthorDTO } from '../../../dtos/author.dto';
import { DataService } from '../../services/data';

@Component({
  selector: 'app-author',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.scss']
})
export class AuthorComponent implements OnInit {
  authorForm: FormGroup;
  authors: AuthorDTO[] = [];

  constructor(
    private fb: FormBuilder,
    private dataService: DataService
  ) {
    this.authorForm = this.fb.group({
      name: ['', [Validators.required]],
      birthDate: ['', [Validators.required, this.pastDateValidator]],
      nationality: ['', [Validators.required]]
    });
  }

  ngOnInit() {

    console.log('TESTTESTTESTTESTTESTTESTTEST');
    this.loadAuthors();
  }

  loadAuthors(): void {
    this.dataService.getAuthors().subscribe({
      next: (data) => {
        console.log('🔁 Authors loaded:', data);
        this.authors = data;
      },
      error: (err) => {
        console.error('❌ Failed to load authors:', err);
      }
    });
  }

  pastDateValidator(control: any) {
    const today = new Date();
    const birthDate = new Date(control.value);
    return birthDate < today ? null : { pastDate: true };
  }

  onSubmit() {
    if (this.authorForm.valid) {
      const formValue = this.authorForm.value;

      const newAuthor = {
        name: formValue.name,
        birthDate: new Date(formValue.birthDate),
        nationality: formValue.nationality
      };

      console.log('📤 Submitting author:', newAuthor);

      this.dataService.addAuthor(newAuthor).subscribe({
        next: (createdAuthor) => {
          console.log('✅ Author created:', createdAuthor);
          this.authorForm.reset();
          this.loadAuthors();
        },
        error: (err) => {
          console.error('❌ Error adding author:', err);
          alert('Failed to add author. Please try again.');
        }
      });
    } else {
      console.warn('⚠️ Form is invalid, submission blocked');
    }
  }
}