package com.saberoueslati.quill;

import com.saberoueslati.quill.entity.Publication;
import com.saberoueslati.quill.repository.PublicationRepository;
import com.saberoueslati.quill.service.impl.PublicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class PublicationServiceTest {

    @Mock
    private PublicationRepository publicationRepository;

    @InjectMocks
    private PublicationServiceImpl publicationService;

    private Publication samplePublication;

    @BeforeEach
    void setUp() {
        samplePublication = new Publication() {
            {
                setId(1L);
                setTitle("Artificial Intelligence Today");
                setPublicationDate(LocalDate.of(2023, 10, 5));
            }
        };
    }

    @Test
    void getPublications_shouldReturnPaginatedList() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Publication> page = new PageImpl<>(List.of(samplePublication));

        when(publicationRepository.findAll(pageable)).thenReturn(page);

        Page<Publication> result = publicationService.getPublications(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Artificial Intelligence Today", result.getContent().get(0).getTitle());
        verify(publicationRepository).findAll(pageable);
    }

    @Test
    void searchPublicationsByTitle_shouldReturnMatchingResults() {
        when(publicationRepository.findByTitleContainingIgnoreCase("intel"))
                .thenReturn(List.of(samplePublication));

        List<Publication> result = publicationService.searchPublicationsByTitle("intel");

        assertEquals(1, result.size());
        assertEquals("Artificial Intelligence Today", result.get(0).getTitle());
        verify(publicationRepository).findByTitleContainingIgnoreCase("intel");
    }
}
