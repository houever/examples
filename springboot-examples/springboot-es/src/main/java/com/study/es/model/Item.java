package com.study.es.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * indexName --> 数据库名字
 * type --> 表名
 */
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "mytest2",type = "item",shards = 1,replicas = 5)
public class Item {
    @Field(type = FieldType.Long)
    @Id
    Long id;

    @Field(type = FieldType.Text,analyzer = "ik_smart")
    String title; //标题

    @Field(type = FieldType.Keyword)
    String category;// 分类

    @Field(type = FieldType.Keyword)
    String brand; // 品牌

    @Field(type = FieldType.Double)
    Double price; // 价格

    @Field(type = FieldType.Keyword,index = false)
    String images; // 图片地址

}
