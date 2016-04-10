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
     * @param volumeMetaData contains all required volume information
     * @param key required key for unlocking the volume
     * @param keyType differences between different key types for one
     * DecryptionProvider
     * @param deviceId identifier stored in Sleuthkit Case
     * @return new instance of DecryptionProvider with all information to unlock
     * the volume
     */
    public DecryptionProvider decryptionProviderFactory(VolumeMetaData volumeMetaData, String key, int keyType, String deviceId);

    /**
     *
     * @param volumeMetaData contains all required volume information
     * @param dataSourceConfiguration contains information to process a new
     * data-source
     * @return new instance of DecryptionProvider with all information to unlock
     * the volume
     */
    public DecryptionProvider decryptionProviderFactory(VolumeMetaData volumeMetaData, DataSourceConfiguration dataSourceConfiguration);

    @Override
    public JPanel getPanel();

    public void start();

    public void stop();

    /**
     *
     * @param volumeMetaData all required information to determine if the volume
     * can be unlocked with this decryptionProvider
     * @return true if the volume matches the DecryptionProvider
     */
    public boolean matchesVolume(VolumeMetaData volumeMetaData);

    /**
     *
     * @param className className of the requested DecryptionProvider
     * @return Empty Instance of the class from the param, null if no valid
     * DecryptionProvider was found
     */
    public static DecryptionProvider getInstaceForClassString(String className) {
        for (DecryptionProvider decryptionProvider : Lookup.getDefault().lookupAll(DecryptionProvider.class)) {
            if (className.equals(decryptionProvider.getClass().toString())) {
                return decryptionProvider;
            }
        }
        return null;
    }
}
