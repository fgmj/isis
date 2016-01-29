/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.isis.applib.services.layout;

import javax.inject.Inject;
import javax.xml.bind.Marshaller;

import com.google.common.collect.ImmutableMap;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.layout.members.v1.Page;
import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.applib.value.Clob;

@Mixin
public class Object_downloadLayoutXml {

    private final Object object;

    public Object_downloadLayoutXml(final Object object) {
        this.object = object;
    }

    public static class ActionDomainEvent extends org.apache.isis.applib.IsisApplibModule.ActionDomainEvent<Object_downloadLayoutXml> {}

    @Action(
            domainEvent = ActionDomainEvent.class,
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            cssClassFa = "fa-download"
    )
    @MemberOrder(sequence = "550.1")
    public Object $$(
            @ParameterLayout(named = "File name")
            final String fileName) {
        final Page page = getPage();
        final String xml = jaxbService.toXml(page,
                ImmutableMap.<String,Object>of(
                        Marshaller.JAXB_SCHEMA_LOCATION,
                        pageService.schemaLocations(page)
                ));

        return new Clob(Util.withSuffix(fileName, "xml"), "text/xml", xml);
    }

    public boolean hide$$() {
        return getPage() == null;
    }
    public String default0$$() {
        return Util.withSuffix(object.getClass().getSimpleName(), "layout.xml");
    }

    protected Page getPage() {
        return pageService.toPage(object);
    }

    @Inject
    PageService pageService;

    @Inject
    JaxbService jaxbService;

}
