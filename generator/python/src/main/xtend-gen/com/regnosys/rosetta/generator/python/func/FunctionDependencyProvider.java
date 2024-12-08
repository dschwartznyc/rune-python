package com.regnosys.rosetta.generator.python.func;

import com.google.common.collect.Iterables;
import com.regnosys.rosetta.rosetta.RosettaEnumValueReference;
import com.regnosys.rosetta.rosetta.RosettaEnumeration;
import com.regnosys.rosetta.rosetta.RosettaExternalFunction;
import com.regnosys.rosetta.rosetta.RosettaRule;
import com.regnosys.rosetta.rosetta.RosettaSymbol;
import com.regnosys.rosetta.rosetta.RosettaType;
import com.regnosys.rosetta.rosetta.TypeCall;
import com.regnosys.rosetta.rosetta.expression.ConstructorKeyValuePair;
import com.regnosys.rosetta.rosetta.expression.InlineFunction;
import com.regnosys.rosetta.rosetta.expression.ListLiteral;
import com.regnosys.rosetta.rosetta.expression.RosettaBinaryOperation;
import com.regnosys.rosetta.rosetta.expression.RosettaConditionalExpression;
import com.regnosys.rosetta.rosetta.expression.RosettaConstructorExpression;
import com.regnosys.rosetta.rosetta.expression.RosettaExpression;
import com.regnosys.rosetta.rosetta.expression.RosettaFeatureCall;
import com.regnosys.rosetta.rosetta.expression.RosettaFunctionalOperation;
import com.regnosys.rosetta.rosetta.expression.RosettaLiteral;
import com.regnosys.rosetta.rosetta.expression.RosettaOnlyExistsExpression;
import com.regnosys.rosetta.rosetta.expression.RosettaReference;
import com.regnosys.rosetta.rosetta.expression.RosettaSymbolReference;
import com.regnosys.rosetta.rosetta.expression.RosettaUnaryOperation;
import com.regnosys.rosetta.rosetta.simple.Data;
import com.regnosys.rosetta.rosetta.simple.Function;
import com.regnosys.rosetta.types.RFunction;
import com.regnosys.rosetta.types.RObjectFactory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Determine the Rosetta dependencies for a Rosetta object
 */
@SuppressWarnings("all")
public class FunctionDependencyProvider {
  @Inject
  private RObjectFactory rTypeBuilderFactory;

  private Set<EObject> visited = new HashSet<EObject>();

  public Set<EObject> findDependencies(final EObject object) {
    boolean _contains = this.visited.contains(object);
    if (_contains) {
      return CollectionLiterals.<EObject>newHashSet();
    }
    Set<EObject> dependencies = ((Set<EObject>) null);
    boolean _matched = false;
    if (object instanceof RosettaBinaryOperation) {
      _matched=true;
      Set<EObject> _findDependencies = this.findDependencies(((RosettaBinaryOperation)object).getLeft());
      Set<EObject> _findDependencies_1 = this.findDependencies(((RosettaBinaryOperation)object).getRight());
      Iterable<EObject> _plus = Iterables.<EObject>concat(_findDependencies, _findDependencies_1);
      dependencies = CollectionLiterals.<EObject>newHashSet(((EObject[])Conversions.unwrapArray(_plus, EObject.class)));
    }
    if (!_matched) {
      if (object instanceof RosettaConditionalExpression) {
        _matched=true;
        Set<EObject> _findDependencies = this.findDependencies(((RosettaConditionalExpression)object).getIf());
        Set<EObject> _findDependencies_1 = this.findDependencies(((RosettaConditionalExpression)object).getIfthen());
        Iterable<EObject> _plus = Iterables.<EObject>concat(_findDependencies, _findDependencies_1);
        Set<EObject> _findDependencies_2 = this.findDependencies(((RosettaConditionalExpression)object).getElsethen());
        Iterable<EObject> _plus_1 = Iterables.<EObject>concat(_plus, _findDependencies_2);
        dependencies = CollectionLiterals.<EObject>newHashSet(((EObject[])Conversions.unwrapArray(_plus_1, EObject.class)));
      }
    }
    if (!_matched) {
      if (object instanceof RosettaOnlyExistsExpression) {
        _matched=true;
        dependencies = this.findDependenciesFromIterable(((RosettaOnlyExistsExpression)object).getArgs());
      }
    }
    if (!_matched) {
      if (object instanceof RosettaFunctionalOperation) {
        _matched=true;
        Set<EObject> _findDependencies = this.findDependencies(((RosettaFunctionalOperation)object).getArgument());
        Set<EObject> _findDependencies_1 = this.findDependencies(((RosettaFunctionalOperation)object).getFunction());
        Iterable<EObject> _plus = Iterables.<EObject>concat(_findDependencies, _findDependencies_1);
        dependencies = CollectionLiterals.<EObject>newHashSet(((EObject[])Conversions.unwrapArray(_plus, EObject.class)));
      }
    }
    if (!_matched) {
      if (object instanceof RosettaUnaryOperation) {
        _matched=true;
        dependencies = this.findDependencies(((RosettaUnaryOperation)object).getArgument());
      }
    }
    if (!_matched) {
      if (object instanceof RosettaFeatureCall) {
        _matched=true;
        dependencies = this.findDependencies(((RosettaFeatureCall)object).getReceiver());
      }
    }
    if (!_matched) {
      if (object instanceof RosettaSymbolReference) {
        _matched=true;
        Set<EObject> _findDependencies = this.findDependencies(((RosettaSymbolReference)object).getSymbol());
        Set<EObject> _findDependenciesFromIterable = this.findDependenciesFromIterable(((RosettaSymbolReference)object).getArgs());
        Iterable<EObject> _plus = Iterables.<EObject>concat(_findDependencies, _findDependenciesFromIterable);
        dependencies = CollectionLiterals.<EObject>newHashSet(((EObject[])Conversions.unwrapArray(_plus, EObject.class)));
      }
    }
    if (!_matched) {
      if (object instanceof Function) {
        _matched=true;
      }
      if (!_matched) {
        if (object instanceof Data) {
          _matched=true;
        }
      }
      if (!_matched) {
        if (object instanceof RosettaEnumeration) {
          _matched=true;
        }
      }
      if (_matched) {
        dependencies = CollectionLiterals.<EObject>newHashSet(object);
      }
    }
    if (!_matched) {
      if (object instanceof InlineFunction) {
        _matched=true;
        dependencies = this.findDependencies(((InlineFunction)object).getBody());
      }
    }
    if (!_matched) {
      if (object instanceof ListLiteral) {
        _matched=true;
        final Function1<RosettaExpression, Set<EObject>> _function = (RosettaExpression it) -> {
          return this.findDependencies(it);
        };
        dependencies = CollectionLiterals.<EObject>newHashSet(((EObject[])Conversions.unwrapArray(IterableExtensions.<RosettaExpression, EObject>flatMap(((ListLiteral)object).getElements(), _function), EObject.class)));
      }
    }
    if (!_matched) {
      if (object instanceof RosettaConstructorExpression) {
        _matched=true;
        HashSet<RosettaType> _xifexpression = null;
        TypeCall _typeCall = ((RosettaConstructorExpression)object).getTypeCall();
        RosettaType _type = null;
        if (_typeCall!=null) {
          _type=_typeCall.getType();
        }
        boolean _tripleNotEquals = (_type != null);
        if (_tripleNotEquals) {
          _xifexpression = CollectionLiterals.<RosettaType>newHashSet(((RosettaConstructorExpression)object).getTypeCall().getType());
        } else {
          _xifexpression = CollectionLiterals.<RosettaType>newHashSet();
        }
        final HashSet<RosettaType> typeDependencies = _xifexpression;
        final Function1<ConstructorKeyValuePair, Set<EObject>> _function = (ConstructorKeyValuePair valuePair) -> {
          return this.findDependencies(valuePair.getValue());
        };
        final Iterable<EObject> keyValuePairsDependencies = Iterables.<EObject>concat(ListExtensions.<ConstructorKeyValuePair, Set<EObject>>map(((RosettaConstructorExpression)object).getValues(), _function));
        Iterable<EObject> _plus = Iterables.<EObject>concat(typeDependencies, keyValuePairsDependencies);
        dependencies = CollectionLiterals.<EObject>newHashSet(((EObject[])Conversions.unwrapArray(_plus, EObject.class)));
      }
    }
    if (!_matched) {
      if (object instanceof RosettaExternalFunction) {
        _matched=true;
      }
      if (!_matched) {
        if (object instanceof RosettaEnumValueReference) {
          _matched=true;
        }
      }
      if (!_matched) {
        if (object instanceof RosettaLiteral) {
          _matched=true;
        }
      }
      if (!_matched) {
        if (object instanceof RosettaReference) {
          _matched=true;
        }
      }
      if (!_matched) {
        if (object instanceof RosettaSymbol) {
          _matched=true;
        }
      }
      if (_matched) {
        dependencies = CollectionLiterals.<EObject>newHashSet();
      }
    }
    if (!_matched) {
      if ((object != null)) {
        StringConcatenation _builder = new StringConcatenation();
        EClass _eClass = null;
        if (object!=null) {
          _eClass=object.eClass();
        }
        String _name = null;
        if (_eClass!=null) {
          _name=_eClass.getName();
        }
        _builder.append(_name);
        _builder.append(": the conversion for this type is not yet implemented.");
        throw new IllegalArgumentException(_builder.toString());
      } else {
        dependencies = CollectionLiterals.<EObject>newHashSet();
      }
    }
    if ((dependencies != null)) {
      this.visited.add(object);
    }
    return dependencies;
  }

  public Set<EObject> findDependenciesFromIterable(final Iterable<? extends EObject> objects) {
    HashSet<EObject> _xblockexpression = null;
    {
      final Function1<EObject, Set<EObject>> _function = (EObject object) -> {
        return this.findDependencies(object);
      };
      final Set<EObject> allDependencies = IterableExtensions.<EObject>toSet(Iterables.<EObject>concat(IterableExtensions.map(objects, _function)));
      _xblockexpression = CollectionLiterals.<EObject>newHashSet(((EObject[])Conversions.unwrapArray(allDependencies, EObject.class)));
    }
    return _xblockexpression;
  }

  public Set<RFunction> rFunctionDependencies(final RosettaExpression expression) {
    Set<RFunction> _xblockexpression = null;
    {
      final Function1<RosettaSymbolReference, RosettaSymbol> _function = (RosettaSymbolReference it) -> {
        return it.getSymbol();
      };
      final List<RosettaSymbol> rosettaSymbols = ListExtensions.<RosettaSymbolReference, RosettaSymbol>map(EcoreUtil2.<RosettaSymbolReference>eAllOfType(expression, RosettaSymbolReference.class), _function);
      final Function1<Function, RFunction> _function_1 = (Function it) -> {
        return this.rTypeBuilderFactory.buildRFunction(it);
      };
      Iterable<RFunction> _map = IterableExtensions.<Function, RFunction>map(Iterables.<Function>filter(rosettaSymbols, Function.class), _function_1);
      final Function1<RosettaRule, RFunction> _function_2 = (RosettaRule it) -> {
        return this.rTypeBuilderFactory.buildRFunction(it);
      };
      Iterable<RFunction> _map_1 = IterableExtensions.<RosettaRule, RFunction>map(Iterables.<RosettaRule>filter(rosettaSymbols, RosettaRule.class), _function_2);
      _xblockexpression = IterableExtensions.<RFunction>toSet(Iterables.<RFunction>concat(_map, _map_1));
    }
    return _xblockexpression;
  }

  public Set<RFunction> rFunctionDependencies(final Iterable<? extends RosettaExpression> expressions) {
    HashSet<RFunction> _xblockexpression = null;
    {
      final Function1<RosettaExpression, Set<RFunction>> _function = (RosettaExpression it) -> {
        return this.rFunctionDependencies(it);
      };
      final Set<RFunction> allFunctionDependencies = IterableExtensions.<RFunction>toSet(IterableExtensions.flatMap(expressions, _function));
      _xblockexpression = CollectionLiterals.<RFunction>newHashSet(((RFunction[])Conversions.unwrapArray(allFunctionDependencies, RFunction.class)));
    }
    return _xblockexpression;
  }

  public void reset() {
    this.visited.clear();
  }
}
