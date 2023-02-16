package cn.asedu.dynamic_rule.service;


import cn.asedu.dynamic_rule.pojo.RuleParam;

/**
 * @author ~~
 * @version 1.0
 * @desc 用户行为次序列条件查询服务接口
 */
public interface UserActionSequenceQueryService {

    public boolean queryActionSequence(String deviceId, RuleParam ruleParam) throws Exception;
}
