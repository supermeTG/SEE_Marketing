package cn.asedu.dynamic_rule.service;

import cn.asedu.dynamic_rule.pojo.BufferAvailableLevel;
import cn.asedu.dynamic_rule.pojo.BufferResult;
import cn.asedu.dynamic_rule.pojo.RuleAtomicParam;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author ~~
 * @version 1.0
 * @desc 缓存管理器
 */
@Slf4j
public class BufferManager {
    Jedis jedis;

    public BufferManager(){
        jedis = new Jedis("node01", 6379);
    }


    /**
     * 获取缓存数据并返回
     * @param bufferKey  缓存key
     * @param atomicParam  规则原子条件对象
     * @return 缓存数据
     */
    public BufferResult getBufferData(String bufferKey, RuleAtomicParam atomicParam){
        BufferResult bufferResult = getBufferData(bufferKey, atomicParam.getRangeStart(), atomicParam.getRangeEnd(), atomicParam.getCnt());
        return bufferResult;
    }

    /**
     * 获取缓存数据并返回
     * @param bufferKey 缓存key
     * @param paramRangeStart 缓存数据时间start
     * @param paramRangeEnd 缓存数据时间end
     * @param threshold 缓存数据对应查询条件的阈值
     * @return 缓存数据结果
     */
    public BufferResult getBufferData(String bufferKey,long paramRangeStart,long paramRangeEnd,int threshold){
        // 准备缓存返回结果实体对象
        BufferResult bufferResult = new BufferResult();
        bufferResult.setBufferAvailableLevel(BufferAvailableLevel.UN_AVL);

        /*
         *  解析缓存数据
         *  格式:   2|t1,t8
         */
        String data = jedis.get(bufferKey);
        // data可能为null，这里直接切，会产生空指针异常
        if(data ==null || data.split("\\|").length<2) return bufferResult;

        String[] split = data.split("\\|");
        String[] timeRange = split[1].split(",");

        bufferResult.setBufferKey(bufferKey);
        bufferResult.setBufferValue(Integer.parseInt(split[0]));
        bufferResult.setBufferRangeStart(Long.parseLong(timeRange[0]));
        bufferResult.setBufferRangeEnd(Long.parseLong(timeRange[1]));

        /*
         * 判断缓存有效性： 完全有效
         */
        if(paramRangeStart<= bufferResult.getBufferRangeStart()
                && paramRangeEnd>=bufferResult.getBufferRangeEnd()
                && bufferResult.getBufferValue()>=threshold){
            bufferResult.setBufferAvailableLevel(BufferAvailableLevel.WHOLE_AVL);
            log.warn("{} 缓存完全匹配,value为: {}",bufferKey,bufferResult.getBufferValue());
        }

        /*
         * 判断缓存有效性： 部分有效
         * TODO 重大bug修改，少了个else，逻辑就完全错误了
         */
        else if(paramRangeStart == bufferResult.getBufferRangeStart() && paramRangeEnd>=bufferResult.getBufferRangeEnd()){
            bufferResult.setBufferAvailableLevel(BufferAvailableLevel.PARTIAL_AVL);
            // 更新外部后续查询的条件窗口起始点
            //bufferResult.setOutSideQueryStart(bufferResult.getBufferRangeEnd());
            log.warn("count缓存部分匹配");
        }

        return bufferResult;
    }

    // 插入数据到缓存
    public void putBufferData(String bufferKey,int value,long bufferRangeStart,long bufferRangeEnd){
        jedis.setex(bufferKey,4*60*60*1000,value+"|"+bufferRangeStart+","+bufferRangeEnd);
    }


    // 更新已存在的缓存数据


    // 删除已存在的缓存数据

}
