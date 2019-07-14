package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.blog.dto.RecentPostDTO;

import java.util.List;

public interface RecentPostService {

    List<RecentPostDTO> getRecentPost();
}
