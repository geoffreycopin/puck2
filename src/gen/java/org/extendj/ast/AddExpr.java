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
 * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/grammar/Java.ast:248
 * @astdecl AddExpr : AdditiveExpr;
 * @production AddExpr : {@link AdditiveExpr};

 */
public class AddExpr extends AdditiveExpr implements Cloneable {
  /**
   * @declaredat ASTNode:1
   */
  public AddExpr() {
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
    name = {"LeftOperand", "RightOperand"},
    type = {"Expr", "Expr"},
    kind = {"Child", "Child"}
  )
  public AddExpr(Expr p0, Expr p1) {
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
    type_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:38
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:42
   */
  public AddExpr clone() throws CloneNotSupportedException {
    AddExpr node = (AddExpr) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:47
   */
  public AddExpr copy() {
    try {
      AddExpr node = (AddExpr) clone();
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
   * @declaredat ASTNode:66
   */
  @Deprecated
  public AddExpr fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:76
   */
  public AddExpr treeCopyNoTransform() {
    AddExpr tree = (AddExpr) copy();
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
   * @declaredat ASTNode:96
   */
  public AddExpr treeCopy() {
    AddExpr tree = (AddExpr) copy();
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
   * @declaredat ASTNode:110
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the LeftOperand child.
   * @param node The new node to replace the LeftOperand child.
   * @apilevel high-level
   */
  public void setLeftOperand(Expr node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the LeftOperand child.
   * @return The current node used as the LeftOperand child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="LeftOperand")
  public Expr getLeftOperand() {
    return (Expr) getChild(0);
  }
  /**
   * Retrieves the LeftOperand child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the LeftOperand child.
   * @apilevel low-level
   */
  public Expr getLeftOperandNoTransform() {
    return (Expr) getChildNoTransform(0);
  }
  /**
   * Replaces the RightOperand child.
   * @param node The new node to replace the RightOperand child.
   * @apilevel high-level
   */
  public void setRightOperand(Expr node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the RightOperand child.
   * @return The current node used as the RightOperand child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="RightOperand")
  public Expr getRightOperand() {
    return (Expr) getChild(1);
  }
  /**
   * Retrieves the RightOperand child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the RightOperand child.
   * @apilevel low-level
   */
  public Expr getRightOperandNoTransform() {
    return (Expr) getChildNoTransform(1);
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/ConstantExpression.jrag:32
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/ConstantExpression.jrag:32")
  public Constant constant() {
    Constant constant_value = type().add(getLeftOperand().constant(), getRightOperand().constant());
    return constant_value;
  }
  /**
   * @attribute syn
   * @aspect TypeCheck
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/TypeCheck.jrag:221
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/TypeCheck.jrag:221")
  public Collection<Problem> typeProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        TypeDecl left = getLeftOperand().type();
        TypeDecl right = getRightOperand().type();
        if (!left.isString() && !right.isString()) {
          return super.typeProblems();
        } else if (left.isVoid()) {
          problems.add(error("The type void of the left hand side is not numeric"));
        } else if (right.isVoid()) {
          problems.add(error("The type void of the right hand side is not numeric"));
        }
        return problems;
      }
  }
  /** The operator string used for pretty printing this expression. 
   * @attribute syn
   * @aspect PrettyPrintUtil
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/PrettyPrintUtil.jrag:345
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PrettyPrintUtil", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/PrettyPrintUtil.jrag:345")
  public String printOp() {
    String printOp_value = "+";
    return printOp_value;
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
    type_value = type_compute();
    if (state().inCircle()) {
      type_computed = state().cycle();
    
    } else {
      type_computed = ASTState.NON_CYCLE;
    
    }
    return type_value;
  }
  /** @apilevel internal */
  private TypeDecl type_compute() {
      TypeDecl left = getLeftOperand().type();
      TypeDecl right = getRightOperand().type();
      if (!left.isString() && !right.isString()) {
        return super.type();
      } else {
        if (left.isVoid() || right.isVoid()) {
          return unknownType();
        }
        // pick the string type
        return left.isString() ? left : right;
      }
    }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/GenericMethodsInference.jrag:69
   * @apilevel internal
   */
  public TypeDecl Define_assignConvertedType(ASTNode _callerNode, ASTNode _childNode) {
    if (getRightOperandNoTransform() != null && _callerNode == getRightOperand()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/GenericMethodsInference.jrag:92
      return getLeftOperand().type().isString() ? typeObject() : typeNull();
    }
    else if (getLeftOperandNoTransform() != null && _callerNode == getLeftOperand()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/GenericMethodsInference.jrag:88
      return getRightOperand().type().isString() ? typeObject() : typeNull();
    }
    else {
      return getParent().Define_assignConvertedType(this, _callerNode);
    }
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/GenericMethodsInference.jrag:69
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute assignConvertedType
   */
  protected boolean canDefine_assignConvertedType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:31
   * @apilevel internal
   */
  public TypeDecl Define_targetType(ASTNode _callerNode, ASTNode _childNode) {
    if (getRightOperandNoTransform() != null && _callerNode == getRightOperand()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:54
      {
          if (getRightOperand().stringContext()) {
            return getLeftOperand().type();
          } else if (!getLeftOperand().isPolyExpression() && !getRightOperand().isPolyExpression()) {
            return type();
          } else {
            return targetType();
          }
        }
    }
    else if (getLeftOperandNoTransform() != null && _callerNode == getLeftOperand()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:45
      {
          if (getLeftOperand().stringContext()) {
            return getRightOperand().type();
          } else if (!getLeftOperand().isPolyExpression() && !getRightOperand().isPolyExpression()) {
            return type();
          } else {
            return targetType();
          }
        }
    }
    else {
      return super.Define_targetType(_callerNode, _childNode);
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
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:238
   * @apilevel internal
   */
  public boolean Define_stringContext(ASTNode _callerNode, ASTNode _childNode) {
    if (getRightOperandNoTransform() != null && _callerNode == getRightOperand()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:273
      {
          if (!getRightOperand().isPolyExpression() && !getLeftOperand().isPolyExpression()) {
            if (getLeftOperand().type().isString() && !getRightOperand().type().isString()) {
              return true;
            }
          }
          return false;
        }
    }
    else if (getLeftOperandNoTransform() != null && _callerNode == getLeftOperand()) {
      // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/TargetType.jrag:265
      {
          if (!getRightOperand().isPolyExpression() && !getLeftOperand().isPolyExpression()) {
            if (getRightOperand().type().isString() && !getLeftOperand().type().isString()) {
              return true;
            }
          }
          return false;
        }
    }
    else {
      return super.Define_stringContext(_callerNode, _childNode);
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
}
