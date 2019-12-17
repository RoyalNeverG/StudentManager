package com.lc.studentmanager.util;

/**
 * @BelongsProject: wechart_lc
 * @BelongsPackage: com.test.wechart.exceptionHandler
 * @Author: 42119
 * @CreateTime: 2019-11-05 23:32
 * @Description: 各种异常代码
 */
public class StatusCode {
    public static final int OK=20000;//成功
    public static final int ERROR =20001;//失败
    public static final int LOGINERROR =20002;//用户名或密码错误
    public static final int ACCESSERROR =20003;//权限不足
    public static final int REMOTEERROR =20004;//远程调用失败
    public static final int REPERROR =20005;//重复操作
}
