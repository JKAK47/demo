package org.vincent.handler;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Package org.vincent.handler
 * @ClassName SalesHandlerChain.java
 * @date 2019/6/14 - 13:59
 * @description : 具体责任链模式实例
 * Created by PengRong .
 */
@Builder
@Slf4j
public class SalesHandlerChain extends AbstractHandlerChain {
    @Override
    public boolean doHandler(double value) {
        if (value < 200) {
            log.info("sale handler.");
            System.out.println("sale handler.");
            return true;
        } else {
            setNextChain(ManagerHandlerChain.builder().build());
            return getNextChain().doHandler(value);
        }
    }
}
