package com.regnosys.rosetta.generator.python.util;

import com.regnosys.rosetta.types.RAttribute;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class PythonModelGeneratorUtil {
  public static CharSequence fileComment(final String version) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("# This file is auto-generated from the ISDA Common Domain Model, do not edit.");
    _builder.newLine();
    _builder.append("# Version: ");
    _builder.append(version);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder;
  }

  public static CharSequence comment(final String definition) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (((definition != null) && (!definition.isEmpty()))) {
        _builder.append("# ");
        _builder.newLine();
        _builder.append("# ");
        _builder.append(definition);
        _builder.newLineIfNotEmpty();
        _builder.append("#");
        _builder.newLine();
      }
    }
    return _builder;
  }

  public static CharSequence classComment(final String definition, final Iterable<RAttribute> attributes) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (((definition != null) && (!definition.isEmpty()))) {
        _builder.append("#");
        _builder.newLine();
        _builder.append("# ");
        _builder.append(definition);
        _builder.newLineIfNotEmpty();
        _builder.append("#");
        _builder.newLine();
        {
          for(final RAttribute attribute : attributes) {
            _builder.append(" ");
            _builder.append("# @param ");
            String _name = attribute.getName();
            _builder.append(_name, " ");
            _builder.append(" ");
            String _definition = attribute.getDefinition();
            _builder.append(_definition, " ");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("#");
        _builder.newLine();
      }
    }
    return _builder;
  }

  public static String createImports(final String name) {
    String _xblockexpression = null;
    {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("# pylint: disable=line-too-long, invalid-name, missing-function-docstring");
      _builder.newLine();
      _builder.append("# pylint: disable=bad-indentation, trailing-whitespace, superfluous-parens");
      _builder.newLine();
      _builder.append("# pylint: disable=wrong-import-position, unused-import, unused-wildcard-import");
      _builder.newLine();
      _builder.append("# pylint: disable=wildcard-import, wrong-import-order, missing-class-docstring");
      _builder.newLine();
      _builder.append("# pylint: disable=missing-module-docstring");
      _builder.newLine();
      _builder.append("from __future__ import annotations");
      _builder.newLine();
      _builder.append("from typing import List, Optional");
      _builder.newLine();
      _builder.append("import datetime");
      _builder.newLine();
      _builder.append("import inspect");
      _builder.newLine();
      _builder.append("from decimal import Decimal");
      _builder.newLine();
      _builder.append("from pydantic import Field");
      _builder.newLine();
      _builder.append("from rosetta.runtime.utils import (");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("BaseDataClass, rosetta_condition, rosetta_resolve_attr, rosetta_resolve_deep_attr");
      _builder.newLine();
      _builder.append(")");
      _builder.newLine();
      _builder.append("from rosetta.runtime.utils import *");
      _builder.newLine();
      _builder.newLine();
      _builder.append("__all__ = [");
      _builder.append((("\'" + name) + "\'"));
      _builder.append("]");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      final String imports = _builder.toString();
      _xblockexpression = imports;
    }
    return _xblockexpression;
  }

  public static String createImportsFunc(final String name) {
    String _xblockexpression = null;
    {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("# pylint: disable=line-too-long, invalid-name, missing-function-docstring, missing-module-docstring, superfluous-parens");
      _builder.newLine();
      _builder.append("# pylint: disable=wrong-import-position, unused-import, unused-wildcard-import, wildcard-import, wrong-import-order, missing-class-docstring");
      _builder.newLine();
      _builder.append("from __future__ import annotations");
      _builder.newLine();
      _builder.append("import sys");
      _builder.newLine();
      _builder.append("import datetime");
      _builder.newLine();
      _builder.append("import inspect");
      _builder.newLine();
      _builder.append("from decimal import Decimal");
      _builder.newLine();
      _builder.append("from rosetta.runtime.utils import *");
      _builder.newLine();
      _builder.append("from rosetta.runtime.func_proxy import replaceable, create_module_attr_guardian");
      _builder.newLine();
      final String imports = _builder.toString();
      _xblockexpression = imports;
    }
    return _xblockexpression;
  }

  public static String toFileName(final String namespace, final String fileName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("src/");
    String _replace = namespace.replace(".", "/");
    _builder.append(_replace);
    _builder.append("/");
    _builder.append(fileName);
    return _builder.toString();
  }

  public static String toPyFileName(final String namespace, final String fileName) {
    StringConcatenation _builder = new StringConcatenation();
    String _fileName = PythonModelGeneratorUtil.toFileName(namespace, fileName);
    _builder.append(_fileName);
    _builder.append(".py");
    return _builder.toString();
  }

  public static String toPyFunctionFileName(final String namespace, final String fileName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("src/");
    String _replace = namespace.replace(".", "/");
    _builder.append(_replace);
    _builder.append("/functions/");
    _builder.append(fileName);
    _builder.append(".py");
    return _builder.toString();
  }

  public static String createTopLevelInitFile(final String version) {
    return "from .version import __version__";
  }

  public static String createVersionFile(final String version) {
    final String versionComma = version.replace(".", ",");
    String _format = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    String _plus = (((((((((("version = (" + versionComma) + ",0)\n") + 
      "version_str = \'") + version) + "-0\'\n") + 
      "__version__ = \'") + version) + "\'\n") + 
      "__build_time__ = \'") + _format);
    return (_plus + "\'");
  }

  public static String createPYProjectTomlFile(final String namespace, final String version) {
    return (((((((((((((((("[build-system]\n" + 
      "requires = [\"setuptools>=62.0\"]\n") + 
      "build-backend = \"setuptools.build_meta\"\n\n") + 
      "[project]\n") + 
      "name = \"python-") + namespace) + "\"\n") + 
      "version = \"") + version) + "\"\n") + 
      "requires-python = \">= 3.10\"\n") + 
      "dependencies = [\n") + 
      "   \"pydantic>=2.6.1\",\n") + 
      "   \"rosetta.runtime==2.1.0\"\n") + 
      "]\n") + 
      "[tool.setuptools.packages.find]\n") + 
      "where = [\"src\"]");
  }
}
