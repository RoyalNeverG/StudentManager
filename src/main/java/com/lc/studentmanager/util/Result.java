package com.lc.studentmanager.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @BelongsProject: wechart_lc
 * @BelongsPackage: com.test.wechart.exceptionHandler
 * @Author: 42119
 * @CreateTime: 2019-11-05 23:35
 * @Description: 返回结果信息
 */
@Getter
@Setter
@ApiModel(description = "返回的结果模型")
public class Result {

//    返回码
    @ApiModelProperty("返回的状态码")
    private Integer code;

//    返回消息
    @ApiModelProperty("执行的消息")
    private String message;

//    是否成功
    @ApiModelProperty("是否执行成功")
    private boolean isSuccess;

//    返回的数据
    @ApiModelProperty("执行返回的结果")
    private Object data;

    public Result() {
    }

    /**
     * 无数据的返回消息
     * @param code
     * @param message
     * @param isSuccess
     */
    public Result(Integer code, String message, boolean isSuccess) {
        this.code = code;
        this.message = message;
        this.isSuccess = isSuccess;
    }

    /**
     * 有数据的返回消息
     * @param code
     * @param message
     * @param isSuccess
     * @param data
     */
    public Result(Integer code, String message, boolean isSuccess, Object data) {
        this.code = code;
        this.message = message;
        this.isSuccess = isSuccess;
        this.data = data;
    }
}
