/*
 * Autopsy Forensic Browser
 *
 * Copyright 2011 - 2015 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.modules.goldenimage;

import org.sleuthkit.autopsy.modules.hashdatabase.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import org.openide.util.NbBundle;
import org.sleuthkit.autopsy.casemodule.Case;
import org.sleuthkit.autopsy.corecomponents.OptionsPanel;
import org.sleuthkit.autopsy.coreutils.Logger;
import org.sleuthkit.autopsy.events.AutopsyEvent;
import org.sleuthkit.autopsy.ingest.IngestJobSettings;
import org.sleuthkit.autopsy.ingest.IngestJobSettingsPanel;
import org.sleuthkit.autopsy.ingest.IngestManager;
import org.sleuthkit.datamodel.TskCoreException;
import org.sleuthkit.autopsy.ingest.IngestModuleGlobalSettingsPanel;
import static org.sleuthkit.autopsy.modules.goldenimage.GoldenImageIngestModuleIngestJobSettingsPanel.counter;
import org.sleuthkit.datamodel.Content;

/**
 * Instances of this class provide a comprehensive UI for managing the hash sets
 * configuration.
 */
public final class GoldenImageGlobalSettingsPanel extends IngestModuleGlobalSettingsPanel implements OptionsPanel {

	public IngestJobSettingsPanel ingestJobSettingsPanel;
	public static int counter = 0;
	
    private final GIManager giManager = GIManager.getInstance();

    public GoldenImageGlobalSettingsPanel() {
	counter++;
	    if(counter > 1){
		    return;
	    }
	IngestJobSettings ingestJobSettings = new IngestJobSettings(GoldenImageGlobalSettingsPanel.class.getCanonicalName(), IngestJobSettings.IngestType.FILES_ONLY);
	giManager.setIngestJobSettings(ingestJobSettings);
	this.ingestJobSettingsPanel = new IngestJobSettingsPanel(ingestJobSettings);
	
        initComponents();
        customizeComponents();

    }
    
    @Override
	protected void finalize() throws Throwable {
		super.finalize(); //To change body of generated methods, choose Tools | Templates.
		//counter--;
	}

    private void customizeComponents() {
	ingestPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ingestPanel.setLayout(new BorderLayout());
        ingestPanel.add(this.ingestJobSettingsPanel, BorderLayout.CENTER);
	ingestPanel.revalidate();
	ingestPanel.repaint();
    }

    private void updateComponents() {
        chChangedFiles.setSelected(giManager.isFilterChangedFilesEnabled());
        chUntaggedFiles.setSelected(giManager.isFilterUntaggedFilesEnabled());
        chSafeFiles.setSelected(giManager.isFilterSafeFilesEnabled());
    }


    @Override
    public void saveSettings() {
	giManager.enableFilterChangedFiles(chChangedFiles.isSelected());
	giManager.enableFilterUntaggedFiles(chUntaggedFiles.isSelected());
	giManager.enableFilterSafeFiles(chSafeFiles.isSelected());
	    
        IngestJobSettings ingestJobSettings = this.ingestJobSettingsPanel.getSettings();
	showWarnings(ingestJobSettings);
        ingestJobSettings.save();
    }

    private static void showWarnings(IngestJobSettings ingestJobSettings) {
        List<String> warnings = ingestJobSettings.getWarnings();
        if (warnings.isEmpty() == false) {
            StringBuilder warningMessage = new StringBuilder();
            for (String warning : warnings) {
                warningMessage.append(warning).append("\n");
            }
            JOptionPane.showMessageDialog(null, warningMessage.toString());
        }
    }

    @Override
    public void load() {
        
    }

    @Override
    public void store() {
        saveSettings();
    }

    public void cancel() {
        /*
         * Revert back to last settings only if the user could have made
         * changes. Doing this while ingest is running causes hash dbs to be
         * closed while they are still being used.
         */
        if (IngestManager.getInstance().isIngestRunning() == false) {
            //HashDbManager.getInstance().loadLastSavedConfiguration();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jLabel2 = new javax.swing.JLabel();
                jLabel4 = new javax.swing.JLabel();
                jLabel6 = new javax.swing.JLabel();
                jButton3 = new javax.swing.JButton();
                jScrollPane2 = new javax.swing.JScrollPane();
                jPanel1 = new javax.swing.JPanel();
                ingestPanel = new javax.swing.JPanel();
                filterPanel = new javax.swing.JPanel();
                lbTxtFilter = new javax.swing.JLabel();
                lbDataFilterDesc = new javax.swing.JLabel();
                chUntaggedFiles = new javax.swing.JCheckBox();
                chChangedFiles = new javax.swing.JCheckBox();
                chSafeFiles = new javax.swing.JCheckBox();
                lbTxtIngestModules = new javax.swing.JLabel();

                jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
                org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(GoldenImageGlobalSettingsPanel.class, "GoldenImageGlobalSettingsPanel.jLabel2.text")); // NOI18N

                jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
                org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(GoldenImageGlobalSettingsPanel.class, "GoldenImageGlobalSettingsPanel.jLabel4.text")); // NOI18N

                jLabel6.setFont(jLabel6.getFont().deriveFont(jLabel6.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
                org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(GoldenImageGlobalSettingsPanel.class, "GoldenImageGlobalSettingsPanel.jLabel6.text")); // NOI18N

                jButton3.setFont(jButton3.getFont().deriveFont(jButton3.getFont().getStyle() & ~java.awt.Font.BOLD, 14));
                org.openide.awt.Mnemonics.setLocalizedText(jButton3, org.openide.util.NbBundle.getMessage(GoldenImageGlobalSettingsPanel.class, "GoldenImageGlobalSettingsPanel.jButton3.text")); // NOI18N

                setMinimumSize(new java.awt.Dimension(700, 430));
                setPreferredSize(new java.awt.Dimension(700, 430));

                jPanel1.setPreferredSize(new java.awt.Dimension(671, 100));

                javax.swing.GroupLayout ingestPanelLayout = new javax.swing.GroupLayout(ingestPanel);
                ingestPanel.setLayout(ingestPanelLayout);
                ingestPanelLayout.setHorizontalGroup(
                        ingestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                );
                ingestPanelLayout.setVerticalGroup(
                        ingestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 287, Short.MAX_VALUE)
                );

                lbTxtFilter.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
                org.openide.awt.Mnemonics.setLocalizedText(lbTxtFilter, org.openide.util.NbBundle.getMessage(GoldenImageGlobalSettingsPanel.class, "GoldenImageGlobalSettingsPanel.lbTxtFilter.text")); // NOI18N

                lbDataFilterDesc.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
                org.openide.awt.Mnemonics.setLocalizedText(lbDataFilterDesc, org.openide.util.NbBundle.getMessage(GoldenImageGlobalSettingsPanel.class, "GoldenImageGlobalSettingsPanel.lbDataFilterDesc.text")); // NOI18N

                chUntaggedFiles.setSelected(true);
                org.openide.awt.Mnemonics.setLocalizedText(chUntaggedFiles, org.openide.util.NbBundle.getMessage(GoldenImageGlobalSettingsPanel.class, "GoldenImageGlobalSettingsPanel.chUntaggedFiles.text")); // NOI18N
                chUntaggedFiles.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                chUntaggedFilesActionPerformed(evt);
                        }
                });

                chChangedFiles.setSelected(true);
                org.openide.awt.Mnemonics.setLocalizedText(chChangedFiles, org.openide.util.NbBundle.getMessage(GoldenImageGlobalSettingsPanel.class, "GoldenImageGlobalSettingsPanel.chChangedFiles.text")); // NOI18N

                org.openide.awt.Mnemonics.setLocalizedText(chSafeFiles, org.openide.util.NbBundle.getMessage(GoldenImageGlobalSettingsPanel.class, "GoldenImageGlobalSettingsPanel.chSafeFiles.text")); // NOI18N

                javax.swing.GroupLayout filterPanelLayout = new javax.swing.GroupLayout(filterPanel);
                filterPanel.setLayout(filterPanelLayout);
                filterPanelLayout.setHorizontalGroup(
                        filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(filterPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbDataFilterDesc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(filterPanelLayout.createSequentialGroup()
                                                .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(filterPanelLayout.createSequentialGroup()
                                                                .addComponent(chSafeFiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGap(69, 69, 69))
                                                        .addGroup(filterPanelLayout.createSequentialGroup()
                                                                .addComponent(chChangedFiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGap(38, 38, 38))
                                                        .addComponent(lbTxtFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(filterPanelLayout.createSequentialGroup()
                                                                .addComponent(chUntaggedFiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGap(31, 31, 31)))
                                                .addGap(465, 465, 465)))
                                .addContainerGap())
                );
                filterPanelLayout.setVerticalGroup(
                        filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(filterPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbTxtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbDataFilterDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chUntaggedFiles, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chChangedFiles, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chSafeFiles, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                );

                lbTxtIngestModules.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
                org.openide.awt.Mnemonics.setLocalizedText(lbTxtIngestModules, org.openide.util.NbBundle.getMessage(GoldenImageGlobalSettingsPanel.class, "GoldenImageGlobalSettingsPanel.lbTxtIngestModules.text")); // NOI18N

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(filterPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(14, 14, 14))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(lbTxtIngestModules, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGap(214, 214, 214))
                                                        .addComponent(ingestPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(12, 12, 12)))
                                .addGap(19, 19, 19))
                );
                jPanel1Layout.setVerticalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lbTxtIngestModules, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ingestPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(filterPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                );

                jScrollPane2.setViewportView(jPanel1);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                                .addContainerGap())
                );
        }// </editor-fold>//GEN-END:initComponents

        private void chUntaggedFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chUntaggedFilesActionPerformed
                // TODO add your handling code here:
        }//GEN-LAST:event_chUntaggedFilesActionPerformed

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JCheckBox chChangedFiles;
        private javax.swing.JCheckBox chSafeFiles;
        private javax.swing.JCheckBox chUntaggedFiles;
        private javax.swing.JPanel filterPanel;
        private javax.swing.JPanel ingestPanel;
        private javax.swing.JButton jButton3;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JLabel lbDataFilterDesc;
        private javax.swing.JLabel lbTxtFilter;
        private javax.swing.JLabel lbTxtIngestModules;
        // End of variables declaration//GEN-END:variables
}
