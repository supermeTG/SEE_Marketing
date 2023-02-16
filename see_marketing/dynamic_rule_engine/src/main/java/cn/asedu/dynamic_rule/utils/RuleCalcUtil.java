package cn.asedu.dynamic_rule.utils;

import cn.asedu.dynamic_rule.pojo.LogBean;
import cn.asedu.dynamic_rule.pojo.RuleAtomicParam;
import org.apache.commons.lang3.time.DateUtils;

import java.util.*;

/**
 * @author ~~
 * @version 1.0
 * @desc 规则计算中可能用到的各类工具方法
 */
public class RuleCalcUtil {
    /**
     * 工具方法，用于判断一个待判断事件和一个规则中的原子条件是否一致
     * @param eventBean
     * @param eventParam
     * @return
     */
    public static boolean eventBeanMatchEventParam(LogBean eventBean, RuleAtomicParam eventParam){
        // 如果传入的一个事件的事件id与参数中的事件id相同，才开始进行属性判断
        if(eventBean.getEventId().equals(eventParam.getEventId())){

            // 取出待判断事件中的属性
            Map<String, String> eventProperties = eventBean.getProperties();

            // 取出条件中的事件属性
            HashMap<String, String> paramProperties = eventParam.getProperties();
            Set<Map.Entry<String, String>> entries = paramProperties.entrySet();
            // 遍历条件中的每个属性及值
            for (Map.Entry<String, String> entry : entries) {
                if(!entry.getValue().equals(eventProperties.get(entry.getKey()))){
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public static boolean eventBeanMatchEventParam(LogBean eventBean, RuleAtomicParam eventParam,boolean neeTimeCompare){
        boolean b = eventBeanMatchEventParam(eventBean, eventParam);

        // 要考虑一点，外部传入的条件中，时间范围条件，如果起始、结束没有约束，应该传入一个 -1
        long start = eventParam.getRangeStart();
        long end = eventParam.getRangeEnd();

        return b && eventBean.getTimeStamp()>= (start==-1?0:start) && eventBean.getTimeStamp()<= (end==-1?Long.MAX_VALUE:end);

    }


    public static String getBufferKey(String deviceId,RuleAtomicParam atomicParam){
        // deviceId-EVENT-p1-v1-p2-v2
        StringBuffer sb = new StringBuffer();
        sb.append(deviceId).append("-").append(atomicParam.getEventId());

        HashMap<String, String> properties = atomicParam.getProperties();
        Set<Map.Entry<String, String>> entries = properties.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            sb.append("-").append(entry.getKey()).append("-").append(entry.getValue());
        }

        return sb.toString();
    }

    public static String getBufferKey(String deviceId, List<RuleAtomicParam> paramList){
        // deviceId-EVENT1-p1-v1-p2-v2-EVENT2-p1-v1-p2-v2
        StringBuffer sb = new StringBuffer();
        sb.append(deviceId);

        for (RuleAtomicParam ruleAtomicParam : paramList) {
            sb.append("-").append(ruleAtomicParam.getEventId());
            HashMap<String, String> properties = ruleAtomicParam.getProperties();
            Set<Map.Entry<String, String>> entries = properties.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                sb.append("-").append(entry.getKey()).append("-").append(entry.getValue());
            }
        }

        return sb.toString();
    }


    public static long getQuerySplit(){
        return DateUtils.addHours(DateUtils.ceiling(new Date(), Calendar.HOUR), -2).getTime();
    }


}
