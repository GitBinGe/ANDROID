package com.bg.library.DataCenter;

import com.bg.library.Base.Objects.JSON.*;

/**
 * Created by BinGe on 2017/8/31.
 * 此类为数据中心所有数据的基本类型
 */

public class Data extends JSON {


    private JError error;

    /**
     * 判断数据是否正常的方法
     *
     * @return
     */
    public boolean isDataNormal() {
        return error == null && !isJSONEmpty();
    }

    /**
     * 设备错误信息
     * @param error
     */
    public void setError(JError error) {
        this.error = error;
    }

    /**
     * 获取错误信息
     * @return
     */
    public JError getError() {
        return error;
    }
}
