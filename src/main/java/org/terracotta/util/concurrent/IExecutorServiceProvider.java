package org.terracotta.util.concurrent;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
public interface IExecutorServiceProvider {

    /**
     * Creates a new ExecutorServiceProvider with fixed size thread pool
     *
     * @param nThreads which represents the fixed size of the pool
     *
     * @return a fresh {@link ExecutorServiceProvider}
     */
    ExecutorServiceProvider buildWithFixedSizePool(final int nThreads);

    /**
     * Creates a new ExecutorServiceProvider with cached thread pool
     *
     * @return a fresh {@link ExecutorServiceProvider}
     */
    ExecutorServiceProvider buildWithCachedPool();

    /**
     * Executes code in this runnable asynchronously
     *
     * @param runnable contains code which should be executed async
     */
    void execute(final Runnable runnable);

    /**
     * Retrieves the {@link ListenableFuture} which executes the code asynchronously
     *
     * @param callable for the {@link ListenableFuture} with an unknown object as value
     *
     * @return a fresh {@link ListenableFuture}
     */
    ListenableFuture<?> submit(final Callable<?> callable);
}