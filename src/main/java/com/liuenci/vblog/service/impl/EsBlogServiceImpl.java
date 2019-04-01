package com.liuenci.vblog.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.liuenci.vblog.service.EsBlogService;
import com.liuenci.vblog.service.UserService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.search.SearchParseException;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.liuenci.vblog.pojo.User;
import com.liuenci.vblog.pojo.EsBlog;
import com.liuenci.vblog.dao.EsBlogDao;
import com.liuenci.vblog.vo.TagVO;

/**
 * EsBlog 服务.
 *
 * @author liuenci
 */
@Service
public class EsBlogServiceImpl implements EsBlogService {
	@Autowired
	private EsBlogDao esBlogDao;

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Autowired
	private UserService userService;
	
	private static final Pageable TOP_5_PAGEABLE = new PageRequest(0, 5);

	private static final String EMPTY_KEYWORD = "";


	@Override
	public void removeEsBlog(String id) {
		esBlogDao.delete(id);
	}


	@Override
	public EsBlog updateEsBlog(EsBlog esBlog) {
		return esBlogDao.save(esBlog);
	}


	@Override
	public EsBlog getEsBlogByBlogId(Long blogId) {
		return esBlogDao.findByBlogId(blogId);
	}


	@Override
	public Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable) throws SearchParseException {
		Sort sort = new Sort(Direction.DESC,"createTime");
		if (pageable.getSort() == null) {
			pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}
		return esBlogDao.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword,keyword,keyword,keyword, pageable);
	}

	private final static String HOT = "hot";
	private final static String NEW = "new";
	@Override
	public Page<EsBlog> listEsBlogsByType(String keyword, String type, Pageable pageable) {
		Sort sort = null;
		if (HOT.equals(type)) {
			sort = new Sort(Direction.DESC, "readSize","commentSize","voteSize","createTime");
		} else if (NEW.equals(type)){
			sort = new Sort(Direction.DESC,"createTime");
		}
		pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
		return esBlogDao.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword,keyword,keyword,keyword,pageable);
	}


	@Override
	public Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable) throws SearchParseException{
 
		Sort sort = new Sort(Direction.DESC,"readSize","commentSize","voteSize","createTime"); 
		if (pageable.getSort() == null) {
			pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}
 
		return esBlogDao.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword, keyword, keyword, keyword, pageable);
	}

	@Override
	public Page<EsBlog> listEsBlogs(Pageable pageable) {
		return esBlogDao.findAll(pageable);
	}
 
 
	/**
	 * 最新前5
	 * @return
	 */
	@Override
	public List<EsBlog> listTop5NewestEsBlogs() {
		Page<EsBlog> page = this.listHotestEsBlogs(EMPTY_KEYWORD, TOP_5_PAGEABLE);
		return page.getContent();
	}
	
	/**
	 * 最热前5
	 * @return
	 */
	@Override
	public List<EsBlog> listTop5HotestEsBlogs() {
		Page<EsBlog> page = this.listHotestEsBlogs(EMPTY_KEYWORD, TOP_5_PAGEABLE);
		return page.getContent();
	}

	@Override
	public List<TagVO> listTop30Tags() {
 
		List<TagVO> list = new ArrayList<>();
		// 构造查询条件
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				// 设置查询所有
				.withQuery(matchAllQuery())
				// 制定索引的类型，只先从各分片中查询匹配的文档
				.withSearchType(SearchType.QUERY_THEN_FETCH)
				// 制定查询的索引库的名称 -> indexName 和类型 -> type
				.withIndices("blog").withTypes("blog")
				// 聚合查询 计算 tag 标签出现的次数，降序排列 取前三十个
				.addAggregation(terms("tags").field("tags").order(Terms.Order.count(false)).size(30))
				.build();
		Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
			@Override
			public Aggregations extract(SearchResponse response) {
				return response.getAggregations();
			}
		});
		// 转化为 map 集合再取值
		StringTerms modelTerms =  (StringTerms)aggregations.asMap().get("tags");
		// 获取所有的 bucket
        Iterator<Bucket> modelBucketIt = modelTerms.getBuckets().iterator();
        while (modelBucketIt.hasNext()) {
            Bucket actiontypeBucket = modelBucketIt.next();
 
            list.add(new TagVO(actiontypeBucket.getKey().toString(),
                    actiontypeBucket.getDocCount()));
        }
		return list;
	}
	
	@Override
	public List<User> listTop12Users() {
 
		List<String> usernamelist = new ArrayList<>();
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(matchAllQuery())
				.withSearchType(SearchType.QUERY_THEN_FETCH)
				.withIndices("blog").withTypes("blog")
				.addAggregation(terms("users").field("username").order(Terms.Order.count(false)).size(12))
				.build();
		Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
			@Override
			public Aggregations extract(SearchResponse response) {
				return response.getAggregations();
			}
		});
		
		StringTerms modelTerms =  (StringTerms)aggregations.asMap().get("users");

		Iterator<Bucket> modelBucketIt = modelTerms.getBuckets().iterator();
        while (modelBucketIt.hasNext()) {
            Bucket actiontypeBucket = modelBucketIt.next();
            String username = actiontypeBucket.getKey().toString();
            usernamelist.add(username);
        }
        List<User> list = userService.listUsersByUsernames(usernamelist);
        
		return list;
	}
}
