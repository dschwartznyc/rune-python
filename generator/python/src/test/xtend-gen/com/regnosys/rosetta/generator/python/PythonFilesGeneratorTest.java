package com.regnosys.rosetta.generator.python;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.regnosys.rosetta.rosetta.RosettaModel;
import com.regnosys.rosetta.tests.RosettaInjectorProvider;
import com.regnosys.rosetta.tests.util.ModelHelper;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test Principal
 */
@ExtendWith(InjectionExtension.class)
@InjectWith(RosettaInjectorProvider.class)
@SuppressWarnings("all")
public class PythonFilesGeneratorTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(PythonFilesGeneratorTest.class);

  @Inject
  private PythonCodeGenerator generator;

  @Inject
  @Extension
  private ParseHelper<RosettaModel> _parseHelper;

  @Inject
  private Provider<XtextResourceSet> resourceSetProvider;

  private Properties getProperties() throws Exception {
    MavenXpp3Reader reader = new MavenXpp3Reader();
    FileReader _fileReader = new FileReader("pom.xml");
    Model model = reader.read(_fileReader);
    return model.getProperties();
  }

  private void cleanSrcFolder(final String folderPath) {
    final File folder = new File(((folderPath + File.separator) + "src"));
    if ((folder.exists() && folder.isDirectory())) {
      try {
        FileUtils.cleanDirectory(folder);
      } catch (final Throwable _t) {
        if (_t instanceof IOException) {
          final IOException e = (IOException)_t;
          String _message = e.getMessage();
          String _plus = ("Failed to delete folder content: " + _message);
          PythonFilesGeneratorTest.LOGGER.error(_plus);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    } else {
      PythonFilesGeneratorTest.LOGGER.error((folderPath + " does not exist or is not a directory"));
    }
  }

  private void writeFiles(final String pythonTgtPath, final Map<String, ? extends CharSequence> generatedFiles) {
    try {
      Set<? extends Map.Entry<String, ? extends CharSequence>> _entrySet = generatedFiles.entrySet();
      for (final Map.Entry<String, ? extends CharSequence> entry : _entrySet) {
        {
          final String filePath = entry.getKey();
          final String fileContents = entry.getValue().toString();
          final Path outputPath = Path.of(((pythonTgtPath + File.separator) + filePath));
          Files.createDirectories(outputPath.getParent());
          Files.write(outputPath, fileContents.getBytes(StandardCharsets.UTF_8));
        }
      }
      PythonFilesGeneratorTest.LOGGER.info("Write Files ... wrote: {}", Integer.valueOf(generatedFiles.size()));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public HashMap<String, CharSequence> generatePythonFromRosettaModel(final RosettaModel m, final ResourceSet resourceSet) {
    HashMap<String, CharSequence> _xblockexpression = null;
    {
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

  public void generatePythonFromRosettaFiles(final String rosettaSourceName, final String outputPathName) {
    try {
      final Properties properties = this.getProperties();
      final String rosettaSource = properties.getProperty(rosettaSourceName);
      if ((rosettaSource == null)) {
        throw new Exception("Initialization failure: source Rosetta path not specified");
      }
      boolean _exists = Files.exists(Paths.get(rosettaSource));
      boolean _not = (!_exists);
      if (_not) {
        throw new Exception(("Unable to generate Python from non-existant Rosetta source directory: " + rosettaSource));
      }
      final String outputPath = properties.getProperty(outputPathName);
      if ((outputPath == null)) {
        throw new Exception("Initialization failure: Python target not specified");
      }
      PythonFilesGeneratorTest.LOGGER.info("generatePython ... creating Python from Rosetta found in {}", rosettaSource);
      PythonFilesGeneratorTest.LOGGER.info("generatePython ... creating resource set and adding common Rosetta models");
      final XtextResourceSet resourceSet = this.resourceSetProvider.get();
      this._parseHelper.parse(ModelHelper.commonTestTypes, resourceSet);
      resourceSet.getResource(URI.createURI("classpath:/model/basictypes.rosetta"), true);
      resourceSet.getResource(URI.createURI("classpath:/model/annotations.rosetta"), true);
      File _file = new File(rosettaSource);
      final Function1<File, List<File>> _function = (File it) -> {
        final FileFilter _function_1 = (File it_1) -> {
          return it_1.getName().endsWith("rosetta");
        };
        return IterableExtensions.<File>toList(((Iterable<File>)Conversions.doWrapArray(it.listFiles(_function_1))));
      };
      final Function1<File, Path> _function_1 = (File it) -> {
        return it.toPath();
      };
      final Iterable<Path> rosettaFilePaths = IterableExtensions.<File, Path>map(Iterables.<File>concat(ListExtensions.<File, List<File>>map(CollectionLiterals.<File>newArrayList(_file), _function)), _function_1);
      PythonFilesGeneratorTest.LOGGER.info("generatePythonFromRosettaFiles ... found {} rosetta files in {}", Integer.valueOf((((Object[])Conversions.unwrapArray(rosettaFilePaths, Object.class)).length)).toString(), rosettaSource);
      final Function1<Path, Resource> _function_2 = (Path it) -> {
        return resourceSet.getResource(URI.createURI(it.toString()), true);
      };
      final List<Resource> resources = IterableExtensions.<Resource>toList(IterableExtensions.<Path, Resource>map(rosettaFilePaths, _function_2));
      PythonFilesGeneratorTest.LOGGER.info("generatePythonFromRosettaFiles ... converted to resources");
      final Function1<Resource, Iterable<RosettaModel>> _function_3 = (Resource it) -> {
        return Iterables.<RosettaModel>filter(it.getContents(), RosettaModel.class);
      };
      List<RosettaModel> _list = IterableExtensions.<RosettaModel>toList(IterableExtensions.<Resource, RosettaModel>flatMap(resources, _function_3));
      final Collection<RosettaModel> rosettaModels = ((Collection<RosettaModel>) _list);
      PythonFilesGeneratorTest.LOGGER.info("generatePythonFromRosettaFiles ... created {} rosetta models", Integer.valueOf((((Object[])Conversions.unwrapArray(rosettaModels, Object.class)).length)).toString());
      final HashMap<String, CharSequence> generatedFiles = CollectionLiterals.<String, CharSequence>newHashMap();
      for (final RosettaModel model : rosettaModels) {
        {
          PythonFilesGeneratorTest.LOGGER.info("generatePythonFromRosettaFiles ... processing model: {}", model.getName());
          final HashMap<String, CharSequence> python = this.generatePythonFromRosettaModel(model, resourceSet);
          generatedFiles.putAll(python);
        }
      }
      this.cleanSrcFolder(outputPath);
      this.writeFiles(outputPath, generatedFiles);
      PythonFilesGeneratorTest.LOGGER.info("generatePythonFromRosettaFiles ... done");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Disabled("Generate CDM from Rosetta Files")
  @Test
  public void generateCDMPythonFromRosetta() {
    try {
      PythonFilesGeneratorTest.LOGGER.info("generateCDMPythonFromRosetta ... start");
      this.generatePythonFromRosettaFiles("cdm.rosetta.source.path", "cdm.python.output.path");
      PythonFilesGeneratorTest.LOGGER.info("generateCDMPythonFromRosetta ... done");
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException ioE = (IOException)_t;
        PythonFilesGeneratorTest.LOGGER.error("PythonFilesGeneratorTest::generateCDMPythonFromRosetta ... processing failed with an IO Exception");
        ioE.printStackTrace();
      } else if (_t instanceof ClassCastException) {
        final ClassCastException ccE = (ClassCastException)_t;
        PythonFilesGeneratorTest.LOGGER.error("PythonFilesGeneratorTest::generateCDMPythonFromRosetta ... processing failed with a ClassCastException");
        ccE.printStackTrace();
      } else if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        PythonFilesGeneratorTest.LOGGER.error("PythonFilesGeneratorTest::generateCDMPythonFromRosetta ... processing failed with an Exception");
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }

  @Test
  public void generatePythonFromGenericRosetta() {
    try {
      PythonFilesGeneratorTest.LOGGER.info("generatePythonFromGenericRosetta ... start");
      this.generatePythonFromRosettaFiles("unit.test.rosetta.source.path", "unit.test.python.output.path");
      PythonFilesGeneratorTest.LOGGER.info("generatePythonFromGenericRosetta ... done");
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException ioE = (IOException)_t;
        PythonFilesGeneratorTest.LOGGER.error("PythonFilesGeneratorTest::generatePythonUnitTestsFromRosetta ... processing failed with an IO Exception");
        String _string = ioE.toString();
        String _plus = ("\n" + _string);
        PythonFilesGeneratorTest.LOGGER.error(_plus);
        ioE.printStackTrace();
      } else if (_t instanceof ClassCastException) {
        final ClassCastException ccE = (ClassCastException)_t;
        PythonFilesGeneratorTest.LOGGER.error("PythonFilesGeneratorTest::generatePythonUnitTestsFromRosetta ... processing failed with a ClassCastException");
        String _string_1 = ccE.toString();
        String _plus_1 = ("\n" + _string_1);
        PythonFilesGeneratorTest.LOGGER.error(_plus_1);
        ccE.printStackTrace();
      } else if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        PythonFilesGeneratorTest.LOGGER.error("PythonFilesGeneratorTest::generatePythonUnitTestsFromRosetta ... processing failed with an Exception");
        String _string_2 = e.toString();
        String _plus_2 = ("\n" + _string_2);
        PythonFilesGeneratorTest.LOGGER.error(_plus_2);
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
}
