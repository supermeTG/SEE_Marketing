package cn.asedu.dynamic_rule.service;

import cn.asedu.dynamic_rule.pojo.RuleParam;
import cn.asedu.dynamic_rule.pojo.LogBean;
import cn.asedu.dynamic_rule.pojo.RuleAtomicParam;
import cn.asedu.dynamic_rule.utils.RuleCalcUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.state.ListState;

import java.util.List;

/**
 * @author ~~
 * @version 1.0
 * @desc 用户行为次数类条件查询服务实现：在flink的state中统计行为次数
 */
@Slf4j
public class UserActionCountQueryServiceStateImpl implements UserActionCountQueryService {

    ListState<LogBean> eventState;
    public UserActionCountQueryServiceStateImpl(ListState<LogBean> eventState){

        this.eventState = eventState;
    }

    /**
     * 查询规则参数对象中，要求的用户行为次数类条件是否满足
     * 同时，将查询到的真实次数，set回 规则参数对象中
     *
     * @param ruleParam  规则整体参数对象
     * @return 条件是否满足
     */
    @Override
    public boolean queryActionCounts(String deviceId, RuleParam ruleParam) throws Exception {

        // 取出各个用户行为次数原子条件
        List<RuleAtomicParam> userActionCountParams = ruleParam.getUserActionCountParams();

        // 取出历史明细数据
        Iterable<LogBean> logBeansIterable = eventState.get();

        // 统计每一个原子条件所发生的真实次数，就在原子条件参数对象中：realCnts
        queryActionCountsHelper(logBeansIterable, userActionCountParams);

        // 经过上面的方法执行后，每一个原子条件中，都拥有了一个真实发生次数，我们在此判断是否每个原子条件都满足
        for (RuleAtomicParam userActionCountParam : userActionCountParams) {
            if (userActionCountParam.getRealCnt() < userActionCountParam.getCnt()) {
                return false;
            }
        }

        // 如果到达这一句话，说明上面的判断中，每个原子条件都满足，则返回整体结果true
        return true;
    }


    /**
     * 根据传入的历史明细，和规则条件
     * 挨个统计每一个规则原子条件的真实发生次数，并将结果set回规则条件参数中
     *
     * @param logBeansIterable
     * @param userActionCountParams
     */
    public void queryActionCountsHelper(Iterable<LogBean> logBeansIterable, List<RuleAtomicParam> userActionCountParams) {
        for (LogBean logBean : logBeansIterable) {

            for (RuleAtomicParam userActionCountParam : userActionCountParams) {

                // 判断当前logbean 和当前 规则原子条件userActionCountParam 是否一致
                boolean isMatch = RuleCalcUtil.eventBeanMatchEventParam(logBean, userActionCountParam,true);
                log.debug("用户:{},查询了近期count条件,{},查询到的结果:{}",logBean.getDeviceId(),userActionCountParam.getEventId(),userActionCountParam.getRealCnt());

                // 如果一致，则查询次数结果+1
                if (isMatch) {
                    userActionCountParam.setRealCnt(userActionCountParam.getRealCnt() + 1);
                }
            }

        }

    }


    /**
     * 接收一个原子count类条件
     * 进行查询，并返回是否匹配
     * 并且，将查询到的realcount塞回参数对象
     * @param deviceId
     * @param atomicParam
     * @return
     * @throws Exception
     */
    @Override
    public boolean queryActionCounts(String deviceId, RuleAtomicParam atomicParam,String ruleId) throws Exception {

        Iterable<LogBean> logBeans = eventState.get();
        for (LogBean logBean : logBeans) {
            boolean b = RuleCalcUtil.eventBeanMatchEventParam(logBean, atomicParam, true);
            // 如果事件和条件匹配，则真实次数+1
            if(b) atomicParam.setRealCnt(atomicParam.getRealCnt()+1);
        }

        return atomicParam.getRealCnt() >= atomicParam.getCnt();
    }


}
