package com.gizwits.lease.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by zhl on 2017/7/19.
 */
public class DeviceUtil {

    /**
     * 将产品状态指令与上报中的指令对比,完全相同返回True
     * @param commandJson
     * @param jsonData
     * @return
     */
    public static boolean equalCommandAndRealTimeData(JSONObject commandJson, JSONObject jsonData){
        boolean resultFlag = true;
        Iterator commandIterator =  commandJson.keySet().iterator();
        while (commandIterator.hasNext()){
            String key = (String) commandIterator.next();
            Object value = commandJson.get(key);
            if(!jsonData.containsKey(key)){
                resultFlag = false;
                break;
            }else if(!equalsCommand(value,jsonData.get(key))){
                resultFlag = false;
                break;
            }
        }
        return resultFlag;
    }

    /**
     * 状态指令配置值与设备上报值的对比
     * @param configObject 状态指令配置值,可能是JSON,也可能是value值(向前兼容)
     * @param value 设备上报值
     * @return
     */
    private static boolean equalsCommand(Object configObject,Object value){
        if(configObject instanceof Map){
            JSONObject commandJson = (JSONObject)configObject;
            return equalsWithOperators(commandJson.getString("operator"),commandJson.get("value"),value);
        }else{
            return value.equals(configObject);
        }
    }

    /**
     * 根据操作符计算上报相关数据点
     * @param operator 操作符
     * @param operatorValue 相关配置值
     * @param realValue 设备上报值
     * @return
     */
    private static boolean equalsWithOperators(String operator, Object operatorValue, Object realValue){
        if(StringUtils.isEmpty(operator)){
            return realValue.equals(operatorValue);
        }
        boolean resultFlag = false;
        if(operatorValue instanceof Number && realValue instanceof Number) {
            operatorValue = ((Number) operatorValue).doubleValue();
            realValue = ((Number) realValue).doubleValue();
            switch (operator) {
                case "=":
                    if (((Double) realValue).compareTo((Double) operatorValue) == 0) {
                        resultFlag = true;
                    }
                    break;
                case ">":
                    if (((Double) realValue).compareTo((Double) operatorValue) > 0) {
                        resultFlag = true;
                    }
                    break;
                case "<":
                    if (((Double) realValue).compareTo((Double) operatorValue) < 0) {
                        resultFlag = true;
                    }
                    break;
                case "<=":
                    if (((Double) realValue).compareTo((Double) operatorValue) < 0
                            || ((Double) realValue).compareTo((Double) operatorValue) == 0) {
                        resultFlag = true;
                    }
                    break;
                case ">=":
                    if (((Double) realValue).compareTo((Double) operatorValue) > 0
                            || ((Double) realValue).compareTo((Double) operatorValue) == 0) {
                        resultFlag = true;
                    }
                    break;
                case "!=":
                    if (((Double) realValue).compareTo((Double) operatorValue) != 0) {
                        resultFlag = true;
                    }
                    break;
                default:
                    break;
            }
        } else {
            // 如果一个为Boolean一个为Number，则将Number转为Boolean再进行比较
            if (realValue instanceof Boolean && operatorValue instanceof Number) {
                operatorValue = ((Number) operatorValue).doubleValue() > 0;
            }else if (operatorValue instanceof Boolean && realValue instanceof Number) {
                realValue = ((Number) realValue).doubleValue() > 0;
            }
            if ("=".equals(operator)) {
                return realValue.equals(operatorValue);
            } else {
                return !realValue.equals(operatorValue);
            }
        }

        return resultFlag;
    }


}
