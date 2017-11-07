package com.bg.library.DataCenter;

import com.bg.library.Base.Objects.JSON.JError;
import com.bg.library.Base.Objects.JSON.JSON;

/**
 * Created by BinGe on 2017/8/31.
 * 此类为数据中心所有数据的基本类型
 */

public abstract class Data extends JSON {

    /**
     * 错误信息
     */
    public JError error;

    /**
     * 判断数据是否正常的方法
     *
     * @return
     */
    public final boolean isDataNormal() {
        return error == null && !isJSONEmpty();
    }

    /**
     * json数据刷新的时候会调用这个方法
     */
    protected abstract void onData();

}
