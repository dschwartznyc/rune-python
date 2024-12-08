package com.regnosys.rosetta.generator.python.func;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.regnosys.rosetta.generator.python.expressions.PythonExpressionGenerator;
import com.regnosys.rosetta.generator.python.util.PythonModelGeneratorUtil;
import com.regnosys.rosetta.generator.python.util.PythonTranslator;
import com.regnosys.rosetta.rosetta.RosettaEnumeration;
import com.regnosys.rosetta.rosetta.RosettaModel;
import com.regnosys.rosetta.rosetta.RosettaType;
import com.regnosys.rosetta.rosetta.TypeCall;
import com.regnosys.rosetta.rosetta.simple.AssignPathRoot;
import com.regnosys.rosetta.rosetta.simple.Attribute;
import com.regnosys.rosetta.rosetta.simple.Condition;
import com.regnosys.rosetta.rosetta.simple.Data;
import com.regnosys.rosetta.rosetta.simple.Function;
import com.regnosys.rosetta.rosetta.simple.Operation;
import com.regnosys.rosetta.rosetta.simple.Segment;
import com.regnosys.rosetta.rosetta.simple.ShortcutDeclaration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("all")
public class PythonFunctionGenerator {
  private static final Logger LOGGER = LoggerFactory.getLogger(PythonFunctionGenerator.class);

  private List<String> importsFound = CollectionLiterals.<String>newArrayList();

  @Inject
  private FunctionDependencyProvider functionDependencyProvider;

  @Inject
  private PythonExpressionGenerator expressionGenerator;

  public Map<String, ? extends CharSequence> generate(final List<Function> rosettaFunctions, final String version) {
    final HashMap<String, String> result = new HashMap<String, String>();
    int _size = rosettaFunctions.size();
    boolean _greaterThan = (_size > 0);
    if (_greaterThan) {
      for (final Function func : rosettaFunctions) {
        {
          EObject _eContainer = func.eContainer();
          final RosettaModel tr = ((RosettaModel) _eContainer);
          final String namespace = tr.getName();
          try {
            final CharSequence funcs = this.generateFunctions(func, version);
            String _pyFunctionFileName = PythonModelGeneratorUtil.toPyFunctionFileName(namespace, func.getName());
            String _createImportsFunc = PythonModelGeneratorUtil.createImportsFunc(func.getName());
            String _plus = (_createImportsFunc + funcs);
            result.put(_pyFunctionFileName, _plus);
          } catch (final Throwable _t) {
            if (_t instanceof Exception) {
              final Exception ex = (Exception)_t;
              PythonFunctionGenerator.LOGGER.error("Exception occurred generating func {}", func.getName(), ex);
            } else {
              throw Exceptions.sneakyThrow(_t);
            }
          }
        }
      }
    }
    return result;
  }

  private CharSequence generateFunctions(final Function function, final String version) {
    CharSequence _xblockexpression = null;
    {
      final Set<EObject> dependencies = this.collectFunctionDependencies(function);
      StringConcatenation _builder = new StringConcatenation();
      String _generateImports = this.generateImports(dependencies, function);
      _builder.append(_generateImports);
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.newLine();
      _builder.append("@replaceable");
      _builder.newLine();
      _builder.append("def ");
      String _name = function.getName();
      _builder.append(_name);
      CharSequence _generatesInputs = this.generatesInputs(function);
      _builder.append(_generatesInputs);
      _builder.append(":");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      String _generateDescription = this.generateDescription(function);
      _builder.append(_generateDescription, "    ");
      _builder.newLineIfNotEmpty();
      {
        int _size = function.getConditions().size();
        boolean _greaterThan = (_size > 0);
        if (_greaterThan) {
          _builder.append("    ");
          _builder.append("_pre_registry = {}");
          _builder.newLine();
        }
      }
      {
        int _size_1 = function.getPostConditions().size();
        boolean _greaterThan_1 = (_size_1 > 0);
        if (_greaterThan_1) {
          _builder.append("    ");
          _builder.append("_post_registry = {}");
          _builder.newLine();
        }
      }
      _builder.append("    ");
      _builder.append("self = inspect.currentframe()");
      _builder.newLine();
      _builder.append("    ");
      _builder.newLine();
      _builder.append("    ");
      CharSequence _generateConditions = this.generateConditions(function);
      _builder.append(_generateConditions, "    ");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.newLine();
      CharSequence _generateIfBlocks = this.generateIfBlocks(function);
      _builder.append(_generateIfBlocks);
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      String _generateAlias = this.generateAlias(function);
      _builder.append(_generateAlias, "    ");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      StringBuilder _generateOperations = this.generateOperations(function);
      _builder.append(_generateOperations, "    ");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      CharSequence _generatesOutput = this.generatesOutput(function);
      _builder.append(_generatesOutput, "    ");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("sys.modules[__name__].__class__ = create_module_attr_guardian(sys.modules[__name__].__class__)");
      _builder.newLine();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  private String generateImports(final Iterable<EObject> dependencies, final Function function) {
    final StringBuilder imports = new StringBuilder();
    for (final EObject dependency : dependencies) {
      {
        EObject _eContainer = dependency.eContainer();
        final RosettaModel tr = ((RosettaModel) _eContainer);
        final String importPath = tr.getName();
        if ((dependency instanceof Function)) {
          imports.append("from ").append(importPath).append(".functions.").append(((Function)dependency).getName()).append(" import ").append(((Function)dependency).getName()).append("\n");
        } else {
          if ((dependency instanceof RosettaEnumeration)) {
            imports.append("from ").append(importPath).append(".").append(((RosettaEnumeration)dependency).getName()).append(" import ").append(((RosettaEnumeration)dependency).getName()).append("\n");
          } else {
            if ((dependency instanceof Data)) {
              imports.append("from ").append(importPath).append(".").append(((Data)dependency).getName()).append(" import ").append(((Data)dependency).getName()).append("\n");
            }
          }
        }
      }
    }
    imports.append("\n");
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("__all__ = [");
    String _name = function.getName();
    String _plus = ("\'" + _name);
    String _plus_1 = (_plus + "\'");
    _builder.append(_plus_1);
    _builder.append("]");
    imports.append(_builder);
    return imports.toString();
  }

  private CharSequence generatesOutput(final Function function) {
    CharSequence _xblockexpression = null;
    {
      final Attribute output = function.getOutput();
      CharSequence _xifexpression = null;
      if ((output != null)) {
        StringConcatenation _builder = new StringConcatenation();
        {
          if (((function.getOperations().size() == 0) && (function.getShortcuts().size() == 0))) {
            String _name = output.getName();
            _builder.append(_name);
            _builder.append(" = rosetta_resolve_attr(self, \"");
            String _name_1 = output.getName();
            _builder.append(_name_1);
            _builder.append("\")");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        CharSequence _generatePostConditions = this.generatePostConditions(function);
        _builder.append(_generatePostConditions);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("return ");
        String _name_2 = output.getName();
        _builder.append(_name_2);
        _builder.newLineIfNotEmpty();
        _xifexpression = _builder;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  private CharSequence generatesInputs(final Function function) {
    CharSequence _xblockexpression = null;
    {
      final EList<Attribute> inputs = function.getInputs();
      final Attribute output = function.getOutput();
      String result = "(";
      for (final Attribute input : inputs) {
        {
          final String typeName = input.getTypeCall().getType().getName();
          String _xifexpression = null;
          int _sup = input.getCard().getSup();
          boolean _equals = (_sup == 0);
          if (_equals) {
            String _pythonBasicType = PythonTranslator.toPythonBasicType(typeName);
            String _plus = ("list[" + _pythonBasicType);
            _xifexpression = (_plus + "]");
          } else {
            _xifexpression = PythonTranslator.toPythonBasicType(typeName);
          }
          final String type = _xifexpression;
          String _result = result;
          String _name = input.getName();
          String _plus_1 = (_name + ": ");
          String _plus_2 = (_plus_1 + type);
          result = (_result + _plus_2);
          int _inf = input.getCard().getInf();
          boolean _equals_1 = (_inf == 0);
          if (_equals_1) {
            String _result_1 = result;
            result = (_result_1 + " | None");
          }
          int _indexOf = inputs.indexOf(input);
          int _size = inputs.size();
          int _minus = (_size - 1);
          boolean _lessThan = (_indexOf < _minus);
          if (_lessThan) {
            String _result_2 = result;
            result = (_result_2 + ", ");
          }
        }
      }
      String _result = result;
      result = (_result + ") -> ");
      if ((output != null)) {
        String _result_1 = result;
        String _pythonBasicType = PythonTranslator.toPythonBasicType(output.getTypeCall().getType().getName());
        result = (_result_1 + _pythonBasicType);
      } else {
        String _result_2 = result;
        result = (_result_2 + "None");
      }
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(result);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  private String generateDescription(final Function function) {
    final EList<Attribute> inputs = function.getInputs();
    final Attribute output = function.getOutput();
    final String description = function.getDefinition();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\"\"\"");
    _builder.newLine();
    _builder.append(description);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("Parameters ");
    _builder.newLine();
    _builder.append("----------");
    _builder.newLine();
    {
      boolean _hasElements = false;
      for(final Attribute input : inputs) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("\n", "");
        }
        String _name = input.getName();
        _builder.append(_name);
        _builder.append(" : ");
        String _name_1 = input.getTypeCall().getType().getName();
        _builder.append(_name_1);
        _builder.newLineIfNotEmpty();
        String _definition = input.getDefinition();
        _builder.append(_definition);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("Returns");
    _builder.newLine();
    _builder.append("-------");
    _builder.newLine();
    {
      if ((output != null)) {
        String _name_2 = output.getName();
        _builder.append(_name_2);
        _builder.append(" : ");
        String _name_3 = output.getTypeCall().getType().getName();
        _builder.append(_name_3);
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("No Return");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("\"\"\"");
    _builder.newLine();
    return _builder.toString();
  }

  private Set<EObject> collectFunctionDependencies(final Function func) {
    final Set<EObject> dependencies = CollectionLiterals.<EObject>newHashSet();
    final Consumer<ShortcutDeclaration> _function = (ShortcutDeclaration shortcut) -> {
      dependencies.addAll(this.functionDependencyProvider.findDependencies(shortcut.getExpression()));
    };
    func.getShortcuts().forEach(_function);
    final Consumer<Operation> _function_1 = (Operation operation) -> {
      dependencies.addAll(this.functionDependencyProvider.findDependencies(operation.getExpression()));
    };
    func.getOperations().forEach(_function_1);
    EList<Condition> _conditions = func.getConditions();
    EList<Condition> _postConditions = func.getPostConditions();
    final Consumer<Condition> _function_2 = (Condition condition) -> {
      dependencies.addAll(this.functionDependencyProvider.findDependencies(condition.getExpression()));
    };
    Iterables.<Condition>concat(_conditions, _postConditions).forEach(_function_2);
    final Consumer<Attribute> _function_3 = (Attribute input) -> {
      TypeCall _typeCall = input.getTypeCall();
      RosettaType _type = null;
      if (_typeCall!=null) {
        _type=_typeCall.getType();
      }
      boolean _tripleNotEquals = (_type != null);
      if (_tripleNotEquals) {
        dependencies.add(input.getTypeCall().getType());
      }
    };
    func.getInputs().forEach(_function_3);
    Attribute _output = func.getOutput();
    TypeCall _typeCall = null;
    if (_output!=null) {
      _typeCall=_output.getTypeCall();
    }
    RosettaType _type = null;
    if (_typeCall!=null) {
      _type=_typeCall.getType();
    }
    boolean _tripleNotEquals = (_type != null);
    if (_tripleNotEquals) {
      dependencies.add(func.getOutput().getTypeCall().getType());
    }
    final Predicate<EObject> _function_4 = (EObject it) -> {
      return ((it instanceof Function) && Objects.equal(((Function) it).getName(), func.getName()));
    };
    dependencies.removeIf(_function_4);
    return dependencies;
  }

  private CharSequence generateIfBlocks(final Function function) {
    CharSequence _xblockexpression = null;
    {
      final ArrayList<Integer> levelList = CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0));
      StringConcatenation _builder = new StringConcatenation();
      {
        EList<ShortcutDeclaration> _shortcuts = function.getShortcuts();
        for(final ShortcutDeclaration shortcut : _shortcuts) {
          String _generateExpressionThenElse = this.expressionGenerator.generateExpressionThenElse(shortcut.getExpression(), levelList);
          _builder.append(_generateExpressionThenElse);
          _builder.newLineIfNotEmpty();
        }
      }
      {
        EList<Operation> _operations = function.getOperations();
        for(final Operation opeartion : _operations) {
          String _generateExpressionThenElse_1 = this.expressionGenerator.generateExpressionThenElse(opeartion.getExpression(), levelList);
          _builder.append(_generateExpressionThenElse_1);
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  private CharSequence generateConditions(final Function function) {
    StringConcatenation _builder = new StringConcatenation();
    {
      int _size = function.getConditions().size();
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        _builder.append("# conditions");
        _builder.newLine();
        String _generateFunctionConditions = this.expressionGenerator.generateFunctionConditions(function.getConditions(), "_pre_registry");
        _builder.append(_generateFunctionConditions);
        _builder.newLineIfNotEmpty();
        _builder.append("# Execute all registered conditions");
        _builder.newLine();
        _builder.append("execute_local_conditions(_pre_registry, \'Pre-condition\')");
        _builder.newLine();
      }
    }
    return _builder;
  }

  private CharSequence generatePostConditions(final Function function) {
    StringConcatenation _builder = new StringConcatenation();
    {
      int _size = function.getPostConditions().size();
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        _builder.append("# post-conditions");
        _builder.newLine();
        String _generateFunctionConditions = this.expressionGenerator.generateFunctionConditions(function.getPostConditions(), "_post_registry");
        _builder.append(_generateFunctionConditions);
        _builder.newLineIfNotEmpty();
        _builder.append("# Execute all registered post-conditions");
        _builder.newLine();
        _builder.append("execute_local_conditions(_post_registry, \'Post-condition\')");
        _builder.newLine();
      }
    }
    return _builder;
  }

  private String generateAlias(final Function function) {
    final String lineSeparator = System.getProperty("line.separator");
    StringBuilder result = new StringBuilder();
    int level = 0;
    EList<ShortcutDeclaration> _shortcuts = function.getShortcuts();
    for (final ShortcutDeclaration shortcut : _shortcuts) {
      {
        ArrayList<String> _arrayList = new ArrayList<String>();
        this.expressionGenerator.if_cond_blocks = _arrayList;
        final String expression = this.expressionGenerator.generateExpression(shortcut.getExpression(), level);
        final ArrayList<String> if_cond_blocks = this.expressionGenerator.if_cond_blocks;
        final boolean isEmpty = if_cond_blocks.isEmpty();
        if ((!isEmpty)) {
          int _level = level;
          level = (_level + 1);
        }
        StringConcatenation _builder = new StringConcatenation();
        String _name = shortcut.getName();
        _builder.append(_name);
        _builder.append(" = ");
        _builder.append(expression);
        String _plus = (_builder.toString() + lineSeparator);
        result.append(_plus);
      }
    }
    return result.toString();
  }

  private StringBuilder generateOperations(final Function function) {
    final String lineSeparator = System.getProperty("line.separator");
    StringBuilder result = new StringBuilder();
    int level = 0;
    Attribute _output = function.getOutput();
    boolean _tripleNotEquals = (_output != null);
    if (_tripleNotEquals) {
      final ArrayList<String> setNames = new ArrayList<String>();
      EList<Operation> _operations = function.getOperations();
      for (final Operation operation : _operations) {
        {
          final AssignPathRoot root = operation.getAssignRoot();
          final String expression = this.expressionGenerator.generateExpression(operation.getExpression(), level);
          final ArrayList<String> if_cond_blocks = this.expressionGenerator.if_cond_blocks;
          final boolean isEmpty = if_cond_blocks.isEmpty();
          if ((!isEmpty)) {
            int _level = level;
            level = (_level + 1);
          }
          boolean _isAdd = operation.isAdd();
          if (_isAdd) {
            String _generateAddOperation = this.generateAddOperation(root, operation, function, expression);
            String _plus = (_generateAddOperation + lineSeparator);
            result.append(_plus);
          } else {
            String _generateSetOperation = this.generateSetOperation(root, operation, function, expression, setNames);
            String _plus_1 = (_generateSetOperation + lineSeparator);
            result.append(_plus_1);
          }
        }
      }
    }
    return result;
  }

  private String generateAddOperation(final AssignPathRoot root, final Operation operation, final Function function, final String expression) {
    final String lineSeparator = System.getProperty("line.separator");
    final Attribute attribute = ((Attribute) root);
    final String fullPath = this.generateFullPath(this.getReversedAttributes(operation.getPath()), root.getName());
    String result = "";
    RosettaType _type = attribute.getTypeCall().getType();
    if ((_type instanceof RosettaEnumeration)) {
      Operation _head = IterableExtensions.<Operation>head(function.getOperations());
      boolean _equals = Objects.equal(operation, _head);
      if (_equals) {
        StringConcatenation _builder = new StringConcatenation();
        String _name = root.getName();
        _builder.append(_name);
        _builder.append(" = []");
        String _plus = (_builder.toString() + lineSeparator);
        result = _plus;
      }
      String _result = result;
      StringConcatenation _builder_1 = new StringConcatenation();
      String _name_1 = root.getName();
      _builder_1.append(_name_1);
      _builder_1.append(".extend(");
      _builder_1.append(expression);
      _builder_1.append(")");
      result = (_result + _builder_1);
    } else {
      Operation _head_1 = IterableExtensions.<Operation>head(function.getOperations());
      boolean _equals_1 = Objects.equal(operation, _head_1);
      if (_equals_1) {
        StringConcatenation _builder_2 = new StringConcatenation();
        String _name_2 = root.getName();
        _builder_2.append(_name_2);
        _builder_2.append(" = ");
        _builder_2.append(expression);
        result = _builder_2.toString();
      } else {
        StringConcatenation _builder_3 = new StringConcatenation();
        String _name_3 = root.getName();
        _builder_3.append(_name_3);
        _builder_3.append(".add_rosetta_attr(");
        _builder_3.append(fullPath);
        _builder_3.append(", ");
        _builder_3.append(expression);
        _builder_3.append(")");
        result = _builder_3.toString();
      }
    }
    return result;
  }

  /**
   * private def List<Operation> getNonAddOperations(Function function) {
   * return function.getOperations().filter[!isAdd()].toList()
   * }
   */
  private String generateSetOperation(final AssignPathRoot root, final Operation operation, final Function function, final String expression, final List<String> setNames) {
    String result = "";
    final Attribute attributeRoot = ((Attribute) root);
    if (((attributeRoot.getTypeCall().getType() instanceof RosettaEnumeration) || (operation.getPath() == null))) {
      StringConcatenation _builder = new StringConcatenation();
      String _name = attributeRoot.getName();
      _builder.append(_name);
      _builder.append(" =  ");
      _builder.append(expression);
      result = _builder.toString();
    } else {
      boolean _contains = setNames.contains(attributeRoot.getName());
      boolean _not = (!_contains);
      if (_not) {
        StringConcatenation _builder_1 = new StringConcatenation();
        String _name_1 = attributeRoot.getName();
        _builder_1.append(_name_1);
        _builder_1.append(" = _get_rosetta_object(\'");
        String _name_2 = attributeRoot.getTypeCall().getType().getName();
        _builder_1.append(_name_2);
        _builder_1.append("\', ");
        String _nextPathElementName = this.getNextPathElementName(operation.getPath());
        _builder_1.append(_nextPathElementName);
        _builder_1.append(", ");
        String _buildObject = this.buildObject(expression, operation.getPath());
        _builder_1.append(_buildObject);
        _builder_1.append(")");
        result = _builder_1.toString();
        setNames.add(attributeRoot.getName());
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        String _name_3 = attributeRoot.getName();
        _builder_2.append(_name_3);
        _builder_2.append(" = set_rosetta_attr(rosetta_resolve_attr(self, \'");
        String _name_4 = attributeRoot.getName();
        _builder_2.append(_name_4);
        _builder_2.append("\'), ");
        StringBuilder _generateAttributesPath = this.generateAttributesPath(operation.getPath());
        _builder_2.append(_generateAttributesPath);
        _builder_2.append(", ");
        _builder_2.append(expression);
        _builder_2.append(")");
        result = _builder_2.toString();
      }
    }
    return result;
  }

  private StringBuilder generateAttributesPath(final Segment path) {
    Segment currentPath = path;
    StringBuilder result = new StringBuilder();
    result.append("\'");
    while ((currentPath != null)) {
      {
        result.append(currentPath.getAttribute().getName());
        Segment _next = currentPath.getNext();
        boolean _tripleNotEquals = (_next != null);
        if (_tripleNotEquals) {
          result.append("->");
        }
        currentPath = currentPath.getNext();
      }
    }
    result.append("\'");
    return result;
  }

  private String getNextPathElementName(final Segment path) {
    if ((path != null)) {
      final Attribute attribute = path.getAttribute();
      String _name = attribute.getName();
      String _plus = ("\'" + _name);
      return (_plus + "\'");
    }
    return null;
  }

  private String buildObject(final String expression, final Segment path) {
    if (((path == null) || (path.getNext() == null))) {
      return expression;
    }
    final Attribute attribute = path.getAttribute();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_get_rosetta_object(\'");
    String _name = attribute.getTypeCall().getType().getName();
    _builder.append(_name);
    _builder.append("\', ");
    String _nextPathElementName = this.getNextPathElementName(path.getNext());
    _builder.append(_nextPathElementName);
    _builder.append(", ");
    String _buildObject = this.buildObject(expression, path.getNext());
    _builder.append(_buildObject);
    _builder.append(")");
    return _builder.toString();
  }

  private String generateFullPath(final Iterable<Attribute> attrs, final String root) {
    boolean _isEmpty = IterableExtensions.isEmpty(attrs);
    if (_isEmpty) {
      return "self";
    }
    final Attribute attr = IterableExtensions.<Attribute>head(attrs);
    final List<Attribute> remainingAttrs = IterableExtensions.<Attribute>toList(IterableExtensions.<Attribute>tail(attrs));
    String _xifexpression = null;
    boolean _isEmpty_1 = remainingAttrs.isEmpty();
    if (_isEmpty_1) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("rosetta_resolve_attr(self, ");
      _builder.append(root);
      _builder.append(")");
      _xifexpression = _builder.toString();
    } else {
      _xifexpression = this.generateFullPath(remainingAttrs, root);
    }
    final String nextPath = _xifexpression;
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("rosetta_resolve_attr(");
    _builder_1.append(nextPath);
    _builder_1.append(", \'");
    String _name = attr.getName();
    _builder_1.append(_name);
    _builder_1.append("\')");
    return _builder_1.toString();
  }

  private ArrayList<Attribute> getReversedAttributes(final Segment segment) {
    final ArrayList<Attribute> attributes = new ArrayList<Attribute>();
    Segment current = segment;
    while ((current != null)) {
      {
        attributes.add(current.getAttribute());
        current = current.getNext();
      }
    }
    Collections.reverse(attributes);
    return attributes;
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
      boolean _contains = this.importsFound.contains(import_);
      boolean _not = (!_contains);
      if (_not) {
        _xifexpression = this.importsFound.add(import_);
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
}
