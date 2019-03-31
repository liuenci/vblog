package com.liuenci.vblog.dao;

import com.liuenci.vblog.pojo.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * EsBlog 存储库.
 * @author liuenci
 */
public interface EsBlogDao extends ElasticsearchRepository<EsBlog, String> {
 
	/**
	 * 模糊查询(去重) 分页
	 * @param title
	 * @param Summary
	 * @param content
	 * @param tags
	 * @param pageable
	 * @return
	 */
	Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title,String Summary,String content,String tags,Pageable pageable);

	/**
	 * 通过 blogId 查找 EsBlog
	 * @param blogId
	 * @return
	 */
	EsBlog findByBlogId(Long blogId);
}
