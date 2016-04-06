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

    private static final String tagNameString = "exeFiles";
    TagsManager tagsManager;
    TagName exeTag;

    @Override
    public ProcessResult process(AbstractFile file) {
        try {
            if (file.getNameExtension().equals("exe")) {
                try {
                    tagsManager.addContentTag(file, exeTag);
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
            exeTag = tagsManager.addTagName(tagNameString, "All Files ending wiht .exe", TagName.HTML_COLOR.LIME);
        } catch (TagsManager.TagNameAlreadyExistsException ex) {
            try {
                for (TagName tagName : tagsManager.getAllTagNames()) {
                    if (tagName.getDisplayName().equals(tagNameString)) {
                        exeTag = tagName;
                        return;
                    }
                }
            } catch (TskCoreException ex1) {
                Exceptions.printStackTrace(ex1);
            }
        } catch (TskCoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

}
