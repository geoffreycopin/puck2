package org.extendj.ast;

import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.util.Set;
import beaver.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.LinkedHashSet;
import org.jastadd.util.*;
import java.util.zip.*;
import java.io.*;
import org.jastadd.util.PrettyPrintable;
import org.jastadd.util.PrettyPrinter;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
/**
 * @ast class
 * @aspect ClassPath
 * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ClassPath.jrag:440
 */
 class ParseSynchronizer extends java.lang.Object {
  
    private AtomicBoolean first = new AtomicBoolean(true);

  
    private CompilationUnit result = null;

  

    /**
     * @return {@code true} in only one thread calling this method.
     * Returns {@code false} in all other threads.
     */
    public boolean first() {
      return first.getAndSet(false);
    }

  

    /**
     * Share a parsed compilation unit with other threads.
     * The compilation unit must not be null!
     */
    public synchronized void set(CompilationUnit result) {
      this.result = result;
      notifyAll();
    }

  

    /**
     * Read the stored compilation unit. This blocks until the result has been
     * stored by another thread.
     */
    public synchronized CompilationUnit get() {
      try {
        while (result == null) {
          wait();
        }
      } catch (InterruptedException e) {
      }
      return result;
    }


}
