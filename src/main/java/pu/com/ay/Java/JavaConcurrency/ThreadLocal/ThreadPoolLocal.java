package com.lld.JavaConcurrency.ThreadLocal;

import java.text.SimpleDateFormat;

/**
 * THREADLOCAL — THEORY
 * ====================
 * ThreadLocal provides thread-local variables: each thread that accesses a ThreadLocal variable
 * gets its own independently initialized copy. No sharing, no synchronization needed.
 *
 * INTERNAL WORKING:
 *   - Each Thread object holds a `ThreadLocalMap` (a hash map keyed by ThreadLocal instances).
 *   - When you call threadLocal.get(), it looks up the current thread's map and returns its value.
 *   - When you call threadLocal.set(v), it stores the value in the current thread's map.
 *   - Values are NOT shared across threads — each thread has its own slot.
 *
 * KEY METHODS:
 *   - get()            → returns the current thread's copy (calls initialValue() on first access)
 *   - set(T value)     → sets the current thread's copy
 *   - remove()         → removes the current thread's copy (IMPORTANT to avoid memory leaks)
 *   - initialValue()   → override to provide a default value per thread (lazy, called once per thread)
 *   - withInitial(Supplier) → static factory, cleaner alternative to overriding initialValue()
 *
 * COMMON USE CASES:
 *   1. SimpleDateFormat per thread (not thread-safe, so give each thread its own instance)
 *   2. Database connections / transactions per thread
 *   3. User/session context in web request handling (e.g., Spring's SecurityContextHolder)
 *   4. Avoiding passing parameters down deep call chains
 *
 * MEMORY LEAK WARNING (critical in thread pools):
 *   - Thread pool threads are reused and never die → their ThreadLocalMap lives forever.
 *   - If you store a value and never call remove(), the value leaks for the lifetime of the pool.
 *   - ALWAYS call threadLocal.remove() in a finally block after the task completes.
 *
 * THREADLOCAL vs SYNCHRONIZATION:
 *   - Synchronization → one shared object, threads take turns accessing it.
 *   - ThreadLocal     → one object per thread, no contention, no locking needed.
 *   - Use ThreadLocal when each thread needs its own isolated state (not shared state).
 *
 * EXAMPLE (cleaner modern style):
 *   ThreadLocal<SimpleDateFormat> fmt =
 *       ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
 */
public class ThreadPoolLocal {

    public static ThreadLocal<SimpleDateFormat> dateFormatter = new ThreadLocal<SimpleDateFormat>(){

        @Override
        protected SimpleDateFormat initialValue(){
            return new SimpleDateFormat("yyyy-mm-dd");
        }
        @Override
        public SimpleDateFormat get(){
            return super.get();
        }
    };
 
}