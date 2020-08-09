package com.kaiqiang.simple.mq.client;

import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

public class SnowFlakeIdGeneratorTest extends TestCase {

    public void testGetNextId() {
        Set<Long> ids = new HashSet<>();
        long start = System.currentTimeMillis();
        IdGenerator idGenerator = new SnowFlakeIdGenerator(1, 2);
        for (int i = 0; i < 30000000; i++) {
            long nextId = idGenerator.getNextId();
            if(ids.contains(nextId)) {
                throw new RuntimeException("生成id重复, id = " + nextId);
            }

            ids.add(nextId);
        }
        System.out.println("time = " + (System.currentTimeMillis() - start));
    }
}