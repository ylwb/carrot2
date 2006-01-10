
/*
 * Carrot2 project.
 *
 * Copyright (C) 2002-2006, Dawid Weiss, Stanisław Osiński.
 * Portions (C) Contributors listed in "carrot2.CONTRIBUTORS" file.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the repository checkout or at:
 * http://www.carrot2.org/carrot2.LICENSE
 */

package com.dawidweiss.carrot.remote.controller.cache;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

public class MemoryCachedQueriesContainerTest extends TestCase {
    private ZIPCachedQueriesContainer testCache;

    public MemoryCachedQueriesContainerTest(String s) {
        super(s);
    }
    
    public void setUp() {
        final File testCacheDir = new File("cache");
        if (!testCacheDir.isDirectory()) {
            fail("Test cache is not a directory: "
                    + testCacheDir.getAbsolutePath());
        }

        this.testCache = new ZIPCachedQueriesContainer();
        testCache.setAbsoluteDir(testCacheDir.getAbsolutePath());
        testCache.configure();
    }

    public void testStoringQueries() throws IOException {
        MemoryCachedQueriesContainer memContainer = new MemoryCachedQueriesContainer();
        CacheRotator rotator = new CacheRotator();
        rotator.copyAll(testCache, memContainer);
        rotator.containsAll(testCache, memContainer);
    }
    
    public void testClear() throws IOException {
        MemoryCachedQueriesContainer memContainer = new MemoryCachedQueriesContainer();
        CacheRotator rotator = new CacheRotator();
        rotator.copyAll(testCache, memContainer);
        memContainer.clear();
        assertTrue(memContainer.getCachedElementSignatures().hasNext() == false);
    }
}
