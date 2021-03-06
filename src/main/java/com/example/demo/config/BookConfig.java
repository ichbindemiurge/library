package com.example.demo.config;


import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Configuration
public class BookConfig {

    @Bean
    CommandLineRunner commandLineRunner (BookRepository repository) {
        return args -> {


            Author authorNameT = new Author(
                    "testNameAuthor1",
                    LocalDate.of(1978, Month.AUGUST, 15)
            );
            Author authorNameY = new Author(
                    "testNameAuthor2",
                    LocalDate.of(1960, Month.JULY, 25)
            );
            Author authorNameP = new Author(
                    "testNameAuthor3",
                    LocalDate.of(1956, Month.JANUARY, 8)
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

//            System.out.println(testOne.getAuthor());
//            System.out.println(testTwo.toString());


            repository.saveAll(
                    List.of(testOne, testTwo)
            );
        };
    }
}
