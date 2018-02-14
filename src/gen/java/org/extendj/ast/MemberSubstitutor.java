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
 * @aspect LookupParTypeDecl
 * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Generics.jrag:1237
 */
 interface MemberSubstitutor {

     
    TypeDecl original();
  /**
   * Substituted local methods.
   * 
   * <p>Includes all non-substitutable original methods plus all substituted methods.
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Generics.jrag:1348
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Generics.jrag:1348")
  public java.util.List<MethodDecl> localMethods();
  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Generics.jrag:1363
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Generics.jrag:1363")
  public SimpleSet<Variable> localFields(String name);
  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Generics.jrag:1394
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Generics.jrag:1394")
  public SimpleSet<TypeDecl> localTypeDecls(String name);
}
