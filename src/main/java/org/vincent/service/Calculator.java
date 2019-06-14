package org.vincent.service;

/**
 * @Package org.vincent
 * @ClassName Calculator.java
 * @date 2019/6/14 - 18:44
 * @description :
 * Created by PengRong .
 */
public interface Calculator {
    /**
     * calculate sum of an integer array
     * @param numbers
     * @return
     */
    public long sum(int[] numbers);

    /**
     * shutdown pool or reclaim any related resources
     */
    public void shutdown();
}
