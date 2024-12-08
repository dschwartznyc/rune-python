package com.regnosys.rosetta.generator.python.object;

import com.google.common.base.Objects;
import com.regnosys.rosetta.generator.python.util.PythonTranslator;
import com.regnosys.rosetta.types.RAttribute;
import com.regnosys.rosetta.types.REnumType;
import com.regnosys.rosetta.types.RType;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class PythonModelObjectBoilerPlate {
  public String toAttributeName(final RAttribute attribute) {
    String _xifexpression = null;
    String _name = attribute.getName();
    boolean _equals = Objects.equal(_name, "val");
    if (_equals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("`val`");
      _xifexpression = _builder.toString();
    } else {
      _xifexpression = StringExtensions.toFirstLower(attribute.getName());
    }
    return _xifexpression;
  }

  public String replaceTabsWithSpaces(final CharSequence code) {
    return code.toString().replace("\t", "  ");
  }

  public CharSequence toEnumAnnotationType(final RType type) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = type.getName();
    _builder.append(_name);
    return _builder;
  }

  public String toType(final RAttribute ra) {
    String _xifexpression = null;
    boolean _isMulti = ra.isMulti();
    if (_isMulti) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("MutableList<");
      String _rawType = this.toRawType(ra);
      _builder.append(_rawType);
      _builder.append(">");
      _xifexpression = _builder.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      String _rawType_1 = this.toRawType(ra);
      _builder_1.append(_rawType_1);
      _xifexpression = _builder_1.toString();
    }
    return _xifexpression;
  }

  public String toRawType(final RAttribute ra) {
    return PythonTranslator.toPythonType(ra.getRMetaAnnotatedType().getRType());
  }

  public CharSequence toReferenceWithMetaTypeName(final RType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ReferenceWithMeta");
    String _metaTypeName = PythonModelObjectBoilerPlate.toMetaTypeName(type);
    _builder.append(_metaTypeName);
    return _builder;
  }

  public CharSequence toBasicReferenceWithMetaTypeName(final RType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("BasicReferenceWithMeta");
    String _metaTypeName = PythonModelObjectBoilerPlate.toMetaTypeName(type);
    _builder.append(_metaTypeName);
    return _builder;
  }

  public CharSequence toFieldWithMetaTypeName(final RType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("FieldWithMeta");
    String _metaTypeName = PythonModelObjectBoilerPlate.toMetaTypeName(type);
    _builder.append(_metaTypeName);
    return _builder;
  }

  public static String toMetaTypeName(final RType type) {
    final String name = PythonTranslator.toPythonType(type);
    if ((name == null)) {
      return "null-name";
    }
    if ((type instanceof REnumType)) {
      return name;
    }
    boolean _contains = name.contains(".");
    if (_contains) {
      int _lastIndexOf = name.lastIndexOf(".");
      int _plus = (_lastIndexOf + 1);
      return StringExtensions.toFirstUpper(name.substring(_plus));
    }
    return StringExtensions.toFirstUpper(name);
  }
}
