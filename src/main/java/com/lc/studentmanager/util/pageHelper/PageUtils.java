package com.lc.studentmanager.util.pageHelper;

import com.github.pagehelper.PageInfo;

/**
 * @BelongsProject: mybatisMultipleDataSource
 * @BelongsPackage: com.mybatis.springbootlcmybatis.utils.pageHelper
 * @Author: lc
 * @CreateTime: 2019-12-01 21:56
 * @Description:
 */
public class PageUtils {

    public static <T> PageResult getPageResult(PageInfo<T> pageInfo) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        /**
         * 获取当前页数的记录数
         */
        pageResult.setContent(pageInfo.getList());
        return pageResult;
    }

}
