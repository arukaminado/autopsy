/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sleuthkit.autopsy.ingest;

import org.openide.util.lookup.ServiceProvider;


@ServiceProvider(service = IngestModuleFactory.class)
public class DummyIngestModuleFactory implements IngestModuleFactory {

	@Override
	public String getModuleDisplayName() {
		return "Dummy Module (BA)";
	}

	@Override
	public String getModuleDescription() {
		return "tags all Files with the ending *.exe";
	}

	@Override
	public String getModuleVersionNumber() {
		return "1.0.0";
	}

	@Override
	public boolean hasGlobalSettingsPanel() {
		return false;
	}

	@Override
	public IngestModuleGlobalSettingsPanel getGlobalSettingsPanel() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public IngestModuleIngestJobSettings getDefaultIngestJobSettings() {
		return new DummyIngestModuleIngestJobSettings();
	}

	@Override
	public boolean hasIngestJobSettingsPanel() {
		return false;
	}

	@Override
	public IngestModuleIngestJobSettingsPanel getIngestJobSettingsPanel(IngestModuleIngestJobSettings settings) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean isDataSourceIngestModuleFactory() {
		return false;
	}

	@Override
	public DataSourceIngestModule createDataSourceIngestModule(IngestModuleIngestJobSettings settings) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean isFileIngestModuleFactory() {
		return true;
	}

	@Override
	public FileIngestModule createFileIngestModule(IngestModuleIngestJobSettings settings) {
		return new DummyIngestModule();
	}
	
}
