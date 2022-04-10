package com.hefl.nettydemo.netty;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author hefl
 * @date 2022/4/10 20:27
 * TODO
 */
@Slf4j
public class TestJdkFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 线程池
        ExecutorService service = Executors.newFixedThreadPool(2);
        // 提交任务
        Future<Object> future = service.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                log.debug("执行计算");
                Thread.sleep(1000);
                return 50;
            }
        });
        // 主线程通过future获取结果
        log.debug("等待结果");
        log.debug("结果是" + future.get());
    }
}
