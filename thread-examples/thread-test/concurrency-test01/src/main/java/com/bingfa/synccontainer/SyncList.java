package com.bingfa.synccontainer;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SyncList {

    private List<Integer> lsit = Collections.synchronizedList(new ArrayList<Integer>());


}
