/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sleuthkit.autopsy.ingest;

/**
 *
 * @author root
 */
public class DummyIngestModuleIngestJobSettings implements IngestModuleIngestJobSettings {

	@Override
	public long getVersionNumber() {
		return 1;
	}

}
