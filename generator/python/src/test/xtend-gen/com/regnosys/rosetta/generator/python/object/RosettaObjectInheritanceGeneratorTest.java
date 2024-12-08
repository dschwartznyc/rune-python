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
public class RosettaObjectInheritanceGeneratorTest {
  @Inject
  @Extension
  private ModelHelper _modelHelper;

  @Inject
  private PythonCodeGenerator generator;

  @Test
  public void shouldGeneratePythonClassWithMultipleParents() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("type D extends C:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("dd string (0..1)");
    _builder.newLine();
    _builder.append("type B extends A:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("bb string (0..1)");
    _builder.newLine();
    _builder.append("type C extends B:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("cc string (0..1)");
    _builder.newLine();
    _builder.append("type A:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("aa string (0..1)");
    _builder.newLine();
    final HashMap<String, CharSequence> python = this.generatePython(_builder);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("class A(BaseDataClass):");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("aa: Optional[str] = Field(None, description=\"\")");
    _builder_1.newLine();
    final String expectedA = _builder_1.toString();
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append("class B(A):");
    _builder_2.newLine();
    _builder_2.append("    ");
    _builder_2.append("bb: Optional[str] = Field(None, description=\"\")");
    _builder_2.newLine();
    final String expectedB = _builder_2.toString();
    StringConcatenation _builder_3 = new StringConcatenation();
    _builder_3.append("class C(B):");
    _builder_3.newLine();
    _builder_3.append("    ");
    _builder_3.append("cc: Optional[str] = Field(None, description=\"\")");
    _builder_3.newLine();
    final String expectedC = _builder_3.toString();
    StringConcatenation _builder_4 = new StringConcatenation();
    _builder_4.append("class D(C):");
    _builder_4.newLine();
    _builder_4.append("    ");
    _builder_4.append("dd: Optional[str] = Field(None, description=\"\")");
    _builder_4.newLine();
    final String expectedD = _builder_4.toString();
    Assertions.assertTrue(python.toString().contains(expectedA));
    Assertions.assertTrue(python.toString().contains(expectedB));
    Assertions.assertTrue(python.toString().contains(expectedC));
    Assertions.assertTrue(python.toString().contains(expectedD));
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
