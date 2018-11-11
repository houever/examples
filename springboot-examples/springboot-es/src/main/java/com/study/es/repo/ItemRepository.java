package com.study.es.repo;

import com.study.es.model.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ItemRepository extends ElasticsearchRepository<Item,Long> {

    List<Item> findByPriceIsBetween(double begin,double end);

}
