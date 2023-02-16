package cn.asedu.dynamic_rule.functions;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

public class SourceFunctions {

    public static FlinkKafkaConsumer<String> getKafkaEventSource(){

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "node01:9092,node02:9092,node03:9092");
        props.setProperty("auto.offset.reset", "latest");
        FlinkKafkaConsumer<String> source = new FlinkKafkaConsumer<>("zenniu_applog", new SimpleStringSchema(), props);


        return source;
    }


    public static FlinkKafkaConsumer<String> getKafkaRuleSource() {


        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "node01:9092,node02:9092,node03:9092");
        props.setProperty("auto.offset.reset", "latest");
        FlinkKafkaConsumer<String> source = new FlinkKafkaConsumer<>("yinew_drl_rule", new SimpleStringSchema(), props);


        return source;
    }
}
