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
 * @aspect BytecodeCONSTANT
 * @declaredat C:\\Users\\Geoffrey\\IdeaProjects\\puck2\\extendj\\java4\\frontend\\BytecodeCONSTANT.jrag:212
 */
 class CONSTANT_Long_Info extends CONSTANT_Info {
  
    public long value;

  

    public CONSTANT_Long_Info(AbstractClassfileParser parser) throws IOException {
      super(parser);
      value = p.readLong();
    }

  

    @Override
    public String toString() {
      return "LongInfo: " + Long.toString(value);
    }

  

    @Override
    public Expr expr() {
      return Literal.buildLongLiteral(value);
    }


}
