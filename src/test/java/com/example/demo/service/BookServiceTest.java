package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.mapping.MappingDTOtoClass;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    private BookService bookService;
    private MappingDTOtoClass mappingDTOtoClass;

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private List<Book> bookList = new ArrayList<>();
    private List<BookDTO> bookDTOList= new ArrayList<>();
    private List <Author> authorList = new ArrayList<>();
    private List <String> authorIDSList = new ArrayList<>();
    @Captor private ArgumentCaptor<Book> argumentCaptor;


    @BeforeEach
    public void testBook() {
        bookRepository = Mockito.mock(BookRepository.class);
        authorRepository = Mockito.mock(AuthorRepository.class);
        mappingDTOtoClass = Mockito.mock(MappingDTOtoClass.class);
        bookService = new BookService(bookRepository, authorRepository, mappingDTOtoClass);
    }

    @Test
    void getBooks() {
        //arrange
        Book book = new Book();
        book.setBookName("TestBookName");
        book.setBookDescription("This should be meaningful");
        book.setId(6);

        authorList.add(Mockito.mock(Author.class));
        book.setAuthorsList(authorList);

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(6);

        bookList.add(book);
        bookList.add(book);
        bookList.add(book);
        bookList.add(book);



        Mockito.when(bookRepository.findAll()).thenReturn(bookList);
        Mockito.when(mappingDTOtoClass.bookToDTO(book)).thenReturn(bookDTO);

        for (Author author : book.getAuthorsList()) {
            Mockito.when(authorRepository.findById(0)).thenReturn(Optional.of(author));
            authorIDSList.add(String.valueOf(author.getId()));
        }
        bookDTO.setAuthorsIDsDTOList(authorIDSList);
        bookDTOList.add(bookDTO);

        //Mockito.when(bookDTO.setAuthorsIDsDTOList()).thenReturn();
        //act
        List<BookDTO> dtos = bookService.getBooks();

        //assert
        Mockito.verify(bookRepository).findAll();

        assertEquals(bookList.size(), dtos.size());
        assertEquals(book.getId(), dtos.get(0).getId());
        assertEquals(book.getAuthorsList().size(), dtos.get(0).getAuthorsIDsDTOList().size());
    }

    @Test
    void addBook() {
        //arrange
        Book book = new Book();
        book.setBookName("TestBookName");
        book.setBookDescription("This should be meaningful");

        Author author = Mockito.mock(Author.class);
        authorList.add(author);
        authorIDSList.add(String.valueOf(author.getId()));


        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookName("TestBookName");
        bookDTO.setBookDescription("This should be meaningful");
        bookDTO.setAuthorsIDsDTOList(authorIDSList);

        List<Integer> intAuthorIds = authorIDSList.stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());


        Mockito.when(mappingDTOtoClass.DTOToBook(bookDTO)).thenReturn(book);
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        Mockito.when(authorRepository.findAuthorsByIdIn(intAuthorIds)).thenReturn(authorList);


        //act
        bookService.addBook(bookDTO);

        //assert
        Mockito.verify(bookRepository).save(book);

        assertEquals(authorList, book.getAuthorsList());
    }

    @Test
    void deleteBook() {
        //arrange
        Book book = Mockito.mock(Book.class);

        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        //act
        bookService.deleteBook(book.getId());

        //assert
        Mockito.verify(bookRepository).deleteById(book.getId());
    }

    @Test
    void deleteBookException() {
        //arrange
        Mockito.when(bookRepository.findById(1)).thenThrow(new IllegalStateException("This book does not exist in the DB"));

        //act
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> bookService.deleteBook(1));

        //assert
        assertEquals("This book does not exist in the DB", illegalStateException.getMessage());

    }

    @Test
    void searchBook() {
        //arrange
        Book book = new Book();
        book.setBookName("TestBookName");
        book.setBookDescription("This should be meaningful");
        authorList.add(Mockito.mock(Author.class));
        authorList.add(Mockito.mock(Author.class));
        authorList.add(Mockito.mock(Author.class));
        book.setAuthorsList(authorList);

        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookName("DTOName");
        bookDTO.setBookDescription("This should be meaningful");
        //bookDTO.setAuthorsIDsDTOList(authorIDSList);


        Mockito.when(bookRepository.findBookByBookName(book.getBookName())).thenReturn(Optional.of(book));
        Mockito.when(mappingDTOtoClass.bookToDTO(book)).thenReturn(bookDTO);


        //act
        BookDTO bookDTORes = bookService.searchBook(book.getBookName());

        //assert
        Mockito.verify(bookRepository).findBookByBookName(book.getBookName());
        Mockito.verify(mappingDTOtoClass).bookToDTO(book);

        assertEquals(bookDTO,bookDTORes);
    }

    @Test
    void searchBookException() {
        //arrange
        Book book = Mockito.mock(Book.class);
        BookDTO bookDTO = Mockito.mock(BookDTO.class);

        Mockito.when(mappingDTOtoClass.bookToDTO(book)).thenReturn(bookDTO);

        //act
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> bookService.searchBook(book.getBookName()));

        //assert
        assertEquals("This book does not exist in the DB", illegalStateException.getMessage());
    }

    @Test
    void updateBookFields() {
        //arrange

        Book book = new Book();
        book.setBookName("TestBookName");
        book.setBookDescription("This should be meaningful");
        book.setId(69);


        Author author = Mockito.mock(Author.class);
        Author author2 = Mockito.mock(Author.class);
        Author author3 = Mockito.mock(Author.class);

        author.addBook(book);
        author2.addBook(book);
        author3.addBook(book);
        authorList.add(author);
        authorList.add(author2);
        authorList.add(author3);

        book.setAuthorsList(authorList);

        authorIDSList.add(String.valueOf(author.getId()));
        authorIDSList.add(String.valueOf(author2.getId()));
        authorIDSList.add(String.valueOf(author3.getId()));

        BookDTO bookUpdValues = new BookDTO();
        bookUpdValues.setBookName("UPDATED");
        bookUpdValues.setBookDescription("Bla");
        bookUpdValues.setAuthorsIDsDTOList(authorIDSList);
        bookUpdValues.setId(69);


        Mockito.when(bookRepository.findById(bookUpdValues.getId())).thenReturn(Optional.of(book));
        Mockito.when(mappingDTOtoClass.bookToDTO(argumentCaptor.capture())).thenReturn(bookUpdValues);

        BookDTO bookMethodRes = bookService.updateBookFields(bookUpdValues);



        //Mockito.verify(mappingDTOtoClass).bookToDTO(argumentCaptor.capture());
        Book captorValue = argumentCaptor.getValue();
        assertSame(captorValue, book);

        List<String> authorsIds = new ArrayList<>();
        for (Author author1 : captorValue.getAuthorsList()) {
            authorsIds.add(String.valueOf(author1.getId()));
        }

        assertEquals(bookUpdValues.getId(),captorValue.getId());
        assertEquals(bookUpdValues.getBookName(),captorValue.getBookName());
        assertEquals(bookUpdValues.getBookDescription(),captorValue.getBookDescription());
        assertEquals(bookUpdValues.getAuthorsIDsDTOList(),authorsIds);
        assertEquals(bookUpdValues, bookMethodRes);
    }

    @Test
    void updateBookFieldException() {
        //arrange
        Book book = new Book();
        book.setBookName("TestBookName");
        book.setBookDescription("This should be meaningful");
        book.setId(69);


        Author author = Mockito.mock(Author.class);
        Author author2 = Mockito.mock(Author.class);
        Author author3 = Mockito.mock(Author.class);

        author.addBook(book);
        author2.addBook(book);
        author3.addBook(book);
        authorList.add(author);
        authorList.add(author2);
        authorList.add(author3);

        book.setAuthorsList(authorList);

        authorIDSList.add(String.valueOf(author.getId()));
        authorIDSList.add(String.valueOf(author2.getId()));
        authorIDSList.add(String.valueOf(author3.getId()));

        BookDTO bookUpdValues = new BookDTO();
        bookUpdValues.setBookName("UPDATED");
        bookUpdValues.setBookDescription("Bla");
        bookUpdValues.setAuthorsIDsDTOList(authorIDSList);
        bookUpdValues.setId(69);


        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> bookService.updateBookFields(bookUpdValues));

        assertEquals("This book does not exist in the DB", exception.getMessage());
    }


    @Test
    void removeAuthorFromBook() {
        //arrange
        Book book = Mockito.mock(Book.class);
        Author author = Mockito.mock(Author.class);
        Author author2 = Mockito.mock(Author.class);
        Author author3 = Mockito.mock(Author.class);
        author.addBook(book);
        author2.addBook(book);
        author3.addBook(book);
        authorList.add(author);
        authorList.add(author2);
        authorList.add(author3);
        book.setAuthorsList(authorList);


        bookService.removeAuthorFromBook(authorList, book);


        //assert
        Mockito.verify(author, Mockito.times(1)).removeBook(book);
        assertEquals(author.getBooksList(), null);
    }

    @Test
    void getAuthorsFromDTO() {
        //arrange
        Author author = Mockito.mock(Author.class);

        authorIDSList.add(String.valueOf(author.getId()));
        authorIDSList.add(String.valueOf(author.getId()));
        authorIDSList.add(String.valueOf(author.getId()));

        Mockito.when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

        Optional<Author> author1 = authorRepository.findById(author.getId());

        Mockito.verify(authorRepository).findById(author.getId());
        assertEquals(Optional.of(author),author1);
    }

    @Test
    void getAuthorsIDsFromBook() {
        List<Author> authors = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        String id = "";

        Author author = new Author();
        author.setAuthorName("Name");
        author.setAuthorDOB(LocalDate.of(1956, 1, 8));
        author.setId(6);

        Author authorOne = new Author();
        author.setAuthorName("One");
        author.setAuthorDOB(LocalDate.of(1696, 12, 9));
        author.setId(66);

        authors.add(author);
        authors.add(authorOne);

        for (Author author1 : authors) {
            Mockito.when(authorRepository.findById(author1.getId())).thenReturn(Optional.of(author));
        }


    }
}