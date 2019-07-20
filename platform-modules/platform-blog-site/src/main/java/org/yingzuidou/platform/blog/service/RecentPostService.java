package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.blog.dto.OperRecordDTO;

import java.util.List;

public interface RecentPostService {

    List<OperRecordDTO> getRecentPost();
}
