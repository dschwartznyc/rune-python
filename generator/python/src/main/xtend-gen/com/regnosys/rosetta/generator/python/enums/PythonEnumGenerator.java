package com.regnosys.rosetta.generator.python.enums;

import com.google.inject.Inject;
import com.regnosys.rosetta.generator.java.enums.EnumHelper;
import com.regnosys.rosetta.generator.python.object.PythonModelObjectBoilerPlate;
import com.regnosys.rosetta.generator.python.util.PythonModelGeneratorUtil;
import com.regnosys.rosetta.rosetta.RosettaEnumValue;
import com.regnosys.rosetta.rosetta.RosettaEnumeration;
import com.regnosys.rosetta.rosetta.RosettaModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class PythonEnumGenerator {
  @Inject
  @Extension
  private PythonModelObjectBoilerPlate _pythonModelObjectBoilerPlate;

  public Map<String, ? extends CharSequence> generate(final Iterable<RosettaEnumeration> rosettaEnums, final String version) {
    final HashMap<String, String> result = new HashMap<String, String>();
    for (final RosettaEnumeration enum_ : rosettaEnums) {
      {
        EObject _eContainer = enum_.eContainer();
        final RosettaModel tr = ((RosettaModel) _eContainer);
        final String namespace = tr.getName();
        final String enums = this._pythonModelObjectBoilerPlate.replaceTabsWithSpaces(this.generateEnums(enum_, version));
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("# pylint: disable=missing-module-docstring, invalid-name, line-too-long");
        _builder.newLine();
        _builder.append("from enum import Enum");
        _builder.newLine();
        _builder.newLine();
        _builder.append("__all__ = [\'");
        String _name = enum_.getName();
        _builder.append(_name);
        _builder.append("\']");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        final String all = _builder.toString();
        result.put(PythonModelGeneratorUtil.toPyFileName(namespace, enum_.getName()), (all + enums));
      }
    }
    return result;
  }

  private List<RosettaEnumValue> allEnumsValues(final RosettaEnumeration enumeration) {
    final ArrayList<RosettaEnumValue> enumValues = new ArrayList<RosettaEnumValue>();
    RosettaEnumeration e = enumeration;
    while ((e != null)) {
      {
        final Consumer<RosettaEnumValue> _function = (RosettaEnumValue it) -> {
          enumValues.add(it);
        };
        e.getEnumValues().forEach(_function);
        e = e.getParent();
      }
    }
    final Function1<RosettaEnumValue, String> _function = (RosettaEnumValue it) -> {
      return it.getName();
    };
    return IterableExtensions.<RosettaEnumValue, String>sortBy(enumValues, _function);
  }

  private CharSequence generateEnums(final RosettaEnumeration enume, final String version) {
    StringConcatenation _builder = new StringConcatenation();
    final List<RosettaEnumValue> allEnumValues = this.allEnumsValues(enume);
    _builder.newLineIfNotEmpty();
    _builder.append("class ");
    String _name = enume.getName();
    _builder.append(_name);
    _builder.append("(Enum):");
    _builder.newLineIfNotEmpty();
    {
      String _definition = enume.getDefinition();
      boolean _tripleNotEquals = (_definition != null);
      if (_tripleNotEquals) {
        _builder.append("    ");
        _builder.append("\"\"\"");
        _builder.newLine();
        _builder.append("    ");
        String _definition_1 = enume.getDefinition();
        _builder.append(_definition_1, "    ");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("\"\"\"");
        _builder.newLine();
      }
    }
    {
      int _size = allEnumValues.size();
      boolean _tripleEquals = (_size == 0);
      if (_tripleEquals) {
        _builder.append("    ");
        _builder.append("pass");
        _builder.newLine();
      } else {
        {
          boolean _hasElements = false;
          for(final RosettaEnumValue value : allEnumValues) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate("", "    ");
            }
            _builder.append("    ");
            String _convertValue = EnumHelper.convertValue(value);
            _builder.append(_convertValue, "    ");
            _builder.append(" = \"");
            {
              String _display = value.getDisplay();
              boolean _tripleNotEquals_1 = (_display != null);
              if (_tripleNotEquals_1) {
                String _display_1 = value.getDisplay();
                _builder.append(_display_1, "    ");
              } else {
                String _name_1 = value.getName();
                _builder.append(_name_1, "    ");
              }
            }
            _builder.append("\"");
            _builder.newLineIfNotEmpty();
            {
              String _definition_2 = value.getDefinition();
              boolean _tripleNotEquals_2 = (_definition_2 != null);
              if (_tripleNotEquals_2) {
                _builder.append("    ");
                _builder.append("\"\"\"");
                _builder.newLine();
                _builder.append("    ");
                String _definition_3 = value.getDefinition();
                _builder.append(_definition_3, "    ");
                _builder.newLineIfNotEmpty();
                _builder.append("    ");
                _builder.append("\"\"\"");
                _builder.newLine();
              }
            }
          }
        }
      }
    }
    return _builder;
  }
}
