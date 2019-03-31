package com.liuenci.vblog.dao;

import com.liuenci.vblog.pojo.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Vote 仓库.
 * @author liuenci
 */
public interface VoteRepository extends JpaRepository<Vote, Long>{
 
}
