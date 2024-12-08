package com.regnosys.rosetta.generator.python.exceptions;

import com.google.inject.Inject;
import com.regnosys.rosetta.generator.python.PythonCodeGenerator;
import com.regnosys.rosetta.rosetta.RosettaModel;
import com.regnosys.rosetta.tests.RosettaInjectorProvider;
import com.regnosys.rosetta.tests.util.ModelHelper;
import java.util.Collections;
import java.util.HashMap;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Test Principal
 */
@ExtendWith(InjectionExtension.class)
@InjectWith(RosettaInjectorProvider.class)
@SuppressWarnings("all")
public class PythonExceptionsTest {
  @Inject
  @Extension
  private ModelHelper _modelHelper;

  @Inject
  private PythonCodeGenerator generator;

  @Test
  public void testNonExistantAttributeType() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("type B:");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("intValue1 int (0..1)");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("intValue2 int (0..1)");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("aValue A (1..1)");
      _builder.newLine();
      this.generatePython(_builder);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception ex = (Exception)_t;
        InputOutput.<String>println(("----- testNonExistantAttributeType ... ex:" + ex));
        InputOutput.<String>println("----- testNonExistantAttributeType ... stack");
        ex.printStackTrace();
        Assertions.assertTrue(ex.getMessage().contains("Attribute type is null"));
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }

  @Test
  public void testNonExistiantTypeCondition() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("type TestType: <\"Test type with one-of condition.\">");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("field1 string (0..1) <\"Test string field 1\">");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("field2 string (0..1) <\"Test string field 2\">");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("condition BusinessCentersChoice: <\"Choice rule to represent an FpML choice construct.\">");
      _builder.newLine();
      _builder.append("         ");
      _builder.append("if field1 exists");
      _builder.newLine();
      _builder.append("             ");
      _builder.append("then field3 > 0");
      _builder.newLine();
      this.generatePython(_builder);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception ex = (Exception)_t;
        Assertions.assertTrue(ex.getMessage().contains("Unsupported callable type"));
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }

  @Test
  public void testNonExistiantTypeSuperType() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("type TestType1 extends TestType2:");
      _builder.newLine();
      _builder.append("TestType2Value1 number (0..1) <\"Test number\">");
      _builder.newLine();
      _builder.append("TestType2Value2 date (0..*) <\"Test date\">");
      _builder.newLine();
      this.generatePython(_builder);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception ex = (Exception)_t;
        Assertions.assertTrue(ex.getMessage().contains("SuperType is null"));
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }

  public HashMap<String, CharSequence> generatePython(final CharSequence model) {
    HashMap<String, CharSequence> _xblockexpression = null;
    {
      final RosettaModel m = this._modelHelper.parseRosetta(model);
      final ResourceSet resourceSet = m.eResource().getResourceSet();
      final String version = m.getVersion();
      final HashMap<String, CharSequence> result = CollectionLiterals.<String, CharSequence>newHashMap();
      result.putAll(this.generator.beforeAllGenerate(resourceSet, Collections.<RosettaModel>unmodifiableSet(CollectionLiterals.<RosettaModel>newHashSet(m)), version));
      result.putAll(this.generator.beforeGenerate(m.eResource(), m, version));
      result.putAll(this.generator.generate(m.eResource(), m, version));
      result.putAll(this.generator.afterGenerate(m.eResource(), m, version));
      result.putAll(this.generator.afterAllGenerate(resourceSet, Collections.<RosettaModel>unmodifiableSet(CollectionLiterals.<RosettaModel>newHashSet(m)), version));
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
}
