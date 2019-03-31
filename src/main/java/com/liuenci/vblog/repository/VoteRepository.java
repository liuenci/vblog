package com.liuenci.vblog.repository;

import com.liuenci.vblog.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Vote 仓库.
 * @author liuenci
 */
public interface VoteRepository extends JpaRepository<Vote, Long>{
 
}
