package com.regnosys.rosetta.generator.python.object;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.regnosys.rosetta.generator.python.expressions.PythonExpressionGenerator;
import com.regnosys.rosetta.generator.python.util.PythonModelGeneratorUtil;
import com.regnosys.rosetta.generator.python.util.PythonTranslator;
import com.regnosys.rosetta.generator.python.util.Util;
import com.regnosys.rosetta.rosetta.RosettaMetaType;
import com.regnosys.rosetta.rosetta.RosettaModel;
import com.regnosys.rosetta.rosetta.simple.Data;
import com.regnosys.rosetta.types.RAttribute;
import com.regnosys.rosetta.types.RChoiceType;
import com.regnosys.rosetta.types.RDataType;
import com.regnosys.rosetta.types.RMetaAnnotatedType;
import com.regnosys.rosetta.types.RMetaAttribute;
import com.regnosys.rosetta.types.RObjectFactory;
import com.regnosys.rosetta.types.RType;
import com.regnosys.rosetta.utils.DeepFeatureCallUtil;
import com.rosetta.util.DottedPath;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class PythonModelObjectGenerator {
  @Inject
  @Extension
  private RObjectFactory _rObjectFactory;

  @Inject
  @Extension
  private DeepFeatureCallUtil _deepFeatureCallUtil;

  @Inject
  @Extension
  private PythonModelObjectBoilerPlate _pythonModelObjectBoilerPlate;

  @Inject
  private PythonExpressionGenerator expressionGenerator;

  private List<String> importsFound = CollectionLiterals.<String>newArrayList();

  public static String toPythonType(final Data c, final RAttribute ra) throws Exception {
    String basicType = PythonTranslator.toPythonType(ra);
    if ((basicType == null)) {
      String _name = ra.getName();
      String _plus = ("Attribute type is null for " + _name);
      String _plus_1 = (_plus + " for class ");
      String _name_1 = c.getName();
      String _plus_2 = (_plus_1 + _name_1);
      throw new Exception(_plus_2);
    }
    RMetaAnnotatedType metaAnnotations = ra.getRMetaAnnotatedType();
    if (((metaAnnotations != null) && metaAnnotations.hasMeta())) {
      String helperClass = "Attribute";
      boolean hasRef = false;
      boolean hasAddress = false;
      boolean hasMeta = false;
      List<RMetaAttribute> _metaAttributes = metaAnnotations.getMetaAttributes();
      for (final RMetaAttribute meta : _metaAttributes) {
        {
          final String mname = meta.getName();
          boolean _equals = Objects.equal(mname, "reference");
          if (_equals) {
            hasRef = true;
          } else {
            boolean _equals_1 = Objects.equal(mname, "address");
            if (_equals_1) {
              hasAddress = true;
            } else {
              if ((((Objects.equal(mname, "key") || Objects.equal(mname, "id")) || Objects.equal(mname, "scheme")) || Objects.equal(mname, "location"))) {
                hasMeta = true;
              } else {
                String _helperClass = helperClass;
                helperClass = (_helperClass + (("---" + mname) + "---"));
              }
            }
          }
        }
      }
      if (hasMeta) {
        String _helperClass = helperClass;
        helperClass = (_helperClass + "WithMeta");
      }
      if (hasAddress) {
        String _helperClass_1 = helperClass;
        helperClass = (_helperClass_1 + "WithAddress");
      }
      if (hasRef) {
        String _helperClass_2 = helperClass;
        helperClass = (_helperClass_2 + "WithReference");
      }
      if ((hasMeta || hasAddress)) {
        String _helperClass_3 = helperClass;
        helperClass = (_helperClass_3 + (("[" + basicType) + "]"));
      }
      basicType = ((helperClass + " | ") + basicType);
    }
    return basicType;
  }

  public Map<String, ? extends CharSequence> generate(final Iterable<Data> rosettaClasses, final Iterable<RosettaMetaType> metaTypes, final String version) {
    HashMap<String, String> _xblockexpression = null;
    {
      final HashMap<String, String> result = new HashMap<String, String>();
      for (final Data type : rosettaClasses) {
        {
          EObject _eContainer = type.eContainer();
          final RosettaModel model = ((RosettaModel) _eContainer);
          final String namespace = Util.getNamespace(model);
          final String pythonBody = this._pythonModelObjectBoilerPlate.replaceTabsWithSpaces(this.generateBody(type, namespace, version));
          String _pyFileName = PythonModelGeneratorUtil.toPyFileName(model.getName(), type.getName());
          String _createImports = PythonModelGeneratorUtil.createImports(type.getName());
          String _plus = (_createImports + pythonBody);
          result.put(_pyFileName, _plus);
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }

  public Map<String, ArrayList<String>> generateChoiceAliases(final RDataType choiceType) {
    boolean _isEligibleForDeepFeatureCall = this._deepFeatureCallUtil.isEligibleForDeepFeatureCall(choiceType);
    boolean _not = (!_isEligibleForDeepFeatureCall);
    if (_not) {
      return null;
    }
    final HashMap<String, ArrayList<String>> deepReferenceMap = new HashMap<String, ArrayList<String>>();
    final Collection<RAttribute> deepFeatures = this._deepFeatureCallUtil.findDeepFeatures(choiceType);
    final Function1<RAttribute, RAttribute> _function = (RAttribute it) -> {
      return it;
    };
    final Function1<RAttribute, Map<RAttribute, Boolean>> _function_1 = (RAttribute it) -> {
      Map<RAttribute, Boolean> _xblockexpression = null;
      {
        final RType attrType = it.getRMetaAnnotatedType().getRType();
        final Function1<RAttribute, RAttribute> _function_2 = (RAttribute it_1) -> {
          return it_1;
        };
        final Function1<RAttribute, Boolean> _function_3 = (RAttribute it_1) -> {
          RType t = attrType;
          if ((t instanceof RChoiceType)) {
            t = ((RChoiceType)t).asRDataType();
          }
          if ((t instanceof RDataType)) {
            boolean _containsKey = this._deepFeatureCallUtil.findDeepFeatureMap(((RDataType)t)).containsKey(it_1.getName());
            if (_containsKey) {
              ArrayList<String> deepReference = deepReferenceMap.get(((RDataType)t).getName());
              if ((deepReference == null)) {
                ArrayList<String> _arrayList = new ArrayList<String>();
                deepReference = _arrayList;
              }
              deepReference.add(it_1.getName());
              deepReferenceMap.put(((RDataType)t).getName(), deepReference);
              return Boolean.valueOf(true);
            }
          }
          return Boolean.valueOf(false);
        };
        _xblockexpression = IterableExtensions.<RAttribute, RAttribute, Boolean>toMap(deepFeatures, _function_2, _function_3);
      }
      return _xblockexpression;
    };
    IterableExtensions.<RAttribute, RAttribute, Map<RAttribute, Boolean>>toMap(choiceType.getAllNonOverridenAttributes(), _function, _function_1);
    final Function1<RAttribute, String> _function_2 = (RAttribute deepFeature) -> {
      String _name = deepFeature.getName();
      String _plus = ("\"" + _name);
      return (_plus + "\"");
    };
    final Function1<RAttribute, ArrayList<String>> _function_3 = (RAttribute deepFeature) -> {
      ArrayList<String> _xblockexpression = null;
      {
        final ArrayList<String> aliasList = new ArrayList<String>();
        final Consumer<RAttribute> _function_4 = (RAttribute attribute) -> {
          final RType attrType = attribute.getRMetaAnnotatedType().getRType();
          RType t = attrType;
          if ((t instanceof RChoiceType)) {
            t = ((RChoiceType)t).asRDataType();
          }
          if ((t instanceof RDataType)) {
            final ArrayList<String> deepReference = deepReferenceMap.get(((RDataType)t).getName());
            String _xifexpression = null;
            if (((deepReference != null) && deepReference.contains(deepFeature.getName()))) {
              _xifexpression = "rosetta_resolve_deep_attr";
            } else {
              _xifexpression = "rosetta_resolve_attr";
            }
            final String resolutionMethod = _xifexpression;
            String _name = attribute.getName();
            String _plus = ("(\"" + _name);
            String _plus_1 = (_plus + "\", ");
            String _plus_2 = (_plus_1 + resolutionMethod);
            String _plus_3 = (_plus_2 + ")");
            aliasList.add(_plus_3);
          }
        };
        choiceType.getAllNonOverridenAttributes().forEach(_function_4);
        _xblockexpression = aliasList;
      }
      return _xblockexpression;
    };
    final Map<String, ArrayList<String>> choiceAlias = IterableExtensions.<RAttribute, String, ArrayList<String>>toMap(deepFeatures, _function_2, _function_3);
    Map<String, ArrayList<String>> _xifexpression = null;
    boolean _isEmpty = choiceAlias.isEmpty();
    if (_isEmpty) {
      _xifexpression = null;
    } else {
      _xifexpression = choiceAlias;
    }
    return _xifexpression;
  }

  public boolean checkBasicType(final RAttribute ra) {
    RType _xifexpression = null;
    if ((ra != null)) {
      _xifexpression = ra.getRMetaAnnotatedType().getRType();
    } else {
      _xifexpression = null;
    }
    final RType rosettaType = _xifexpression;
    return ((rosettaType != null) && PythonTranslator.checkPythonType(rosettaType.toString()));
  }

  /**
   * Generate the classes
   */
  private String generateBody(final Data rosettaClass, final String namespace, final String version) {
    try {
      List<String> enumImports = CollectionLiterals.<String>newArrayList();
      List<String> dataImports = CollectionLiterals.<String>newArrayList();
      Data superType = rosettaClass.getSuperType();
      if (((superType != null) && (superType.getName() == null))) {
        String _name = rosettaClass.getName();
        String _plus = ("SuperType is null for " + _name);
        throw new Exception(_plus);
      }
      this.importsFound = this.getImportsFromAttributes(rosettaClass);
      this.expressionGenerator.importsFound = this.importsFound;
      final String classDefinition = this.generateClass(rosettaClass);
      enumImports = IterableExtensions.<String>toList(IterableExtensions.<String>toSet(enumImports));
      dataImports = IterableExtensions.<String>toList(IterableExtensions.<String>toSet(dataImports));
      StringConcatenation _builder = new StringConcatenation();
      {
        if ((superType != null)) {
          _builder.append("from ");
          EObject _eContainer = superType.eContainer();
          String _name_1 = ((RosettaModel) _eContainer).getName();
          _builder.append(_name_1);
          _builder.append(".");
          String _name_2 = superType.getName();
          _builder.append(_name_2);
          _builder.append(" import ");
          String _name_3 = superType.getName();
          _builder.append(_name_3);
        }
      }
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append(classDefinition);
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("import ");
      _builder.append(namespace);
      _builder.append(" ");
      _builder.newLineIfNotEmpty();
      {
        boolean _hasElements = false;
        for(final String dataImport : this.importsFound) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("\n", "");
          }
          _builder.append(dataImport);
        }
      }
      _builder.newLineIfNotEmpty();
      return _builder.toString();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  private List<String> getImportsFromAttributes(final Data rosettaClass) {
    try {
      final RDataType rdt = this._rObjectFactory.buildRDataType(rosettaClass);
      final Function1<RAttribute, Boolean> _function = (RAttribute it) -> {
        return Boolean.valueOf((((it.getName() != "reference") && (it.getName() != "meta")) && (it.getName() != "scheme")));
      };
      final Function1<RAttribute, Boolean> _function_1 = (RAttribute it) -> {
        boolean _checkBasicType = this.checkBasicType(it);
        return Boolean.valueOf((!_checkBasicType));
      };
      final Iterable<RAttribute> filteredAttributes = IterableExtensions.<RAttribute>filter(IterableExtensions.<RAttribute>filter(rdt.getOwnAttributes(), _function), _function_1);
      final ArrayList<String> imports = CollectionLiterals.<String>newArrayList();
      for (final RAttribute attribute : filteredAttributes) {
        {
          RType rt = attribute.getRMetaAnnotatedType().getRType();
          if ((rt == null)) {
            String _name = attribute.getName();
            String _plus = ("Attribute type is null for " + _name);
            String _plus_1 = (_plus + " for class ");
            String _name_1 = rosettaClass.getName();
            String _plus_2 = (_plus_1 + _name_1);
            throw new Exception(_plus_2);
          }
          final DottedPath modelName = rt.getQualifiedName();
          if ((modelName != null)) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("import ");
            _builder.append(modelName);
            imports.add(_builder.toString());
          }
        }
      }
      return IterableExtensions.<String>toList(IterableExtensions.<String>toSet(imports));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  private String generateChoiceMapStrings(final Map<String, ArrayList<String>> choiceAliases) {
    String _xifexpression = null;
    if ((choiceAliases == null)) {
      _xifexpression = "";
    } else {
      final Function1<Map.Entry<String, ArrayList<String>>, String> _function = (Map.Entry<String, ArrayList<String>> e) -> {
        String _key = e.getKey();
        String _plus = (_key + ":");
        String _string = e.getValue().toString();
        return (_plus + _string);
      };
      _xifexpression = IterableExtensions.join(IterableExtensions.<Map.Entry<String, ArrayList<String>>, String>map(choiceAliases.entrySet(), _function), ",");
    }
    String result = _xifexpression;
    return result;
  }

  private String generateClass(final Data rosettaClass) {
    final RDataType t = this._rObjectFactory.buildRDataType(rosettaClass);
    final Map<String, ArrayList<String>> choiceAliases = this.generateChoiceAliases(t);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("class ");
    String _name = rosettaClass.getName();
    _builder.append(_name);
    {
      Data _superType = rosettaClass.getSuperType();
      boolean _tripleEquals = (_superType == null);
      if (_tripleEquals) {
      }
    }
    {
      Data _superType_1 = rosettaClass.getSuperType();
      boolean _tripleNotEquals = (_superType_1 != null);
      if (_tripleNotEquals) {
        _builder.append("(");
        String _name_1 = rosettaClass.getSuperType().getName();
        _builder.append(_name_1);
        _builder.append("):");
      } else {
        _builder.append("(BaseDataClass):");
      }
    }
    _builder.newLineIfNotEmpty();
    {
      if ((choiceAliases != null)) {
        _builder.append("    ");
        _builder.append("_CHOICE_ALIAS_MAP ={");
        String _generateChoiceMapStrings = this.generateChoiceMapStrings(choiceAliases);
        _builder.append(_generateChoiceMapStrings, "    ");
        _builder.append("}");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      String _definition = rosettaClass.getDefinition();
      boolean _tripleNotEquals_1 = (_definition != null);
      if (_tripleNotEquals_1) {
        _builder.append("    ");
        _builder.append("\"\"\"");
        _builder.newLine();
        _builder.append("    ");
        String _definition_1 = rosettaClass.getDefinition();
        _builder.append(_definition_1, "    ");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("\"\"\"");
        _builder.newLine();
      }
    }
    _builder.append("    ");
    CharSequence _generateAttributes = this.generateAttributes(rosettaClass);
    _builder.append(_generateAttributes, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    String _generateConditions = this.expressionGenerator.generateConditions(rosettaClass);
    _builder.append(_generateConditions, "    ");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }

  private CharSequence generateAttributes(final Data rosettaClass) {
    CharSequence _xblockexpression = null;
    {
      final List<RAttribute> attr = this._rObjectFactory.buildRDataType(rosettaClass).getOwnAttributes();
      final int attrSize = attr.size();
      final int conditionsSize = rosettaClass.getConditions().size();
      StringConcatenation _builder = new StringConcatenation();
      {
        if (((attrSize == 0) && (conditionsSize == 0))) {
          _builder.append("pass");
        } else {
          {
            boolean _hasElements = false;
            for(final RAttribute attribute : attr) {
              if (!_hasElements) {
                _hasElements = true;
              } else {
                _builder.appendImmediate("", "");
              }
              CharSequence _createPythonFromAttribute = this.createPythonFromAttribute(rosettaClass, attribute);
              _builder.append(_createPythonFromAttribute);
            }
          }
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  private CharSequence createPythonFromAttribute(final Data c, final RAttribute ra) {
    try {
      CharSequence _xblockexpression = null;
      {
        String attString = "";
        int lowerCardinality = ra.getCardinality().getMinBound();
        Integer _xifexpression = null;
        boolean _isUnboundedRight = ra.getCardinality().isUnboundedRight();
        boolean _not = (!_isUnboundedRight);
        if (_not) {
          _xifexpression = ra.getCardinality().getMax().get();
        } else {
          _xifexpression = Integer.valueOf((-1));
        }
        Integer upperCardinality = _xifexpression;
        String _xifexpression_1 = null;
        boolean _isUnboundedRight_1 = ra.getCardinality().isUnboundedRight();
        if (_isUnboundedRight_1) {
          _xifexpression_1 = "None";
        } else {
          _xifexpression_1 = ra.getCardinality().getMax().get().toString();
        }
        String upperCardString = _xifexpression_1;
        String _xifexpression_2 = null;
        if ((((upperCardinality).intValue() == 1) && (lowerCardinality == 1))) {
          _xifexpression_2 = "...";
        } else {
          _xifexpression_2 = "None";
        }
        String fieldDefault = _xifexpression_2;
        if ((ra.getCardinality().isUnboundedRight() || ((upperCardinality).intValue() > 1))) {
          String _attString = attString;
          String _pythonType = PythonModelObjectGenerator.toPythonType(c, ra);
          String _plus = ("List[" + _pythonType);
          String _plus_1 = (_plus + "]");
          attString = (_attString + _plus_1);
          fieldDefault = "[]";
        } else {
          if ((lowerCardinality == 0)) {
            String _attString_1 = attString;
            String _pythonType_1 = PythonModelObjectGenerator.toPythonType(c, ra);
            String _plus_2 = ("Optional[" + _pythonType_1);
            String _plus_3 = (_plus_2 + "]");
            attString = (_attString_1 + _plus_3);
          } else {
            String _attString_2 = attString;
            String _pythonType_2 = PythonModelObjectGenerator.toPythonType(c, ra);
            attString = (_attString_2 + _pythonType_2);
          }
        }
        String attrName = PythonTranslator.mangleName(ra.getName());
        boolean needCardCheck = (!((((lowerCardinality == 0) && ((upperCardinality).intValue() == 1)) || ((lowerCardinality == 1) && ((upperCardinality).intValue() == 1))) || ((lowerCardinality == 0) && ra.getCardinality().isUnboundedRight())));
        String _xifexpression_3 = null;
        String _definition = ra.getDefinition();
        boolean _tripleEquals = (_definition == null);
        if (_tripleEquals) {
          _xifexpression_3 = "";
        } else {
          _xifexpression_3 = ra.getDefinition().replaceAll("\\s+", " ");
        }
        final String attrDesc = _xifexpression_3;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(attrName);
        _builder.append(": ");
        _builder.append(attString);
        _builder.append(" = Field(");
        _builder.append(fieldDefault);
        _builder.append(", description=\"");
        _builder.append(attrDesc);
        _builder.append("\")");
        _builder.newLineIfNotEmpty();
        {
          String _definition_1 = ra.getDefinition();
          boolean _tripleNotEquals = (_definition_1 != null);
          if (_tripleNotEquals) {
            _builder.append("\"\"\"");
            _builder.newLine();
            String _definition_2 = ra.getDefinition();
            _builder.append(_definition_2);
            _builder.newLineIfNotEmpty();
            _builder.append("\"\"\"");
            _builder.newLine();
          }
        }
        {
          if (needCardCheck) {
            _builder.append("@rosetta_condition");
            _builder.newLine();
            _builder.append("def cardinality_");
            _builder.append(attrName);
            _builder.append("(self):");
            _builder.newLineIfNotEmpty();
            _builder.append("    ");
            _builder.append("return check_cardinality(self.");
            _builder.append(attrName, "    ");
            _builder.append(", ");
            _builder.append(lowerCardinality, "    ");
            _builder.append(", ");
            _builder.append(upperCardString, "    ");
            _builder.append(")");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
          }
        }
        _xblockexpression = _builder;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public String definition(final Data element) {
    return element.getDefinition();
  }
}
