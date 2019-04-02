package com.liuenci.vblog.service.impl;

import javax.transaction.Transactional;

import com.liuenci.vblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liuenci.vblog.pojo.Comment;
import com.liuenci.vblog.dao.CommentRepository;

/**
 * Comment 服务.
 */
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	/* (non-Javadoc)
	 * @see CommentService#removeComment(java.lang.Long)
	 */
	@Override
	@Transactional
	public void removeComment(Long id) {
		commentRepository.delete(id);
	}
	@Override
	public Comment getCommentById(Long id) {
		return commentRepository.findOne(id);
	}

}
