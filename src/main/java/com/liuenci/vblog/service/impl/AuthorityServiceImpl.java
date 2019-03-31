/**
 * 
 */
package com.liuenci.vblog.service.impl;

import com.liuenci.vblog.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liuenci.vblog.domain.Authority;
import com.liuenci.vblog.repository.AuthorityRepository;

/**
 * Authority 服务.
 * @author liuenci
 */
@Service
public class AuthorityServiceImpl  implements AuthorityService {
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Override
	public Authority getAuthorityById(Long id) {
		return authorityRepository.findOne(id);
	}

}
