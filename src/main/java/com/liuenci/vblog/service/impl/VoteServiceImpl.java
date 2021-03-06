package com.liuenci.vblog.service.impl;

import javax.transaction.Transactional;

import com.liuenci.vblog.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liuenci.vblog.pojo.Vote;
import com.liuenci.vblog.dao.VoteRepository;

/**
 * Vote 服务.
 *
 * @author liuenci
 */
@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;


    @Override
    @Transactional
    public void removeVote(Long id) {
        voteRepository.delete(id);
    }

    @Override
    public Vote getVoteById(Long id) {
        return voteRepository.findOne(id);
    }

}
