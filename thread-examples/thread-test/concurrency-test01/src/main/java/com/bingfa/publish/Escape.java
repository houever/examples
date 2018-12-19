package com.bingfa.publish;

import com.bingfa.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;

/**
 * 对象逸出
 */
@Slf4j
@NotThreadSafe
public class Escape {

    private int thisCanBeEscape;

    public Escape(){

    }
    private class InnerClass{
        public InnerClass(){
            log.info("{}",Escape.this.thisCanBeEscape);
        }
    }

    public static void main(String[] args) {
        new Escape();
    }
}
