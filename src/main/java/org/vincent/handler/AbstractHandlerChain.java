package org.vincent.handler;

import lombok.Data;

/**
 * @Package org.vincent.handler
 * @ClassName AbstractHandlerChain.java
 * @date 2019/6/14 - 13:56
 * @description : 责任链模式 抽象类 定义责任链 统一接口
 * Created by PengRong .
 */
@Data
public  abstract class AbstractHandlerChain {
    /**
     * 责任链中下一个 处理对象
     */
    private AbstractHandlerChain nextChain;

    public abstract boolean doHandler(double value);

}
