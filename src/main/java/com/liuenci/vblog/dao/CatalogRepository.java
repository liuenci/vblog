package com.liuenci.vblog.dao;

import java.util.List;

import com.liuenci.vblog.pojo.Catalog;
import com.liuenci.vblog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Catalog 仓库.
 * @author liuenci
 */
public interface CatalogRepository extends JpaRepository<Catalog, Long>{
	
	/**
	 * 根据用户查询
	 * @param user
	 * @return
	 */
	List<Catalog> findByUser(User user);
	
	/**
	 * 根据用户查询
	 * @param user
	 * @param name
	 * @return
	 */
	List<Catalog> findByUserAndName(User user,String name);
}
