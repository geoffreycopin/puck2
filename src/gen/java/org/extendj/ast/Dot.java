/* This file was generated with JastAdd2 (http://jastadd.org) version 2.3.0 */
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
 * A qualified expression.
 * The left hand side qualifies the right hand side.
 * 
 * <p>Note: this type can represent more than a simple "dot"
 * expression. There can be an array access as the right hand side,
 * e.g., {@code left[3] }.
 * @ast node
 * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\grammar\\Java.ast:79
 * @astdecl Dot : Access ::= Left:Expr Right:Access;
 * @production Dot : {@link Access} ::= <span class="component">Left:{@link Expr}</span> <span class="component">Right:{@link Access}</span>;

 */
public class Dot extends Access implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\PrettyPrint.jadd:337
   */
  public void prettyPrint(PrettyPrinter out) {
    if (needsDot()) {
      out.print(getLeft());
      out.print(".");
      out.print(getRight());
    } else {
      out.print(getLeft());
      out.print(getRight());
    }
  }
  /**
   * @aspect PrettyPrintUtil
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\PrettyPrintUtil.jrag:143
   */
  @Override public String toString() {
    Expr left = getLeftNoTransform();
    Expr right = getRightNoTransform();
    if (!(right instanceof ArrayAccess)) {
      return String.format("%s.%s",
          left.toString(),
          right.toString());
    } else {
      ArrayAccess array = (ArrayAccess) right;
      return String.format("%s[%s]",
          left.toString(),
          array.getExprNoTransform().toString());

    }
  }
  /**
   * @aspect QualifiedNames
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:190
   */
  public Dot lastDot() {
    Dot node = this;
    while (node.getRightNoTransform() instanceof Dot) {
      node = (Dot) node.getRightNoTransform();
    }
    return node;
  }
  /**
   * @aspect QualifiedNames
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:208
   */
  public Access qualifiesAccess(Access access) {
    Dot lastDot = lastDot();
    Expr last = lastDot.getRightNoTransform();
    Access qualified = last.qualifiesAccess(access);
    qualified.setEnd(access.getEnd());
    lastDot.setRight(qualified);
    return this;
  }
  /**
   * Used when replacing pairs from a list to concatenate the result to the
   * tail of the current location.
   * @aspect QualifiedNames
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:235
   */
  private Access qualifyTailWith(Access expr) {
    if (getRight() instanceof Dot) {
      Dot dot = (Dot) getRight();
      return expr.qualifiesAccess(dot.getRight().treeCopyNoTransform());
    }
    return expr;
  }
  /**
   * @declaredat ASTNode:1
   */
  public Dot() {
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
    children = new ASTNode[2];
  }
  /**
   * @declaredat ASTNode:13
   */
  @ASTNodeAnnotation.Constructor(
    name = {"Left", "Right"},
    type = {"Expr", "Access"},
    kind = {"Child", "Child"}
  )
  public Dot(Expr p0, Access p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:23
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:29
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:33
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    unassignedAfterTrue_Variable_reset();
    unassignedAfterFalse_Variable_reset();
    unassignedAfter_Variable_reset();
    type_reset();
    stmtCompatible_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:42
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:46
   */
  public Dot clone() throws CloneNotSupportedException {
    Dot node = (Dot) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:51
   */
  public Dot copy() {
    try {
      Dot node = (Dot) clone();
      node.parent = null;
      if (children != null) {
        node.children = (ASTNode[]) children.clone();
      }
      return node;
    } catch (CloneNotSupportedException e) {
      throw new Error("Error: clone not supported for " + getClass().getName());
    }
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:70
   */
  @Deprecated
  public Dot fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:80
   */
  public Dot treeCopyNoTransform() {
    Dot tree = (Dot) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        ASTNode child = (ASTNode) children[i];
        if (child != null) {
          child = child.treeCopyNoTransform();
          tree.setChild(child, i);
        }
      }
    }
    return tree;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:100
   */
  public Dot treeCopy() {
    Dot tree = (Dot) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        ASTNode child = (ASTNode) getChild(i);
        if (child != null) {
          child = child.treeCopy();
          tree.setChild(child, i);
        }
      }
    }
    return tree;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:114
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Left child.
   * @param node The new node to replace the Left child.
   * @apilevel high-level
   */
  public void setLeft(Expr node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Left child.
   * @return The current node used as the Left child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Left")
  public Expr getLeft() {
    return (Expr) getChild(0);
  }
  /**
   * Retrieves the Left child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Left child.
   * @apilevel low-level
   */
  public Expr getLeftNoTransform() {
    return (Expr) getChildNoTransform(0);
  }
  /**
   * Replaces the Right child.
   * @param node The new node to replace the Right child.
   * @apilevel high-level
   */
  public void setRight(Access node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the Right child.
   * @return The current node used as the Right child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Right")
  public Access getRight() {
    return (Access) getChild(1);
  }
  /**
   * Retrieves the Right child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Right child.
   * @apilevel low-level
   */
  public Access getRightNoTransform() {
    return (Access) getChildNoTransform(1);
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ConstantExpression.jrag:32
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ConstantExpression.jrag:32")
  public Constant constant() {
    Constant constant_value = lastAccess().constant();
    return constant_value;
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ConstantExpression.jrag:383
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ConstantExpression.jrag:383")
  public boolean isConstant() {
    boolean isConstant_value = lastAccess().isConstant();
    return isConstant_value;
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:77
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:77")
  public Variable varDecl() {
    Variable varDecl_value = lastAccess().varDecl();
    return varDecl_value;
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:375
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:375")
  public boolean assignedAfterTrue(Variable v) {
    boolean assignedAfterTrue_Variable_value = assignedAfter(v);
    return assignedAfterTrue_Variable_value;
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:377
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:377")
  public boolean assignedAfterFalse(Variable v) {
    boolean assignedAfterFalse_Variable_value = assignedAfter(v);
    return assignedAfterFalse_Variable_value;
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:268
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:268")
  public boolean assignedAfter(Variable v) {
    boolean assignedAfter_Variable_value = lastAccess().assignedAfter(v);
    return assignedAfter_Variable_value;
  }
  /** @apilevel internal */
  private void unassignedAfterTrue_Variable_reset() {
    unassignedAfterTrue_Variable_values = null;
  }
  protected java.util.Map unassignedAfterTrue_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:905")
  public boolean unassignedAfterTrue(Variable v) {
    Object _parameters = v;
    if (unassignedAfterTrue_Variable_values == null) unassignedAfterTrue_Variable_values = new java.util.HashMap(4);
    ASTState.CircularValue _value;
    if (unassignedAfterTrue_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfterTrue_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTState.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTState.CircularValue) _cache;
      }
    } else {
      _value = new ASTState.CircularValue();
      unassignedAfterTrue_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTState state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfterTrue_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfterTrue_Variable_value = unassignedAfter(v);
        if (((Boolean)_value.value) != new_unassignedAfterTrue_Variable_value) {
          state.setChangeInCycle();
          _value.value = new_unassignedAfterTrue_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedAfterTrue_Variable_values.put(_parameters, new_unassignedAfterTrue_Variable_value);

      state.leaveCircle();
      return new_unassignedAfterTrue_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedAfterTrue_Variable_value = unassignedAfter(v);
      if (((Boolean)_value.value) != new_unassignedAfterTrue_Variable_value) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfterTrue_Variable_value;
      }
      return new_unassignedAfterTrue_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private void unassignedAfterFalse_Variable_reset() {
    unassignedAfterFalse_Variable_values = null;
  }
  protected java.util.Map unassignedAfterFalse_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:907")
  public boolean unassignedAfterFalse(Variable v) {
    Object _parameters = v;
    if (unassignedAfterFalse_Variable_values == null) unassignedAfterFalse_Variable_values = new java.util.HashMap(4);
    ASTState.CircularValue _value;
    if (unassignedAfterFalse_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfterFalse_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTState.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTState.CircularValue) _cache;
      }
    } else {
      _value = new ASTState.CircularValue();
      unassignedAfterFalse_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTState state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfterFalse_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfterFalse_Variable_value = unassignedAfter(v);
        if (((Boolean)_value.value) != new_unassignedAfterFalse_Variable_value) {
          state.setChangeInCycle();
          _value.value = new_unassignedAfterFalse_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedAfterFalse_Variable_values.put(_parameters, new_unassignedAfterFalse_Variable_value);

      state.leaveCircle();
      return new_unassignedAfterFalse_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedAfterFalse_Variable_value = unassignedAfter(v);
      if (((Boolean)_value.value) != new_unassignedAfterFalse_Variable_value) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfterFalse_Variable_value;
      }
      return new_unassignedAfterFalse_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private void unassignedAfter_Variable_reset() {
    unassignedAfter_Variable_values = null;
  }
  protected java.util.Map unassignedAfter_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:899")
  public boolean unassignedAfter(Variable v) {
    Object _parameters = v;
    if (unassignedAfter_Variable_values == null) unassignedAfter_Variable_values = new java.util.HashMap(4);
    ASTState.CircularValue _value;
    if (unassignedAfter_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfter_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTState.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTState.CircularValue) _cache;
      }
    } else {
      _value = new ASTState.CircularValue();
      unassignedAfter_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTState state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfter_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfter_Variable_value = lastAccess().unassignedAfter(v);
        if (((Boolean)_value.value) != new_unassignedAfter_Variable_value) {
          state.setChangeInCycle();
          _value.value = new_unassignedAfter_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedAfter_Variable_values.put(_parameters, new_unassignedAfter_Variable_value);

      state.leaveCircle();
      return new_unassignedAfter_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedAfter_Variable_value = lastAccess().unassignedAfter(v);
      if (((Boolean)_value.value) != new_unassignedAfter_Variable_value) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfter_Variable_value;
      }
      return new_unassignedAfter_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /**
   * Pretty-printed qualified expression needs a dot unless the right
   * hand side is an array access.
   * @attribute syn
   * @aspect PrettyPrintUtil
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\PrettyPrintUtil.jrag:342
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PrettyPrintUtil", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\PrettyPrintUtil.jrag:342")
  public boolean needsDot() {
    boolean needsDot_value = !(rightSide() instanceof ArrayAccess);
    return needsDot_value;
  }
  /**
   * @attribute syn
   * @aspect Names
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\QualifiedNames.jrag:73
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Names", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\QualifiedNames.jrag:73")
  public String typeName() {
    String typeName_value = lastAccess().typeName();
    return typeName_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:35
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:35")
  public boolean isTypeAccess() {
    boolean isTypeAccess_value = getRight().isTypeAccess();
    return isTypeAccess_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:47
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:47")
  public boolean isMethodAccess() {
    boolean isMethodAccess_value = getRight().isMethodAccess();
    return isMethodAccess_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:51
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:51")
  public boolean isFieldAccess() {
    boolean isFieldAccess_value = getRight().isFieldAccess();
    return isFieldAccess_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:56
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:56")
  public boolean isSuperAccess() {
    boolean isSuperAccess_value = getRight().isSuperAccess();
    return isSuperAccess_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:62
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:62")
  public boolean isThisAccess() {
    boolean isThisAccess_value = getRight().isThisAccess();
    return isThisAccess_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:68
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:68")
  public boolean isPackageAccess() {
    boolean isPackageAccess_value = getRight().isPackageAccess();
    return isPackageAccess_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:72
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:72")
  public boolean isArrayAccess() {
    boolean isArrayAccess_value = getRight().isArrayAccess();
    return isArrayAccess_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:76
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:76")
  public boolean isClassAccess() {
    boolean isClassAccess_value = getRight().isClassAccess();
    return isClassAccess_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:80
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:80")
  public boolean isSuperConstructorAccess() {
    boolean isSuperConstructorAccess_value = getRight().isSuperConstructorAccess();
    return isSuperConstructorAccess_value;
  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:164
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:164")
  public boolean isQualified() {
    boolean isQualified_value = hasParentDot();
    return isQualified_value;
  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:169
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:169")
  public Expr leftSide() {
    Expr leftSide_value = getLeft();
    return leftSide_value;
  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:171
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:171")
  public Access rightSide() {
    Access rightSide_value = getRight() instanceof Dot ?
        (Access)((Dot) getRight()).getLeft() : (Access) getRight();
    return rightSide_value;
  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:174
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:174")
  public Access lastAccess() {
    Access lastAccess_value = getRight().lastAccess();
    return lastAccess_value;
  }
  /**
   * Defines the expected kind of name for the left hand side in a qualified
   * expression.
   * @attribute syn
   * @aspect SyntacticClassification
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\SyntacticClassification.jrag:60
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SyntacticClassification", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\SyntacticClassification.jrag:60")
  public NameType predNameType() {
    NameType predNameType_value = getLeft() instanceof Access
          ? ((Access) getLeft()).predNameType()
          : NameType.NOT_CLASSIFIED;
    return predNameType_value;
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
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\TypeAnalysis.jrag:295
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\TypeAnalysis.jrag:295")
  public TypeDecl type() {
    ASTState state = state();
    if (type_computed == ASTState.NON_CYCLE || type_computed == state().cycle()) {
      return type_value;
    }
    type_value = lastAccess().type();
    if (state().inCircle()) {
      type_computed = state().cycle();
    
    } else {
      type_computed = ASTState.NON_CYCLE;
    
    }
    return type_value;
  }
  /**
   * @attribute syn
   * @aspect TypeCheck
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\TypeCheck.jrag:33
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\TypeCheck.jrag:33")
  public boolean isVariable() {
    boolean isVariable_value = lastAccess().isVariable();
    return isVariable_value;
  }
  /**
   * @attribute syn
   * @aspect TypeHierarchyCheck
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:224
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:224")
  public boolean staticContextQualifier() {
    boolean staticContextQualifier_value = lastAccess().staticContextQualifier();
    return staticContextQualifier_value;
  }
  /**
   * Creates a copy of this access where parameterized types have been erased.
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java5\\frontend\\Generics.jrag:1596
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java5\\frontend\\Generics.jrag:1596")
  public Access erasedCopy() {
    Access erasedCopy_value = new Dot(getLeft().erasedCopy(), getRight().erasedCopy());
    return erasedCopy_value;
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java7\\frontend\\PreciseRethrow.jrag:145
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java7\\frontend\\PreciseRethrow.jrag:145")
  public boolean modifiedInScope(Variable var) {
    boolean modifiedInScope_Variable_value = getLeft().modifiedInScope(var) || getRight().modifiedInScope(var);
    return modifiedInScope_Variable_value;
  }
  /** @apilevel internal */
  private void stmtCompatible_reset() {
    stmtCompatible_computed = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle stmtCompatible_computed = null;

  /** @apilevel internal */
  protected boolean stmtCompatible_value;

  /**
   * @attribute syn
   * @aspect StmtCompatible
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\LambdaExpr.jrag:154
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StmtCompatible", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\LambdaExpr.jrag:154")
  public boolean stmtCompatible() {
    ASTState state = state();
    if (stmtCompatible_computed == ASTState.NON_CYCLE || stmtCompatible_computed == state().cycle()) {
      return stmtCompatible_value;
    }
    stmtCompatible_value = getRight().stmtCompatible();
    if (state().inCircle()) {
      stmtCompatible_computed = state().cycle();
    
    } else {
      stmtCompatible_computed = ASTState.NON_CYCLE;
    
    }
    return stmtCompatible_value;
  }
  /**
   * @attribute inh
   * @aspect DefiniteUnassignment
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:903
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:903")
  public boolean unassignedBefore(Variable v) {
    boolean unassignedBefore_Variable_value = getParent().Define_unassignedBefore(this, null, v);
    return unassignedBefore_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect NameResolution
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:540
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NameResolution", declaredAt="C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:540")
  public boolean canResolve() {
    boolean canResolve_value = getParent().Define_canResolve(this, null);
    return canResolve_value;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:34
   * @apilevel internal
   */
  public boolean Define_isDest(ASTNode _callerNode, ASTNode _childNode) {
    if (getLeftNoTransform() != null && _callerNode == getLeft()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:40
      return false;
    }
    else {
      return getParent().Define_isDest(this, _callerNode);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:34
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isDest
   */
  protected boolean canDefine_isDest(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:44
   * @apilevel internal
   */
  public boolean Define_isSource(ASTNode _callerNode, ASTNode _childNode) {
    if (getLeftNoTransform() != null && _callerNode == getLeft()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:50
      return true;
    }
    else {
      return getParent().Define_isSource(this, _callerNode);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:44
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isSource
   */
  protected boolean canDefine_isSource(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:256
   * @apilevel internal
   */
  public boolean Define_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    if (getRightNoTransform() != null && _callerNode == getRight()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:400
      return getLeft().assignedAfter(v);
    }
    else {
      return getParent().Define_assignedBefore(this, _callerNode, v);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:256
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute assignedBefore
   */
  protected boolean canDefine_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:887
   * @apilevel internal
   */
  public boolean Define_unassignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    if (getRightNoTransform() != null && _callerNode == getRight()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1088
      return getLeft().unassignedAfter(v);
    }
    else {
      return getParent().Define_unassignedBefore(this, _callerNode, v);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:887
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute unassignedBefore
   */
  protected boolean canDefine_unassignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\LookupConstructor.jrag:35
   * @apilevel internal
   */
  public Collection<ConstructorDecl> Define_lookupConstructor(ASTNode _callerNode, ASTNode _childNode) {
    if (getRightNoTransform() != null && _callerNode == getRight()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\LookupConstructor.jrag:41
      return getLeft().type().constructors();
    }
    else {
      return getParent().Define_lookupConstructor(this, _callerNode);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\LookupConstructor.jrag:35
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupConstructor
   */
  protected boolean canDefine_lookupConstructor(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\LookupConstructor.jrag:43
   * @apilevel internal
   */
  public Collection<ConstructorDecl> Define_lookupSuperConstructor(ASTNode _callerNode, ASTNode _childNode) {
    if (getRightNoTransform() != null && _callerNode == getRight()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\LookupConstructor.jrag:56
      return getLeft().type().lookupSuperConstructor();
    }
    else {
      return getParent().Define_lookupSuperConstructor(this, _callerNode);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\LookupConstructor.jrag:43
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupSuperConstructor
   */
  protected boolean canDefine_lookupSuperConstructor(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\LookupMethod.jrag:98
   * @apilevel internal
   */
  public Expr Define_nestedScope(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return isQualified() ? nestedScope() : this;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\LookupMethod.jrag:98
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute nestedScope
   */
  protected boolean canDefine_nestedScope(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\LookupMethod.jrag:116
   * @apilevel internal
   */
  public Collection<MethodDecl> Define_lookupMethod(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (getRightNoTransform() != null && _callerNode == getRight()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\LookupMethod.jrag:194
      return getLeft().type().memberMethods(name);
    }
    else {
      return getParent().Define_lookupMethod(this, _callerNode, name);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\LookupMethod.jrag:116
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupMethod
   */
  protected boolean canDefine_lookupMethod(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\LookupType.jrag:113
   * @apilevel internal
   */
  public boolean Define_hasPackage(ASTNode _callerNode, ASTNode _childNode, String packageName) {
    if (getRightNoTransform() != null && _callerNode == getRight()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\LookupType.jrag:108
      return getLeft().hasQualifiedPackage(packageName);
    }
    else {
      return getParent().Define_hasPackage(this, _callerNode, packageName);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\LookupType.jrag:113
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute hasPackage
   */
  protected boolean canDefine_hasPackage(ASTNode _callerNode, ASTNode _childNode, String packageName) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java5\\frontend\\GenericMethods.jrag:231
   * @apilevel internal
   */
  public SimpleSet<TypeDecl> Define_lookupType(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (getRightNoTransform() != null && _callerNode == getRight()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\LookupType.jrag:610
      return getLeft().qualifiedLookupType(name);
    }
    else {
      return getParent().Define_lookupType(this, _callerNode, name);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java5\\frontend\\GenericMethods.jrag:231
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupType
   */
  protected boolean canDefine_lookupType(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet<Variable> Define_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (getRightNoTransform() != null && _callerNode == getRight()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\LookupVariable.jrag:257
      return getLeft().qualifiedLookupVariable(name);
    }
    else {
      return getParent().Define_lookupVariable(this, _callerNode, name);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\LookupVariable.jrag:30
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupVariable
   */
  protected boolean canDefine_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:86
   * @apilevel internal
   */
  public boolean Define_isLeftChildOfDot(ASTNode _callerNode, ASTNode _childNode) {
    if (getRightNoTransform() != null && _callerNode == getRight()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:90
      return false;
    }
    else if (getLeftNoTransform() != null && _callerNode == getLeft()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:89
      return true;
    }
    else {
      return super.Define_isLeftChildOfDot(_callerNode, _childNode);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:86
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isLeftChildOfDot
   */
  protected boolean canDefine_isLeftChildOfDot(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:101
   * @apilevel internal
   */
  public boolean Define_isRightChildOfDot(ASTNode _callerNode, ASTNode _childNode) {
    if (getRightNoTransform() != null && _callerNode == getRight()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:105
      return true;
    }
    else if (getLeftNoTransform() != null && _callerNode == getLeft()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:104
      return isRightChildOfDot();
    }
    else {
      return super.Define_isRightChildOfDot(_callerNode, _childNode);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:101
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isRightChildOfDot
   */
  protected boolean canDefine_isRightChildOfDot(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:118
   * @apilevel internal
   */
  public Expr Define_prevExpr(ASTNode _callerNode, ASTNode _childNode) {
    if (getRightNoTransform() != null && _callerNode == getRight()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:121
      return getLeft();
    }
    else if (getLeftNoTransform() != null && _callerNode == getLeft()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:120
      return prevExpr();
    }
    else {
      return super.Define_prevExpr(_callerNode, _childNode);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:118
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute prevExpr
   */
  protected boolean canDefine_prevExpr(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:142
   * @apilevel internal
   */
  public Access Define_nextAccess(ASTNode _callerNode, ASTNode _childNode) {
    if (getRightNoTransform() != null && _callerNode == getRight()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:145
      return nextAccessError();
    }
    else if (getLeftNoTransform() != null && _callerNode == getLeft()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:144
      return getRight();
    }
    else {
      return super.Define_nextAccess(_callerNode, _childNode);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:142
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute nextAccess
   */
  protected boolean canDefine_nextAccess(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:539
   * @apilevel internal
   */
  public boolean Define_canResolve(ASTNode _callerNode, ASTNode _childNode) {
    if (getRightNoTransform() != null && _callerNode == getRight()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:549
      return !getLeft().containsParseName() && canResolve();
    }
    else {
      return getParent().Define_canResolve(this, _callerNode);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:539
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute canResolve
   */
  protected boolean canDefine_canResolve(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode _callerNode, ASTNode _childNode) {
    if (getLeftNoTransform() != null && _callerNode == getLeft()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\SyntacticClassification.jrag:81
      return getRightNoTransform().predNameType();
    }
    else {
      return getParent().Define_nameType(this, _callerNode);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute nameType
   */
  protected boolean canDefine_nameType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\TypeCheck.jrag:667
   * @apilevel internal
   */
  public TypeDecl Define_enclosingInstance(ASTNode _callerNode, ASTNode _childNode) {
    if (getRightNoTransform() != null && _callerNode == getRight()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\TypeCheck.jrag:684
      return getLeft().type();
    }
    else {
      return getParent().Define_enclosingInstance(this, _callerNode);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\TypeCheck.jrag:667
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute enclosingInstance
   */
  protected boolean canDefine_enclosingInstance(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:33
   * @apilevel internal
   */
  public String Define_methodHost(ASTNode _callerNode, ASTNode _childNode) {
    if (getRightNoTransform() != null && _callerNode == getRight()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:37
      return getLeft().type().typeName();
    }
    else {
      return getParent().Define_methodHost(this, _callerNode);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:33
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute methodHost
   */
  protected boolean canDefine_methodHost(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\TargetType.jrag:31
   * @apilevel internal
   */
  public TypeDecl Define_targetType(ASTNode _callerNode, ASTNode _childNode) {
    if (getRightNoTransform() != null && _callerNode == getRight()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\TargetType.jrag:37
      return targetType();
    }
    else {
      return getParent().Define_targetType(this, _callerNode);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\TargetType.jrag:31
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute targetType
   */
  protected boolean canDefine_targetType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\TargetType.jrag:235
   * @apilevel internal
   */
  public boolean Define_assignmentContext(ASTNode _callerNode, ASTNode _childNode) {
    if (getLeftNoTransform() != null && _callerNode == getLeft()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\TargetType.jrag:319
      return false;
    }
    else {
      return getParent().Define_assignmentContext(this, _callerNode);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\TargetType.jrag:235
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute assignmentContext
   */
  protected boolean canDefine_assignmentContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\TargetType.jrag:236
   * @apilevel internal
   */
  public boolean Define_invocationContext(ASTNode _callerNode, ASTNode _childNode) {
    if (getLeftNoTransform() != null && _callerNode == getLeft()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\TargetType.jrag:320
      return false;
    }
    else {
      return getParent().Define_invocationContext(this, _callerNode);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\TargetType.jrag:236
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute invocationContext
   */
  protected boolean canDefine_invocationContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\TargetType.jrag:237
   * @apilevel internal
   */
  public boolean Define_castContext(ASTNode _callerNode, ASTNode _childNode) {
    if (getLeftNoTransform() != null && _callerNode == getLeft()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\TargetType.jrag:321
      return false;
    }
    else {
      return getParent().Define_castContext(this, _callerNode);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\TargetType.jrag:237
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute castContext
   */
  protected boolean canDefine_castContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\TargetType.jrag:238
   * @apilevel internal
   */
  public boolean Define_stringContext(ASTNode _callerNode, ASTNode _childNode) {
    if (getLeftNoTransform() != null && _callerNode == getLeft()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\TargetType.jrag:322
      return false;
    }
    else {
      return getParent().Define_stringContext(this, _callerNode);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\TargetType.jrag:238
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute stringContext
   */
  protected boolean canDefine_stringContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\TargetType.jrag:239
   * @apilevel internal
   */
  public boolean Define_numericContext(ASTNode _callerNode, ASTNode _childNode) {
    if (getLeftNoTransform() != null && _callerNode == getLeft()) {
      // @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\TargetType.jrag:323
      return false;
    }
    else {
      return getParent().Define_numericContext(this, _callerNode);
    }
  }
  /**
   * @declaredat C:\\Users\\amdja\\git\\puck2-develp\\extendj\\java8\\frontend\\TargetType.jrag:239
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute numericContext
   */
  protected boolean canDefine_numericContext(ASTNode _callerNode, ASTNode _childNode) {
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
