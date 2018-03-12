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
 * @declaredat C:\\Users\\Geoffrey\\IdeaProjects\\puck2\\extendj\\java4\\frontend\\BytecodeCONSTANT.jrag:282
 */
 class CONSTANT_Utf8_Info extends CONSTANT_Info {
  
    public String string;

  

    public CONSTANT_Utf8_Info(AbstractClassfileParser parser) throws IOException {
      super(parser);
      string = p.readUTF();
    }

  

    @Override
    public String toString() {
      return "Utf8Info: " + string;
    }

  

    @Override
    public Expr expr() {
      return Literal.buildStringLiteral(string);
    }

  

    public String string() {
      return string;
    }


}
