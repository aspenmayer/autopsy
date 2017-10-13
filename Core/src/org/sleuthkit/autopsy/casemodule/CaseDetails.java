/*
 * Autopsy Forensic Browser
 *
 * Copyright 2017 Basis Technology Corp.
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
package org.sleuthkit.autopsy.casemodule;

/**
 * Wrapper to contain the modifiable details of a case, such as case display
 * name, case number, and examiner related fields.
 */
class CaseDetails {

    private final String caseDisplayName;
    private final String caseNumber;
    private final String examinerName;
    private final String examinerPhone;
    private final String examinerEmail;
    private final String examinerNotes;

    CaseDetails(String displayName, String number, String exName, String exPhone, String exEmail, String exNotes) {
        caseDisplayName = displayName;
        caseNumber = number;
        examinerName = exName;
        examinerPhone = exPhone;
        examinerEmail = exEmail;
        examinerNotes = exNotes;
    }

    /**
     *  Get the case display name
     * @return  caseDisplayName - the display name of the case
     */
    String getCaseDisplayName(){
        return caseDisplayName;
    }
    
    /**
     *  Get the case number
     * @return caseNumber - the optional number assiciated with the case
     */
    String getCaseNumber(){
        return caseNumber;
    }
    
    /**
     * Get the examiner name
     *
     * @return name - the name associated with the examiner
     */
    String getExaminerName() {
        return examinerName;
    }

    /**
     * Get the examiner phone number
     *
     * @return phone - the phone number associated with the examiner
     */
    String getExaminerPhone() {
        return examinerPhone;
    }

    /**
     * Get the examiner email address
     *
     * @return email - the email address associated with the examiner
     */
    String getExaminerEmail() {
        return examinerEmail;
    }

    /**
     * Get the examiner notes
     *
     * @return notes - the note asssociated with the examiner
     */
    String getExaminerNotes() {
        return examinerNotes;
    }
}
