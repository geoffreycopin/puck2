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
 * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/PathPart.jadd:590
 */
public class SourceFilePath extends PathPart {
  
    private final String filePath;

  

    public SourceFilePath(String path) {
      super(true);
      this.filePath = path;
    }

  

    @Override
    public String getPath() {
      return filePath;
    }

  

    /**
     * <b>Use the parsed CompilationUnit to find the package name of the file!</b>
     */
    @Override
    public boolean hasPackage(String name) {
      return false;
    }

  

    @Override
    public ClassSource findSource(String name) {
      if (filePath.equals(name)) {
        File file = new File(filePath);
        if (file.isFile() && file.canRead()) {
          return new FileClassSource(this, filePath);
        }
      }
      return ClassSource.NONE;
    }

  

    @Override
    public String toString() {
      return filePath;
    }


}
