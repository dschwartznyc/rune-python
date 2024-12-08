package com.regnosys.rosetta.generator.python.util;

import com.google.common.base.Objects;
import com.regnosys.rosetta.rosetta.simple.Attribute;
import com.regnosys.rosetta.types.RAttribute;
import com.regnosys.rosetta.types.REnumType;
import com.regnosys.rosetta.types.RType;
import com.rosetta.util.DottedPath;
import java.util.Arrays;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class PythonTranslator {
  private static String toPythonBasicTypeInnerFunction(final String rosettaType) {
    if (rosettaType != null) {
      switch (rosettaType) {
        case "string":
        case "eventType":
        case "calculation":
        case "productType":
          return "str";
        case "time":
          return "datetime.time";
        case "date":
          return "datetime.date";
        case "dateTime":
        case "zonedDateTime":
          return "datetime.datetime";
        case "number":
          return "Decimal";
        case "boolean":
          return "bool";
        case "int":
          return "int";
        default:
          return null;
      }
    } else {
      return null;
    }
  }

  public static String mangleName(final String attrib) {
    if (attrib != null) {
      switch (attrib) {
        case "False":
        case "await":
        case "else":
        case "import":
        case "pass":
        case "None":
        case "break":
        case "except":
        case "in":
        case "raise":
        case "True":
        case "class":
        case "finally":
        case "is":
        case "return":
        case "and":
        case "continue":
        case "for":
        case "lambda":
        case "try":
        case "as":
        case "def":
        case "from":
        case "nonlocal":
        case "while":
        case "assert":
        case "del":
        case "global":
        case "not":
        case "with":
        case "async":
        case "elif":
        case "if":
        case "or":
        case "yield":
        case "match":
        case "case":
        case "type":
        case "_":
          return ("rosetta_attr_" + attrib);
        default:
          String _xifexpression = null;
          char _charAt = attrib.charAt(0);
          boolean _equals = Objects.equal(Character.valueOf(_charAt), "_");
          if (_equals) {
            _xifexpression = ("rosetta_attr_" + attrib);
          } else {
            _xifexpression = attrib;
          }
          return _xifexpression;
      }
    } else {
      String _xifexpression = null;
      char _charAt = attrib.charAt(0);
      boolean _equals = Objects.equal(Character.valueOf(_charAt), "_");
      if (_equals) {
        _xifexpression = ("rosetta_attr_" + attrib);
      } else {
        _xifexpression = attrib;
      }
      return _xifexpression;
    }
  }

  public static String toPythonBasicType(final String rosettaType) {
    final String pythonType = PythonTranslator.toPythonBasicTypeInnerFunction(rosettaType);
    String _xifexpression = null;
    if ((pythonType == null)) {
      _xifexpression = rosettaType;
    } else {
      _xifexpression = pythonType;
    }
    return _xifexpression;
  }

  public static String toPythonType(final RType rt) {
    if ((rt == null)) {
      return null;
    }
    String pythonType = PythonTranslator.toPythonBasicTypeInnerFunction(rt.getName());
    if ((pythonType == null)) {
      String _xifexpression = null;
      if ((rt instanceof REnumType)) {
        StringConcatenation _builder = new StringConcatenation();
        String _firstUpper = StringExtensions.toFirstUpper(((REnumType)rt).getName());
        _builder.append(_firstUpper);
        _xifexpression = _builder.toString();
      } else {
        _xifexpression = StringExtensions.toFirstUpper(rt.getName());
      }
      pythonType = _xifexpression;
    }
    return pythonType;
  }

  public static String toPythonType(final RAttribute ra) {
    RType _xifexpression = null;
    if ((ra == null)) {
      _xifexpression = null;
    } else {
      _xifexpression = ra.getRMetaAnnotatedType().getRType();
    }
    final RType rt = _xifexpression;
    String _xifexpression_1 = null;
    if ((rt == null)) {
      _xifexpression_1 = null;
    } else {
      _xifexpression_1 = rt.getName();
    }
    final String rtName = _xifexpression_1;
    if ((rtName == null)) {
      return null;
    }
    final String pythonType = PythonTranslator.toPythonBasicTypeInnerFunction(rtName);
    String _xifexpression_2 = null;
    if ((pythonType == null)) {
      DottedPath _namespace = rt.getNamespace();
      String _plus = (_namespace + ".");
      String _plus_1 = (_plus + rtName);
      String _plus_2 = (_plus_1 + ".");
      _xifexpression_2 = (_plus_2 + rtName);
    } else {
      _xifexpression_2 = pythonType;
    }
    return _xifexpression_2;
  }

  public static String toPythonType(final Attribute rosettaAttributeType) {
    if ((rosettaAttributeType == null)) {
      return null;
    }
    final String rosettaType = rosettaAttributeType.getTypeCall().getType().getName();
    final String pythonType = PythonTranslator.toPythonBasicTypeInnerFunction(rosettaType);
    String _xifexpression = null;
    if ((pythonType == null)) {
      _xifexpression = StringExtensions.toFirstUpper(rosettaType);
    } else {
      _xifexpression = pythonType;
    }
    return _xifexpression;
  }

  public static boolean checkBasicType(final Attribute rosettaAttributeType) {
    return ((rosettaAttributeType != null) && (PythonTranslator.toPythonBasicTypeInnerFunction(rosettaAttributeType.getTypeCall().getType().getName()) != null));
  }

  public static boolean checkBasicType(final String rosettaType) {
    return ((rosettaType != null) && (PythonTranslator.toPythonBasicTypeInnerFunction(rosettaType) != null));
  }

  public static boolean checkPythonType(final String pythonType) {
    final List<String> types = Arrays.<String>asList("int", 
      "str", 
      "Decimal", 
      "date", 
      "datetime", 
      "datetime.datetime", 
      "datetime.date", 
      "datetime.time", 
      "time", 
      "bool");
    return types.contains(pythonType);
  }
}
