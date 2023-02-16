package cn.asedu.dynamic_rule.engine;

import cn.asedu.dynamic_rule.functions.DeviceKeySelector;
import cn.asedu.dynamic_rule.functions.Json2BeanMapFunction;
import cn.asedu.dynamic_rule.functions.RuleProcessFunctionV4;
import cn.asedu.dynamic_rule.functions.SourceFunctions;
import cn.asedu.dynamic_rule.pojo.LogBean;
import cn.asedu.dynamic_rule.pojo.ResultBean;
import cn.asedu.dynamic_rule.functions.*;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author ~~
 * @version 1.0
 * @desc 静态规则引擎版本2主程序
 * 相对于v1.0来说，只有一处改变：  keyed.process（这里用了 RuleProcessFunctionV2）
 */
public class RuleEngineV4 {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());

        // 添加一个消费kafka中用户实时行为事件数据的source
        DataStreamSource<String> logStream = env.addSource(SourceFunctions.getKafkaEventSource());

        // 将json格式的数据，转成 logBean格式的数据
        SingleOutputStreamOperator<LogBean> beanStream = logStream.map(new Json2BeanMapFunction());

        // 对数据按用户deviceid分key
        // TODO 后续可以升级改造成 动态keyBy
        KeyedStream<LogBean, String> keyed = beanStream.keyBy(new DeviceKeySelector());

        // 开始核心计算处理
        SingleOutputStreamOperator<ResultBean> resultStream = keyed.process(new RuleProcessFunctionV4());

        // 打印
        resultStream.print();

        env.execute();
    }
}
