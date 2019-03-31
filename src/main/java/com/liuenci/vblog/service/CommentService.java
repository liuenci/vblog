package com.liuenci.vblog.service;

import com.liuenci.vblog.domain.Comment;

/**
 * Comment 服务接口.
 * 
 */
public interface CommentService {
	/**
	 * 根据id获取 Comment
	 * @param id
	 * @return
	 */
	Comment getCommentById(Long id);
	/**
	 * 删除评论
	 * @param id
	 * @return
	 */
	void removeComment(Long id);
}
