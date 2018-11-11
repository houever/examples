package com.study.es.repo;

import com.study.es.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRepositoryTest {
    @Resource
    private BookRepository bookRepository;

    @Test
    public void test1(){
        Book book = new Book(1,"三国演义","罗贯中");
        bookRepository.index(book);
    }
}