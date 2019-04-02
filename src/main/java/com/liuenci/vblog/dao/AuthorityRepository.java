package com.liuenci.vblog.dao;

import com.liuenci.vblog.pojo.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Authority 仓库.
 * @author liuenci
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long>{
}
