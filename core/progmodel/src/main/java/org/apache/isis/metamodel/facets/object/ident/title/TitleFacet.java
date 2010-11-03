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

package org.apache.isis.metamodel.facets.object.ident.title;

import org.apache.isis.metamodel.adapter.ObjectAdapter;
import org.apache.isis.metamodel.facets.Facet;
import org.apache.isis.metamodel.facets.object.ident.icon.IconFacet;
import org.apache.isis.metamodel.facets.object.ident.plural.PluralFacet;

/**
 * Mechanism for obtaining the title of an instance of a class, used to label the instance in the viewer (usually
 * alongside an icon representation).
 * 
 * <p>
 * In the standard Apache Isis Programming Model, typically corresponds to a method named <tt>title</tt>.
 * 
 * @see IconFacet
 * @see PluralFacet
 */
public interface TitleFacet extends Facet {

    String title(final ObjectAdapter object);
}
