package org.vincent.service.impl;

import org.vincent.service.Calculator;

/**
 * @Package org.vincent.service.impl
 * @ClassName SinglethreadCalculator.java
 * @date 2019/6/14 - 18:46
 * @description :
 * Created by PengRong .
 */
public class SinglethreadCalculator implements Calculator {
    public long sum(int[] numbers) {
        long total = 0L;
        for (int i : numbers) {
            total += i;
        }
        return total;
    }

    @Override
    public void shutdown() {
        // nothing to do
    }
}
