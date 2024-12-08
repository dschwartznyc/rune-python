package com.regnosys.rosetta.generator.python.expressions;

import com.google.common.base.Objects;
import com.regnosys.rosetta.generator.java.enums.EnumHelper;
import com.regnosys.rosetta.rosetta.RosettaCallableWithArgs;
import com.regnosys.rosetta.rosetta.RosettaEnumValue;
import com.regnosys.rosetta.rosetta.RosettaEnumValueReference;
import com.regnosys.rosetta.rosetta.RosettaEnumeration;
import com.regnosys.rosetta.rosetta.RosettaFeature;
import com.regnosys.rosetta.rosetta.RosettaMetaType;
import com.regnosys.rosetta.rosetta.RosettaModel;
import com.regnosys.rosetta.rosetta.RosettaSymbol;
import com.regnosys.rosetta.rosetta.RosettaType;
import com.regnosys.rosetta.rosetta.TypeCall;
import com.regnosys.rosetta.rosetta.expression.AsKeyOperation;
import com.regnosys.rosetta.rosetta.expression.CardinalityModifier;
import com.regnosys.rosetta.rosetta.expression.ChoiceOperation;
import com.regnosys.rosetta.rosetta.expression.ClosureParameter;
import com.regnosys.rosetta.rosetta.expression.ConstructorKeyValuePair;
import com.regnosys.rosetta.rosetta.expression.DistinctOperation;
import com.regnosys.rosetta.rosetta.expression.FilterOperation;
import com.regnosys.rosetta.rosetta.expression.FirstOperation;
import com.regnosys.rosetta.rosetta.expression.FlattenOperation;
import com.regnosys.rosetta.rosetta.expression.InlineFunction;
import com.regnosys.rosetta.rosetta.expression.LastOperation;
import com.regnosys.rosetta.rosetta.expression.ListLiteral;
import com.regnosys.rosetta.rosetta.expression.MapOperation;
import com.regnosys.rosetta.rosetta.expression.ModifiableBinaryOperation;
import com.regnosys.rosetta.rosetta.expression.Necessity;
import com.regnosys.rosetta.rosetta.expression.OneOfOperation;
import com.regnosys.rosetta.rosetta.expression.RosettaAbsentExpression;
import com.regnosys.rosetta.rosetta.expression.RosettaBinaryOperation;
import com.regnosys.rosetta.rosetta.expression.RosettaBooleanLiteral;
import com.regnosys.rosetta.rosetta.expression.RosettaConditionalExpression;
import com.regnosys.rosetta.rosetta.expression.RosettaConstructorExpression;
import com.regnosys.rosetta.rosetta.expression.RosettaCountOperation;
import com.regnosys.rosetta.rosetta.expression.RosettaDeepFeatureCall;
import com.regnosys.rosetta.rosetta.expression.RosettaExistsExpression;
import com.regnosys.rosetta.rosetta.expression.RosettaExpression;
import com.regnosys.rosetta.rosetta.expression.RosettaFeatureCall;
import com.regnosys.rosetta.rosetta.expression.RosettaImplicitVariable;
import com.regnosys.rosetta.rosetta.expression.RosettaIntLiteral;
import com.regnosys.rosetta.rosetta.expression.RosettaNumberLiteral;
import com.regnosys.rosetta.rosetta.expression.RosettaOnlyElement;
import com.regnosys.rosetta.rosetta.expression.RosettaOnlyExistsExpression;
import com.regnosys.rosetta.rosetta.expression.RosettaReference;
import com.regnosys.rosetta.rosetta.expression.RosettaStringLiteral;
import com.regnosys.rosetta.rosetta.expression.RosettaSymbolReference;
import com.regnosys.rosetta.rosetta.expression.SortOperation;
import com.regnosys.rosetta.rosetta.expression.SumOperation;
import com.regnosys.rosetta.rosetta.expression.ThenOperation;
import com.regnosys.rosetta.rosetta.expression.ToEnumOperation;
import com.regnosys.rosetta.rosetta.expression.ToStringOperation;
import com.regnosys.rosetta.rosetta.simple.Attribute;
import com.regnosys.rosetta.rosetta.simple.Condition;
import com.regnosys.rosetta.rosetta.simple.Data;
import com.regnosys.rosetta.rosetta.simple.ShortcutDeclaration;
import com.regnosys.rosetta.rosetta.simple.impl.FunctionImpl;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class PythonExpressionGenerator {
  public List<String> importsFound;

  public ArrayList<String> if_cond_blocks = new ArrayList<String>();

  public String generateConditions(final Data cls) {
    int n_condition = 0;
    String res = "";
    EList<Condition> _conditions = cls.getConditions();
    for (final Condition cond : _conditions) {
      {
        String _res = res;
        CharSequence _generateConditionBoilerPlate = this.generateConditionBoilerPlate(cond, n_condition);
        res = (_res + _generateConditionBoilerPlate);
        boolean _isConstraintCondition = this.isConstraintCondition(cond);
        if (_isConstraintCondition) {
          String _res_1 = res;
          CharSequence _generateConstraintCondition = this.generateConstraintCondition(cls, cond);
          res = (_res_1 + _generateConstraintCondition);
        } else {
          String _res_2 = res;
          String _generateExpressionCondition = this.generateExpressionCondition(cond);
          res = (_res_2 + _generateExpressionCondition);
        }
        int _n_condition = n_condition;
        n_condition = (_n_condition + 1);
      }
    }
    return res;
  }

  public String generateConditions(final List<Condition> conditions) {
    int n_condition = 0;
    String res = "";
    for (final Condition cond : conditions) {
      {
        String _res = res;
        CharSequence _generateConditionBoilerPlate = this.generateConditionBoilerPlate(cond, n_condition);
        res = (_res + _generateConditionBoilerPlate);
        String _res_1 = res;
        String _generateExpressionCondition = this.generateExpressionCondition(cond);
        res = (_res_1 + _generateExpressionCondition);
        int _n_condition = n_condition;
        n_condition = (_n_condition + 1);
      }
    }
    return res;
  }

  public String generateFunctionConditions(final List<Condition> conditions, final String condition_type) {
    int n_condition = 0;
    String res = "";
    for (final Condition cond : conditions) {
      {
        String _res = res;
        CharSequence _generateFunctionConditionBoilerPlate = this.generateFunctionConditionBoilerPlate(cond, n_condition, condition_type);
        res = (_res + _generateFunctionConditionBoilerPlate);
        String _res_1 = res;
        String _generateExpressionCondition = this.generateExpressionCondition(cond);
        res = (_res_1 + _generateExpressionCondition);
        int _n_condition = n_condition;
        n_condition = (_n_condition + 1);
      }
    }
    return res;
  }

  public boolean isConstraintCondition(final Condition cond) {
    return (this.isOneOf(cond) || this.isChoice(cond));
  }

  private boolean isOneOf(final Condition cond) {
    RosettaExpression _expression = cond.getExpression();
    return (_expression instanceof OneOfOperation);
  }

  private boolean isChoice(final Condition cond) {
    RosettaExpression _expression = cond.getExpression();
    return (_expression instanceof ChoiceOperation);
  }

  private CharSequence generateConditionBoilerPlate(final Condition cond, final int n_condition) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("@rosetta_condition");
    _builder.newLine();
    _builder.append("def condition_");
    _builder.append(n_condition);
    _builder.append("_");
    String _name = cond.getName();
    _builder.append(_name);
    _builder.append("(self):");
    _builder.newLineIfNotEmpty();
    {
      String _definition = cond.getDefinition();
      boolean _tripleNotEquals = (_definition != null);
      if (_tripleNotEquals) {
        _builder.append("    ");
        _builder.append("\"\"\"");
        _builder.newLine();
        _builder.append("    ");
        String _definition_1 = cond.getDefinition();
        _builder.append(_definition_1, "    ");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("\"\"\"");
        _builder.newLine();
      }
    }
    _builder.append("    ");
    _builder.append("item = self");
    _builder.newLine();
    return _builder;
  }

  private CharSequence generateFunctionConditionBoilerPlate(final Condition cond, final int n_condition, final String condition_type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("@rosetta_local_condition(");
    _builder.append(condition_type);
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    _builder.append("def condition_");
    _builder.append(n_condition);
    _builder.append("_");
    String _name = cond.getName();
    _builder.append(_name);
    _builder.append("(self):");
    _builder.newLineIfNotEmpty();
    {
      String _definition = cond.getDefinition();
      boolean _tripleNotEquals = (_definition != null);
      if (_tripleNotEquals) {
        _builder.append("    ");
        _builder.append("\"\"\"");
        _builder.newLine();
        _builder.append("    ");
        String _definition_1 = cond.getDefinition();
        _builder.append(_definition_1, "    ");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("\"\"\"");
        _builder.newLine();
      }
    }
    return _builder;
  }

  private CharSequence generateConstraintCondition(final Data cls, final Condition cond) {
    CharSequence _xblockexpression = null;
    {
      final RosettaExpression expression = cond.getExpression();
      EList<Attribute> attributes = cls.getAttributes();
      String necessity = "necessity=True";
      if ((expression instanceof ChoiceOperation)) {
        attributes = ((ChoiceOperation)expression).getAttributes();
        Necessity _necessity = ((ChoiceOperation)expression).getNecessity();
        boolean _equals = Objects.equal(_necessity, Necessity.OPTIONAL);
        if (_equals) {
          necessity = "necessity=False";
        }
      }
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("    ");
      _builder.append("return self.check_one_of_constraint(");
      {
        boolean _hasElements = false;
        for(final Attribute a : attributes) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate(", ", "    ");
          }
          _builder.append("\'");
          String _name = a.getName();
          _builder.append(_name, "    ");
          _builder.append("\'");
        }
      }
      _builder.append(", ");
      _builder.append(necessity, "    ");
      _builder.append(")");
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  private String generateExpressionCondition(final Condition c) {
    ArrayList<String> _arrayList = new ArrayList<String>();
    this.if_cond_blocks = _arrayList;
    String expr = this.generateExpression(c.getExpression(), 0);
    String blocks = "";
    boolean _isEmpty = this.if_cond_blocks.isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("    ");
      {
        for(final String arg : this.if_cond_blocks) {
          _builder.append(arg, "    ");
        }
      }
      blocks = _builder.toString();
    }
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append(blocks);
    _builder_1.append("    return ");
    _builder_1.append(expr);
    _builder_1.newLineIfNotEmpty();
    return _builder_1.toString();
  }

  public String generateExpressionThenElse(final RosettaExpression expr, final List<Integer> iflvl) {
    ArrayList<String> _arrayList = new ArrayList<String>();
    this.if_cond_blocks = _arrayList;
    this.generateExpression(expr, (iflvl.get(0)).intValue());
    String blocks = "";
    boolean _isEmpty = this.if_cond_blocks.isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      Integer _get = iflvl.get(0);
      int _plus = ((_get).intValue() + 1);
      iflvl.set(0, Integer.valueOf(_plus));
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("    ");
      {
        for(final String arg : this.if_cond_blocks) {
          _builder.append(arg, "    ");
        }
      }
      blocks = _builder.toString();
    }
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append(blocks);
    return _builder_1.toString();
  }

  public String generateExpression(final RosettaExpression expr, final int iflvl) {
    String _switchResult = null;
    boolean _matched = false;
    if (expr instanceof RosettaDeepFeatureCall) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("rosetta_resolve_deep_attr(self, \"");
      String _name = ((RosettaDeepFeatureCall)expr).getFeature().getName();
      _builder.append(_name);
      _builder.append("\")");
      return _builder.toString();
    }
    if (!_matched) {
      if (expr instanceof RosettaConditionalExpression) {
        _matched=true;
        String _xblockexpression = null;
        {
          final String ifexpr = this.generateExpression(((RosettaConditionalExpression)expr).getIf(), (iflvl + 1));
          final String ifthen = this.generateExpression(((RosettaConditionalExpression)expr).getIfthen(), (iflvl + 1));
          String _xifexpression = null;
          if (((((RosettaConditionalExpression)expr).getElsethen() != null) && ((RosettaConditionalExpression)expr).isFull())) {
            _xifexpression = this.generateExpression(((RosettaConditionalExpression)expr).getElsethen(), 
              (iflvl + 1));
          } else {
            _xifexpression = "True";
          }
          String elsethen = _xifexpression;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("def _then_fn");
          _builder.append(iflvl);
          _builder.append("():");
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("return ");
          _builder.append(ifthen, "    ");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("def _else_fn");
          _builder.append(iflvl);
          _builder.append("():");
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("return ");
          _builder.append(elsethen, "    ");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          final String if_blocks = _builder.toString();
          this.if_cond_blocks.add(if_blocks);
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("if_cond_fn(");
          _builder_1.append(ifexpr);
          _builder_1.append(", _then_fn");
          _builder_1.append(iflvl);
          _builder_1.append(", _else_fn");
          _builder_1.append(iflvl);
          _builder_1.append(")");
          _xblockexpression = _builder_1.toString();
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      if (expr instanceof RosettaFeatureCall) {
        _matched=true;
        String _xblockexpression = null;
        {
          String _switchResult_1 = null;
          RosettaFeature _feature = ((RosettaFeatureCall)expr).getFeature();
          boolean _matched_1 = false;
          if (_feature instanceof Attribute) {
            _matched_1=true;
            _switchResult_1 = ((RosettaFeatureCall)expr).getFeature().getName();
          }
          if (!_matched_1) {
            if (_feature instanceof RosettaMetaType) {
              _matched_1=true;
              _switchResult_1 = ((RosettaFeatureCall)expr).getFeature().getName();
            }
          }
          if (!_matched_1) {
            if (_feature instanceof RosettaEnumValue) {
              _matched_1=true;
              String _xblockexpression_1 = null;
              {
                RosettaFeature _feature_1 = ((RosettaFeatureCall)expr).getFeature();
                final RosettaEnumValue rosettaValue = ((RosettaEnumValue) _feature_1);
                final String value = EnumHelper.convertValue(rosettaValue);
                RosettaExpression _receiver = ((RosettaFeatureCall)expr).getReceiver();
                final RosettaSymbol symbol = ((RosettaSymbolReference) _receiver).getSymbol();
                EObject _eContainer = symbol.eContainer();
                final RosettaModel model = ((RosettaModel) _eContainer);
                this.addImportsFromConditions(symbol.getName(), model.getName());
                _xblockexpression_1 = value;
              }
              _switchResult_1 = _xblockexpression_1;
            }
          }
          if (!_matched_1) {
            if (_feature instanceof RosettaFeature) {
              _matched_1=true;
              _switchResult_1 = ((RosettaFeatureCall)expr).getFeature().getName();
            }
          }
          if (!_matched_1) {
            String _name = ((RosettaFeatureCall)expr).getFeature().eClass().getName();
            String _plus = ("Unsupported expression type of " + _name);
            throw new UnsupportedOperationException(_plus);
          }
          String right = _switchResult_1;
          boolean _equals = Objects.equal(right, "None");
          if (_equals) {
            right = "NONE";
          }
          String receiver = this.generateExpression(((RosettaFeatureCall)expr).getReceiver(), iflvl);
          String _xifexpression = null;
          if ((receiver == null)) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append(right);
            _xifexpression = _builder.toString();
          } else {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("rosetta_resolve_attr(");
            _builder_1.append(receiver);
            _builder_1.append(", \"");
            _builder_1.append(right);
            _builder_1.append("\")");
            _xifexpression = _builder_1.toString();
          }
          _xblockexpression = _xifexpression;
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      if (expr instanceof RosettaExistsExpression) {
        _matched=true;
        String _xblockexpression = null;
        {
          RosettaExpression _argument = ((RosettaExistsExpression)expr).getArgument();
          final RosettaExpression argument = ((RosettaExpression) _argument);
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("rosetta_attr_exists(");
          String _generateExpression = this.generateExpression(argument, iflvl);
          _builder.append(_generateExpression);
          _builder.append(")");
          _xblockexpression = _builder.toString();
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      if (expr instanceof RosettaBinaryOperation) {
        _matched=true;
        _switchResult = this.binaryExpr(((RosettaBinaryOperation)expr), iflvl);
      }
    }
    if (!_matched) {
      if (expr instanceof RosettaAbsentExpression) {
        _matched=true;
        String _xblockexpression = null;
        {
          RosettaExpression _argument = ((RosettaAbsentExpression)expr).getArgument();
          final RosettaExpression argument = ((RosettaExpression) _argument);
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("(not rosetta_attr_exists(");
          String _generateExpression = this.generateExpression(argument, iflvl);
          _builder.append(_generateExpression);
          _builder.append("))");
          _xblockexpression = _builder.toString();
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      if (expr instanceof RosettaReference) {
        _matched=true;
        _switchResult = this.reference(((RosettaReference)expr), iflvl);
      }
    }
    if (!_matched) {
      if (expr instanceof RosettaNumberLiteral) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        BigDecimal _value = ((RosettaNumberLiteral)expr).getValue();
        _builder.append(_value);
        _switchResult = _builder.toString();
      }
    }
    if (!_matched) {
      if (expr instanceof RosettaBooleanLiteral) {
        _matched=true;
        String _xblockexpression = null;
        {
          final String trimmedValue = Boolean.valueOf(((RosettaBooleanLiteral)expr).isValue()).toString();
          String _xifexpression = null;
          boolean _equals = trimmedValue.equals("true");
          if (_equals) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("True");
            _xifexpression = _builder.toString();
          } else {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("False");
            _xifexpression = _builder_1.toString();
          }
          _xblockexpression = _xifexpression;
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      if (expr instanceof RosettaIntLiteral) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        BigInteger _value = ((RosettaIntLiteral)expr).getValue();
        _builder.append(_value);
        _switchResult = _builder.toString();
      }
    }
    if (!_matched) {
      if (expr instanceof RosettaStringLiteral) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("\"");
        String _value = ((RosettaStringLiteral)expr).getValue();
        _builder.append(_value);
        _builder.append("\"");
        _switchResult = _builder.toString();
      }
    }
    if (!_matched) {
      if (expr instanceof RosettaOnlyElement) {
        _matched=true;
        String _xblockexpression = null;
        {
          RosettaExpression _argument = ((RosettaOnlyElement)expr).getArgument();
          final RosettaExpression argument = ((RosettaExpression) _argument);
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("get_only_element(");
          String _generateExpression = this.generateExpression(argument, iflvl);
          _builder.append(_generateExpression);
          _builder.append(")");
          _xblockexpression = _builder.toString();
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      if (expr instanceof RosettaEnumValueReference) {
        _matched=true;
        String _xblockexpression = null;
        {
          final String value = EnumHelper.convertValue(((RosettaEnumValueReference)expr).getValue());
          StringConcatenation _builder = new StringConcatenation();
          RosettaEnumeration _enumeration = ((RosettaEnumValueReference)expr).getEnumeration();
          _builder.append(_enumeration);
          _builder.append(".");
          _builder.append(value);
          _xblockexpression = _builder.toString();
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      if (expr instanceof RosettaOnlyExistsExpression) {
        _matched=true;
        String _xblockexpression = null;
        {
          RosettaOnlyExistsExpression aux = ((RosettaOnlyExistsExpression) expr);
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("self.check_one_of_constraint(self, ");
          String _generateExpression = this.generateExpression(aux.getArgs().get(0), iflvl);
          _builder.append(_generateExpression);
          _builder.append(")");
          _xblockexpression = _builder.toString();
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      if (expr instanceof RosettaCountOperation) {
        _matched=true;
        String _xblockexpression = null;
        {
          RosettaExpression _argument = ((RosettaCountOperation)expr).getArgument();
          final RosettaExpression argument = ((RosettaExpression) _argument);
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("rosetta_count(");
          String _generateExpression = this.generateExpression(argument, iflvl);
          _builder.append(_generateExpression);
          _builder.append(")");
          _xblockexpression = _builder.toString();
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      if (expr instanceof ListLiteral) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("[");
        {
          EList<RosettaExpression> _elements = ((ListLiteral)expr).getElements();
          boolean _hasElements = false;
          for(final RosettaExpression arg : _elements) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate(", ", "");
            }
            String _generateExpression = this.generateExpression(arg, iflvl);
            _builder.append(_generateExpression);
          }
        }
        _builder.append("]");
        _switchResult = _builder.toString();
      }
    }
    if (!_matched) {
      if (expr instanceof DistinctOperation) {
        _matched=true;
        final String argument = this.generateExpression(((DistinctOperation)expr).getArgument(), iflvl);
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("set(");
        _builder.append(argument);
        _builder.append(")");
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (expr instanceof SortOperation) {
        _matched=true;
        final String argument = this.generateExpression(((SortOperation)expr).getArgument(), iflvl);
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("sorted(");
        _builder.append(argument);
        _builder.append(")");
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (expr instanceof ThenOperation) {
        _matched=true;
        final InlineFunction funcExpr = ((ThenOperation)expr).getFunction();
        final String argExpr = this.generateExpression(((ThenOperation)expr).getArgument(), iflvl);
        final String body = this.generateExpression(funcExpr.getBody(), iflvl);
        final Function1<ClosureParameter, String> _function = (ClosureParameter it) -> {
          return it.getName();
        };
        final String funcParams = IterableExtensions.join(ListExtensions.<ClosureParameter, String>map(funcExpr.getParameters(), _function), ", ");
        String _xifexpression = null;
        boolean _isEmpty = funcParams.isEmpty();
        if (_isEmpty) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("(lambda item: ");
          _builder.append(body);
          _builder.append(")");
          _xifexpression = _builder.toString();
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("(lambda ");
          _builder_1.append(funcParams);
          _builder_1.append(": ");
          _builder_1.append(body);
          _builder_1.append(")");
          _xifexpression = _builder_1.toString();
        }
        final String lambdaFunction = _xifexpression;
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append(lambdaFunction);
        _builder_2.append("(");
        _builder_2.append(argExpr);
        _builder_2.append(")");
        return _builder_2.toString();
      }
    }
    if (!_matched) {
      if (expr instanceof LastOperation) {
        _matched=true;
        final String argument = this.generateExpression(((LastOperation)expr).getArgument(), iflvl);
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(argument);
        _builder.append("[-1]");
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (expr instanceof SumOperation) {
        _matched=true;
        final String argument = this.generateExpression(((SumOperation)expr).getArgument(), iflvl);
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("sum(");
        _builder.append(argument);
        _builder.append(")");
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (expr instanceof FirstOperation) {
        _matched=true;
        final String argument = this.generateExpression(((FirstOperation)expr).getArgument(), iflvl);
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(argument);
        _builder.append("[0]");
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (expr instanceof FilterOperation) {
        _matched=true;
        final String argument = this.generateExpression(((FilterOperation)expr).getArgument(), iflvl);
        final String filterExpression = this.generateExpression(((FilterOperation)expr).getFunction().getBody(), iflvl);
        final String filterCall = (((("rosetta_filter(" + argument) + ", lambda item: ") + filterExpression) + ")");
        return filterCall;
      }
    }
    if (!_matched) {
      if (expr instanceof MapOperation) {
        _matched=true;
        InlineFunction _function = ((MapOperation)expr).getFunction();
        final InlineFunction inlineFunc = ((InlineFunction) _function);
        final Function1<ClosureParameter, String> _function_1 = (ClosureParameter it) -> {
          return it.getName();
        };
        final String funcParameters = IterableExtensions.join(ListExtensions.<ClosureParameter, String>map(inlineFunc.getParameters(), _function_1), ", ");
        final String funcBody = this.generateExpression(inlineFunc.getBody(), iflvl);
        final String lambdaFunction = ("lambda item: " + funcBody);
        final String argument = this.generateExpression(((MapOperation)expr).getArgument(), iflvl);
        final String pythonMapOperation = (((("map(" + lambdaFunction) + ", ") + argument) + ")");
        return pythonMapOperation;
      }
    }
    if (!_matched) {
      if (expr instanceof AsKeyOperation) {
        _matched=true;
        final String argument = this.generateExpression(((AsKeyOperation)expr).getArgument(), iflvl);
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("{");
        _builder.append(argument);
        _builder.append(": True}");
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (expr instanceof FlattenOperation) {
        _matched=true;
        final String nestedListExpr = this.generateExpression(((FlattenOperation)expr).getArgument(), iflvl);
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("flatten_list(");
        _builder.append(nestedListExpr);
        _builder.append(")");
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (expr instanceof RosettaConstructorExpression) {
        _matched=true;
        TypeCall _typeCall = ((RosettaConstructorExpression)expr).getTypeCall();
        RosettaType _type = null;
        if (_typeCall!=null) {
          _type=_typeCall.getType();
        }
        String _name = null;
        if (_type!=null) {
          _name=_type.getName();
        }
        final String type = _name;
        final EList<ConstructorKeyValuePair> keyValuePairs = ((RosettaConstructorExpression)expr).getValues();
        String _xifexpression = null;
        if ((type != null)) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append(type);
          _builder.append("(");
          {
            boolean _hasElements = false;
            for(final ConstructorKeyValuePair pair : keyValuePairs) {
              if (!_hasElements) {
                _hasElements = true;
              } else {
                _builder.appendImmediate(", ", "");
              }
              String _name_1 = pair.getKey().getName();
              _builder.append(_name_1);
              _builder.append("=");
              String _generateExpression = this.generateExpression(pair.getValue(), iflvl);
              _builder.append(_generateExpression);
            }
          }
          _builder.append(")");
          _xifexpression = _builder.toString();
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("{");
          {
            boolean _hasElements_1 = false;
            for(final ConstructorKeyValuePair pair_1 : keyValuePairs) {
              if (!_hasElements_1) {
                _hasElements_1 = true;
              } else {
                _builder_1.appendImmediate(", ", "");
              }
              _builder_1.append("\'");
              String _name_2 = pair_1.getKey().getName();
              _builder_1.append(_name_2);
              _builder_1.append("\': ");
              String _generateExpression_1 = this.generateExpression(pair_1.getValue(), iflvl);
              _builder_1.append(_generateExpression_1);
            }
          }
          _builder_1.append("}");
          _xifexpression = _builder_1.toString();
        }
        final String pythonConstructor = _xifexpression;
        return pythonConstructor;
      }
    }
    if (!_matched) {
      if (expr instanceof ToStringOperation) {
        _matched=true;
        final String argument = this.generateExpression(((ToStringOperation)expr).getArgument(), iflvl);
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("rosetta_str(");
        _builder.append(argument);
        _builder.append(")");
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (expr instanceof ToEnumOperation) {
        _matched=true;
        final String argument = this.generateExpression(((ToEnumOperation)expr).getArgument(), iflvl);
        StringConcatenation _builder = new StringConcatenation();
        String _name = ((ToEnumOperation)expr).getEnumeration().getName();
        _builder.append(_name);
        _builder.append("(");
        _builder.append(argument);
        _builder.append(")");
        return _builder.toString();
      }
    }
    if (!_matched) {
      Class<? extends RosettaExpression> _class = null;
      if (expr!=null) {
        _class=expr.getClass();
      }
      String _simpleName = null;
      if (_class!=null) {
        _simpleName=_class.getSimpleName();
      }
      String _plus = ("Unsupported expression type of " + _simpleName);
      throw new UnsupportedOperationException(_plus);
    }
    return _switchResult;
  }

  protected String reference(final RosettaReference expr, final int iflvl) {
    String _switchResult = null;
    boolean _matched = false;
    if (expr instanceof RosettaImplicitVariable) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      String _name = ((RosettaImplicitVariable)expr).getName();
      _builder.append(_name);
      _switchResult = _builder.toString();
    }
    if (!_matched) {
      if (expr instanceof RosettaSymbolReference) {
        _matched=true;
        _switchResult = this.symbolReference(((RosettaSymbolReference)expr), iflvl);
      }
    }
    return _switchResult;
  }

  public String symbolReference(final RosettaSymbolReference expr, final int iflvl) {
    String _xblockexpression = null;
    {
      final RosettaSymbol s = expr.getSymbol();
      String _switchResult = null;
      boolean _matched = false;
      if (s instanceof Data) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _name = s.getName();
        _builder.append(_name);
        _switchResult = _builder.toString();
      }
      if (!_matched) {
        if (s instanceof Attribute) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("rosetta_resolve_attr(self, \"");
          String _name = ((Attribute)s).getName();
          _builder.append(_name);
          _builder.append("\")");
          _switchResult = _builder.toString();
        }
      }
      if (!_matched) {
        if (s instanceof RosettaEnumeration) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          String _name = ((RosettaEnumeration)s).getName();
          _builder.append(_name);
          _switchResult = _builder.toString();
        }
      }
      if (!_matched) {
        if (s instanceof RosettaCallableWithArgs) {
          _matched=true;
          _switchResult = this.callableWithArgsCall(((RosettaCallableWithArgs)s), expr, iflvl);
        }
      }
      if (!_matched) {
        if (s instanceof ShortcutDeclaration) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("rosetta_resolve_attr(self, \"");
          String _name = ((ShortcutDeclaration)s).getName();
          _builder.append(_name);
          _builder.append("\")");
          _switchResult = _builder.toString();
        }
      }
      if (!_matched) {
        if (s instanceof ClosureParameter) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("rosetta_resolve_attr(self, \"");
          String _name = ((ClosureParameter)s).getName();
          _builder.append(_name);
          _builder.append("\")");
          _switchResult = _builder.toString();
        }
      }
      if (!_matched) {
        String _simpleName = s.getClass().getSimpleName();
        String _plus = ("Unsupported callable type of " + _simpleName);
        throw new UnsupportedOperationException(_plus);
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }

  public String callableWithArgsCall(final RosettaCallableWithArgs s, final RosettaSymbolReference expr, final int iflvl) {
    String _xblockexpression = null;
    {
      if ((s instanceof FunctionImpl)) {
        String _name = ((FunctionImpl)s).getName();
        EObject _eContainer = ((FunctionImpl)s).eContainer();
        String _name_1 = ((RosettaModel) _eContainer).getName();
        String _plus = (_name_1 + ".");
        String _plus_1 = (_plus + "functions");
        this.addImportsFromConditions(_name, _plus_1);
      } else {
        EObject _eContainer_1 = s.eContainer();
        this.addImportsFromConditions(s.getName(), ((RosettaModel) _eContainer_1).getName());
      }
      StringConcatenation _builder = new StringConcatenation();
      {
        EList<RosettaExpression> _args = expr.getArgs();
        boolean _hasElements = false;
        for(final RosettaExpression arg : _args) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate(", ", "");
          }
          String _generateExpression = this.generateExpression(arg, iflvl);
          _builder.append(_generateExpression);
        }
      }
      String args = _builder.toString();
      StringConcatenation _builder_1 = new StringConcatenation();
      String _name_2 = s.getName();
      _builder_1.append(_name_2);
      _builder_1.append("(");
      _builder_1.append(args);
      _builder_1.append(")");
      _xblockexpression = _builder_1.toString();
    }
    return _xblockexpression;
  }

  public String binaryExpr(final RosettaBinaryOperation expr, final int iflvl) {
    String _xifexpression = null;
    if ((expr instanceof ModifiableBinaryOperation)) {
      String _xifexpression_1 = null;
      CardinalityModifier _cardMod = ((ModifiableBinaryOperation)expr).getCardMod();
      boolean _tripleNotEquals = (_cardMod != null);
      if (_tripleNotEquals) {
        String _xifexpression_2 = null;
        String _operator = ((ModifiableBinaryOperation)expr).getOperator();
        boolean _equals = Objects.equal(_operator, "<>");
        if (_equals) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("any_elements(");
          String _generateExpression = this.generateExpression(((ModifiableBinaryOperation)expr).getLeft(), iflvl);
          _builder.append(_generateExpression);
          _builder.append(", \"");
          String _operator_1 = ((ModifiableBinaryOperation)expr).getOperator();
          _builder.append(_operator_1);
          _builder.append("\", ");
          String _generateExpression_1 = this.generateExpression(((ModifiableBinaryOperation)expr).getRight(), iflvl);
          _builder.append(_generateExpression_1);
          _builder.append(")");
          _xifexpression_2 = _builder.toString();
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("all_elements(");
          String _generateExpression_2 = this.generateExpression(((ModifiableBinaryOperation)expr).getLeft(), iflvl);
          _builder_1.append(_generateExpression_2);
          _builder_1.append(", \"");
          String _operator_2 = ((ModifiableBinaryOperation)expr).getOperator();
          _builder_1.append(_operator_2);
          _builder_1.append("\", ");
          String _generateExpression_3 = this.generateExpression(((ModifiableBinaryOperation)expr).getRight(), iflvl);
          _builder_1.append(_generateExpression_3);
          _builder_1.append(")");
          _xifexpression_2 = _builder_1.toString();
        }
        _xifexpression_1 = _xifexpression_2;
      }
      _xifexpression = _xifexpression_1;
    } else {
      String _switchResult = null;
      String _operator_3 = expr.getOperator();
      if (_operator_3 != null) {
        switch (_operator_3) {
          case "=":
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("(");
            String _generateExpression_4 = this.generateExpression(expr.getLeft(), iflvl);
            _builder_2.append(_generateExpression_4);
            _builder_2.append(" == ");
            String _generateExpression_5 = this.generateExpression(expr.getRight(), iflvl);
            _builder_2.append(_generateExpression_5);
            _builder_2.append(")");
            _switchResult = _builder_2.toString();
            break;
          case "<>":
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("(");
            String _generateExpression_6 = this.generateExpression(expr.getLeft(), iflvl);
            _builder_3.append(_generateExpression_6);
            _builder_3.append(" != ");
            String _generateExpression_7 = this.generateExpression(expr.getRight(), iflvl);
            _builder_3.append(_generateExpression_7);
            _builder_3.append(")");
            _switchResult = _builder_3.toString();
            break;
          case "contains":
            StringConcatenation _builder_4 = new StringConcatenation();
            _builder_4.append("contains(");
            String _generateExpression_8 = this.generateExpression(expr.getLeft(), iflvl);
            _builder_4.append(_generateExpression_8);
            _builder_4.append(", ");
            String _generateExpression_9 = this.generateExpression(expr.getRight(), iflvl);
            _builder_4.append(_generateExpression_9);
            _builder_4.append(")");
            _switchResult = _builder_4.toString();
            break;
          case "disjoint":
            StringConcatenation _builder_5 = new StringConcatenation();
            _builder_5.append("disjoint(");
            String _generateExpression_10 = this.generateExpression(expr.getLeft(), iflvl);
            _builder_5.append(_generateExpression_10);
            _builder_5.append(", ");
            String _generateExpression_11 = this.generateExpression(expr.getRight(), iflvl);
            _builder_5.append(_generateExpression_11);
            _builder_5.append(")");
            _switchResult = _builder_5.toString();
            break;
          case "join":
            StringConcatenation _builder_6 = new StringConcatenation();
            _builder_6.append("join(");
            String _generateExpression_12 = this.generateExpression(expr.getLeft(), iflvl);
            _builder_6.append(_generateExpression_12);
            _builder_6.append(", ");
            String _generateExpression_13 = this.generateExpression(expr.getRight(), iflvl);
            _builder_6.append(_generateExpression_13);
            _builder_6.append(")");
            _switchResult = _builder_6.toString();
            break;
          default:
            StringConcatenation _builder_7 = new StringConcatenation();
            _builder_7.append("(");
            String _generateExpression_14 = this.generateExpression(expr.getLeft(), iflvl);
            _builder_7.append(_generateExpression_14);
            _builder_7.append(" ");
            String _operator_4 = expr.getOperator();
            _builder_7.append(_operator_4);
            _builder_7.append(" ");
            String _generateExpression_15 = this.generateExpression(expr.getRight(), iflvl);
            _builder_7.append(_generateExpression_15);
            _builder_7.append(")");
            _switchResult = _builder_7.toString();
            break;
        }
      } else {
        StringConcatenation _builder_7 = new StringConcatenation();
        _builder_7.append("(");
        String _generateExpression_14 = this.generateExpression(expr.getLeft(), iflvl);
        _builder_7.append(_generateExpression_14);
        _builder_7.append(" ");
        String _operator_4 = expr.getOperator();
        _builder_7.append(_operator_4);
        _builder_7.append(" ");
        String _generateExpression_15 = this.generateExpression(expr.getRight(), iflvl);
        _builder_7.append(_generateExpression_15);
        _builder_7.append(")");
        _switchResult = _builder_7.toString();
      }
      _xifexpression = _switchResult;
    }
    return _xifexpression;
  }

  public boolean addImportsFromConditions(final String variable, final String namespace) {
    boolean _xblockexpression = false;
    {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("from ");
      _builder.append(namespace);
      _builder.append(".");
      _builder.append(variable);
      _builder.append(" import ");
      _builder.append(variable);
      final String import_ = _builder.toString();
      boolean _xifexpression = false;
      if ((this.importsFound != null)) {
        boolean _xifexpression_1 = false;
        boolean _contains = this.importsFound.contains(import_);
        boolean _not = (!_contains);
        if (_not) {
          _xifexpression_1 = this.importsFound.add(import_);
        }
        _xifexpression = _xifexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
}
