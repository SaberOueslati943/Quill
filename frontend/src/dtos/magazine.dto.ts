export interface MagazineDTO {
  id: number;
  title: string;
  publicationDate: Date;
  issueNumber: number;
  authorIds: number[];
}