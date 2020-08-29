package org.terracotta.util.concurrent;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.NoArgsConstructor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
@NoArgsConstructor
public class ExecutorServiceProvider implements IExecutorServiceProvider {

    private ExecutorService service;

    @Override
    public ExecutorServiceProvider buildWithFixedSizePool(final int nThreads) {
        this.service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(nThreads));
        return this;
    }

    @Override
    public ExecutorServiceProvider buildWithCachedPool() {
        this.service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        return this;
    }

    @Override
    public void execute(final Runnable runnable) {
        this.service.execute(runnable);
    }

    @Override
    public ListenableFuture<?> submit(final Callable<?> callable) {
        return (ListenableFuture<?>) this.service.submit(callable);
    }
}