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

package org.apache.isis.metamodel.facets.collections.validate;

import org.apache.isis.metamodel.adapter.ObjectAdapter;
import org.apache.isis.metamodel.facets.Facet;
import org.apache.isis.metamodel.interactions.ValidatingInteractionAdvisor;

/**
 * Validate that an object can be removed to a collection.
 * 
 * <p>
 * In the standard Apache Isis Programming Model, corresponds to invoking the <tt>validateRemoveFromXxx</tt> support
 * method for a collection.
 */
public interface CollectionValidateRemoveFromFacet extends Facet, ValidatingInteractionAdvisor {

    /**
     * Reason the object cannot be removed, or <tt>null</tt> if okay.
     */
    public String invalidReason(ObjectAdapter inObject, ObjectAdapter value);
}
