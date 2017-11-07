package com.bg.library.DataCenter;

import com.bg.library.Base.Objects.JSON.JSON;

/**
 * Created by BinGe on 2017/8/31.
 * 此类为数据中心所有数据的基本类型
 */

public abstract class Data extends JSON {
    /**
     * 判断数据是否正常的方法
     *
     * @return
     */
    public abstract boolean isDataNormal();
}
