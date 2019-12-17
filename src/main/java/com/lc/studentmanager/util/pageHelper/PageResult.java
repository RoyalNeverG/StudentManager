package com.lc.studentmanager.util.pageHelper;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @BelongsProject: mybatisMultipleDataSource
 * @BelongsPackage: com.mybatis.springbootlcmybatis.utils.pageHelper
 * @Author: lc
 * @CreateTime: 2019-12-01 21:41
 * @Description:
 */
@Setter
@Getter
public class PageResult {

    /**
     * 页码
     */
    private int pageNum;

    /**
     * 每页的大小
     */
    private int pageSize;

    /**
     * 总记录数
     */
    private long totalSize;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 记录模型
     */
    private List<?> content;

    @Override
    public String toString() {
        return "PageResult{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalSize=" + totalSize +
                ", totalPages=" + totalPages +
                ", content=" + content +
                '}';
    }
}
