package cn.asedu.dynamic_rule.functions;

import cn.asedu.dynamic_rule.pojo.LogBean;
import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.functions.MapFunction;

public class Json2BeanMapFunction implements MapFunction<String, LogBean> {
    @Override
    public LogBean map(String value) throws Exception {
        return JSON.parseObject(value,LogBean.class);
    }
}
