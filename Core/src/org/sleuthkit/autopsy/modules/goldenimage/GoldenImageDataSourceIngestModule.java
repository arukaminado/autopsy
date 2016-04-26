/*
 * Sample module in the public domain.  Feel free to use this as a template
 * for your modules.
 * 
 *  Contact: Brian Carrier [carrier <at> sleuthkit [dot] org]
 *
 *  This is free and unencumbered software released into the public domain.
 *  
 *  Anyone is free to copy, modify, publish, use, compile, sell, or
 *  distribute this software, either in source code form or as a compiled
 *  binary, for any purpose, commercial or non-commercial, and by any
 *  means.
 *  
 *  In jurisdictions that recognize copyright laws, the author or authors
 *  of this software dedicate any and all copyright interest in the
 *  software to the public domain. We make this dedication for the benefit
 *  of the public at large and to the detriment of our heirs and
 *  successors. We intend this dedication to be an overt act of
 *  relinquishment in perpetuity of all present and future rights to this
 *  software under copyright law.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *  IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 *  OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 *  ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE. 
 */
package org.sleuthkit.autopsy.modules.goldenimage;

import java.io.IOException;
import java.util.ArrayList;
import org.sleuthkit.autopsy.modules.goldenimage.*;
import java.util.List;
import java.util.logging.Level;
import org.openide.util.Exceptions;
import org.sleuthkit.autopsy.casemodule.Case;
import org.sleuthkit.autopsy.casemodule.LocalFilesDSProcessor;
import org.sleuthkit.autopsy.casemodule.services.FileManager;
import org.sleuthkit.autopsy.casemodule.services.TagsManager;
import org.sleuthkit.autopsy.ingest.DataSourceIngestModuleProgress;
import org.sleuthkit.autopsy.ingest.IngestModule;
import org.sleuthkit.datamodel.AbstractFile;
import org.sleuthkit.datamodel.Content;
import org.sleuthkit.datamodel.TskCoreException;
import org.sleuthkit.autopsy.coreutils.Logger;
import org.sleuthkit.autopsy.ingest.DataSourceIngestModule;
import org.sleuthkit.autopsy.ingest.IngestJobContext;
import org.sleuthkit.autopsy.ingest.IngestManager;
import org.sleuthkit.autopsy.ingest.IngestMessage;
import org.sleuthkit.autopsy.ingest.IngestServices;
import org.sleuthkit.datamodel.HashUtility;
import org.sleuthkit.datamodel.LocalFilesDataSource;
import org.sleuthkit.datamodel.TskData;

/**
 * Sample data source ingest module that doesn't do much. Demonstrates per
 * ingest job module settings, checking for job cancellation, updating the
 * DataSourceIngestModuleProgress object, and use of a subset of the available
 * ingest services.
 */
class GoldenImageDataSourceIngestModule implements DataSourceIngestModule {

   // private final boolean skipKnownFiles;
    private IngestJobContext context = null;
    GoldenImageModuleIngestJobSettings settings;
    private ArrayList<AbstractFile> deletedFiles;
    private ArrayList<AbstractFile> comparisonFailFiles;
    
    private ArrayList<Content> contentsToScan;
    private boolean ingestStarted = false;
    private boolean readyToIngest = false;

    GoldenImageDataSourceIngestModule(GoldenImageModuleIngestJobSettings pSettings) {
	    settings = pSettings;
	    deletedFiles = new ArrayList<>();
	    comparisonFailFiles = new ArrayList<>();
	    contentsToScan = new ArrayList<>();
        //this.skipKnownFiles = settings.skipKnownFiles();
    }

    @Override
    public void startUp(IngestJobContext context) throws IngestModuleException {
        this.context = context;
	
    }

    @Override
    public ProcessResult process(Content dataSource, DataSourceIngestModuleProgress progressBar) {
	GIManager giManager = GIManager.getInstance();
	TagsManager tagsManager = Case.getCurrentCase().getServices().getTagsManager();
	Content goldenImageDS = settings.getSelectedDatasource();//giManager.getGoldenImageContent();
	
	
	if(goldenImageDS == null){
		throw new IllegalStateException("Golden Image DS Ingest Module: The Golden Image Datasource is null.");
	}
	
	System.out.println("Golden Image: Start Process");
	
	try {
		FileManager fileManager = Case.getCurrentCase().getServices().getFileManager();
		List<AbstractFile> allFiles = fileManager.findFiles(goldenImageDS, "%");
		
		int giFileCount = 0;
		int hashedGIFilesCount = 0;
		int hashedDIFilesCount = 0;
		
		if(!allFiles.isEmpty()){
			progressBar.switchToDeterminate(2000);//allFiles.size()); TODO
			for(AbstractFile aFile : allFiles){
				giFileCount++;
				//TODO DELETE
				if(giFileCount >= 2000)
					break;
				//Check if the AbstractFile is a File. Continue if it's a directory or similar.
				if(aFile.getType() != null && aFile.getType() != TskData.TSK_DB_FILES_TYPE_ENUM.DERIVED &&
					aFile.getType() != TskData.TSK_DB_FILES_TYPE_ENUM.FS &&
					aFile.getType() != TskData.TSK_DB_FILES_TYPE_ENUM.LOCAL){
					progressBar.progress("Jumping over Non-File.",giFileCount);
					continue;
				}
				
				
				AbstractFile dirtyImageFile = findFile(dataSource, aFile);
				String diFileName = "";
				String giFileName = (aFile.getName() == null?"":aFile.getName());
				
				////CALC HASH HashUtility.calculateMd5(pFile);
				if(dirtyImageFile != null){
					diFileName = (dirtyImageFile.getName()==null?"":dirtyImageFile.getName());
					//Check if the md5-Sum of the 2 Files are calculated; If not, calculate.
					if(calculateHash(dirtyImageFile)){
						hashedDIFilesCount++;
					}
					if(calculateHash(aFile)){
						hashedGIFilesCount++;
					}
					
					
					//Compare Files
					if(dirtyImageFile.getMd5Hash() == null || aFile.getMd5Hash() == null){
						//Can't compare - One of the hashes is missing
						comparisonFailFiles.add(aFile);
					}else if(dirtyImageFile.getMd5Hash().equals(aFile.getMd5Hash())){
						//System.out.println(giFileCount+": SAMESAME md5sum: "+dirtyImageFile.getMd5Hash()+" VS "+aFile.getMd5Hash());
						tagsManager.addContentTag(dirtyImageFile, GoldenImageIngestModuleFactory.giTagSafe, "");
						
						//Add File to "Files to Filter" List
						if(settings.getFilterSafeFiles()){
							contentsToScan.add(dirtyImageFile);
						}
					}else if(!dirtyImageFile.getMd5Hash().equals(aFile.getMd5Hash())){
						tagsManager.addContentTag(dirtyImageFile, GoldenImageIngestModuleFactory.giTagChanged, "The Content of this file is different from it's equivalent on the golden image.");
						
						//Add File to "Files to Filter" List
						if(settings.getFilterChangedFiles()){
							contentsToScan.add(dirtyImageFile);
						}
					}else{
						//Untagged File
						//Add File to "Files to Filter" List
						if(settings.getFilterUntaggedFiles()){
							contentsToScan.add(dirtyImageFile);
						}
					}
				}else{
					//File wasn't found in the dirty image
					deletedFiles.add(aFile);
				}
				
				progressBar.progress("Comparing "+dataSource.getName()+" with "+goldenImageDS.getName(),giFileCount);
			}
		}
		
		
		if (context.dataSourceIngestIsCancelled()) {
			return IngestModule.ProcessResult.OK;
		}
		
		//Run Ingest Modules TODO
		readyToIngest = true;
		startIngest();
		
		// Post Message
		//String msgText = String.format("Processed GI %d files", giFileCount);
		String msgText = "Golden Image: RESULTS =====\n Hashed GI Files: "+hashedGIFilesCount+"\n Hashed DI Files: "+hashedDIFilesCount+"\n Total Files: "+allFiles.size()+"\n Processed Files: "+giFileCount+"\n Deleted Files: "+deletedFiles.size()+"\n Comparison Failure Files: "+comparisonFailFiles.size();
		IngestMessage message = IngestMessage.createMessage(
			IngestMessage.MessageType.DATA,
			GoldenImageIngestModuleFactory.getModuleName(),
			msgText);
		IngestServices.getInstance().postMessage(message);

		return IngestModule.ProcessResult.OK;
		
		
	} catch (TskCoreException ex) {
		Exceptions.printStackTrace(ex);
	}
	  
	return IngestModule.ProcessResult.ERROR;
    }
    
    /**
     * This method searches for a file by filename and filepath in the given Datasource.
     * 
     * @param pDataSource The Datasource in which the file should be searched in
     * @param pFile The File which should be found in the Datasource
     * 
     * @return Returns an AbstractFile if the file was found in the datasource. Returns Null if it wasn't found.
     */
    private AbstractFile findFile(Content pDataSource, AbstractFile pFile){
	    if(pFile.getName() == null || pFile.getName().equals("") || pFile.getParentPath() == null || pFile.getParentPath().equals("")){
		    return null;
	    }
	    AbstractFile foundFile = null;
	    
	    
	    //TODO: EVTL CHECKEN OB ES SICH UM DIR ODER FILE HANDELT...NUR CHECK WENN FILE
	    FileManager fileManager = Case.getCurrentCase().getServices().getFileManager();
	    try {
		    ArrayList<AbstractFile> foundFiles = new ArrayList(fileManager.findFiles(pDataSource, (pFile.getName() != null ? pFile.getName() : ""), (pFile.getParentPath()!=null?pFile.getParentPath():"")));
		    if(foundFiles.isEmpty()){
			    foundFile = null;
		    }else{
			    foundFile = foundFiles.get(0);
		    }
	    } catch (TskCoreException ex) {
		    Exceptions.printStackTrace(ex);
	    }
	    
	    return foundFile;
    }
    
    /** This method takes an Abstract File, checks if its hash is already calculated, if not it tries to calculate it. 
     * @param AbstractFile The Abstract File of which the hash should be checked & calculated.
     * @return boolean true if the hash was calculated, false if it already exists or an error occured.
     *
     */
    private boolean calculateHash(AbstractFile pFile){
	if(pFile.getMd5Hash() == null || pFile.getMd5Hash().isEmpty()){
		try {
			HashUtility.calculateMd5(pFile);
			return true;
		} catch (IOException ex) {
			String uniquePath = "";
			try {
				uniquePath = pFile.getUniquePath();
			} catch (TskCoreException ex1) {
				System.out.println("EXCEPTION: Couldn't get Unique Path");
				//Exceptions.printStackTrace(ex1);
			}
			System.out.println("Golden Image Hashing error: Couldn't hash file, see following error.\n File: "+pFile.getName()+"\n DirType: "+pFile.getDirTypeAsString()+"\n  Size: "+pFile.getSize()+"\n Unique Path: "+uniquePath+"\n Parent Path: "+pFile.getParentPath()+"\n Type: "+pFile.getType());
			Exceptions.printStackTrace(ex);
			return false;
		}
	}
	return false;
    }
    
    /**
     * Start ingest after verifying we have a new image, we are ready to ingest,
     * and we haven't already ingested.
     */
    private void startIngest() {
	    System.out.println("Golden Image Ingest: Start");
	    System.out.println("Golden Image Ingest: Count of Files to ingest: "+contentsToScan.size());
        if (!contentsToScan.isEmpty() && readyToIngest && !ingestStarted) {
            ingestStarted = true;
	    
	    System.out.println("Golden Image Ingest: Queue Job");
            IngestManager.getInstance().queueIngestJob(contentsToScan, GIManager.getInstance().getIngestJobSettings());
        }
    }
}
