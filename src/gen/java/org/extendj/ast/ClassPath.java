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
 * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ClassPath.jrag:180
 */
public class ClassPath extends java.lang.Object {
  

    /**
     * Tracks all currently available packages in the program classpath.
     */
    private Set<String> packages = new HashSet<String>();

  

    private boolean pathsInitialized = false;

  

    private ArrayList<PathPart> classPath = new ArrayList<PathPart>();

  

    private ArrayList<PathPart> sourcePath = new ArrayList<PathPart>();

  

    private final Program program;

  

    public ClassPath(Program program) {
      this.program = program;
    }

  

    /**
     * Used to make the classpath empty, in case you want more control
     * over the classpath initialization. Usually you would use
     * addClassPath to manually setup the classpath after this.
     */
    public synchronized void initEmptyPaths() {
      pathsInitialized = true;
    }

  

    /**
     * Set up the classpaths (standard + boot classpath).
     */
    private synchronized void initPaths() {
      if (pathsInitialized) {
        return;
      }
      pathsInitialized = true;

      ArrayList<String> classPaths = new ArrayList<String>();
      ArrayList<String> sourcePaths = new ArrayList<String>();

      String bootclasspath;
      if (program.options().hasValueForOption("-bootclasspath")) {
        bootclasspath = program.options().getValueForOption("-bootclasspath");
      } else {
        bootclasspath = System.getProperty("sun.boot.class.path", "");
      }
      for (String path : bootclasspath.split(File.pathSeparator)) {
        classPaths.add(path);
      }

      String extdirs;
      if (program.options().hasValueForOption("-extdirs")) {
        extdirs = program.options().getValueForOption("-extdirs");
      } else {
        extdirs = System.getProperty("java.ext.dirs", "");
      }
      for (String path : extdirs.split(File.pathSeparator)) {
        File dir = new File(path);
        if (dir.isDirectory()) {
          for (File jar : dir.listFiles()) {
            if (jar.getName().toLowerCase().endsWith(".jar")) {
              classPaths.add(jar.getPath());
            }
          }
        }
      }

      String[] userClasses;
      if (program.options().hasValueForOption("-classpath")) {
        userClasses = program.options().getValueForOption("-classpath").split(File.pathSeparator);
      } else if (program.options().hasValueForOption("-cp")) {
        userClasses = program.options().getValueForOption("-cp").split(File.pathSeparator);
      } else {
        userClasses = new String[] { "." };
      }
      if (!program.options().hasValueForOption("-sourcepath")) {
        for (String path : userClasses) {
          classPaths.add(path);
          sourcePaths.add(path);
        }
      } else {
        for (String path : userClasses) {
          classPaths.add(path);
        }
        userClasses = program.options().getValueForOption("-sourcepath").split(File.pathSeparator);
        for (String path : userClasses) {
          sourcePaths.add(path);
        }
      }

      for (String path : classPaths) {
        PathPart part = PathPart.createClassPath(path);
        if (part != null) {
          addClassPath(part);
        } else if (program.options().verbose()) {
          System.out.println("Warning: Could not use " + path + " as class path");
        }
      }
      for (String path : sourcePaths) {
        PathPart part = PathPart.createSourcePath(path);
        if (part != null) {
          addSourcePath(part);
        } else if(program.options().verbose()) {
          System.out.println("Warning: Could not use " + path + " as source path");
        }
      }
    }

  

    /**
     * Get the input stream for a compilation unit specified using a canonical
     * name. This is used by the bytecode reader to load nested types.
     * @param name The canonical name of the compilation unit.
     */
    public synchronized InputStream getInputStream(String name) {
      try {
        for (PathPart part : classPath) {
          ClassSource source = part.findSource(name);
          if (source != ClassSource.NONE) {
            return source.openInputStream();
          }
        }
      } catch(IOException e) {
      }
      throw new Error("Could not find nested type " + name);
    }

  

    /**
     * Load a compilation unit from disk based on a classname. A class file is parsed if one exists
     * matching the classname that is not older than a corresponding source file, otherwise the
     * source file is selected.
     *
     * <p>This method is called by the LibCompilationUnit NTA.  We rely on the result of this method
     * being cached because it will return a newly parsed compilation unit each time it is called.
     *
     * @return the loaded compilation unit, or the provided default compilation unit if no matching
     * compilation unit was found.
     */
    public CompilationUnit getCompilationUnit(String typeName,
        CompilationUnit defaultCompilationUnit) {
      try {
        initPaths();
        ClassSource sourcePart = ClassSource.NONE;
        ClassSource classPart = ClassSource.NONE;
        for (PathPart part : sourcePath) {
          sourcePart = part.findSource(typeName);
          if (sourcePart != ClassSource.NONE) {
            break;
          }
        }
        for (PathPart part : classPath) {
          classPart = part.findSource(typeName);
          if (classPart != ClassSource.NONE) {
            break;
          }
        }

        if (sourcePart != ClassSource.NONE && (classPart == ClassSource.NONE
              || classPart.lastModified() < sourcePart.lastModified())) {
          CompilationUnit unit = sourcePart.parseCompilationUnit(program);
          int index = typeName.lastIndexOf('.');
          if (index == -1) {
            return unit;
          }
          String pkgName = typeName.substring(0, index);
          if (pkgName.equals(unit.getPackageDecl())) {
            return unit;
          }
        }
        if (classPart != ClassSource.NONE) {
          CompilationUnit unit = classPart.parseCompilationUnit(program);
          int index = typeName.lastIndexOf('.');
          if (index == -1) {
            return unit;
          }
          String pkgName = typeName.substring(0, index);
          if (pkgName.equals(unit.getPackageDecl())) {
            return unit;
          }
        }
        return defaultCompilationUnit;
      } catch (IOException e) {
        // Attributes can't throw checked exceptions, so convert this to an Error.
        throw new Error(e);
      }
    }

  

    /**
     * Add a package name to available package set.
     */
    public synchronized void addPackage(String packageName) {
      int end = packageName.length();
      while (end > 0 && packages.add(packageName.substring(0, end))) {
        end = packageName.lastIndexOf('.', end - 1);
      }
    }

  

    /**
     * Add a path part to the library class path.
     */
    public synchronized void addClassPath(PathPart pathPart) {
      classPath.add(pathPart);
    }

  

    /**
     * Add a path part to the user class path.
     */
    public synchronized void addSourcePath(PathPart pathPart) {
      sourcePath.add(pathPart);
    }

  

    /**
     * Quick pass, slow fail. Cache existing package names in a concurrent set.
     * @return <code>true</code> if there is a package with the given name on
     * the classpath
     */
    public synchronized boolean isPackage(String packageName) {
      initPaths();
      if (packages.contains(packageName)) {
        return true;
      }
      for (PathPart part : classPath) {
        if (part.hasPackage(packageName)) {
          addPackage(packageName);
          return true;
        }
      }
      for (PathPart part : sourcePath) {
        if (part.hasPackage(packageName)) {
          addPackage(packageName);
          return true;
        }
      }
      return false;
    }

  

    /**
     * @return a copy of the source path parts
     */
    public synchronized Collection<PathPart> getSourcePath() {
      return new ArrayList<PathPart>(sourcePath);
    }

  

    /**
     * @return a copy of the class path parts
     */
    public synchronized Collection<PathPart> getClassPath() {
      return new ArrayList<PathPart>(classPath);
    }


}
