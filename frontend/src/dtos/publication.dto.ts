export interface PublicationDTO {
  id: number;
  title: string;
  publicationDate: Date;
  type: 'BOOK' | 'MAGAZINE';
}