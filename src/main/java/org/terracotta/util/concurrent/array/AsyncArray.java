package org.terracotta.util.concurrent.array;

import lombok.SneakyThrows;
import org.terracotta.util.concurrent.ExecutorServiceProvider;

import java.util.Arrays;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
public class AsyncArray<T> {

    private final ExecutorServiceProvider serviceProvider = new ExecutorServiceProvider().buildWithCachedPool();

    private Object[] objects = new Object[]{};

    private int length = 1;

    /**
     * Sets an element of this array
     *
     * @param index where the element should be set
     * @param t     element which should be set
     */
    @SneakyThrows
    public void set(final int index, final T t) {
        this.serviceProvider.execute(() -> {
            if (this.length < index) {
                this.length = index + 1;
            } else {
                this.length++;
            }

            this.objects = Arrays.copyOf(this.objects, this.length);
            this.objects[index] = t;
        });
    }

    /**
     * Removes an element from this array
     *
     * @param index used to find the element which should be removed
     */
    public void remove(final int index) {
        this.serviceProvider.execute(() -> {
            if (this.length < index) {
                throw new ArrayIndexOutOfBoundsException("The given index is out of bounds");
            }

            if (this.objects[index] == null) {
                throw new NullPointerException("The element at the given index does not exist");
            }

            if (this.length - 1 - index >= 0) {
                System.arraycopy(this.objects, index + 1, this.objects, index, this.length - 1 - index);
            }

            this.length--;
        });
    }

    /**
     * Retrieves an element from this array
     *
     * @param index which is needed to find the element
     *
     * @return a fresh object
     */
    @SneakyThrows
    public T get(final int index) {
        return (T) this.serviceProvider.submit(() -> {
            if (this.objects[index] == null) {
                throw new NullPointerException("The element at the given index does not exist");
            }

            return this.objects[index];
        }).get();
    }

    /**
     * Retrieves the length of this array
     *
     * @return a fresh Integer primitive data type
     */
    @SneakyThrows
    public int getLength() {
        // sleep one ms because the future is too fast (when getLength is called directly after a change in the array)
        Thread.sleep(1);

        return this.length;
    }

    @Override
    public String toString() {
        return "Length: " + this.length + " | " + Arrays.toString(this.objects);
    }
}