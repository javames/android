package com.hong.cookbook.http;

/**
 * Created by Administrator on 2017/10/27.
 */

public class CommonApi {
//    private static final String BASE_URL="http://app.duomiwan.com/";

    //测试图片地址
    public static final String PIC="http://musicdata.baidu.com/data2/pic/115430787/115430787.jpg";

    public static final String BASE_URL="http://192.168.1.47:8080/";


    //操作失败
    public static final String errorCode="APP_000";
    //操作成功
    public static final String succedCode="APP_001";
    //用户已存在
    public static final String userHasRegister="APP_032";
    //用户不存在
    public static final String userNotExsit="APP_101";
    //token超出有效时间
    public static final String outUsedTime="APP_102";

    //注册成功
    public static final String register_success="APP_103";
    //注册失败
    public static final String register_fail="APP_104";


    //验证成功
    public static final String identify_succed="200";
    //验证码错误
    public static final String identify_code_error="468";
    //验证码验证次数过多，已经失效
    public static final String identify_code_unused="467";

    //
    public static final String refreshTokenEntity="refreshTokenEntity";
}
