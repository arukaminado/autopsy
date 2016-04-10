/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sleuthkit.autopsy.casemodule;

import javax.swing.JPanel;
import org.openide.util.Lookup;
import org.sleuthkit.autopsy.casemodule.AddImageWizardChooseDataSourceVisual.DataSourceConfiguration;
import org.sleuthkit.autopsy.corecomponentinterfaces.DataSourceProcessor;

public interface DecryptionProvider extends DataSourceProcessor {

    /**
     *
     * @param volumeMetaData
     */
    public void setVolumeMetaData(VolumeMetaData volumeMetaData);

    public void setDataSourceConfiguration(DataSourceConfiguration dataSourceConfiguration);

    public DecryptionProvider decryptionProviderFactory(VolumeMetaData volumeMetaData, String key, int keyType, String deviceId);

    public String getName();

    public JPanel getPanel();

    public void start();
    
    public void stop();
    /**
     *
     * @param volumeMetaData
     * @return
     */
    public boolean matchesVolume(VolumeMetaData volumeMetaData);


    /**
     *
     * @param className
     * @return
     */
    public static DecryptionProvider getInstaceForClassString(String className) {
        for (DecryptionProvider decryptionProvider : Lookup.getDefault().lookupAll(DecryptionProvider.class)) {
            if(className.equals(decryptionProvider.getClass().toString())) { 
                return decryptionProvider;
            }
        }
        return null;
    }
}
