/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sleuthkit.autopsy.ingest;

import java.util.LinkedList;
import org.sleuthkit.datamodel.AbstractFile;

/**
 *
 * @author root
 */
public class DummyIngestModule implements FileIngestModule {

	LinkedList<String> a;

	@Override
	public ProcessResult process(AbstractFile file) {
		if (file.getNameExtension().equals("exe")) {
			a.add(file.getName());
		}
		return ProcessResult.OK;
	}

	@Override
	public void shutDown() {
		System.err.println(a);
	}

	@Override
	public void startUp(IngestJobContext context) throws IngestModuleException {
		a = new LinkedList<String>();
	}

}
