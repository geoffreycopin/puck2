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
 * @aspect Generics
 * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Generics.jrag:242
 */
 interface GenericTypeDecl {

     
    TypeDecl original();

     
    int getNumTypeParameter();

     
    TypeVariable getTypeParameter(int index);

     
    List<TypeVariable> getTypeParameterList();

     
    public String fullName();

     
    public String typeName();
public TypeDecl makeGeneric(Signatures.ClassSignature s);

public SimpleSet<TypeDecl> addTypeVariables(SimpleSet<TypeDecl> types,
      String name);

  /**
   * @attribute syn
   * @aspect Generics
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Generics.jrag:243
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Generics", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Generics.jrag:243")
  public boolean isGenericType();
  /**
   * @attribute syn
   * @aspect Generics
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Generics.jrag:248
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Generics", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Generics.jrag:248")
  public TypeDecl rawType();
  /** Transforms the parameter and calls the lookupParTypeDecl attribute for ArrayList arguments. 
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Generics.jrag:937
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Generics.jrag:937")
  public TypeDecl lookupParTypeDecl(ParTypeAccess p);
  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Generics.jrag:945
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Generics.jrag:945")
  public TypeDecl lookupParTypeDecl(Collection<TypeDecl> typeArgs);
}
