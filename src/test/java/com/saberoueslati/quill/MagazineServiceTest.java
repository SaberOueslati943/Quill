package com.saberoueslati.quill;

import com.saberoueslati.quill.entity.Author;
import com.saberoueslati.quill.entity.Magazine;
import com.saberoueslati.quill.repository.MagazineRepository;
import com.saberoueslati.quill.service.impl.MagazineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MagazineServiceTest {

    @Mock
    private MagazineRepository magazineRepository;

    @InjectMocks
    private MagazineServiceImpl magazineService;

    private Magazine sampleMagazine;
    private Author sampleAuthor;

    @BeforeEach
    void setUp() {
        sampleAuthor = new Author();
        sampleAuthor.setId(1L);
        sampleAuthor.setName("Jane Doe");
        sampleAuthor.setBirthDate(LocalDate.of(1980, 3, 14));
        sampleAuthor.setNationality("Canadian");

        sampleMagazine = new Magazine();
        sampleMagazine.setId(100L);
        sampleMagazine.setTitle("Tech Monthly");
        sampleMagazine.setPublicationDate(LocalDate.of(2024, 7, 1));
        sampleMagazine.setIssueNumber(42);
        sampleMagazine.setAuthors(List.of(sampleAuthor));
    }

    @Test
    void addMagazine_shouldReturnSavedMagazine() {
        when(magazineRepository.save(sampleMagazine)).thenReturn(sampleMagazine);

        Magazine result = magazineService.addMagazine(sampleMagazine);

        assertNotNull(result);
        assertEquals("Tech Monthly", result.getTitle());
        verify(magazineRepository).save(sampleMagazine);
    }

    @Test
    void addMagazines_shouldReturnSavedList() {
        List<Magazine> magazines = List.of(sampleMagazine);
        when(magazineRepository.saveAll(magazines)).thenReturn(magazines);

        List<Magazine> result = magazineService.addMagazines(magazines);

        assertEquals(1, result.size());
        assertEquals("Tech Monthly", result.get(0).getTitle());
        verify(magazineRepository).saveAll(magazines);
    }

    @Test
    void getAllMagazines_shouldReturnAll() {
        when(magazineRepository.findAll()).thenReturn(List.of(sampleMagazine));

        List<Magazine> result = magazineService.getAllMagazines();

        assertEquals(1, result.size());
        verify(magazineRepository).findAll();
    }
}
