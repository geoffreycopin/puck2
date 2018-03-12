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
 * @ast interface
 * @aspect Generics
 * @declaredat C:\\Users\\Geoffrey\\IdeaProjects\\puck2\\extendj\\java5\\frontend\\Generics.jrag:337
 */
 interface ParTypeDecl {

     
    TypeVariable getTypeParameter(int i);

     
    Parameterization getParameterization();

     
    public String typeName();

     
    SimpleSet<Variable> localFields(String name);

     
    Map<String, SimpleSet<MethodDecl>> localMethodsSignatureMap();

     
    List<TypeVariable> getSubstTypeParamList();
public int numTypeParameter();

public TypeVariable typeParameter(int index);

public Access createQualifiedAccess();

  /**
   * @attribute syn
   * @aspect Generics
   * @declaredat C:\\Users\\Geoffrey\\IdeaProjects\\puck2\\extendj\\java5\\frontend\\Generics.jrag:340
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Generics", declaredAt="C:\\Users\\Geoffrey\\IdeaProjects\\puck2\\extendj\\java5\\frontend\\Generics.jrag:340")
  public boolean isParameterizedType();
  /**
   * @attribute syn
   * @aspect Generics
   * @declaredat C:\\Users\\Geoffrey\\IdeaProjects\\puck2\\extendj\\java5\\frontend\\Generics.jrag:341
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Generics", declaredAt="C:\\Users\\Geoffrey\\IdeaProjects\\puck2\\extendj\\java5\\frontend\\Generics.jrag:341")
  public boolean isRawType();
  /**
   * @attribute syn
   * @aspect GenericsTypeCheck
   * @declaredat C:\\Users\\Geoffrey\\IdeaProjects\\puck2\\extendj\\java5\\frontend\\Generics.jrag:628
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsTypeCheck", declaredAt="C:\\Users\\Geoffrey\\IdeaProjects\\puck2\\extendj\\java5\\frontend\\Generics.jrag:628")
  public boolean sameArguments(ParTypeDecl decl);
  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Users\\Geoffrey\\IdeaProjects\\puck2\\extendj\\java5\\frontend\\Generics.jrag:876
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="C:\\Users\\Geoffrey\\IdeaProjects\\puck2\\extendj\\java5\\frontend\\Generics.jrag:876")
  public boolean sameSignature(Access a);
  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Users\\Geoffrey\\IdeaProjects\\puck2\\extendj\\java5\\frontend\\Generics.jrag:923
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="C:\\Users\\Geoffrey\\IdeaProjects\\puck2\\extendj\\java5\\frontend\\Generics.jrag:923")
  public boolean sameSignature(java.util.List<TypeDecl> list);
  /**
   * @attribute syn
   * @aspect GenericsParTypeDecl
   * @declaredat C:\\Users\\Geoffrey\\IdeaProjects\\puck2\\extendj\\java5\\frontend\\GenericsParTypeDecl.jrag:55
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsParTypeDecl", declaredAt="C:\\Users\\Geoffrey\\IdeaProjects\\puck2\\extendj\\java5\\frontend\\GenericsParTypeDecl.jrag:55")
  public String nameWithArgs();
  /**
   * @attribute inh
   * @aspect GenericsParTypeDecl
   * @declaredat C:\\Users\\Geoffrey\\IdeaProjects\\puck2\\extendj\\java5\\frontend\\GenericsParTypeDecl.jrag:74
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="GenericsParTypeDecl", declaredAt="C:\\Users\\Geoffrey\\IdeaProjects\\puck2\\extendj\\java5\\frontend\\GenericsParTypeDecl.jrag:74")
  public TypeDecl genericDecl();
}
