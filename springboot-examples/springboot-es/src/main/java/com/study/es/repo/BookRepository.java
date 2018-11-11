package com.study.es.repo;

import com.study.es.model.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookRepository extends ElasticsearchRepository<Book,Integer> {
}
