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
 * @ast interface
 * @aspect BytecodeSignatures
 * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/BytecodeSignatures.jrag:393
 */
public interface Flags {

     
    public int ACC_PUBLIC = 0x0001;

     
    public int ACC_PRIVATE = 0x0002;

     
    public int ACC_PROTECTED = 0x0004;

     
    public int ACC_STATIC = 0x0008;

     
    public int ACC_FINAL = 0x0010;

     
    public int ACC_SUPER = 0x0020;

     
    public int ACC_SYNCHRONIZED = 0x0020;

     
    public int ACC_VOLATILE = 0x0040;

     
    public int ACC_BRIDGE = 0x0040;

     
    public int ACC_TRANSIENT = 0x0080;

     
    public int ACC_VARARGS = 0x0080;

     
    public int ACC_NATIVE = 0x0100;

     
    public int ACC_INTERFACE = 0x0200;

     
    public int ACC_ABSTRACT = 0x0400;

     
    public int ACC_STRICT = 0x0800;

     
    public int ACC_SYNTHETIC = 0x1000;

     
    public int ACC_ANNOTATION = 0x2000;

     
    public int ACC_ENUM = 0x4000;
}
