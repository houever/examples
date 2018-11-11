package com.study.es.repo;

import com.study.es.model.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 请查阅官方文档  https://github.com/spring-projects/spring-data-elasticsearch
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemRepositoryTest {


    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    private ItemRepository itemRepository;

    /**
     * 创建索引
     */
    @Test
    public void testCreate(){
        //创建索引库
        elasticsearchTemplate.createIndex(Item.class);
        //映射索引
        elasticsearchTemplate.putMapping(Item.class);
    }

    @Test
    public void testDelete(){
        elasticsearchTemplate.deleteIndex(Item.class);
    }

    /**
     * 插入索引
     */
    @Test
    public void testInsert(){
        elasticsearchTemplate.deleteIndex(Item.class);
    }

    @Test
    public void testBatchList(){
        List<Item> list = new ArrayList<Item>();
        list.add(new Item(2L, "坚果手机R1", " 手机", "锤子", 3699.00, "http://image.leyou.com/123.jpg"));
        list.add(new Item(3L, "华为META10", " 手机", "华为", 4499.00, "http://image.leyou.com/3.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }

    @Test
    public void testQuery(){

        List<Item> list = itemRepository.findByPriceIsBetween(2000d, 4000d);
        for(Item item : list){
            System.out.println("item==="+item);
        }
    }

}