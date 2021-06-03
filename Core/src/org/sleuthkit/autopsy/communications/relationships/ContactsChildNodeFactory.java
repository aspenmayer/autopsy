/*
 * Autopsy Forensic Browser
 *
 * Copyright 2019-2021 Basis Technology Corp.
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
package org.sleuthkit.autopsy.communications.relationships;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.sleuthkit.autopsy.coreutils.Logger;
import org.sleuthkit.autopsy.datamodel.BlackboardArtifactNode.BlackboardArtifactNodeKey;
import org.sleuthkit.datamodel.BlackboardArtifact;
import static org.sleuthkit.datamodel.BlackboardArtifact.ARTIFACT_TYPE.TSK_CONTACT;
import org.sleuthkit.datamodel.Content;
import org.sleuthkit.datamodel.TskCoreException;

/**
 * ChildFactory for ContactNodes.
 */
final class ContactsChildNodeFactory extends ChildFactory<BlackboardArtifactNodeKey>{
    private static final Logger logger = Logger.getLogger(ContactsChildNodeFactory.class.getName());

    private SelectionInfo selectionInfo;

    /**
     * Construct a new ContactsChildNodeFactory from the currently selectionInfo
     *
     * @param selectionInfo SelectionInfo object for the currently selected
     *                      accounts
     */
    ContactsChildNodeFactory(SelectionInfo selectionInfo) {
        this.selectionInfo = selectionInfo;    
    }
    
    /**
     * Updates the current instance of selectionInfo and calls the refresh method.
     * 
     * @param selectionInfo New instance of the currently selected accounts
     */
    public void refresh(SelectionInfo selectionInfo) {
        this.selectionInfo = selectionInfo;
        refresh(true);
    }

    /**
     * Creates a list of Keys (BlackboardArtifact) for only contacts of the
     * currently selected accounts
     * @param list List of BlackboardArtifact to populate
     * @return True on success
     */
    @Override
    protected boolean createKeys(List<BlackboardArtifactNodeKey> list) {
        
        if(selectionInfo == null) {
            return true;
        }

        final Set<Content> relationshipSources;
        try {
            relationshipSources = selectionInfo.getRelationshipSources();
        } catch (TskCoreException ex) {
            logger.log(Level.SEVERE, "Failed to load relationship sources.", ex); //NON-NLS
            return false;
        }
  
        relationshipSources.stream().filter((content) -> (content instanceof BlackboardArtifact)).forEachOrdered((content) -> {

            BlackboardArtifact bba = (BlackboardArtifact) content;
            BlackboardArtifact.ARTIFACT_TYPE fromID = BlackboardArtifact.ARTIFACT_TYPE.fromID(bba.getArtifactTypeID());

            if (fromID == TSK_CONTACT) {
                try {
                    list.add(BlackboardArtifactNodeKey.createNodeKey(bba));
                } catch (TskCoreException ex) {
                    logger.log(Level.SEVERE, "Failed create contact node keys.", ex); //NON-NLS
                }
            }
        });

        return true;
    }

    @Override
    protected Node createNodeForKey(BlackboardArtifactNodeKey key) {
        return new ContactNode(key);
    }
}
