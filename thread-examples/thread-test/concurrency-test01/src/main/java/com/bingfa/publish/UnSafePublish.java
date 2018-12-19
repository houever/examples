package com.bingfa.publish;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class UnSafePublish {

    private String[] states = {"a","b","c"};


    public String[] getStates() {
        return states;
    }

    public static void main(String[] args) {
        UnSafePublish publish = new UnSafePublish();
        log.debug("{}", Arrays.toString(publish.getStates()));

        publish.getStates()[0]="d";
        log.debug("{}", Arrays.toString(publish.getStates()));

    }

}
