/* This file was generated with JastAdd2 (http://jastadd.org) version 2.3.0 */
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
 * An abstract access.
 * Concrete subclasses include field, method, and type access.
 * @ast node
 * @declaredat /home/hadjer/git/puck2/extendj/java4/grammar/Java.ast:69
 * @astdecl Access : Expr;
 * @production Access : {@link Expr};

 */
public abstract class Access extends Expr implements Cloneable {
  /**
   * Used by the parser to build a method access from a parsed, potentially qualified, name.
   * @aspect QualifiedNames
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:451
   */
  public Access buildMethodAccess(List<Expr> arguments) {
    throw new Error("Can not build method access from access of type "
        + getClass().getSimpleName());
  }
  /**
   * @aspect QualifiedNames
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:474
   */
  public Access addArrayDims(List list) {
    Access a = this;
    for (int i = 0; i < list.getNumChildNoTransform(); i++) {
      Dims dims = (Dims) list.getChildNoTransform(i);
      Opt<Expr> opt = dims.getExprOpt();
      if (opt.getNumChildNoTransform() == 1) {
        a = new ArrayTypeWithSizeAccess(a, (Expr) opt.getChildNoTransform(0));
      } else {
        a = new ArrayTypeAccess(a);
      }
      a.setStart(dims.start());
      a.setEnd(dims.end());
    }
    return a;
  }
  /**
   * Builds a copy of this Access node where all occurrences
   * of type variables in the original type parameter list have been replaced
   * by the substitution type parameters.
   * 
   * <p>This should only be used to generate candidate methods for Diamond type
   * inference.
   * 
   * @return the substituted Access node
   * @aspect Diamond
   * @declaredat /home/hadjer/git/puck2/extendj/java7/frontend/Diamond.jrag:263
   */
  public Access substituted(Collection<TypeVariable> original,
      List<TypeVariable> substitution) {
    return (Access) treeCopyNoTransform();
  }
  /**
   * Checks that two type accesses are the same, while taking type variable
   * substitution into account.
   * @aspect FunctionalInterface
   * @declaredat /home/hadjer/git/puck2/extendj/java8/frontend/FunctionalInterface.jrag:164
   */
  public boolean sameType(Access a) {
    if (this instanceof ArrayTypeAccess && a instanceof ArrayTypeAccess) {
      ArrayTypeAccess at1 = (ArrayTypeAccess) this;
      ArrayTypeAccess at2 = (ArrayTypeAccess) a;
      return at1.sameType(at2);
    } else if (this instanceof AbstractWildcard && a instanceof AbstractWildcard) {
      AbstractWildcard w1 = (AbstractWildcard) this;
      AbstractWildcard w2 = (AbstractWildcard) a;
      return w1.sameType(w2);
    } else if (this instanceof TypeAccess && a instanceof TypeAccess) {
      TypeAccess t1 = (TypeAccess) this;
      TypeAccess t2 = (TypeAccess) a;
      return t1.sameType(t2);
    } else if (this instanceof ParTypeAccess && a instanceof ParTypeAccess) {
      ParTypeAccess pta1 = (ParTypeAccess) this;
      ParTypeAccess pta2 = (ParTypeAccess) a;
      return pta1.sameType(pta2);
    } else {
      return false;
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public Access() {
    super();
  }
  /**
   * Initializes the child array to the correct size.
   * Initializes List and Opt nta children.
   * @apilevel internal
   * @ast method
   * @declaredat ASTNode:10
   */
  public void init$Children() {
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:13
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:19
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:23
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    type_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:28
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:32
   */
  public Access clone() throws CloneNotSupportedException {
    Access node = (Access) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:43
   */
  @Deprecated
  public abstract Access fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:51
   */
  public abstract Access treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:59
   */
  public abstract Access treeCopy();
  /**
   * Find the outermost qualified expression this access.
   * 
   * <p>If this is not a qualified access, then this access is returned.
   * 
   * <p>For example, if {@code unqualifiedScope()} is evaluated for the {@code
   * MethodAccess} inside the expression {@code Dot(FieldAccess,
   * Dot(FieldAccess, MethodAcceess))}, then the outermost {@code Dot} is the
   * unqualified scope of the {@code MethodAccess}.
   * @attribute syn
   * @aspect LookupMethod
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/LookupMethod.jrag:87
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupMethod", declaredAt="/home/hadjer/git/puck2/extendj/java4/frontend/LookupMethod.jrag:87")
  public Expr unqualifiedScope() {
    Expr unqualifiedScope_value = isQualified() ? nestedScope() : this;
    return unqualifiedScope_value;
  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:164
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="/home/hadjer/git/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:164")
  public boolean isQualified() {
    boolean isQualified_value = hasPrevExpr();
    return isQualified_value;
  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:167
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="/home/hadjer/git/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:167")
  public Expr qualifier() {
    Expr qualifier_value = prevExpr();
    return qualifier_value;
  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:174
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="/home/hadjer/git/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:174")
  public Access lastAccess() {
    Access lastAccess_value = this;
    return lastAccess_value;
  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:184
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="/home/hadjer/git/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:184")
  public boolean hasPrevExpr() {
    boolean hasPrevExpr_value = isRightChildOfDot();
    return hasPrevExpr_value;
  }
  /** @apilevel internal */
  private void type_reset() {
    type_computed = null;
    type_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle type_computed = null;

  /** @apilevel internal */
  protected TypeDecl type_value;

  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/TypeAnalysis.jrag:295
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="/home/hadjer/git/puck2/extendj/java4/frontend/TypeAnalysis.jrag:295")
  public TypeDecl type() {
    ASTState state = state();
    if (type_computed == ASTState.NON_CYCLE || type_computed == state().cycle()) {
      return type_value;
    }
    type_value = unknownType();
    if (state().inCircle()) {
      type_computed = state().cycle();
    
    } else {
      type_computed = ASTState.NON_CYCLE;
    
    }
    return type_value;
  }
  /**
   * Defines the expected kind of name for the left hand side in a qualified
   * expression.
   * @attribute syn
   * @aspect SyntacticClassification
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/SyntacticClassification.jrag:60
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SyntacticClassification", declaredAt="/home/hadjer/git/puck2/extendj/java4/frontend/SyntacticClassification.jrag:60")
  public NameType predNameType() {
    {
        switch (nameType()) {
          case PACKAGE_NAME:
            return NameType.PACKAGE_NAME;
          case TYPE_NAME:
          case PACKAGE_OR_TYPE_NAME:
            return NameType.PACKAGE_OR_TYPE_NAME;
          case AMBIGUOUS_NAME:
          case EXPRESSION_NAME:
            return NameType.AMBIGUOUS_NAME;
          case NOT_CLASSIFIED:
          default:
            return NameType.NOT_CLASSIFIED;
        }
      }
  }
  /**
   * WARNING: this attribute is not the same as TypeDecl.isWildcard,
   * which returns true for any wildcard type (even bounded wildcard types).
   * @return {@code true} if this is an unbounded wildcard access
   * @attribute syn
   * @aspect ReifiableTypes
   * @declaredat /home/hadjer/git/puck2/extendj/java5/frontend/ReifiableTypes.jrag:106
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ReifiableTypes", declaredAt="/home/hadjer/git/puck2/extendj/java5/frontend/ReifiableTypes.jrag:106")
  public boolean isWildcard() {
    boolean isWildcard_value = false;
    return isWildcard_value;
  }
  /**
   * Creates a copy of this access where parameterized types have been erased.
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat /home/hadjer/git/puck2/extendj/java5/frontend/Generics.jrag:1596
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/home/hadjer/git/puck2/extendj/java5/frontend/Generics.jrag:1596")
  public Access erasedCopy() {
    Access erasedCopy_value = treeCopyNoTransform();
    return erasedCopy_value;
  }
  /**
   * @attribute syn
   * @aspect Diamond
   * @declaredat /home/hadjer/git/puck2/extendj/java7/frontend/Diamond.jrag:99
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Diamond", declaredAt="/home/hadjer/git/puck2/extendj/java7/frontend/Diamond.jrag:99")
  public boolean isDiamond() {
    boolean isDiamond_value = false;
    return isDiamond_value;
  }
  /**
   * @attribute syn
   * @aspect LambdaParametersInference
   * @declaredat /home/hadjer/git/puck2/extendj/java8/frontend/TypeCheck.jrag:620
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LambdaParametersInference", declaredAt="/home/hadjer/git/puck2/extendj/java8/frontend/TypeCheck.jrag:620")
  public boolean mentionsTypeVariable(TypeVariable var) {
    boolean mentionsTypeVariable_TypeVariable_value = false;
    return mentionsTypeVariable_TypeVariable_value;
  }
  /**
   * @attribute inh
   * @aspect VariableScopePropagation
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/LookupVariable.jrag:355
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="VariableScopePropagation", declaredAt="/home/hadjer/git/puck2/extendj/java4/frontend/LookupVariable.jrag:355")
  public Variable unknownField() {
    Variable unknownField_value = getParent().Define_unknownField(this, null);
    return unknownField_value;
  }
  /**
   * Find the outermost qualified expression of a qualified access.
   * 
   * <p>It is an error if this attribute is evaluated on an unqualified
   * access.
   * 
   * @throws UnsupportedOperationException if evaluated on an unqualified
   * access.
   * @attribute inh
   * @aspect LookupMethod
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/LookupMethod.jrag:98
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LookupMethod", declaredAt="/home/hadjer/git/puck2/extendj/java4/frontend/LookupMethod.jrag:98")
  public Expr nestedScope() {
    Expr nestedScope_value = getParent().Define_nestedScope(this, null);
    return nestedScope_value;
  }
  /**
   * @attribute inh
   * @aspect TypeScopePropagation
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/LookupType.jrag:329
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="/home/hadjer/git/puck2/extendj/java4/frontend/LookupType.jrag:329")
  public TypeDecl unknownType() {
    TypeDecl unknownType_value = getParent().Define_unknownType(this, null);
    return unknownType_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat /home/hadjer/git/puck2/extendj/java5/frontend/Annotations.jrag:400
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/hadjer/git/puck2/extendj/java5/frontend/Annotations.jrag:400")
  public boolean withinSuppressWarnings(String annot) {
    boolean withinSuppressWarnings_String_value = getParent().Define_withinSuppressWarnings(this, null, annot);
    return withinSuppressWarnings_String_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat /home/hadjer/git/puck2/extendj/java5/frontend/Annotations.jrag:531
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/hadjer/git/puck2/extendj/java5/frontend/Annotations.jrag:531")
  public boolean withinDeprecatedAnnotation() {
    boolean withinDeprecatedAnnotation_value = getParent().Define_withinDeprecatedAnnotation(this, null);
    return withinDeprecatedAnnotation_value;
  }
  /**
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:86
   * @apilevel internal
   */
  public boolean Define_isLeftChildOfDot(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:86
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isLeftChildOfDot
   */
  protected boolean canDefine_isLeftChildOfDot(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:101
   * @apilevel internal
   */
  public boolean Define_isRightChildOfDot(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:101
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isRightChildOfDot
   */
  protected boolean canDefine_isRightChildOfDot(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:118
   * @apilevel internal
   */
  public Expr Define_prevExpr(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return prevExprError();
  }
  /**
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:118
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute prevExpr
   */
  protected boolean canDefine_prevExpr(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:142
   * @apilevel internal
   */
  public Access Define_nextAccess(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return nextAccessError();
  }
  /**
   * @declaredat /home/hadjer/git/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:142
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute nextAccess
   */
  protected boolean canDefine_nextAccess(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /** @apilevel internal */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
  /** @apilevel internal */
  public boolean canRewrite() {
    return false;
  }
}
