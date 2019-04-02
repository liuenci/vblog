package com.liuenci.vblog.service;

import java.util.List;

import com.liuenci.vblog.pojo.Catalog;
import com.liuenci.vblog.pojo.User;

/**
 * Catalog 服务接口.
 *
 * @author liuenci
 */
public interface CatalogService {
	/**
	 * 保存Catalog
	 * @param catalog
	 * @return
	 */
	Catalog saveCatalog(Catalog catalog);
	
	/**
	 * 删除Catalog
	 * @param id
	 * @return
	 */
	void removeCatalog(Long id);

	/**
	 * 根据id获取Catalog
	 * @param id
	 * @return
	 */
	Catalog getCatalogById(Long id);
	
	/**
	 * 获取Catalog列表
	 * @return
	 */
	List<Catalog> listCatalogs(User user);
}
