/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sleuthkit.autopsy.modules.goldenimage;

import java.util.ArrayList;
import java.util.logging.Level;
import org.sleuthkit.autopsy.casemodule.Case;
import org.sleuthkit.autopsy.ingest.IngestJobSettings;
import org.sleuthkit.autopsy.ingest.IngestModuleIngestJobSettings;
import org.sleuthkit.datamodel.Content;
import org.sleuthkit.datamodel.TskCoreException;

/**
 *
 * @author root
 */
public class GIManager {
    private static GIManager instance = null;
    
    private GoldenImageModuleIngestJobSettings settings = null;
    private IngestJobSettings ingestJobSettings= null;
    private Content goldenImageContent = null;
    
	/**
     * Gets the singleton instance of this class.
     */
    public static synchronized GIManager getInstance() {
        if (instance == null) {
            instance = new GIManager();
        }
        return instance;
    }
    
    public void setGoldenImageContent(Content pGoldenImageContent){
	    goldenImageContent = pGoldenImageContent;
    }
    
    public Content getGoldenImageContent(){
	    return goldenImageContent;
    }
    
    public void setSettings(GoldenImageModuleIngestJobSettings pSettings){
	    this.settings = pSettings;
    }
    
    public void setIngestJobSettings(IngestJobSettings pIngestJobSettings){
	    ingestJobSettings = pIngestJobSettings;
    }
    
    public GoldenImageModuleIngestJobSettings getSettings(){
	    return this.settings;
    }
    
    public IngestJobSettings getIngestJobSettings(){
	    return this.ingestJobSettings;
    }
    
    public void enableFilterUntaggedFiles(boolean pEnable){
	    if(settings != null){
		    settings.setFilterUntaggedFiles(pEnable);
	    }
    }
    
    public void enableFilterChangedFiles(boolean pEnable){
	    if(settings != null){
		    settings.setFilterChangedFiles(pEnable);
	    }
    }
    
    public void enableFilterSafeFiles(boolean pEnable){
	    if(settings != null){
		    settings.setFilterSafeFiles(pEnable);
	    }
    }
    
    public boolean isFilterUntaggedFilesEnabled(){
	    if(settings != null){
		    return settings.getFilterUntaggedFiles();
	    }
	    return false;
    }
    
    public boolean isFilterChangedFilesEnabled(){
	    if(settings != null){
		    return settings.getFilterChangedFiles();
	    }
	    return false;
    }
    
    public boolean isFilterSafeFilesEnabled(){
	    if(settings != null){
		    return settings.getFilterSafeFiles();
	    }
	    return false;
    }
    
    public Content getDatasourceById(long pDataSourceId){
	    Case currentCase = Case.getCurrentCase();
	    ArrayList<Content> listDS = new ArrayList<>();
	    try {
		    listDS.addAll(currentCase.getDataSources());
		    
	    } catch (TskCoreException ex) {
		    java.util.logging.Logger.getLogger(GoldenImageModuleIngestJobSettings.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    
	    if(!listDS.isEmpty()){
		    for(Content c : listDS){
			    if(c.getId() == pDataSourceId){
				    return c;
			    }
		    }
	    }
	    return null;
    }
    
    private GIManager(){}
}
