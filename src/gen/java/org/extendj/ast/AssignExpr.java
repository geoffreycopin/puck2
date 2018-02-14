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
import java.util.zip.*;
import java.io.*;
import org.jastadd.util.*;
import java.util.LinkedHashSet;
import org.jastadd.util.PrettyPrintable;
import org.jastadd.util.PrettyPrinter;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
/**
 * @ast node
 * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/grammar/Java.ast:196
 * @astdecl AssignExpr : Expr ::= Dest:Expr Source:Expr;
 * @production AssignExpr : {@link Expr} ::= <span class="component">Dest:{@link Expr}</span> <span class="component">Source:{@link Expr}</span>;

 */
public abstract class AssignExpr extends Expr implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/PrettyPrint.jadd:81
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getDest());
    out.print(" ");
    out.print(printOp());
    out.print(" ");
    out.print(getSource());
  }
  /**
   * @aspect NodeConstructors
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/NodeConstructors.jrag:96
   */
  public static Stmt asStmt(Expr left, Expr right) {
    return new ExprStmt(new AssignSimpleExpr(left, right));
  }
  /**
   * @aspect DefiniteAssignment
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:587
   */
  protected boolean checkDUeverywhere(Variable v) {
    if (getDest().isVariable() && getDest().varDecl() == v) {
      if (!getSource().assignedAfter(v)) {
        return false;
      }
    }
    return super.checkDUeverywhere(v);
  }
  /**
   * @declaredat ASTNode:1
   */
  public AssignExpr() {
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
    name = {"Dest", "Source"},
    type = {"Expr", "Expr"},
    kind = {"Child", "Child"}
  )
  public AssignExpr(Expr p0, Expr p1) {
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
    unassignedAfter_Variable_reset();
    unassignedAfterTrue_Variable_reset();
    unassignedAfterFalse_Variable_reset();
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
  public AssignExpr clone() throws CloneNotSupportedException {
    AssignExpr node = (AssignExpr) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:57
   */
  @Deprecated
  public abstract AssignExpr fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:65
   */
  public abstract AssignExpr treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:73
   */
  public abstract AssignExpr treeCopy();
  /**
   * Replaces the Dest child.
   * @param node The new node to replace the Dest child.
   * @apilevel high-level
   */
  public void setDest(Expr node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Dest child.
   * @return The current node used as the Dest child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Dest")
  public Expr getDest() {
    return (Expr) getChild(0);
  }
  /**
   * Retrieves the Dest child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Dest child.
   * @apilevel low-level
   */
  public Expr getDestNoTransform() {
    return (Expr) getChildNoTransform(0);
  }
  /**
   * Replaces the Source child.
   * @param node The new node to replace the Source child.
   * @apilevel high-level
   */
  public void setSource(Expr node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the Source child.
   * @return The current node used as the Source child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Source")
  public Expr getSource() {
    return (Expr) getChild(1);
  }
  /**
   * Retrieves the Source child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Source child.
   * @apilevel low-level
   */
  public Expr getSourceNoTransform() {
    return (Expr) getChildNoTransform(1);
  }
  /** The operator string used for pretty printing this expression. 
   * @attribute syn
   * @aspect PrettyPrintUtil
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/PrettyPrintUtil.jrag:367
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PrettyPrintUtil", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/PrettyPrintUtil.jrag:367")
  public abstract String printOp();
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:268
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:268")
  public boolean assignedAfter(Variable v) {
    boolean assignedAfter_Variable_value = (getDest().isVariable() && getDest().varDecl() == v) || getSource().assignedAfter(v);
    return assignedAfter_Variable_value;
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:375
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:375")
  public boolean assignedAfterTrue(Variable v) {
    boolean assignedAfterTrue_Variable_value = isFalse() || assignedAfter(v);
    return assignedAfterTrue_Variable_value;
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:377
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:377")
  public boolean assignedAfterFalse(Variable v) {
    boolean assignedAfterFalse_Variable_value = isTrue() || assignedAfter(v);
    return assignedAfterFalse_Variable_value;
  }
  /** @apilevel internal */
  private void unassignedAfter_Variable_reset() {
    unassignedAfter_Variable_values = null;
  }
  protected java.util.Map unassignedAfter_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:899")
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
        new_unassignedAfter_Variable_value = unassignedAfter_compute(v);
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
      boolean new_unassignedAfter_Variable_value = unassignedAfter_compute(v);
      if (((Boolean)_value.value) != new_unassignedAfter_Variable_value) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfter_Variable_value;
      }
      return new_unassignedAfter_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private boolean unassignedAfter_compute(Variable v) {
      if (getDest().isVariable() && getDest().varDecl() == v) {
        return false;
      } else {
        return getSource().unassignedAfter(v);
      }
    }
  /** @apilevel internal */
  private void unassignedAfterTrue_Variable_reset() {
    unassignedAfterTrue_Variable_values = null;
  }
  protected java.util.Map unassignedAfterTrue_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:905")
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
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:907")
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
  /**
   * @attribute syn
   * @aspect TypeCheck
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/TypeCheck.jrag:77
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/TypeCheck.jrag:77")
  public Collection<Problem> typeProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        if (!getDest().isVariable()) {
          return Collections.singletonList((error("left hand side is not a variable")));
        } else {
          TypeDecl source = getSource().type();
          TypeDecl dest = getDest().type();
          if (getSource().type().isPrimitive() && getDest().type().isPrimitive()) {
            return Collections.emptyList();
          }
          return Collections.singletonList(errorf("can not assign %s of type %s a value of type %s",
              getDest().prettyPrint(), getDest().type().typeName(), getSource().type().typeName()));
        }
      }
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
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/TypeAnalysis.jrag:295
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/TypeAnalysis.jrag:295")
  public TypeDecl type() {
    ASTState state = state();
    if (type_computed == ASTState.NON_CYCLE || type_computed == state().cycle()) {
      return type_value;
    }
    type_value = getDest().type();
    if (state().inCircle()) {
      type_computed = state().cycle();
    
    } else {
      type_computed = ASTState.NON_CYCLE;
    
    }
    return type_value;
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java7/frontend/PreciseRethrow.jrag:145
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java7/frontend/PreciseRethrow.jrag:145")
  public boolean modifiedInScope(Variable var) {
    {
        boolean isLeft = getDest().isVariable(var);
        if (isLeft && var instanceof VariableDeclarator) {
          VariableDeclarator decl = (VariableDeclarator) var;
          if (!decl.hasInit()) {
            // Variable is being written to in an inner class.
            if (decl.hostType() != hostType()) {
              return true;
            }
            // 4.12.4;
            return !getSource().unassignedAfter(var);
          }
          return true;
        } else {
          return isLeft || getSource().modifiedInScope(var);
        }
      }
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
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/LambdaExpr.jrag:148
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StmtCompatible", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/LambdaExpr.jrag:148")
  public boolean stmtCompatible() {
    ASTState state = state();
    if (stmtCompatible_computed == ASTState.NON_CYCLE || stmtCompatible_computed == state().cycle()) {
      return stmtCompatible_value;
    }
    stmtCompatible_value = true;
    if (state().inCircle()) {
      stmtCompatible_computed = state().cycle();
    
    } else {
      stmtCompatible_computed = ASTState.NON_CYCLE;
    
    }
    return stmtCompatible_value;
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:34
   * @apilevel internal
   */
  public boolean Define_isDest(ASTNode _callerNode, ASTNode _childNode) {
    if (getSourceNoTransform() != null && _callerNode == getSource()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:38
      return false;
    }
    else if (getDestNoTransform() != null && _callerNode == getDest()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:37
      return true;
    }
    else {
      return getParent().Define_isDest(this, _callerNode);
    }
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:34
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isDest
   */
  protected boolean canDefine_isDest(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:44
   * @apilevel internal
   */
  public boolean Define_isSource(ASTNode _callerNode, ASTNode _childNode) {
    if (getSourceNoTransform() != null && _callerNode == getSource()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:48
      return true;
    }
    else if (getDestNoTransform() != null && _callerNode == getDest()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:47
      return true;
    }
    else {
      return getParent().Define_isSource(this, _callerNode);
    }
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:44
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isSource
   */
  protected boolean canDefine_isSource(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:256
   * @apilevel internal
   */
  public boolean Define_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    if (getDestNoTransform() != null && _callerNode == getDest()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:476
      return assignedBefore(v);
    }
    else if (getSourceNoTransform() != null && _callerNode == getSource()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:474
      return getDest().assignedAfter(v);
    }
    else {
      return getParent().Define_assignedBefore(this, _callerNode, v);
    }
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:256
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute assignedBefore
   */
  protected boolean canDefine_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    return true;
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:887
   * @apilevel internal
   */
  public boolean Define_unassignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    if (getDestNoTransform() != null && _callerNode == getDest()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:1080
      return unassignedBefore(v);
    }
    else if (getSourceNoTransform() != null && _callerNode == getSource()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:1078
      return getDest().unassignedAfter(v);
    }
    else {
      return getParent().Define_unassignedBefore(this, _callerNode, v);
    }
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:887
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute unassignedBefore
   */
  protected boolean canDefine_unassignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    return true;
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode _callerNode, ASTNode _childNode) {
    if (getDestNoTransform() != null && _callerNode == getDest()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/SyntacticClassification.jrag:122
      return NameType.EXPRESSION_NAME;
    }
    else {
      return getParent().Define_nameType(this, _callerNode);
    }
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/SyntacticClassification.jrag:36
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute nameType
   */
  protected boolean canDefine_nameType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:31
   * @apilevel internal
   */
  public TypeDecl Define_targetType(ASTNode _callerNode, ASTNode _childNode) {
    if (getSourceNoTransform() != null && _callerNode == getSource()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:39
      return getDest().type();
    }
    else {
      return getParent().Define_targetType(this, _callerNode);
    }
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:31
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute targetType
   */
  protected boolean canDefine_targetType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:235
   * @apilevel internal
   */
  public boolean Define_assignmentContext(ASTNode _callerNode, ASTNode _childNode) {
    if (getSourceNoTransform() != null && _callerNode == getSource()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:338
      return true;
    }
    else if (getDestNoTransform() != null && _callerNode == getDest()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:332
      return false;
    }
    else {
      return getParent().Define_assignmentContext(this, _callerNode);
    }
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:235
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute assignmentContext
   */
  protected boolean canDefine_assignmentContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:236
   * @apilevel internal
   */
  public boolean Define_invocationContext(ASTNode _callerNode, ASTNode _childNode) {
    if (getSourceNoTransform() != null && _callerNode == getSource()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:339
      return false;
    }
    else if (getDestNoTransform() != null && _callerNode == getDest()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:333
      return false;
    }
    else {
      return getParent().Define_invocationContext(this, _callerNode);
    }
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:236
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute invocationContext
   */
  protected boolean canDefine_invocationContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:237
   * @apilevel internal
   */
  public boolean Define_castContext(ASTNode _callerNode, ASTNode _childNode) {
    if (getSourceNoTransform() != null && _callerNode == getSource()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:340
      return false;
    }
    else if (getDestNoTransform() != null && _callerNode == getDest()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:334
      return false;
    }
    else {
      return getParent().Define_castContext(this, _callerNode);
    }
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:237
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute castContext
   */
  protected boolean canDefine_castContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:239
   * @apilevel internal
   */
  public boolean Define_numericContext(ASTNode _callerNode, ASTNode _childNode) {
    if (getSourceNoTransform() != null && _callerNode == getSource()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:342
      return false;
    }
    else if (getDestNoTransform() != null && _callerNode == getDest()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:335
      return false;
    }
    else {
      return getParent().Define_numericContext(this, _callerNode);
    }
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:239
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute numericContext
   */
  protected boolean canDefine_numericContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:238
   * @apilevel internal
   */
  public boolean Define_stringContext(ASTNode _callerNode, ASTNode _childNode) {
    if (getSourceNoTransform() != null && _callerNode == getSource()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:341
      return false;
    }
    else if (getDestNoTransform() != null && _callerNode == getDest()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:336
      return false;
    }
    else {
      return getParent().Define_stringContext(this, _callerNode);
    }
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:238
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute stringContext
   */
  protected boolean canDefine_stringContext(ASTNode _callerNode, ASTNode _childNode) {
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
  /** @apilevel internal */
  protected void collect_contributors_CompilationUnit_problems(CompilationUnit _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/TypeCheck.jrag:75
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    super.collect_contributors_CompilationUnit_problems(_root, _map);
  }
  /** @apilevel internal */
  protected void contributeTo_CompilationUnit_problems(LinkedList<Problem> collection) {
    super.contributeTo_CompilationUnit_problems(collection);
    for (Problem value : typeProblems()) {
      collection.add(value);
    }
  }
}
