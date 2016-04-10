/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sleuthkit.autopsy.casemodule;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.sleuthkit.autopsy.casemodule.AddImageWizardChooseDataSourceVisual.DataSourceType;

public class AddImageWizardConfigureEncryptionPanel implements WizardDescriptor.Panel<WizardDescriptor>, PropertyChangeListener {

    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0

    private AddImageWizardChooseDataSourcePanel dsPanel;
    private AddImageWizardConfigureEncryptionVisual visual;

    private String lastPath = "";
    private List<DecryptionProvider> decryptionProviders;
    private final AddImageAction action;

    /**
     *
     * @param dsPanel
     */
    public AddImageWizardConfigureEncryptionPanel(AddImageWizardChooseDataSourcePanel dsPanel, AddImageAction action) {
        this.dsPanel = dsPanel;
        this.action = action;
        visual = new AddImageWizardConfigureEncryptionVisual(this);
    }

    @Override
    public Component getComponent() {
        WizardDescriptor settings = null;
        AddImageWizardChooseDataSourceVisual.DataSourceConfiguration dataSourceConfiguration = dsPanel.getComponent().getDataSourceConfiguration();

        if (!lastPath.equals(dataSourceConfiguration.path) && (dataSourceConfiguration.type == DataSourceType.Image || dataSourceConfiguration.type == DataSourceType.Device)) {
            decryptionProviders = getEncryptionProviderForDataSource(dataSourceConfiguration);
            visual.updateProviderList(decryptionProviders);
            lastPath = dataSourceConfiguration.path;
        }

        return visual;
    }

    @Override
    public HelpCtx getHelp() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public void readSettings(WizardDescriptor data) {
        return;
    }

    @Override
    public void storeSettings(WizardDescriptor data) {
        return;
    }

    @Override
    public boolean isValid() {
        if (decryptionProviders == null) {
            return true;
        }
        boolean result = true;
        for (DecryptionProvider dp : decryptionProviders) {
            result &= dp.getPanel().isValid();
        }
        return true;
    }

    @Override
    public void addChangeListener(ChangeListener cl) {
        synchronized (listeners) {
            listeners.add(cl);
        }
    }

    @Override
    public void removeChangeListener(ChangeListener cl) {
        synchronized (listeners) {
            listeners.remove(cl);
        }
    }

    private List<DecryptionProvider> getEncryptionProviderForDataSource(AddImageWizardChooseDataSourceVisual.DataSourceConfiguration dataSourceConfiguration) {
        List<DecryptionProvider> result = new LinkedList<>();

        if (!new File(dataSourceConfiguration.path).isFile()) {
            return result;
        }
        List<VolumeMetaData> volumeMetaDataList = VolumeMetaData.getVolumeMetaData(dataSourceConfiguration.path);

        for (VolumeMetaData volumeMetaData : volumeMetaDataList) {
            DecryptionProvider decryptionProvider = getDecryptionProvider(volumeMetaData, dataSourceConfiguration);
            if (decryptionProvider != null) {
                result.add(decryptionProvider);
            }
        }

        return result;
    }

    private DecryptionProvider getDecryptionProvider(VolumeMetaData volumeMetaData, AddImageWizardChooseDataSourceVisual.DataSourceConfiguration dataSourceConfiguration) {
        for (DecryptionProvider decryptionProvider : Lookup.getDefault().lookupAll(DecryptionProvider.class)) {
            if (decryptionProvider.matchesVolume(volumeMetaData)) {
                return decryptionProvider.decryptionProviderFactory(volumeMetaData, dataSourceConfiguration);
            }
        }
        return null;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Iterator<ChangeListener> it;
        synchronized (listeners) {
            it = new HashSet<>(listeners).iterator();
        }
        ChangeEvent ev = new ChangeEvent(this);
        while (it.hasNext()) {
            it.next().stateChanged(ev);
        }
    }

    List<DecryptionProvider> getDecryptionProvider() {
        return this.decryptionProviders;
    }

}
