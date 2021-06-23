package com.example.demo.service;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.mapping.MappingDTOtoClass;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.configuration.IMockitoConfiguration;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    private AuthorService authorService;

    private AuthorRepository authorRepository;
    private MappingDTOtoClass mappingDTOtoClass;

    private List<Author> authorList = new ArrayList<>();
    private List<AuthorDTO> authorDTOList = new ArrayList<>();
    @Captor private ArgumentCaptor<Author> argumentCaptor;



    @BeforeEach
    public void testAuthor(){
        authorRepository = Mockito.mock(AuthorRepository.class);
        mappingDTOtoClass = Mockito.mock(MappingDTOtoClass.class);
        authorService = new AuthorService(Mockito.mock(BookRepository.class), authorRepository, mappingDTOtoClass);
    }

    @Test
    void addAuthor() {
        //arrange
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setAuthorDOB(LocalDate.of(1956, 1, 8));
        authorDTO.setAuthorName("UnitTestAuth");

        Author author = new Author();
        author.setAuthorName("UnitTestAuth");
        author.setAuthorDOB(LocalDate.of(1956, 1, 8));


        Mockito.when(mappingDTOtoClass.authorToDTO(authorDTO)).thenReturn(author);

        //act
        authorService.addAuthor(authorDTO);

        //assert
        Mockito.verify(authorRepository).save(author);
    }



    @Test
    void getAllAuthors() {
        //arrange
        Author author = Mockito.mock(Author.class);
        AuthorDTO authorDTO = Mockito.mock(AuthorDTO.class);

        authorDTOList.add(authorDTO);
        authorDTOList.add(authorDTO);
        authorDTOList.add(authorDTO);

        authorList.add(author);
        authorList.add(author);
        authorList.add(author);


        Mockito.when(mappingDTOtoClass.DTOToAuthor(author)).thenReturn(authorDTO);
        Mockito.when(authorRepository.findAll()).thenReturn(authorList);


        //act
        List<AuthorDTO> authorDTORes = authorService.getAllAuthors();

        //assert
        Mockito.verify(authorRepository).findAll();
        assertEquals(authorDTOList,authorDTORes);
    }

    @Test
    void getAuthor() {
        //arrange
        Author author = Mockito.mock(Author.class);
        AuthorDTO authorDTO = Mockito.mock(AuthorDTO.class);
        String authorName = "blabla";


        Mockito.when(authorRepository.findAuthorByAuthorName(authorName)).thenReturn(Optional.of(author));
        Mockito.when(mappingDTOtoClass.DTOToAuthor(author)).thenReturn(authorDTO);


        //act
        AuthorDTO a = authorService.getAuthor(authorName);

        //assert
        assertEquals(authorDTO, a);
    }

    @Test
    void updateAuthor() {

        Author author = new Author();
        author.setAuthorName("Name");
        author.setAuthorDOB(LocalDate.of(1956, 1, 8));
        author.setId(6);


        AuthorDTO authorDTOUpd = new AuthorDTO();
        authorDTOUpd.setAuthorDOB(LocalDate.of(1956, 1, 8));
        authorDTOUpd.setAuthorName("PIUPIU");
        authorDTOUpd.setId(6);


        Mockito.when(authorRepository.findById(authorDTOUpd.getId())).thenReturn(Optional.of(author));
        Mockito.when(mappingDTOtoClass.DTOToAuthor(argumentCaptor.capture())).thenReturn(authorDTOUpd);


        AuthorDTO authorRes = authorService.updateAuthor(authorDTOUpd);

        Author authorCaptured = argumentCaptor.getValue();
        assertSame(authorCaptured, author);

        //Mockito.verify(authorRepository).findById(author.getId());

        assertEquals(authorDTOUpd.getId(), authorCaptured.getId());
        assertEquals(authorDTOUpd.getAuthorDOB(), authorCaptured.getAuthorDOB());
        assertEquals(authorDTOUpd.getAuthorName(), authorCaptured.getAuthorName());
        assertEquals(authorDTOUpd, authorRes);
    }

    @Test
    void updateAuthorException() {
        Author author = new Author();
        author.setAuthorName("Name");
        author.setAuthorDOB(LocalDate.of(1956, 1, 8));
        author.setId(6);

        AuthorDTO authorDTOUpd = new AuthorDTO();
        authorDTOUpd.setAuthorDOB(LocalDate.of(1956, 1, 8));
        authorDTOUpd.setAuthorName("PIUPIU");
        authorDTOUpd.setId(6);


        //Mockito.when(authorRepository.findById(authorDTOUpd.getId())).thenThrow(new IllegalStateException());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> authorService.updateAuthor(authorDTOUpd));

        //Assertions.assertThrows(IllegalStateException.class, () -> authorService.updateAuthor(authorDTOUpd));
        assertEquals("This author does not exist in the DB", exception.getMessage());
    }

    @Test
    void deleteAuthor() {
        //arrange
        Author author = Mockito.mock(Author.class);
        //author.setAuthorName("blabla");
        //author.setAuthorDOB(LocalDate.of(1956, 1, 8));

        Mockito.when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

        authorService.deleteAuthor(String.valueOf(author.getId()));

        Mockito.verify(authorRepository).deleteById(author.getId());

    }

    @Test
    void deleteAuthorException() {

        Author author = Mockito.mock(Author.class);

        Mockito.when(authorRepository.findById(author.getId() + 25)).thenThrow(new IllegalStateException());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> authorService.deleteAuthor(String.valueOf(author.getId())));

        //assert
        assertEquals("This author does not exist in the DB", exception.getMessage());
    }
}