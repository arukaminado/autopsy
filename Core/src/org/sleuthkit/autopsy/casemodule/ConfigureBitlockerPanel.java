/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sleuthkit.autopsy.casemodule;

import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import org.sleuthkit.autopsy.casemodule.BitlockerDecryptionProvider.KeyType;

/**
 *
 * @author root
 */
public class ConfigureBitlockerPanel extends JPanel {

	private BitlockerDecryptionProvider.KeyType keyType;
	private String activeButton;
	private boolean initialized = false;

	/**
	 * Creates new form ConfigureBitlockerPanel
	 */
	public ConfigureBitlockerPanel() {
		super();
		initComponents();
		this.initialized = true;
		radioButtonRecoverykey.setSelected(true);
		keyType = KeyType.RECOVERY_KEY;
	}

	public KeyType getKeyType() {
		return keyType;
	}

	public String getKeyValue() {
		return recoveryKeyTextfield.getText();
	}

	private Boolean isValidRecoveryKey(String recoveryKey) {
		return recoveryKey.matches("([0-9]{6}-){7}[0-9]{6}");
	}
    
    public void setTitle(String title) {
        layeredPaneTitle.setBorder(BorderFactory.createTitledBorder(title));
    }

	@Override
	public synchronized boolean isValid() {
		
		if (!initialized) {
			return false;
		}
		errorLabel.setVisible(false);
		String str = recoveryKeyTextfield.getText();
		if (radioButtenBEK.isSelected()) {
			if (new File(str).exists()) {
				return true;
			} else {
				setError("BEK File does not exist");
				return false;
			}
		}

		if (radioButtonPassword.isSelected()) {
			if (str.length() > 0) {
				return true;
			} else {
				setError("Password cannot be empty");
				return false;
			}
		}

		if (radioButtonRecoverykey.isSelected()) {
			if (isValidRecoveryKey(str)) {
				return true;
			} else {
				setError("Invalid Recovery Key");
				return false;
			}
		}
		return false;
	}

	private void setError(String message) {
		errorLabel.setVisible(false);
		errorLabel.setText(message);
	}

	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        methodButtonGroup = new javax.swing.ButtonGroup();
        layeredPaneTitle = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        radioButtonRecoverykey = new javax.swing.JRadioButton();
        recoveryKeyTextfield = new javax.swing.JTextField();
        radioButtonPassword = new javax.swing.JRadioButton();
        radioButtenBEK = new javax.swing.JRadioButton();
        browseButton = new javax.swing.JButton();
        errorLabel = new javax.swing.JLabel();

        setName(""); // NOI18N

        layeredPaneTitle.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(ConfigureBitlockerPanel.class, "ConfigureBitlockerPanel.layeredPaneTitle.border.title"))); // NOI18N
        layeredPaneTitle.setName("last"); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ConfigureBitlockerPanel.class, "ConfigureBitlockerPanel.jLabel1.text")); // NOI18N

        methodButtonGroup.add(radioButtonRecoverykey);
        org.openide.awt.Mnemonics.setLocalizedText(radioButtonRecoverykey, org.openide.util.NbBundle.getMessage(ConfigureBitlockerPanel.class, "ConfigureBitlockerPanel.radioButtonRecoverykey.text")); // NOI18N
        radioButtonRecoverykey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButtonRecoverykeyActionPerformed(evt);
            }
        });

        recoveryKeyTextfield.setText(org.openide.util.NbBundle.getMessage(ConfigureBitlockerPanel.class, "ConfigureBitlockerPanel.recoveryKeyTextfield.text")); // NOI18N
        recoveryKeyTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recoveryKeyTextfieldActionPerformed(evt);
            }
        });

        methodButtonGroup.add(radioButtonPassword);
        org.openide.awt.Mnemonics.setLocalizedText(radioButtonPassword, org.openide.util.NbBundle.getMessage(ConfigureBitlockerPanel.class, "ConfigureBitlockerPanel.radioButtonPassword.text")); // NOI18N
        radioButtonPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButtonPasswordActionPerformed(evt);
            }
        });

        methodButtonGroup.add(radioButtenBEK);
        org.openide.awt.Mnemonics.setLocalizedText(radioButtenBEK, org.openide.util.NbBundle.getMessage(ConfigureBitlockerPanel.class, "ConfigureBitlockerPanel.radioButtenBEK.text")); // NOI18N
        radioButtenBEK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButtenBEKActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(browseButton, org.openide.util.NbBundle.getMessage(ConfigureBitlockerPanel.class, "ConfigureBitlockerPanel.browseButton.text")); // NOI18N
        browseButton.setEnabled(false);
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        errorLabel.setForeground(new java.awt.Color(255, 0, 0));
        org.openide.awt.Mnemonics.setLocalizedText(errorLabel, org.openide.util.NbBundle.getMessage(ConfigureBitlockerPanel.class, "ConfigureBitlockerPanel.errorLabel.text")); // NOI18N

        layeredPaneTitle.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredPaneTitle.setLayer(radioButtonRecoverykey, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredPaneTitle.setLayer(recoveryKeyTextfield, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredPaneTitle.setLayer(radioButtonPassword, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredPaneTitle.setLayer(radioButtenBEK, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredPaneTitle.setLayer(browseButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredPaneTitle.setLayer(errorLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layeredPaneTitleLayout = new javax.swing.GroupLayout(layeredPaneTitle);
        layeredPaneTitle.setLayout(layeredPaneTitleLayout);
        layeredPaneTitleLayout.setHorizontalGroup(
            layeredPaneTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layeredPaneTitleLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(layeredPaneTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(errorLabel)
                    .addGroup(layeredPaneTitleLayout.createSequentialGroup()
                        .addComponent(recoveryKeyTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseButton)))
                .addGap(24, 24, 24))
            .addGroup(layeredPaneTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layeredPaneTitleLayout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addGroup(layeredPaneTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1)
                        .addGroup(layeredPaneTitleLayout.createSequentialGroup()
                            .addComponent(radioButtonRecoverykey)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(radioButtonPassword)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(radioButtenBEK)))
                    .addContainerGap(108, Short.MAX_VALUE)))
        );
        layeredPaneTitleLayout.setVerticalGroup(
            layeredPaneTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layeredPaneTitleLayout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addGroup(layeredPaneTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(recoveryKeyTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errorLabel)
                .addGap(20, 20, 20))
            .addGroup(layeredPaneTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layeredPaneTitleLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(jLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layeredPaneTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radioButtonRecoverykey)
                        .addComponent(radioButtonPassword)
                        .addComponent(radioButtenBEK))
                    .addContainerGap(63, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(layeredPaneTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(layeredPaneTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        layeredPaneTitle.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(ConfigureBitlockerPanel.class, "ConfigureBitlockerPanel.layeredPaneTitle.AccessibleContext.accessibleDescription")); // NOI18N

        getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(ConfigureBitlockerPanel.class, "ConfigureBitlockerPanel.AccessibleContext.accessibleName")); // NOI18N
        getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(ConfigureBitlockerPanel.class, "ConfigureBitlockerPanel.AccessibleContext.accessibleDescription")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

        private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
		String oldText = recoveryKeyTextfield.getText();
		// set the current directory of the FileChooser if the ImagePath Field is valid
		File currentDir = new File(oldText);
		JFileChooser fc = new JFileChooser();
		if (currentDir.exists()) {
			fc.setCurrentDirectory(currentDir);
		}

		int retval = fc.showOpenDialog(this);
		if (retval == JFileChooser.APPROVE_OPTION) {
			String path = fc.getSelectedFile().getPath();
			recoveryKeyTextfield.setText(path);
		}
        }//GEN-LAST:event_browseButtonActionPerformed

        private void radioButtenBEKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioButtenBEKActionPerformed
		keyType = BitlockerDecryptionProvider.KeyType.BEK_FILE;
		browseButton.setEnabled(true);
        }//GEN-LAST:event_radioButtenBEKActionPerformed

        private void recoveryKeyTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recoveryKeyTextfieldActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_recoveryKeyTextfieldActionPerformed

        private void radioButtonPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioButtonPasswordActionPerformed
		keyType = BitlockerDecryptionProvider.KeyType.USER_PASSWORD;
		browseButton.setEnabled(false);
        }//GEN-LAST:event_radioButtonPasswordActionPerformed

        private void radioButtonRecoverykeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioButtonRecoverykeyActionPerformed
		keyType = BitlockerDecryptionProvider.KeyType.RECOVERY_KEY;
		browseButton.setEnabled(false);
        }//GEN-LAST:event_radioButtonRecoverykeyActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane layeredPaneTitle;
    private javax.swing.ButtonGroup methodButtonGroup;
    private javax.swing.JRadioButton radioButtenBEK;
    private javax.swing.JRadioButton radioButtonPassword;
    private javax.swing.JRadioButton radioButtonRecoverykey;
    private javax.swing.JTextField recoveryKeyTextfield;
    // End of variables declaration//GEN-END:variables
}