package com.bg.library.Base.Objects.JSON;

import com.bg.library.Base.Objects.BaseObject;

/**
 * Created by BinGe on 2017/8/31.
 * 错误信息类
 */

public class JError extends BaseObject {


    /**
     * 默认错误代码
     */
    public final static int DEFAULT_ERROR_CODE = Integer.MIN_VALUE;

    /**
     * 错误代码，后期添加一个错误代码对应表
     */
    public int errorCode;
    /**
     * 错误提示，可用于显示错误提示能用户看
     */
    public String errorInfo;

    public JError() {
        this(JError.DEFAULT_ERROR_CODE);
    }

    public JError(String info) {
        this(DEFAULT_ERROR_CODE, info);
    }

    public JError(int errorCode) {
        this(errorCode, null);
    }

    public JError(int errorCode, String errorInfo) {
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

    @Override
    public String toString() {
        return "errorCode: " + this.errorCode + ", errorInfo: " + this.errorInfo;
    }
}
