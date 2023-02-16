package cn.asedu.dynamic_rule.service;

import cn.asedu.dynamic_rule.pojo.RuleParam;

/**
 * @author ~~
 * @version 1.0
 * @desc 用户画像数据查询服务接口
 */
public interface UserProfileQueryService {

    public boolean judgeProfileCondition(String deviceId, RuleParam ruleParam);

}
