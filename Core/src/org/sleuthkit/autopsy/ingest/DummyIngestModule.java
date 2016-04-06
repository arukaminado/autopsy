/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sleuthkit.autopsy.ingest;

import org.openide.util.Exceptions;
import org.sleuthkit.autopsy.casemodule.Case;
import org.sleuthkit.autopsy.casemodule.services.TagsManager;
import org.sleuthkit.datamodel.AbstractFile;
import org.sleuthkit.datamodel.TagName;
import org.sleuthkit.datamodel.TskCoreException;

/**
 *
 * @author root
 */
public class DummyIngestModule implements FileIngestModule {

    TagsManager tagsManager;
    TagName holz;

    @Override
    public ProcessResult process(AbstractFile file) {
        try {
            if (file.getNameExtension().equals("exe")) {
                try {
                    tagsManager.addContentTag(file, holz);
                } catch (TskCoreException ex) {
                    Exceptions.printStackTrace(ex);
                }

            }
        } catch (NullPointerException e) {
            // Because Null pointer Exceptions happen. 
        }
        return ProcessResult.OK;
    }

    @Override
    public void shutDown() {
    }

    @Override
    public void startUp(IngestJobContext context) throws IngestModuleException {
        tagsManager = Case.getCurrentCase().getServices().getTagsManager();
        try {
            holz = tagsManager.addTagName("exeFiles", "All Files ending wiht .exe", TagName.HTML_COLOR.LIME);
        } catch (TagsManager.TagNameAlreadyExistsException ex) {
            //
        } catch (TskCoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

}
