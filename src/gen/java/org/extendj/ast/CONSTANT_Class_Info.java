package org.extendj.ast;

import java.util.*;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import org.jastadd.util.*;
import java.util.LinkedHashSet;
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
import org.jastadd.util.PrettyPrintable;
import org.jastadd.util.PrettyPrinter;
import java.util.zip.*;
import java.io.*;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
/**
 * @ast class
 * @aspect BytecodeCONSTANT
 * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/BytecodeCONSTANT.jrag:32
 */
 class CONSTANT_Class_Info extends CONSTANT_Info {
  
    public int name_index;

  

    public CONSTANT_Class_Info(AbstractClassfileParser parser) throws IOException {
      super(parser);
      name_index = p.u2();
    }

  

    @Override
    public String toString() {
      return "ClassInfo: " + name();
    }

  

    public String name() {
      String name = ((CONSTANT_Utf8_Info) p.constantPool[name_index]).string();
      name = name.replace('/', '.');
      return name;
    }

  

    public String simpleName() {
      String name = name();
      int pos = name.lastIndexOf('.');
      return name.substring(pos + 1, name.length());
    }

  

    public String packageDecl() {
      String name = name();
      int pos = name.lastIndexOf('.');
      if (pos == -1) {
        return "";
      }
      return name.substring(0, pos);
    }

  

    public Access access() {
      String name = name();
      int pos = name.lastIndexOf('.');
      String typeName = name.substring(pos + 1, name.length());
      String packageName = pos == -1 ? "" : name.substring(0, pos);
      if (typeName.indexOf('$') != -1) {
        return new BytecodeTypeAccess(packageName, typeName);
      } else {
        return new TypeAccess(packageName, typeName);
      }
    }


}
