package br.com.gustavo.rest_with_spring_boot_person_exercise.services;

import br.com.gustavo.rest_with_spring_boot_person_exercise.data.dto.v1.BooksDTO;
import br.com.gustavo.rest_with_spring_boot_person_exercise.exception.RequiredObjectIsNullException;
import br.com.gustavo.rest_with_spring_boot_person_exercise.model.Books;
import br.com.gustavo.rest_with_spring_boot_person_exercise.repository.BooksRepository;
import br.com.gustavo.rest_with_spring_boot_person_exercise.unitetests.mapper.mocks.MockBooks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BooksServicesTest {

    MockBooks input;

    @InjectMocks
    private BooksServices service;


    @Mock
    BooksRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockBooks();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void findById() {

        Books books = input.mockEntity(1);
        books.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(books));

        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/books/v1/1")
                            && link.getType().equals("DELETE")
                )
        );

        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        assertEquals(25D, result.getPrice());
        assertNotNull(result.getLaunchDate());

    }

    @Test
    void create() {

        Books books = input.mockEntity(1);
        Books persisted = books;
        persisted.setId(1L);

        BooksDTO dto = input.mockDTO(1);

        when(repository.save(books)).thenReturn(persisted);

        var result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        assertEquals(25D, result.getPrice());
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void testCreateWithNullBooks() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.create(null);
                });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Books books = input.mockEntity(1);
        Books persisted = books;
        persisted.setId(1L);

        BooksDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(books));
        when(repository.save(books)).thenReturn(persisted);

        var result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        assertEquals(25D, result.getPrice());
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void testUpdateWithNullBooks() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.update(null);
                });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Books books = input.mockEntity(1);
        books.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(books));

        service.delete(1L);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Books.class));
        verifyNoMoreInteractions(repository);
    }


    @Test
    void findAll() {
        List<Books> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        List<BooksDTO> listBooks = service.findAll();

        assertNotNull(listBooks);
        assertEquals(14,listBooks.size());

        var booksOne = listBooks.get(1);

        assertNotNull(booksOne);
        assertNotNull(booksOne.getId());
        assertNotNull(booksOne.getLinks());

        assertNotNull(booksOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(booksOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(booksOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(booksOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(booksOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Author Test1", booksOne.getAuthor());
        assertEquals("Title Test1", booksOne.getTitle());
        assertEquals(25D, booksOne.getPrice());
        assertNotNull(booksOne.getLaunchDate());

        var booksFour = listBooks.get(4);

        assertNotNull(booksFour);
        assertNotNull(booksFour.getId());
        assertNotNull(booksFour.getLinks());

        assertNotNull(booksFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(booksFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(booksFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(booksFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(booksFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Author Test4", booksFour.getAuthor());
        assertEquals("Title Test4", booksFour.getTitle());
        assertEquals(25D, booksFour.getPrice());
        assertNotNull(booksFour.getLaunchDate());

        var booksSeven = listBooks.get(7);

        assertNotNull(booksSeven);
        assertNotNull(booksSeven.getId());
        assertNotNull(booksSeven.getLinks());

        assertNotNull(booksSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(booksSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(booksSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(booksSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(booksSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Author Test7", booksSeven.getAuthor());
        assertEquals("Title Test7", booksSeven.getTitle());
        assertEquals(25D, booksSeven.getPrice());
        assertNotNull(booksSeven.getLaunchDate());
    }

}