package com.study.es.repo;

import com.study.es.model.Item;
import lombok.val;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * es 原生查询方式
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NativeQueryTest {


    @Resource
    private ElasticsearchTemplate template;

    @Resource
    private ItemRepository itemRepository;

    /**
     * QueryBuilders提供了大量的静态方法，
     * 用于生成各种不同类型的查询对象，例如：词条、模糊、通配符等QueryBuilder对象。
     */
    @Test
    public void testQuery1(){

        // 词条查询
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "坚果手机R1");
        // 执行查询
        Iterable<Item> items = this.itemRepository.search(queryBuilder);
        items.forEach(System.out::println);
    }

    /**
     * =======结果过滤，只显示一个字段=====
     * GET mytest2/_search
     * {
     *   "query": {
     *     "match": {
     *       "title": "坚果"
     *     }
     *   },
     *   "_source": "title"
     * }
     *
     * ===============分页=============
     * GET mytest2/_search
     * {
     *   "query": {
     *     "match": {
     *       "title": "坚果"
     *     }
     *   },
     *   "_source": "title",
     *   "sort": [
     *     {
     *       "price": {
     *         "order": "desc"
     *       }
     *     }
     *   ]
     *   ,
     *   "from": 0,
     *   "size": 2
     * }
     *
     *
     *
     */
    @Test
    public void testQuery2(){

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //结果过滤字段
        builder.withSourceFilter(new FetchSourceFilter(new String[]{"id","title","price"},null));
        //添加查询条件
        NativeSearchQueryBuilder query = builder.withQuery(QueryBuilders.matchQuery("title", "坚果手机R1"));
        //构建查询条件----->排序
        query.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));

        Page<Item> page = itemRepository.search(query.build());
        List<Item> list = page.getContent();
        list.forEach(System.out::println);
    }

    /**
     * 原生分页实现
     */
    @Test
    public void testNativeQuery(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本的分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "坚果手机R1"));

        // 初始化分页参数
        int page = 0;
        int size = 3;
        // 设置分页参数
        queryBuilder.withPageable(PageRequest.of(page, size));

        // 执行搜索，获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        // 打印总条数
        System.out.println(items.getTotalElements());
        // 打印总页数
        System.out.println(items.getTotalPages());
        // 每页大小
        System.out.println(items.getSize());
        // 当前页
        System.out.println(items.getNumber());
        items.forEach(System.out::println);
    }

    /**
     * 原生排序
     */
    @Test
    public void testNativeSort(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本的分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "坚果手机R1"));

        // 排序
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));

        // 执行搜索，获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        // 打印总条数
        System.out.println(items.getTotalElements());
        items.forEach(System.out::println);
    }

    /**
     * es高级查询 聚合 桶 类似 mysql  group by
     *
     * GET mytest2/_search
     * {
     *   "size": 20,
     *   "aggs": {
     *     "popularBrand": {
     *       "terms": {
     *         "field": "brand",
     *         "size": 10
     *       }
     *     }
     *   }
     * }
     */
    @Test
    public void testAgg1(){

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //聚合查询
        queryBuilder.addAggregation(AggregationBuilders.terms("popularBrand").field("brand"));
        //查询并返回带聚合结果
        AggregatedPage<Item> items = template.queryForPage(queryBuilder.build(), Item.class);

        //解析聚合
        Aggregations aggregations = items.getAggregations();

        //获取指定名称的聚合
        StringTerms terms = aggregations.get("popularBrand");

        //获取桶
        List<StringTerms.Bucket> list = terms.getBuckets();

        for (StringTerms.Bucket bucket : list){

            // 3.4、获取桶中的key，即品牌名称
            System.out.println(bucket.getKeyAsString());
            // 3.5、获取桶中的文档数量
            System.out.println(bucket.getDocCount());
        }


    }

    /**
     * es高级查询 聚合 度量 类似 mysql  聚合函数
     */
    @Test
    public void testAgg2(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand")
                        .subAggregation(AggregationBuilders.avg("priceAvg").field("price")) // 在品牌聚合桶内进行嵌套聚合，求平均值
        );
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量
            System.out.println(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "台");

            // 3.6.获取子聚合结果：
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
            System.out.println("平均售价：" + avg.getValue());
        }
    }

}