package com.liuenci.vblog.dao;

import com.liuenci.vblog.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Comment 仓库.
 * @author liuenci
 */
public interface CommentRepository extends JpaRepository<Comment, Long>{
 
}
