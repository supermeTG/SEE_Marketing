package cn.asedu.dynamic_rule.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author ~~
 * @version 1.0
 * @desc 封装从缓存中查询到的结果的实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BufferResult {

    // 缓存结果所对应的key
    private String bufferKey;

    // 缓存结果中的value
    private Integer bufferValue;

    // 缓存数据的时间窗口起始
    private Long bufferRangeStart;

    // 缓存数据的时间窗口结束
    private Long bufferRangeEnd;

    // 缓存结果的有效性等级
    private BufferAvailableLevel bufferAvailableLevel;


    // 调整后的后续查询窗口起始点
    private Long outSideQueryStart;


}
