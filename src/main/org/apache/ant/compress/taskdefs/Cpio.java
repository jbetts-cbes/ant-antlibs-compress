/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.apache.ant.compress.taskdefs;

import org.apache.ant.compress.util.CpioStreamFactory;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.cpio.CpioArchiveEntry;
import org.apache.commons.compress.archivers.cpio.CpioConstants;

/**
 * Creates cpio archives.
 */
public class Cpio extends ArchiveBase {
    public Cpio() {
        super(new CpioStreamFactory(),
              new ArchiveBase.EntryBuilder() {
                public ArchiveEntry buildEntry(ArchiveBase.ResourceWithFlags r) {
                    boolean isDir = r.getResource().isDirectory();
                    CpioArchiveEntry ent =
                        new CpioArchiveEntry(r.getName(),
                                             isDir
                                             ? 0 : r.getResource().getSize());
                    ent.setTime(r.getResource().getLastModified() / 1000);

                    int mode =
                        isDir ? CpioConstants.C_ISDIR : CpioConstants.C_ISREG;
                    if (r.getResourceFlags().hasModeBeenSet()) {
                        ent.setMode(mode | r.getResourceFlags().getMode());
                    } else if (!isDir
                               && r.getCollectionFlags().hasModeBeenSet()) {
                        ent.setMode(mode | r.getCollectionFlags().getMode());
                    } else if (isDir
                               && r.getCollectionFlags().hasDirModeBeenSet()) {
                        ent.setMode(mode | r.getCollectionFlags().getDirMode());
                    } else {
                        ent.setMode(mode);
                    }

                    if (r.getResourceFlags().hasUserIdBeenSet()) {
                        ent.setUID(r.getResourceFlags().getUserId());
                    } else if (r.getCollectionFlags().hasUserIdBeenSet()) {
                        ent.setUID(r.getCollectionFlags().getUserId());
                    }

                    if (r.getResourceFlags().hasGroupIdBeenSet()) {
                        ent.setGID(r.getResourceFlags().getGroupId());
                    } else if (r.getCollectionFlags().hasGroupIdBeenSet()) {
                        ent.setGID(r.getCollectionFlags().getGroupId());
                    }
 
                    return ent;
                }
            });
    }

}