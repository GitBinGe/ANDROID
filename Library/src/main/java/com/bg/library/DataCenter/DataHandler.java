package com.bg.library.DataCenter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BinGe on 2017/9/8.
 */

public abstract class DataHandler {

    /**
     * 操作对应的处理器的集合
     */
    private Map<String, IPerformer> operationHandler = new HashMap<>();

    /**
     * 原始数据对应的处理器集合
     */
    private Map<String, IParser> operationParser = new HashMap<>();

    /**
     * 每个handler都有一个data供外面调用
     * @return
     */
    public abstract Data getData();

    /**
     * 判断此Handler是否绑定了operation这个操作
     * @param operation
     * @return
     */
    public boolean isContainsOperation(String operation) {
        return operationHandler.containsKey(operation);
    }

    /**
     * 获取该操作的处理器
     * @param operation
     * @return
     */
    public IPerformer getOperationPerformer(String operation) {
        return operationHandler.get(operation);
    }

    /**
     * 子类通过这个方法绑定操作与相应的处理器
     * @param operation
     * @param handler
     * @return
     */
    public boolean bindOperation(String operation, IPerformer handler) {
        if (operation != null && handler != null) {
            operationHandler.put(operation, handler);
            return true;
        }
        return false;
    }


    /**
     * 判断此Handler是否绑定了operation这个数据解析器
     * @param operation
     * @return
     */
    public boolean isContainsParser(String operation) {
        return operationParser.containsKey(operation);
    }

    /**
     * 此方法是DataCenter在执行一个operation后会拿到一份原始数据，
     * 通过bind相应事件，来绑定原始数据的解析对对象
     * @param operation
     * @return
     */
    public boolean bindDataParser(String operation, IParser parser) {
        if (operation != null && parser != null) {
            operationParser.put(operation, parser);
            return true;
        }
        return false;
    }

    /**
     * 此方法是DataCenter在执行一个operation后会拿到一份原始数据，
     * 通过bind相应事件，调用相应的解析器去解析数据，并返回解析后的数据
     * @param operation
     * @param data
     */
    public final Data parseData(String operation, Data data) {
        IParser parser = operationParser.get(operation);
        data = parser.parse(operation, data);
        return data;
    }

}
