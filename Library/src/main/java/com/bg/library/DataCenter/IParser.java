package com.bg.library.DataCenter;

/**
 * Created by BinGe on 2017/9/14.
 * 数据解析器接口，用于DataHandler中如果需要具备数据处理能需要实现这个接口
 */

public interface IParser {

    /**
     * 数据中心在处理完一个操作后会有一份原始数据，数据会由这个方法进行解析
     * @param operation 操作的名称
     * @param data  需求解析的数据
     * @return  返回解析后的数据
     */
    Data parse(String operation, Data data);

}
