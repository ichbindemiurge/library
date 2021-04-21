package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.mapping.MappingDTOtoClass;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private MappingDTOtoClass mappingDTOtoClaas = new MappingDTOtoClass();



    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public void addBook(BookDTO bookDTO) {

        if(bookDTO.getBookName().isEmpty()){
            throw new IllegalStateException("EMPTY NAME");
        } else {

            bookRepository.save(mappingDTOtoClaas.mapToDTOBook(bookDTO));
        }
        System.out.println(bookDTO);
    }

    @Transactional
    public void deleteBook(int bookID) {
        BookDTO bookDTO;
        if(!bookRepository.existsById(bookID)) {
            throw new IllegalStateException("NO SUCH BOOK");
        } else {
            bookDTO = mappingDTOtoClaas.mapToBookDTO(bookRepository.findById(bookID).get());
            if(!bookDTO.getAuthorsIDsDTOList().isEmpty()) {

            }

            bookRepository.deleteById(bookID);
        }
    }

    @Transactional
    public void searchBook(int bookID) {
        System.out.println("BLBLLDSALDASDASDLADLA");
            bookRepository.findById(bookID);
    }

    @Transactional
    public void updateBookFields(Book book) {


            book.setBookName(book.getBookName());

//        Book book = bookRepository.findById(bookID).orElseThrow(()-> new IllegalStateException("something went terribly wrong"));
//
//        //bookName.isBlank() && bookAuthor
//        // instead of params -> Book. do a validation for object
//
//        if (bookName != null && bookName.length() > 0 && !Objects.equals(book.getBookName(), bookName)) {
//            book.setBookName(bookName);
//        }
//
//        if (bookAuthor != null && bookAuthor.size() > 0 && !Objects.equals(book.getBooksAuthor(), bookAuthor)) {
//            book.setBooksAuthor(bookAuthor);
//        }
    }

    public void addAuthor(Author author) {
        Optional<Author> authorOptional = authorRepository.findAuthorByAuthorName(author.getAuthorName());

        if(authorOptional.isPresent()){
            throw new IllegalStateException("ALREADY EXISTS");
        } else {
            authorRepository.save(author);
        }
        System.out.println(author);
    }

//    public List<Book> getBooks() {
//        return List.of(
//                new Book(
//                        0,
//                        "TestBookName",
//                        "This book is about something",
//                        Collections.singletonList("Author 1" + "Author 2" + "Author 3")
//                )
//        );
//    }
}
