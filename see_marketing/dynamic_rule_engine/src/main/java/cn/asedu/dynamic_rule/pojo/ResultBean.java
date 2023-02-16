package cn.asedu.dynamic_rule.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultBean {

    private String ruleId;
    private String deviceId;
    private long timeStamp;
}
