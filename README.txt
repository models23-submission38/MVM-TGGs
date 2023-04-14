Installation:
- download and install Eclipse Modeling Tools (e.g. via the Eclipse installer from https://www.eclipse.org/downloads/)
- add the Eclipse Neon update-site (http://download.eclipse.org/releases/neon/) to the list of registered update-sites
- install ClassDiagram, SDM- and MoTE2-related Eclipse-Plugins from our update-site: https://www.hpi.uni-potsdam.de/giese/update-site/ ("SAM Modeling Languages", "SDM Metamodels, Editors, and Interpreters" and "MoTE2" category)
- install MoDisco from the MoDisco update-site: http://download.eclipse.org/modeling/mdt/modisco/updates/release/

Running
- the "de.mdelab.migmm.sample.java2class.execute" project contains classes with main methods for experiment execution
- these classes usually need to be parametrized with at least an input model (e.g. from the "instances.zip" archive; use "*.history" files for multi-version ("History") experiments and the related directories for single-version ("Vanilla") experiments) and a TGG ("de.mdelab.mltgg.java2class/model-gen/config.xmi" for single-version models or "de.mdelab.mltgg.java2class.adapted/model-gen/config.xmi" for multi-version models)
- the "de.mdelab.migmm.sample.java2class.tests" project contains tests that validate the transformation and synchronization results of the multi-version implementation against the standard MoTE2 implementation for a sample model
