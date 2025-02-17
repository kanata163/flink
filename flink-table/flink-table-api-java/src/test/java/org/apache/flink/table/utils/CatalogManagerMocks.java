/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.table.utils;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.catalog.Catalog;
import org.apache.flink.table.catalog.CatalogManager;
import org.apache.flink.table.catalog.GenericInMemoryCatalog;

import javax.annotation.Nullable;

/** Mock implementations of {@link CatalogManager} for testing purposes. */
public final class CatalogManagerMocks {

    public static final String DEFAULT_CATALOG = EnvironmentSettings.DEFAULT_BUILTIN_CATALOG;

    public static final String DEFAULT_DATABASE = EnvironmentSettings.DEFAULT_BUILTIN_DATABASE;

    public static CatalogManager createEmptyCatalogManager() {
        return createCatalogManager(null);
    }

    public static CatalogManager createCatalogManager(@Nullable Catalog catalog) {
        final CatalogManager.Builder builder = preparedCatalogManager();
        if (catalog != null) {
            builder.defaultCatalog(DEFAULT_CATALOG, catalog);
        }
        final CatalogManager catalogManager = builder.build();
        catalogManager.initSchemaResolver(true, ExpressionResolverMocks.dummyResolver());
        return catalogManager;
    }

    public static CatalogManager.Builder preparedCatalogManager() {
        return CatalogManager.newBuilder()
                .classLoader(CatalogManagerMocks.class.getClassLoader())
                .config(new Configuration())
                .defaultCatalog(DEFAULT_CATALOG, createEmptyCatalog())
                .executionConfig(new ExecutionConfig());
    }

    public static Catalog createEmptyCatalog() {
        return new GenericInMemoryCatalog(DEFAULT_CATALOG, DEFAULT_DATABASE);
    }

    private CatalogManagerMocks() {
        // no instantiation
    }
}
