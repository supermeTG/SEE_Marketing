package cn.asedu.dynamic_rule.service;

import cn.asedu.dynamic_rule.pojo.RuleParam;
import cn.asedu.dynamic_rule.pojo.RuleAtomicParam;

/**
 * @author ~~
 * @version 1.0
 * @desc 用户行为次数类条件查询服务接口
 */
public interface UserActionCountQueryService {

    public boolean queryActionCounts(String deviceId, RuleParam ruleParam) throws Exception;

    public boolean queryActionCounts(String deviceId, RuleAtomicParam atomicParam,String ruleId) throws Exception;

}
