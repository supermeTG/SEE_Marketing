package cn.asedu.dynamic_rule.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kie.api.runtime.KieSession;

/**
 * @author ~~
 * @version 1.0
 * @desc 用于封装放入state中的规则相关信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleStateBean {

    private String ruleName;
    private KieSession kieSession;
    private RuleParam ruleParam;
    private String ruleType;
    private String routerClass;
    private String cntSqls;
    private String seqSqls;

}
