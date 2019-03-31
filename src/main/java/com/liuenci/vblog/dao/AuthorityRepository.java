package com.liuenci.vblog.dao;

import com.liuenci.vblog.pojo.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Authority 仓库.
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long>{
}
