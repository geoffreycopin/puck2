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
import java.util.zip.*;
import java.io.*;
import org.jastadd.util.*;
import java.util.LinkedHashSet;
import org.jastadd.util.PrettyPrintable;
import org.jastadd.util.PrettyPrinter;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
/**
 * @ast class
 * @aspect PathPart
 * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/PathPart.jadd:46
 */
public abstract class ClassSource extends java.lang.Object {
  
    protected final PathPart sourcePath;

  

    /**
     * Used to represent an non-existent compilation unit source file.
     *
     * <p>This class source should never be used to attempt to load a class, it
     * is simply used as a marker to indicate failure to find a compilation
     * unit.
     */
    public static final ClassSource NONE = new ClassSource(null) {
      @Override
      public long lastModified() {
        return 0;
      }
      @Override
      public InputStream openInputStream() {
        throw new UnsupportedOperationException(
            "ClassSource.NONE can not open an input stream!");
      }
      @Override
      public String pathName() {
        return "<Unknown Source>";
      }
    };

  

    public ClassSource(PathPart sourcePath) {
      this.sourcePath = sourcePath;
    }

  

    public PathPart getSourcePath() {
      return sourcePath;
    }

  

    /**
     * @return Last modification time of the class source.
     */
    abstract public long lastModified();

  

    /**
     * @return Input stream pointing to the class source.
     */
    abstract public InputStream openInputStream() throws IOException;

  

    /**
     * It is sufficient to only overload pathName if sourceName=pathName
     * @return the full name of the class source (e.g. file path).
     */
    public String sourceName() {
      return pathName();
    }

  

    /**
     * @return the path to the source file or the enclosing jar file
     */
    abstract public String pathName();

  

    /**
     * It is sufficient to only overload pathName if relativeName = pathName
     * @return the path to the source file, or the path to the file inside a
     * jar file
     */
    public String relativeName() {
      return pathName();
    }

  

    @Override
    public String toString() {
      return sourceName();
    }

  

    /**
     * Parses the compilation unit from this class source.
     *
     * NB only call this once! The input stream is closed after the compilation
     * unit is parsed.
     *
     * @return parsed compilation unit, or {@code null} if something failed
     */
    public CompilationUnit parseCompilationUnit(Program program) throws IOException {
      InputStream is = openInputStream();
      try {
        if (program.options().verbose()) {
          System.out.print("Loading " + sourceName());
        }

        long start = System.nanoTime();
        CompilationUnit u = program.javaParser.parse(is, sourceName());
        long elapsed = System.nanoTime() - start;
        program.javaParseTime += elapsed;
        program.numJavaFiles += 1;

        u.setFromSource(true);
        u.setClassSource(this);

        if (program.options().verbose()) {
          System.out.println(" in " + (elapsed / 1000000) + " ms");
        }
        return u;
      } catch (Exception e) {
        System.err.println("Unexpected error of kind " + e.getClass().getName());
        throw new Error(sourceName() + ": " + e.getMessage(), e);
      } finally {
        if (is != null) {
          try {
            is.close();
          } catch (IOException e) {
          }
        }
      }
    }


}
