package com.thepokecraftmod;

import com.thebombzen.jxlatte.JXLDecoder;
import com.thebombzen.jxlatte.JXLOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class jxlTest {
    public static final ThreadPoolExecutor FG_THREAD_POOL = (ThreadPoolExecutor) Executors.newFixedThreadPool(6); // 4
    public static final ThreadPoolExecutor BG_THREAD_POOL = (ThreadPoolExecutor) Executors.newFixedThreadPool(3); // 2

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        JXLOptions options = new JXLOptions();
        options.threads = 2;
        options.hdr = JXLOptions.HDR_OFF;

        for (int i = 0; i < 50; i++) {
            var time = System.currentTimeMillis();
            var futures = new LinkedList<Future<?>>();

            for (int j = 0; j < 5; j++) {
                futures.add(BG_THREAD_POOL.submit(() -> {
                    try {
                        new JXLDecoder(Files.newInputStream(Paths.get("samples/body_rgn.jxl")), options, FG_THREAD_POOL).decode();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }));
            }

            for (var future : futures) future.get();
            System.out.println((System.currentTimeMillis() - time) + "ms taken");
            System.out.println("=============");
        }

        BG_THREAD_POOL.shutdownNow();
        FG_THREAD_POOL.shutdownNow();
    }
}
