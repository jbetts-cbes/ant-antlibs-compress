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

package org.apache.ant.compress.resources;

import java.io.InputStream;
import java.io.IOException;

import org.apache.tools.ant.types.Resource;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

/**
 * Scans tar archives for resources.
 */
public class TarScanner extends CommonsCompressArchiveScanner {

    protected ArchiveInputStream getArchiveStream(InputStream is,
                                                  String encoding)
        throws IOException {
        return new TarArchiveInputStream(is);
    }

    protected Resource getResource(Resource archive, String encoding,
                                   ArchiveEntry entry) {
        return new TarResource(archive, (TarArchiveEntry) entry);
    }
}