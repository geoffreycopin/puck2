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
 * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/grammar/Java.ast:81
 * @astdecl VarAccess : Access ::= <ID:String>;
 * @production VarAccess : {@link Access} ::= <span class="component">&lt;ID:String&gt;</span>;

 */
public class VarAccess extends Access implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/PrettyPrint.jadd:626
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getID());
  }
  /**
   * @aspect NodeConstructors
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/NodeConstructors.jrag:52
   */
  public VarAccess(String name, int start, int end) {
    this(name);
    this.start = this.IDstart = start;
    this.end = this.IDend = end;
  }
  /**
   * @aspect DefiniteAssignment
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:579
   */
  protected boolean checkDUeverywhere(Variable v) {
    if (isDest() && decl() == v) {
      return false;
    }
    return super.checkDUeverywhere(v);
  }
  /**
   * @aspect NameCheck
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/NameCheck.jrag:330
   */
  public BodyDecl closestBodyDecl(TypeDecl t) {
    ASTNode node = this;
    while (!(node.getParent().getParent() instanceof Program)
        && node.getParent().getParent() != t) {
      node = node.getParent();
    }
    if (node instanceof BodyDecl) {
      return (BodyDecl) node;
    }
    return null;
  }
  /**
   * @aspect PrettyPrintUtil
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/PrettyPrintUtil.jrag:97
   */
  @Override public String toString() {
    return name();
  }
  /**
   * @declaredat ASTNode:1
   */
  public VarAccess() {
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
  /**
   * @declaredat ASTNode:12
   */
  @ASTNodeAnnotation.Constructor(
    name = {"ID"},
    type = {"String"},
    kind = {"Token"}
  )
  public VarAccess(String p0) {
    setID(p0);
  }
  /**
   * @declaredat ASTNode:20
   */
  public VarAccess(beaver.Symbol p0) {
    setID(p0);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:24
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:30
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:34
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    isFieldAccess_reset();
    unassignedAfter_Variable_reset();
    isConstant_reset();
    decls_reset();
    decl_reset();
    type_reset();
    enclosingLambda_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:45
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:49
   */
  public VarAccess clone() throws CloneNotSupportedException {
    VarAccess node = (VarAccess) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:54
   */
  public VarAccess copy() {
    try {
      VarAccess node = (VarAccess) clone();
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
   * @declaredat ASTNode:73
   */
  @Deprecated
  public VarAccess fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:83
   */
  public VarAccess treeCopyNoTransform() {
    VarAccess tree = (VarAccess) copy();
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
   * @declaredat ASTNode:103
   */
  public VarAccess treeCopy() {
    VarAccess tree = (VarAccess) copy();
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
   * @declaredat ASTNode:117
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((VarAccess) node).tokenString_ID);    
  }
  /**
   * Replaces the lexeme ID.
   * @param value The new value for the lexeme ID.
   * @apilevel high-level
   */
  public void setID(String value) {
    tokenString_ID = value;
  }
  /** @apilevel internal 
   */
  protected String tokenString_ID;
  /**
   */
  public int IDstart;
  /**
   */
  public int IDend;
  /**
   * JastAdd-internal setter for lexeme ID using the Beaver parser.
   * @param symbol Symbol containing the new value for the lexeme ID
   * @apilevel internal
   */
  public void setID(beaver.Symbol symbol) {
    if (symbol.value != null && !(symbol.value instanceof String))
    throw new UnsupportedOperationException("setID is only valid for String lexemes");
    tokenString_ID = (String)symbol.value;
    IDstart = symbol.getStart();
    IDend = symbol.getEnd();
  }
  /**
   * Retrieves the value for the lexeme ID.
   * @return The value for the lexeme ID.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Token(name="ID")
  public String getID() {
    return tokenString_ID != null ? tokenString_ID : "";
  }
  /** @apilevel internal */
  private void isFieldAccess_reset() {
    isFieldAccess_computed = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle isFieldAccess_computed = null;

  /** @apilevel internal */
  protected boolean isFieldAccess_value;

  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:53
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/ResolveAmbiguousNames.jrag:53")
  public boolean isFieldAccess() {
    ASTState state = state();
    if (isFieldAccess_computed == ASTState.NON_CYCLE || isFieldAccess_computed == state().cycle()) {
      return isFieldAccess_value;
    }
    isFieldAccess_value = decl().isClassVariable() || decl().isInstanceVariable();
    if (state().inCircle()) {
      isFieldAccess_computed = state().cycle();
    
    } else {
      isFieldAccess_computed = ASTState.NON_CYCLE;
    
    }
    return isFieldAccess_value;
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:77
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:77")
  public Variable varDecl() {
    Variable varDecl_value = decl();
    return varDecl_value;
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:111
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:111")
  public Collection<Problem> definiteAssignmentProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        if (isSource()) {
          if (decl() instanceof Declarator) {
            Declarator v = (Declarator) decl();
            if (v.isField()) {
              if (v.isFinal() && !v.hasInit() && !isQualified() && !assignedBefore(v)) {
                problems.add(errorf("Final field %s is not assigned before used", v.name()));
              }
            } else if (!v.isValue()) {
              if (v.isBlankFinal()) {
                if (!assignedBefore(v)) {
                  problems.add(errorf("Final variable %s is not assigned before used", v.name()));
                }
              } else {
                // We can not use v.hasInit() here as a quick test for assignedness, because
                // v is a variable and the initialization may not have been reached from the
                // current access, e.g., if declared in a previous switch branch.
                if (!assignedBefore(v)) {
                  problems.add(errorf("Local variable %s is not assigned before used", v.name()));
                }
              }
            }
          }
        }
        if (isDest()) {
          Variable v = decl();
          if (v.isFinal() && v.isBlank() && !hostType().instanceOf(v.hostType())) {
            // Blank final field.
            problems.add(error("The final variable is not a blank final in this context, "
                + "so it may not be assigned."));
          } else if (v.isFinal() && isQualified()
              && (!qualifier().isThisAccess() || ((Access) qualifier()).isQualified())) {
            problems.add(errorf("the blank final field %s may only be assigned by simple name",
                  v.name()));
          } else if (v instanceof VariableDeclarator) {
            // Local variable.
            VariableDeclarator var = (VariableDeclarator) v;
            if (!var.isValue()
                // TODO(joqvist): use inherited attribute instead.
                && var.getParent().getParent().getParent() instanceof SwitchStmt
                && var.isFinal()) {
              if (!unassignedBefore(var)) {
                problems.add(errorf("Final variable %s may only be assigned once", var.name()));
              }
            } else if (var.isValue()) {
              if (var.hasInit() || !unassignedBefore(var)) {
                problems.add(errorf("Final variable %s may only be assigned once", var.name()));
              }
            } else if (var.isBlankFinal()) {
              if (var.hasInit() || !unassignedBefore(var)) {
                problems.add(errorf("Final variable %s may only be assigned once", var.name()));
              }
            }
          } else if (v.isField()) {
            // Field.
            if (v.isFinal()) {
              if (v.hasInit()) {
                problems.add(errorf("already initialized final field %s can not be assigned",
                      v.name()));
              } else {
                BodyDecl bodyDecl = enclosingBodyDecl();
                if (!(bodyDecl instanceof ConstructorDecl)
                    && !(bodyDecl instanceof InstanceInitializer)
                    && !(bodyDecl instanceof StaticInitializer)
                    && !(bodyDecl instanceof FieldDecl)) {
                  problems.add(errorf(
                      "final field %s may only be assigned in constructors and initializers",
                      v.name()));
                } else if (!unassignedBefore(v)) {
                  problems.add(errorf("blank final field %s may only be assigned once", v.name()));
                }
              }
            }
          } else if (v.isParameter()) {
            // 8.4.1
            if (v.isFinal()) {
              problems.add(errorf("Final parameter %s may not be assigned", v.name()));
            }
          }
        }
        return problems;
      }
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:268
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:268")
  public boolean assignedAfter(Variable v) {
    boolean assignedAfter_Variable_value = assignedBefore(v);
    return assignedAfter_Variable_value;
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
        new_unassignedAfter_Variable_value = unassignedBefore(v);
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
      boolean new_unassignedAfter_Variable_value = unassignedBefore(v);
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
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/ConstantExpression.jrag:32
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/ConstantExpression.jrag:32")
  public Constant constant() {
    Constant constant_value = type().cast(decl().constant());
    return constant_value;
  }
/** @apilevel internal */
protected ASTState.Cycle isConstant_cycle = null;
  /** @apilevel internal */
  private void isConstant_reset() {
    isConstant_computed = false;
    isConstant_initialized = false;
    isConstant_cycle = null;
  }
  /** @apilevel internal */
  protected boolean isConstant_computed = false;

  /** @apilevel internal */
  protected boolean isConstant_value;
  /** @apilevel internal */
  protected boolean isConstant_initialized = false;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/ConstantExpression.jrag:423")
  public boolean isConstant() {
    if (isConstant_computed) {
      return isConstant_value;
    }
    ASTState state = state();
    if (!isConstant_initialized) {
      isConstant_initialized = true;
      isConstant_value = false;
    }
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      do {
        isConstant_cycle = state.nextCycle();
        boolean new_isConstant_value = isConstant_compute();
        if (isConstant_value != new_isConstant_value) {
          state.setChangeInCycle();
        }
        isConstant_value = new_isConstant_value;
      } while (state.testAndClearChangeInCycle());
      isConstant_computed = true;

      state.leaveCircle();
    } else if (isConstant_cycle != state.cycle()) {
      isConstant_cycle = state.cycle();
      boolean new_isConstant_value = isConstant_compute();
      if (isConstant_value != new_isConstant_value) {
        state.setChangeInCycle();
      }
      isConstant_value = new_isConstant_value;
    } else {
    }
    return isConstant_value;
  }
  /** @apilevel internal */
  private boolean isConstant_compute() {
      Variable v = decl();
      if (v.isField()) {
        return v.isConstant() && (!isQualified() || (isQualified() && qualifier().isTypeAccess()));
      } else {
        return v.isFinal() && v.hasInit()
            && v.getInit().isConstant() && (v.type().isPrimitive() || v.type().isString())
            && (!isQualified() || (isQualified() && qualifier().isTypeAccess()));
      }
    }
  /**
   * @attribute syn
   * @aspect TypeCheck
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/TypeCheck.jrag:33
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/TypeCheck.jrag:33")
  public boolean isVariable() {
    boolean isVariable_value = true;
    return isVariable_value;
  }
  /** @apilevel internal */
  private void decls_reset() {
    decls_computed = null;
    decls_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle decls_computed = null;

  /** @apilevel internal */
  protected SimpleSet<Variable> decls_value;

  /**
   * @attribute syn
   * @aspect VariableScopePropagation
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/LookupVariable.jrag:357
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="VariableScopePropagation", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/LookupVariable.jrag:357")
  public SimpleSet<Variable> decls() {
    ASTState state = state();
    if (decls_computed == ASTState.NON_CYCLE || decls_computed == state().cycle()) {
      return decls_value;
    }
    decls_value = decls_compute();
    if (state().inCircle()) {
      decls_computed = state().cycle();
    
    } else {
      decls_computed = ASTState.NON_CYCLE;
    
    }
    return decls_value;
  }
  /** @apilevel internal */
  private SimpleSet<Variable> decls_compute() {
      SimpleSet<Variable> result = lookupVariable(name());
      if (result.isSingleton()) {
        Variable v = result.singletonValue();
        if (!isQualified() && inStaticContext()) {
          if (v.isInstanceVariable() && !hostType().memberFields(v.name()).isEmpty()) {
            return emptySet();
          }
        } else if (isQualified() && qualifier().staticContextQualifier()) {
          if (v.isInstanceVariable()) {
            return emptySet();
          }
        }
      }
      return result;
    }
  /** @apilevel internal */
  private void decl_reset() {
    decl_computed = null;
    decl_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle decl_computed = null;

  /** @apilevel internal */
  protected Variable decl_value;

  /**
   * @attribute syn
   * @aspect VariableScopePropagation
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/LookupVariable.jrag:374
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="VariableScopePropagation", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/LookupVariable.jrag:374")
  public Variable decl() {
    ASTState state = state();
    if (decl_computed == ASTState.NON_CYCLE || decl_computed == state().cycle()) {
      return decl_value;
    }
    decl_value = decl_compute();
    if (state().inCircle()) {
      decl_computed = state().cycle();
    
    } else {
      decl_computed = ASTState.NON_CYCLE;
    
    }
    return decl_value;
  }
  /** @apilevel internal */
  private Variable decl_compute() {
      SimpleSet<Variable> decls = decls();
      if (decls.isSingleton()) {
        return decls.singletonValue();
      }
      return unknownField();
    }
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/NameCheck.jrag:280
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/NameCheck.jrag:280")
  public Collection<Problem> nameProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        if (decls().isEmpty() && (!isQualified() || !qualifier().type().isUnknown()
              || qualifier().isPackageAccess())) {
          problems.add(errorf("no field named %s is accessible", name()));
        }
    
        if (decls().size() > 1) {
          StringBuilder sb = new StringBuilder();
          sb.append("several fields named " + name());
          ArrayList<String> fields = new ArrayList<String>();
          for (Variable v : decls()) {
            fields.add(String.format("%n    %s %s declared in %s",
                v.type().typeName(), v.name(), v.hostType().typeName()));
          }
          Collections.sort(fields);
          for (String line : fields) {
            sb.append(line);
          }
          problems.add(error(sb.toString()));
        }
    
        // 8.8.5.1
        if (inExplicitConstructorInvocation() && !isQualified() && decl().isInstanceVariable()
            && hostType() == decl().hostType()) {
          problems.add(errorf("instance variable %s may not be accessed in an explicit constructor invocation",
              name()));
        }
    
        Variable var = decl();
        if (!var.isClassVariable() && !var.isInstanceVariable() && var.hostType() != hostType()
            && !var.isEffectivelyFinal()) {
          problems.add(error("A parameter/variable used but not declared in an inner class must be"
              + " final or effectively final"));
        }
    
        // 8.3.2.3
        if ((decl().isInstanceVariable() || decl().isClassVariable()) && !isQualified()) {
          if (hostType() != null && !declaredBefore(decl())) {
            if (inSameInitializer() && !simpleAssignment() && inDeclaringClass()) {
              BodyDecl b = closestBodyDecl(hostType());
              problems.add(errorf("variable %s is used in %s before it is declared",
                  decl().name(), b.prettyPrint()));
            }
          }
        }
    
        if (!var.isClassVariable() && !var.isInstanceVariable() && enclosingLambda() != null) {
          if (var instanceof ParameterDeclaration) {
            ParameterDeclaration decl = (ParameterDeclaration) var;
            if (decl.enclosingLambda() != enclosingLambda()) {
              // 15.27.2
              if (!decl.isEffectivelyFinal()) {
                problems.add(errorf("Parameter %s must be effectively final", var.name()));
              }
            }
          } else if (var instanceof InferredParameterDeclaration) {
            InferredParameterDeclaration decl = (InferredParameterDeclaration) var;
            if (decl.enclosingLambda() != enclosingLambda()) {
              // 15.27.2
              if (!decl.isEffectivelyFinal()) {
                problems.add(errorf("Parameter %s must be effectively final", var.name()));
              }
            }
          } else if (var instanceof VariableDeclarator) {
            VariableDeclarator decl = (VariableDeclarator) var;
            if (decl.enclosingLambda() != enclosingLambda()) {
              // 15.27.2
              if (!decl.isEffectivelyFinal()) {
                problems.add(errorf("Variable %s must be effectively final", var.name()));
              }
              // 15.27.2
              if (!enclosingLambda().assignedBefore(decl)) {
                problems.add(errorf("Variable %s must be definitely assigned before used in a lambda",
                    var.name()));
              }
            }
          }
        }
        return problems;
      }
  }
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/NameCheck.jrag:342
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/NameCheck.jrag:342")
  public boolean inSameInitializer() {
    {
        BodyDecl b = closestBodyDecl(decl().hostType());
        if (b == null) {
          return false;
        }
        if (b instanceof FieldDecl && ((FieldDecl) b).isStatic() == decl().isStatic()) {
          // TODO(joqvist): fixme
          return true;
        }
        if (b instanceof InstanceInitializer && !decl().isStatic()) {
          return true;
        }
        if (b instanceof StaticInitializer && decl().isStatic()) {
          return true;
        }
        return false;
      }
  }
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/NameCheck.jrag:360
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/NameCheck.jrag:360")
  public boolean simpleAssignment() {
    boolean simpleAssignment_value = isDest() && getParent() instanceof AssignSimpleExpr;
    return simpleAssignment_value;
  }
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/NameCheck.jrag:362
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/NameCheck.jrag:362")
  public boolean inDeclaringClass() {
    boolean inDeclaringClass_value = hostType() == decl().hostType();
    return inDeclaringClass_value;
  }
  /**
   * Defines the expected kind of name for the left hand side in a qualified
   * expression.
   * @attribute syn
   * @aspect SyntacticClassification
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/SyntacticClassification.jrag:60
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SyntacticClassification", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/SyntacticClassification.jrag:60")
  public NameType predNameType() {
    NameType predNameType_value = NameType.AMBIGUOUS_NAME;
    return predNameType_value;
  }
  /**
   * @attribute syn
   * @aspect Names
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/QualifiedNames.jrag:35
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Names", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/QualifiedNames.jrag:35")
  public String name() {
    String name_value = getID();
    return name_value;
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
    type_value = decl().type();
    if (state().inCircle()) {
      type_computed = state().cycle();
    
    } else {
      type_computed = ASTState.NON_CYCLE;
    
    }
    return type_value;
  }
  /**
   * @attribute syn
   * @aspect Enums
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Enums.jrag:645
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Enums", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Enums.jrag:645")
  public boolean isEnumConstant() {
    boolean isEnumConstant_value = varDecl() instanceof EnumConstant;
    return isEnumConstant_value;
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java7/frontend/PreciseRethrow.jrag:33
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java7/frontend/PreciseRethrow.jrag:33")
  public Collection<TypeDecl> throwTypes() {
    Collection<TypeDecl> throwTypes_value = decl().throwTypes();
    return throwTypes_value;
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java7/frontend/PreciseRethrow.jrag:145
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java7/frontend/PreciseRethrow.jrag:145")
  public boolean modifiedInScope(Variable var) {
    boolean modifiedInScope_Variable_value = false;
    return modifiedInScope_Variable_value;
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java7/frontend/PreciseRethrow.jrag:196
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java7/frontend/PreciseRethrow.jrag:196")
  public boolean isVariable(Variable var) {
    boolean isVariable_Variable_value = decl() == var;
    return isVariable_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect TypeHierarchyCheck
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/TypeHierarchyCheck.jrag:183
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/TypeHierarchyCheck.jrag:183")
  public boolean inExplicitConstructorInvocation() {
    boolean inExplicitConstructorInvocation_value = getParent().Define_inExplicitConstructorInvocation(this, null);
    return inExplicitConstructorInvocation_value;
  }
  /** Checks if this var access is inside an instance initializer for an enum type. 
   * @attribute inh
   * @aspect Enums
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Enums.jrag:563
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Enums", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Enums.jrag:563")
  public boolean inEnumInitializer() {
    boolean inEnumInitializer_value = getParent().Define_inEnumInitializer(this, null);
    return inEnumInitializer_value;
  }
  /**
   * @attribute inh
   * @aspect EnclosingLambda
   * @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/EnclosingLambda.jrag:32
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="EnclosingLambda", declaredAt="/Users/geoffrey/IdeaProjects/puck2/extendj/java8/frontend/EnclosingLambda.jrag:32")
  public LambdaExpr enclosingLambda() {
    ASTState state = state();
    if (enclosingLambda_computed == ASTState.NON_CYCLE || enclosingLambda_computed == state().cycle()) {
      return enclosingLambda_value;
    }
    enclosingLambda_value = getParent().Define_enclosingLambda(this, null);
    if (state().inCircle()) {
      enclosingLambda_computed = state().cycle();
    
    } else {
      enclosingLambda_computed = ASTState.NON_CYCLE;
    
    }
    return enclosingLambda_value;
  }
  /** @apilevel internal */
  private void enclosingLambda_reset() {
    enclosingLambda_computed = null;
    enclosingLambda_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle enclosingLambda_computed = null;

  /** @apilevel internal */
  protected LambdaExpr enclosingLambda_value;

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
    // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/DefiniteAssignment.jrag:109
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java4/frontend/NameCheck.jrag:278
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Annotations.jrag:504
    if (decl().isField()
              && decl().getModifiers().hasDeprecatedAnnotation()
              && !withinDeprecatedAnnotation()
              && hostType().topLevelType() != decl().hostType().topLevelType()
              && !withinSuppressWarnings("deprecation")) {
      {
        java.util.Set<ASTNode> contributors = _map.get(_root);
        if (contributors == null) {
          contributors = new java.util.LinkedHashSet<ASTNode>();
          _map.put((ASTNode) _root, contributors);
        }
        contributors.add(this);
      }
    }
    // @declaredat /Users/geoffrey/IdeaProjects/puck2/extendj/java5/frontend/Enums.jrag:577
    if (decl().isStatic()
              && decl().hostType() == hostType()
              && !isConstant()
              && inEnumInitializer()) {
      {
        java.util.Set<ASTNode> contributors = _map.get(_root);
        if (contributors == null) {
          contributors = new java.util.LinkedHashSet<ASTNode>();
          _map.put((ASTNode) _root, contributors);
        }
        contributors.add(this);
      }
    }
    super.collect_contributors_CompilationUnit_problems(_root, _map);
  }
  /** @apilevel internal */
  protected void contributeTo_CompilationUnit_problems(LinkedList<Problem> collection) {
    super.contributeTo_CompilationUnit_problems(collection);
    for (Problem value : definiteAssignmentProblems()) {
      collection.add(value);
    }
    for (Problem value : nameProblems()) {
      collection.add(value);
    }
    if (decl().isField()
              && decl().getModifiers().hasDeprecatedAnnotation()
              && !withinDeprecatedAnnotation()
              && hostType().topLevelType() != decl().hostType().topLevelType()
              && !withinSuppressWarnings("deprecation")) {
      collection.add(warning(decl().name() + " in " + decl().hostType().typeName() + " has been deprecated"));
    }
    if (decl().isStatic()
              && decl().hostType() == hostType()
              && !isConstant()
              && inEnumInitializer()) {
      collection.add(error("may not reference a static field of an enum type from here"));
    }
  }
}
