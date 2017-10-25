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
package org.apache.isis.applib.layout.menus;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.google.common.collect.Lists;

import org.apache.isis.applib.layout.component.ActionLayoutData;
import org.apache.isis.applib.layout.component.ActionLayoutDataOwner;

/**
 * Corresponds to a domain service that contributes its actions under a particular {@link MenuBar}.
 */
@XmlType(
        name = "section"
        , propOrder = {
            "oid",
            "actions"
        }
)
public class MenuSection implements Serializable, HasOid, ActionLayoutDataOwner {

    private static final long serialVersionUID = 1L;

    public MenuSection() {
    }

    public MenuSection(String oid) {
        this.oid = oid;
    }

    private String oid;

    @Override
    @XmlAttribute(required = true)
    public String getOid() {
        return oid;
    }

    @Override
    public void setOid(final String oid) {
        this.oid = oid;
    }


    private List<ActionLayoutData> actions = Lists.newArrayList();

    // no wrapper
    @XmlElement(name = "action", required = false)
    public List<ActionLayoutData> getActions() {
        return actions;
    }

    public void setActions(List<ActionLayoutData> actionLayoutDatas) {
        this.actions = actionLayoutDatas;
    }


}