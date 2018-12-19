package com.july.guava.guavathreadpool;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

@Component
public class ListeningExecutor {

    @Bean
    public ListeningExecutorService createListeningExectorService(){
        return MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
    }

}
