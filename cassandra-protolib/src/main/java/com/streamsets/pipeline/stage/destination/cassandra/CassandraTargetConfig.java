/*
 * Copyright 2017 StreamSets Inc.
 *
 * Licensed under the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.streamsets.pipeline.stage.destination.cassandra;

import com.datastax.driver.core.ProtocolVersion;
import com.streamsets.pipeline.api.ConfigDef;
import com.streamsets.pipeline.api.ListBeanModel;
import com.streamsets.pipeline.api.ValueChooserModel;
import com.streamsets.pipeline.lib.el.VaultEL;

import java.util.List;

public class CassandraTargetConfig {
  @ConfigDef(
      required = true,
      type = ConfigDef.Type.LIST,
      defaultValue = "[\"localhost\"]",
      label = "Cassandra Contact Points",
      description = "Hostnames of Cassandra nodes to use as contact points. To ensure a connection, enter several.",
      displayPosition = 10,
      group = "CASSANDRA"
  )
  public List<String> contactPoints;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.NUMBER,
      defaultValue = "9042",
      label = "Cassandra Port",
      description = "Port number to use when connecting to Cassandra nodes",
      displayPosition = 20,
      group = "CASSANDRA"
  )
  public int port;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.MODEL,
      label = "Protocol Version",
      description = "If unsure which setting to use, refer to: https://datastax.github" +
          ".io/java-driver/manual/native_protocol",
      displayPosition = 30,
      group = "CASSANDRA"
  )
  @ValueChooserModel(ProtocolVersionChooserValues.class)
  public ProtocolVersion protocolVersion;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.MODEL,
      defaultValue = "LZ4",
      label = "Compression",
      description = "Optional compression for transport-level requests and responses.",
      displayPosition = 40,
      group = "CASSANDRA"
  )
  @ValueChooserModel(CompressionChooserValues.class)
  public CassandraCompressionCodec compression;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.BOOLEAN,
      label = "Use Credentials",
      defaultValue = "false",
      displayPosition = 60,
      group = "CASSANDRA"
  )
  public boolean useCredentials = false;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.STRING,
      label = "Fully Qualified Table Name",
      description = "Table write to, e.g. <keyspace>.<table_name>",
      displayPosition = 70,
      group = "CASSANDRA"
  )
  public String qualifiedTableName;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.MODEL,
      label = "Field to Column Mapping",
      description = "Fields to map to Cassandra columns. To avoid errors, field data types must match.",
      displayPosition = 80,
      group = "CASSANDRA"
  )
  @ListBeanModel
  public List<CassandraFieldMappingConfig> columnNames;

  /** Credentials group **/
  @ConfigDef(
      required = true,
      type = ConfigDef.Type.STRING,
      label = "Username",
      displayPosition = 10,
      elDefs = VaultEL.class,
      group = "CREDENTIALS",
      dependsOn = "useCredentials",
      triggeredByValue = "true"
  )
  public String username;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.STRING,
      label = "Password",
      defaultValue = "",
      displayPosition = 20,
      elDefs = VaultEL.class,
      group = "CREDENTIALS",
      dependsOn = "useCredentials",
      triggeredByValue = "true"
  )
  public String password;
}
