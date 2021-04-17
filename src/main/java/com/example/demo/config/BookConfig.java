package com.example.demo.config;


import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;

import javax.management.modelmbean.ModelMBean;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Configuration
public class BookConfig {

    @Bean
    CommandLineRunner commandLineRunner (BookRepository bookRepository, AuthorRepository authorRepository) {
        return args -> {


            Author authorNameT = new Author(
                    "testNameAuthor1",
                    //DateFormat.getDateInstance().parse("1976-04-03")
                    //new Date("1976-04-03")
                    //DateFormat.getDateTimeInstance().parse("1976-04-03")
                    LocalDate.of(1976, 2, 16)
            );
            Author authorNameY = new Author(
                    "testNameAuthor2",
                   // DateFormat.getDateInstance().parse("1960-06-25")
                    //DateFormat.getDateTimeInstance().parse("1976-04-03")
                    LocalDate.of(1976, 4, 3)
            );
            Author authorNameP = new Author(
                    "testNameAuthor3",
                   // DateFormat.getDateInstance().parse("1956-01-8")
                    //DateFormat.getDateTimeInstance().parse("1976-04-03")
                    LocalDate.of(1956, 1, 8)
            );


            List<Author> authorListOne = new ArrayList<>();
            authorListOne.add(authorNameT);
            authorListOne.add(authorNameY);
            authorListOne.add(authorNameP);

            List<Author> authorListTwo = new ArrayList<>();
            authorListTwo.add(authorNameP);



            Book testOne = new Book(
                        "TestBookName",
                        "This book is about something",
                         authorListOne
                );
            Book testTwo = new Book(
                    "BLABLA",
                    "This book is about BLAAAA",
                     authorListTwo
            );


            for (Author author : authorListOne) {
                author.addBook(testOne);
            }

            for (Author author : authorListTwo) {
                author.addBook(testTwo);
            }

            authorRepository.saveAll(authorListOne);
            authorRepository.saveAll(authorListTwo);

            bookRepository.saveAll(
                    List.of(testOne, testTwo)
            );
        };
    }
}
