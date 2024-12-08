package com.regnosys.rosetta.generator.python.object;

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
import org.eclipse.xtext.xbase.lib.Extension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(InjectionExtension.class)
@InjectWith(RosettaInjectorProvider.class)
@SuppressWarnings("all")
public class RosettaExtensionsTest {
  @Inject
  @Extension
  private ModelHelper _modelHelper;

  @Inject
  private PythonCodeGenerator generator;

  @Test
  public void testSuperClasses() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("namespace test");
    _builder.newLine();
    _builder.newLine();
    _builder.append("type Foo extends Bar:");
    _builder.newLine();
    _builder.append("type Bar extends Baz:");
    _builder.newLine();
    _builder.append("type Baz:");
    _builder.newLine();
    final HashMap<String, CharSequence> python = this.generatePython(_builder);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("class Baz(BaseDataClass):");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("pass");
    _builder_1.newLine();
    final String expectedBaz = _builder_1.toString();
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append("class Bar(Baz):");
    _builder_2.newLine();
    _builder_2.append("    ");
    _builder_2.append("pass");
    _builder_2.newLine();
    final String expectedBar = _builder_2.toString();
    StringConcatenation _builder_3 = new StringConcatenation();
    _builder_3.append("class Foo(Bar):");
    _builder_3.newLine();
    _builder_3.append("    ");
    _builder_3.append("pass");
    _builder_3.newLine();
    final String expectedFoo = _builder_3.toString();
    Assertions.assertTrue(python.toString().contains(expectedBaz));
    Assertions.assertTrue(python.toString().contains(expectedBar));
    Assertions.assertTrue(python.toString().contains(expectedFoo));
  }

  @Test
  public void testEnumValue() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("namespace test");
    _builder.newLine();
    _builder.append("version \"1.2.3\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("enum Foo:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("foo0 foo1");
    _builder.newLine();
    _builder.newLine();
    _builder.append("enum Bar extends Foo:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("bar");
    _builder.newLine();
    _builder.append("enum Baz extends Bar:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("baz");
    _builder.newLine();
    final HashMap<String, CharSequence> python = this.generatePython(_builder);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("class Bar(Enum):");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("BAR = \"bar\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("FOO_0 = \"foo0\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("FOO_1 = \"foo1\"");
    _builder_1.newLine();
    final String expectedBar = _builder_1.toString();
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append("class Baz(Enum):");
    _builder_2.newLine();
    _builder_2.append("    ");
    _builder_2.append("BAR = \"bar\"");
    _builder_2.newLine();
    _builder_2.append("    ");
    _builder_2.append("BAZ = \"baz\"");
    _builder_2.newLine();
    _builder_2.append("    ");
    _builder_2.append("FOO_0 = \"foo0\"");
    _builder_2.newLine();
    _builder_2.append("    ");
    _builder_2.append("FOO_1 = \"foo1\"");
    _builder_2.newLine();
    final String expectedBaz = _builder_2.toString();
    StringConcatenation _builder_3 = new StringConcatenation();
    _builder_3.append("class Foo(Enum):");
    _builder_3.newLine();
    _builder_3.append("    ");
    _builder_3.append("FOO_0 = \"foo0\"");
    _builder_3.newLine();
    _builder_3.append("    ");
    _builder_3.append("FOO_1 = \"foo1\"");
    _builder_3.newLine();
    final String expectedFoo = _builder_3.toString();
    Assertions.assertTrue(python.toString().contains(expectedBar));
    Assertions.assertTrue(python.toString().contains(expectedBaz));
    Assertions.assertTrue(python.toString().contains(expectedFoo));
  }

  public HashMap<String, CharSequence> generatePython(final CharSequence model) {
    HashMap<String, CharSequence> _xblockexpression = null;
    {
      final RosettaModel m = this._modelHelper.parseRosettaWithNoErrors(model);
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
