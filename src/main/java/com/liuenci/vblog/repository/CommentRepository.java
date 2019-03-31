package com.liuenci.vblog.repository;

import com.liuenci.vblog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Comment 仓库.
 * @author liuenci
 */
public interface CommentRepository extends JpaRepository<Comment, Long>{
 
}
