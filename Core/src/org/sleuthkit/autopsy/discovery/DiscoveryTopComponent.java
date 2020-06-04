/*
 * Autopsy
 *
 * Copyright 2019-2020 Basis Technology Corp.
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
package org.sleuthkit.autopsy.discovery;

import com.google.common.eventbus.Subscribe;
import java.awt.Graphics;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JSplitPane;
import org.openide.util.NbBundle;
import org.openide.windows.Mode;
import org.openide.windows.RetainLocation;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.sleuthkit.autopsy.coreutils.ThreadConfined;

/**
 * Create a dialog for displaying the Discovery results.
 */
@TopComponent.Description(preferredID = "DiscoveryTopComponent", persistenceType = TopComponent.PERSISTENCE_NEVER)
@TopComponent.Registration(mode = "discovery", openAtStartup = false)
@RetainLocation("discovery")
@NbBundle.Messages("DiscoveryTopComponent.name= Discovery")
public final class DiscoveryTopComponent extends TopComponent {

    private static final long serialVersionUID = 1L;
    private static final String PREFERRED_ID = "DiscoveryTopComponent"; // NON-NLS
    private final GroupListPanel groupListPanel;
    private final DetailsPanel detailsPanel;
    private final ResultsPanel resultsPanel;
    private int dividerLocation = -1;

    private static final int ANIMATION_INCREMENT = 10;
    private static final int RESULTS_AREA_SMALL_SIZE = 250;

    private SwingAnimator animator = null;

    /**
     * Creates new form DiscoveryTopComponent.
     */
    @ThreadConfined(type = ThreadConfined.ThreadType.AWT)
    public DiscoveryTopComponent() {
        initComponents();
        setName(Bundle.DiscoveryTopComponent_name());
        groupListPanel = new GroupListPanel();
        resultsPanel = new ResultsPanel();
        detailsPanel = new DetailsPanel();
        mainSplitPane.setLeftComponent(groupListPanel);
        rightSplitPane.setTopComponent(resultsPanel);
        rightSplitPane.setBottomComponent(detailsPanel);
    }

    /**
     * Get the current DiscoveryTopComponent if it is open.
     *
     * @return The open DiscoveryTopComponent or null if it has not been opened.
     */
    public static DiscoveryTopComponent getTopComponent() {
        return (DiscoveryTopComponent) WindowManager.getDefault().findTopComponent(PREFERRED_ID);
    }

    /**
     * Reset the top component so it isn't displaying any results.
     */
    public void resetTopComponent() {
        resultsPanel.resetResultViewer();
        groupListPanel.resetGroupList();
    }

    @Override
    public void componentOpened() {
        super.componentOpened();
        WindowManager.getDefault().setTopComponentFloating(this, true);
        DiscoveryEventUtils.getDiscoveryEventBus().register(this);
        DiscoveryEventUtils.getDiscoveryEventBus().register(resultsPanel);
        DiscoveryEventUtils.getDiscoveryEventBus().register(groupListPanel);
        DiscoveryEventUtils.getDiscoveryEventBus().register(detailsPanel);
    }

    @Override
    protected void componentClosed() {
        cancelCurrentSearch();
        DiscoveryEventUtils.getDiscoveryEventBus().unregister(this);
        DiscoveryEventUtils.getDiscoveryEventBus().unregister(groupListPanel);
        DiscoveryEventUtils.getDiscoveryEventBus().unregister(resultsPanel);
        DiscoveryEventUtils.getDiscoveryEventBus().unregister(detailsPanel);
        super.componentClosed();
    }

    private void cancelCurrentSearch() {
        DiscoveryDialog.getDiscoveryDialogInstance().cancelSearch();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainSplitPane = new javax.swing.JSplitPane();
        rightSplitPane = new AnimatedSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(199, 200));
        setPreferredSize(new java.awt.Dimension(1100, 700));
        setLayout(new java.awt.BorderLayout());

        mainSplitPane.setDividerLocation(250);
        mainSplitPane.setPreferredSize(new java.awt.Dimension(1100, 700));

        rightSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        rightSplitPane.setResizeWeight(1.0);
        rightSplitPane.setPreferredSize(new java.awt.Dimension(800, 700));
        mainSplitPane.setRightComponent(rightSplitPane);

        add(mainSplitPane, java.awt.BorderLayout.CENTER);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(DiscoveryTopComponent.class, "DiscoveryTopComponent.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(987, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        close();
        new OpenDiscoveryAction().actionPerformed(evt);
    }//GEN-LAST:event_jButton1ActionPerformed

    @Override
    public List<Mode> availableModes(List<Mode> modes) {
        /*
         * This looks like the right thing to do, but online discussions seems
         * to indicate this method is effectively deprecated. A break point
         * placed here was never hit.
         */
        return modes.stream().filter(mode -> mode.getName().equals("discovery"))
                .collect(Collectors.toList());
    }

    /**
     *
     * Fades this JPanel in. *
     */
    @Subscribe
    void handleDetailsVisibleEvent(DiscoveryEventUtils.DetailsVisibleEvent detailsVisibleEvent) {
        if (animator != null && animator.isRunning()) {
            animator.stop();
        }
        dividerLocation = rightSplitPane.getDividerLocation();
        if (detailsVisibleEvent.isShowDetailsArea()) {
            animator = new SwingAnimator(new ShowDetailsAreaCallback());
        } else {
            animator = new SwingAnimator(new HideDetailsAreaCallback());
        }
        animator.start();
    }

    /**
     *
     * Callback implementation for fading in
     *
     * @author Greg Cope
     *
     *
     *
     */
    private final class ShowDetailsAreaCallback implements SwingAnimatorCallback {

        @Override
        public void callback(Object caller) {
            dividerLocation -= ANIMATION_INCREMENT;
            repaint();
        }

        @Override
        public boolean hasTerminated() {
            if (dividerLocation != JSplitPane.UNDEFINED_CONDITION && dividerLocation < RESULTS_AREA_SMALL_SIZE) {
                dividerLocation = RESULTS_AREA_SMALL_SIZE;
                return true;
            }
            return false;
        }

    }

    /**
     *
     * Callback implementation to fade out
     *
     * @author Greg Cope
     *
     *
     *
     */
    private final class HideDetailsAreaCallback implements SwingAnimatorCallback {

        @Override
        public void callback(Object caller) {
            dividerLocation += ANIMATION_INCREMENT;
            repaint();
        }

        @Override
        public boolean hasTerminated() {
            if (dividerLocation > rightSplitPane.getHeight() || dividerLocation == JSplitPane.UNDEFINED_CONDITION) {
                dividerLocation = rightSplitPane.getHeight();
                return true;
            }
            return false;
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane mainSplitPane;
    private javax.swing.JSplitPane rightSplitPane;
    // End of variables declaration//GEN-END:variables

    private final class AnimatedSplitPane extends JSplitPane {

        private static final long serialVersionUID = 1L;

        @Override
        public void paintComponent(Graphics g) {
            if ((dividerLocation == JSplitPane.UNDEFINED_CONDITION) || (dividerLocation <= rightSplitPane.getHeight() && dividerLocation >= RESULTS_AREA_SMALL_SIZE)) {
                rightSplitPane.setDividerLocation(dividerLocation);
            }
            super.paintComponent(g);
        }

    }

}
