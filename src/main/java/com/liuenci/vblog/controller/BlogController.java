package com.liuenci.vblog.controller;

import java.util.List;

import com.liuenci.vblog.pojo.EsBlog;
import com.liuenci.vblog.pojo.User;
import com.liuenci.vblog.service.EsBlogService;
import com.liuenci.vblog.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 主页控制器
 */
@Controller
@RequestMapping("blogs")
public class BlogController {

    @Autowired
    private EsBlogService esBlogService;

    private static final String HOT = "hot";

    /**
     * 博客列表
     *
     * @param order     排序规则 默认为 new
     * @param keyword   关键字查询 默认为空
     * @param async     是否为异步查询方式 默认为 false
     * @param pageIndex 页码
     * @param pageSize  页大小
     * @param model     模型数据
     * @return
     */
    @GetMapping
    public String listEsBlogs(
            @RequestParam(value = "order", required = false, defaultValue = "new") String order,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "async", required = false) boolean async,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            Model model) {
        Page<EsBlog> page = null;
        List<EsBlog> list = null;
        // 系统初始化时，没有博客数据
        boolean isEmpty = true;
        try {
            Pageable pageable = new PageRequest(pageIndex, pageSize);
            page = esBlogService.listEsBlogsByType(keyword, order, pageable);
            isEmpty = false;
        } catch (Exception e) {
            Pageable pageable = new PageRequest(pageIndex, pageSize);
            page = esBlogService.listEsBlogs(pageable);
        }
        // 当前所在页面数据列表
        list = page.getContent();
        model.addAttribute("order", order);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("blogList", list);

        // 首次访问页面才加载 同步并且有博客数据的时候加载以下数据
        if (!async && !isEmpty) {
            // 最新的五条博客数据
            List<EsBlog> newest = esBlogService.listTop5NewestEsBlogs();
            model.addAttribute("newest", newest);
            // 最热的五条博客数据
            List<EsBlog> hotest = esBlogService.listTop5HotestEsBlogs();
            model.addAttribute("hotest", hotest);
            // 最多的 30 个标签
            List<TagVO> tags = esBlogService.listTop30Tags();
            model.addAttribute("tags", tags);
            // 最活跃的 12 个博主
            List<User> users = esBlogService.listTop12Users();
            model.addAttribute("users", users);
        }

        return (async == true ? "/index :: #mainContainerRepleace" : "/index");
    }

    @GetMapping("newest")
    public String listNewestEsBlogs(Model model) {
        List<EsBlog> newest = esBlogService.listTop5NewestEsBlogs();
        model.addAttribute("newest", newest);
        return "newest";
    }

    @GetMapping("hotest")
    public String listHotestEsBlogs(Model model) {
        List<EsBlog> hotest = esBlogService.listTop5HotestEsBlogs();
        model.addAttribute("hotest", hotest);
        return "hotest";
    }


}
