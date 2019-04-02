package com.liuenci.vblog.dao;

import com.liuenci.vblog.pojo.Blog;
import com.liuenci.vblog.pojo.Catalog;
import com.liuenci.vblog.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Blog 仓库.
 * @author liuenci
 */
public interface BlogRepository extends JpaRepository<Blog, Long>{
	/**
	 * 根据用户名分页查询用户列表（最新）
	 * 由 findByUserAndTitleLikeOrTagsLikeOrderByCreateTimeDesc 代替，可以根据标签进行查询
	 * @param user
	 * @param title
	 * @param pageable
	 * @return
	 */
	@Deprecated
	Page<Blog> findByUserAndTitleLikeOrderByCreateTimeDesc(User user, String title, Pageable pageable);
	
	/**
	 * 根据用户名分页查询用户列表
	 * @param user
	 * @param title
	 * @param pageable
	 * @return
	 */
	Page<Blog> findByUserAndTitleLike(User user, String title, Pageable pageable);
	
	/**
	 * 根据用户名分页查询用户列表
	 * @param user
	 * @param title
	 * @param pageable
	 * @return
	 */
	Page<Blog> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(String title,User user,String tags,User user2,Pageable pageable);
	/**
	 * 根据用户名分页查询用户列表
	 * @param pageable
	 * @return
	 */
	Page<Blog> findByCatalog(Catalog catalog, Pageable pageable);
}
