package com.liuenci.vblog.service;

import com.liuenci.vblog.pojo.Authority;

/**
 * Authority 服务接口.
 * @author liuenci
 */
public interface AuthorityService {
	 
	
	/**
	 * 根据id获取 Authority
	 * @return
	 */
	Authority getAuthorityById(Long id);
}
