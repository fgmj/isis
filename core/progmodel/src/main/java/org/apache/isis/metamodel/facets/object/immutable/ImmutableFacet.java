/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.isis.metamodel.facets.object.immutable;

import org.apache.isis.metamodel.facets.SingleWhenValueFacet;
import org.apache.isis.metamodel.facets.object.ebc.EqualByContentFacet;
import org.apache.isis.metamodel.facets.object.value.ValueFacet;
import org.apache.isis.metamodel.interactions.DisablingInteractionAdvisor;

/**
 * Indicates that the instances of this class are immutable and so may not be modified either through the viewer or
 * indeed programmatically.
 * 
 * <p>
 * In the standard Apache Isis Programming Model, typically corresponds to applying the <tt>@Immutable</tt> annotation
 * at the class level.
 * 
 * @see EqualByContentFacet
 * @see ValueFacet
 */
public interface ImmutableFacet extends SingleWhenValueFacet, DisablingInteractionAdvisor {

}
