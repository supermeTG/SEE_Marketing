package cn.asedu.dynamic_rule.datagen;

import cn.asedu.dynamic_rule.pojo.LogBean;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/***
 * @author hunter.d
 * @qq 657270652
 * @wx haitao-duan
 * @date 2021/4/5
 **/
public class ActionLogGenOne {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "node01:9092,node02:9092,node03:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props);

        LogBean logBean = new LogBean();
        logBean.setDeviceId("000053");
        logBean.setEventId("E");
        Map<String, String> ps = new HashMap();
        props.put("p1", "v1");
        logBean.setProperties(ps);
        logBean.setTimeStamp(System.currentTimeMillis());

        String log = JSON.toJSONString(logBean);
        ProducerRecord<String, String> record = new ProducerRecord<>("zenniu_applog", log);
        kafkaProducer.send(record);
        kafkaProducer.flush();
    }
}
