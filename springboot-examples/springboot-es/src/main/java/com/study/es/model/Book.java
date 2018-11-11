package com.study.es.model;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "atguigu",type = "book")
public class Book {
    private Integer id;
    private String bookname;
    private String author;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Book(Integer id, String bookname, String author) {
        this.id = id;
        this.bookname = bookname;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookname='" + bookname + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
