package cn.asedu.dynamic_rule.utils;

import cn.asedu.dynamic_rule.pojo.RuleStateBean;
import cn.asedu.dynamic_rule.pojo.LogBean;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.MapStateDescriptor;

public class StateDescUtil {

    /**
     * 存放drools规则容器session的state定义器
     */
    public static final MapStateDescriptor<String, RuleStateBean> ruleKieStateDesc = new MapStateDescriptor<String, RuleStateBean>("ruleKieState",String.class,RuleStateBean.class);

    public static final ListStateDescriptor<LogBean> eventStateDesc = new ListStateDescriptor<>("eventState", LogBean.class);


}
