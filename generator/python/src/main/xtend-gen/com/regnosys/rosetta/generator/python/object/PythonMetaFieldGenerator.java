package com.regnosys.rosetta.generator.python.object;

import com.google.inject.Inject;
import com.regnosys.rosetta.generator.python.util.PythonTranslator;
import com.regnosys.rosetta.generator.util.IterableUtil;
import com.regnosys.rosetta.rosetta.RosettaMetaType;
import com.regnosys.rosetta.rosetta.simple.Data;
import com.regnosys.rosetta.types.REnumType;
import com.regnosys.rosetta.types.RObjectFactory;
import com.regnosys.rosetta.types.RType;
import java.util.List;
import java.util.function.Function;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class PythonMetaFieldGenerator {
  @Inject
  @Extension
  private RObjectFactory _rObjectFactory;

  public Object generateMetaFields(final List<Data> rosettaClasses, final Iterable<RosettaMetaType> metaTypes, final String version) {
    return null;
  }

  private CharSequence generateMetaFieldsImports() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }

  private CharSequence generateFieldWithMeta(final RType rt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("class FieldWithMeta");
    String _metaTypeName = PythonModelObjectBoilerPlate.toMetaTypeName(rt);
    _builder.append(_metaTypeName);
    _builder.append(":");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    String _generateAttribute = this.generateAttribute(rt);
    _builder.append(_generateAttribute, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("meta = MetaFields()");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }

  /**
   * private def generateFieldWithMeta(ExpandedType type) '''
   * class FieldWithMeta«type.toMetaTypeName»:
   * «generateAttribute(type)»
   * meta = MetaFields()
   * 
   * '''
   */
  private String generateAttribute(final RType rt) {
    String _xifexpression = null;
    if ((rt instanceof REnumType)) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("value = ");
      String _pythonType = PythonTranslator.toPythonType(rt);
      _builder.append(_pythonType);
      _xifexpression = _builder.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("value = None");
      _xifexpression = _builder_1.toString();
    }
    return _xifexpression;
  }

  /**
   * private def generateAttribute(ExpandedType type) {
   * if (type.enumeration) {
   * '''value = «type.toPythonType»'''
   * } else {
   * '''value = None'''
   * }
   * }
   */
  private CharSequence generateReferenceWithMeta(final RType rt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("class ReferenceWithMeta");
    String _metaTypeName = PythonModelObjectBoilerPlate.toMetaTypeName(rt);
    _builder.append(_metaTypeName);
    _builder.append(":");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("value = ");
    String _name = rt.getName();
    _builder.append(_name, "    ");
    _builder.append("()");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("globalReference = None");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("externalReference = None");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("address = Reference()");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }

  /**
   * private def generateReferenceWithMeta(ExpandedType type)
   * '''
   * class ReferenceWithMeta«type.toMetaTypeName»:
   * value = «type.name»()
   * globalReference = None
   * externalReference = None
   * address = Reference()
   * 
   * '''
   */
  private CharSequence generateBasicReferenceWithMeta(final RType rt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("class BasicReferenceWithMeta");
    String _metaTypeName = PythonModelObjectBoilerPlate.toMetaTypeName(rt);
    _builder.append(_metaTypeName);
    _builder.append(":");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("value = None");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("globalReference = None");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("externalReference = None");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("address = Reference()");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }

  /**
   * private def generateBasicReferenceWithMeta(ExpandedType type)
   * '''
   * class BasicReferenceWithMeta«type.toMetaTypeName»:
   * value = None
   * globalReference = None
   * externalReference = None
   * address = Reference()
   * 
   * '''
   */
  private CharSequence genMetaFields(final Iterable<RosettaMetaType> types, final String version) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("class MetaFields:");
    _builder.newLine();
    _builder.append("    ");
    {
      final Function<RosettaMetaType, String> _function = (RosettaMetaType t) -> {
        return StringExtensions.toFirstLower(t.getName());
      };
      Iterable<RosettaMetaType> _distinctBy = IterableUtil.<RosettaMetaType, String>distinctBy(types, _function);
      boolean _hasElements = false;
      for(final RosettaMetaType type : _distinctBy) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("\n", "    ");
        }
        String _firstLower = StringExtensions.toFirstLower(type.getName());
        _builder.append(_firstLower, "    ");
        _builder.append(" = None");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("globalKey = None");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("externalKey = None");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("location = []");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("class MetaAndTemplateFields:");
    _builder.newLine();
    _builder.append("    ");
    {
      final Function<RosettaMetaType, String> _function_1 = (RosettaMetaType t) -> {
        return StringExtensions.toFirstLower(t.getName());
      };
      Iterable<RosettaMetaType> _distinctBy_1 = IterableUtil.<RosettaMetaType, String>distinctBy(types, _function_1);
      boolean _hasElements_1 = false;
      for(final RosettaMetaType type_1 : _distinctBy_1) {
        if (!_hasElements_1) {
          _hasElements_1 = true;
        } else {
          _builder.appendImmediate("\n", "    ");
        }
        String _firstLower_1 = StringExtensions.toFirstLower(type_1.getName());
        _builder.append(_firstLower_1, "    ");
        _builder.append(" = None");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("globalKey = None");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("externalKey = None");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("templateGlobalReference = None");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("location = []");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("class Key:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("scope = None");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("value = None");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("class Reference:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("scope = None");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("value = None");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
}
