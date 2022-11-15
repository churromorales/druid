/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.druid.uint;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.inject.Binder;
import org.apache.druid.initialization.DruidModule;
import org.apache.druid.segment.serde.ComplexMetrics;
import org.apache.druid.uint.aggs.UnsignedIntAnyAggregatorFactory;
import org.apache.druid.uint.aggs.UnsignedIntMaxAggregatorFactory;
import org.apache.druid.uint.aggs.UnsignedIntMinAggregatorFactory;
import org.apache.druid.uint.aggs.UnsignedIntSumAggregatorFactory;

import java.util.Arrays;
import java.util.List;

public class UnsignedIntDruidModule implements DruidModule
{
  @Override
  public List<? extends Module> getJacksonModules()
  {
    return Arrays.asList(new SimpleModule("UnsignedIntDruidModule")
                             .registerSubtypes(
                                 new NamedType(UnsignedIntSumAggregatorFactory.class, UnsignedIntComplexSerde.TYPE),
                                 new NamedType(UnsignedIntMaxAggregatorFactory.class, UnsignedIntComplexSerde.TYPE),
                                 new NamedType(UnsignedIntMinAggregatorFactory.class, UnsignedIntComplexSerde.TYPE),
                                 new NamedType(UnsignedIntAnyAggregatorFactory.class, UnsignedIntComplexSerde.TYPE)
                             )
    );
  }

  @Override
  public void configure(Binder binder)
  {
    if (ComplexMetrics.getSerdeForType(UnsignedIntComplexSerde.TYPE) == null) {
      ComplexMetrics.registerSerde(UnsignedIntComplexSerde.TYPE, new UnsignedIntComplexSerde());
    }
  }
}