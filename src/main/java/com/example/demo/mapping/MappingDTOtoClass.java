package com.example.demo.mapping;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.dto.BookDTO;
import com.example.demo.model.Author;
import com.example.demo.model.Book;


public class MappingDTOtoClass {

    public BookDTO BookToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setBookName(book.getBookName());
        bookDTO.setBookDescription(book.getBookDescription());
        return bookDTO;
    }

    public Book DTOToBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setBookName(bookDTO.getBookName());
        book.setBookDescription(bookDTO.getBookDescription());
        return book;
    }

    public AuthorDTO DTOToAuthor(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setAuthorName(author.getAuthorName());
        authorDTO.setAuthorDOB(author.getAuthorDOB());
        return authorDTO;
    }

    public Author authorToDTO(AuthorDTO authorDTO){
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setAuthorName(authorDTO.getAuthorName());
        author.setAuthorDOB(authorDTO.getAuthorDOB());
        return author;
    }
}
