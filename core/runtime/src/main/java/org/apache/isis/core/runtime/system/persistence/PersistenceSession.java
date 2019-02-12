/*
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
package org.apache.isis.core.runtime.system.persistence;

import java.util.List;
import java.util.Map;

import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.inject.ServiceInjector;
import org.apache.isis.config.IsisConfiguration;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.adapter.ObjectAdapterByIdProvider;
import org.apache.isis.core.metamodel.adapter.ObjectAdapterProvider;
import org.apache.isis.core.metamodel.adapter.oid.RootOid;
import org.apache.isis.core.metamodel.spec.ManagedObjectState;
import org.apache.isis.core.runtime.persistence.objectstore.transaction.PersistenceCommand;
import org.apache.isis.core.runtime.persistence.objectstore.transaction.TransactionalResource;
import org.apache.isis.core.runtime.system.persistence.adaptermanager.ObjectAdapterContext.MementoRecreateObjectSupport;
import org.apache.isis.core.runtime.system.transaction.IsisTransactionManager;

public interface PersistenceSession 
extends 
    ObjectAdapterProvider.Delegating,
    ObjectAdapterByIdProvider.Delegating,
    TransactionalResource {

    // -------------------------------------------------------------------------------------------------
    // -- STABLE API (DRAFT)
    // -------------------------------------------------------------------------------------------------
    
    IsisConfiguration getConfiguration();
    IsisTransactionManager getTransactionManager();
    ServiceInjector getServiceInjector();
    
    void open();
    void close();
    
    default boolean flush() {
        return getTransactionManager().flushTransaction();
    }

    /**
     * Forces a reload (refresh in JDO terminology) of the domain object
     */
    void refreshRoot(Object domainObject);


    /**
     * Re-initializes the fields of an object. If the object is unresolved then
     * the object's missing data should be retrieved from the persistence
     * mechanism and be used to set up the value objects and associations.
     * @since 2.0.0-M2
     */
    default void refreshRootInTransaction(final Object domainObject) {
        getTransactionManager().executeWithinTransaction(()->refreshRoot(domainObject));
    }
    
    /**
     * @param pojo a persistable object
     * @return String representing an object's id.
     * @since 2.0.0-M2
     */
    String identifierFor(Object pojo);
    
    /**
     * @since 2.0.0-M3
     */
    ManagedObjectState stateOf(Object pojo);
    
//    /**
//     * For Persistable, state can either be ATTACHED or DETACHED or DESTROYED.
//     * @since 2.0.0-M3
//     */
//    boolean isAttached(Object pojo);
//    
//    /**
//     * For Persistable, state can either be ATTACHED or DETACHED or DESTROYED.
//     * @since 2.0.0-M3
//     */
//    boolean isDetached(Object pojo);
//       
//    /**
//     * For Persistable, state can either be ATTACHED or DETACHED or DESTROYED.
//     * @since 2.0.0-M2
//     */
//    boolean isDestroyed(Object pojo);
//    
//    
//    /**
//     * Tests whether this object is persistent. Instances that represent persistent objects in the 
//     * data store return true. (DELETED || ATTACHED)
//     * <p>
//     * May also be 'deleted' (that is, {@link #isDestroyed(Object)} could return true).
//     * @param pojo
//     * @return
//     * @since 2.0.0-M2
//     */
//    boolean isRepresentingPersistent(Object pojo);
    
    /** whether pojo is recognized by the persistence layer, that is, it has an ObjectId
     * @since 2.0.0-M2*/
    boolean isRecognized(Object pojo);
    
    /**@since 2.0.0-M2*/
    Object fetchPersistentPojo(RootOid rootOid);

    /**@since 2.0.0-M2*/
    default Object fetchPersistentPojoInTransaction(final RootOid oid) {
        return getTransactionManager().executeWithinTransaction(()->fetchPersistentPojo(oid));
    }

    /**@since 2.0.0-M2*/
    Map<RootOid, Object> fetchPersistentPojos(List<RootOid> rootOids);




    // -------------------------------------------------------------------------------------------------
    // -- JDO SPECIFIC
    // -------------------------------------------------------------------------------------------------
    
    javax.jdo.PersistenceManager getJDOPersistenceManager();
    /**
     * Convenient equivalent to {@code getPersistenceManager()}.
     * @return
     */
    default javax.jdo.PersistenceManager pm() {
        return getJDOPersistenceManager();
    }
    
    /**
     * Not type safe. For type-safe queries use <br/><br/> {@code pm().newNamedQuery(cls, queryName)}
     * @param cls
     * @param queryName
     * @return
     */
    default <T> javax.jdo.Query newJdoNamedQuery(Class<T> cls, String queryName){
        return pm().newNamedQuery(cls, queryName);
    }

    /**
     * Not type safe. For type-safe queries use <br/><br/> {@code pm().newQuery(cls, queryName)}
     * @param cls
     * @return
     */
    default <T> javax.jdo.Query newJdoQuery(Class<T> cls){
        return pm().newQuery(cls);
    }

    /**
     * Not type safe. For type-safe queries use <br/><br/> {@code pm().newQuery(cls, filter)}
     * @param cls
     * @param filter
     * @return
     */
    default <T> javax.jdo.Query newJdoQuery(Class<T> cls, String filter){
        return pm().newQuery(cls, filter);
    }
    
    // -------------------------------------------------------------------------------------------------
    // -- API NOT STABLE YET - SUBJECT TO REFACTORING
    // -------------------------------------------------------------------------------------------------
    

    // -- FIXTURE SUPPORT
    
    /**
     * @see #isFixturesInstalled()
     */
    static final String INSTALL_FIXTURES_KEY = "isis.persistor.datanucleus.install-fixtures";
    static final boolean INSTALL_FIXTURES_DEFAULT = false;
    
    boolean isFixturesInstalled();
    
    // -- MEMENTO SUPPORT
    
    MementoRecreateObjectSupport mementoSupport();
    
    // -- TODO remove ObjectAdapter references from API
    
    <T> List<ObjectAdapter> allMatchingQuery(final Query<T> query);
    <T> ObjectAdapter firstMatchingQuery(final Query<T> query);
    
    void destroyObjectInTransaction(ObjectAdapter adapter);
    void makePersistentInTransaction(ObjectAdapter adapter);
    
    // -- OTHERS
    
    void execute(List<PersistenceCommand> persistenceCommandList);
    
    long getLifecycleStartedAtSystemNanos();
    

}
