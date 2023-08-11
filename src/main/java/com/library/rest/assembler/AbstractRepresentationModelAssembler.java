package com.library.rest.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

/**
 *
 * @author Erik Margaryam
 * @see RepresentationModel
 * @see RepresentationModelAssemblerSupport
 * @see LinkRelationProvider
 * @see EntityLinks
 */
public abstract class AbstractRepresentationModelAssembler<D, T extends RepresentationModel<T>> extends
        RepresentationModelAssemblerSupport<D, T> {

    private LinkRelationProvider relProvider;

    private EntityLinks entityLinks;

    /**
     * Creates a new {@link RepresentationModelAssemblerSupport} using the given controller class and resource type.
     *
     * @param controllerClass must not be {@literal null}.
     * @param resourceType    must not be {@literal null}.
     */
    protected AbstractRepresentationModelAssembler(Class<?> controllerClass, Class<T> resourceType) {
        super(controllerClass, resourceType);
    }

    /**
     * Sets Rel Provider
     *
     * @param relProvider {@link LinkRelationProvider}
     */
    @Autowired
    public void setRelProvider(LinkRelationProvider relProvider) {
        this.relProvider = relProvider;
    }

    /**
     * Sets entity links.
     *
     * @param entityLinks {@link EntityLinks}
     */
    @Autowired
    public void setEntityLinks(EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    /**
     * Gets rel provider.
     *
     * @return the rel provider
     */
    protected LinkRelationProvider getRelProvider() {
        return this.relProvider;
    }

    /**
     * Gets entity links.
     *
     * @return the entity links
     */
    protected EntityLinks getEntityLinks() {
        return this.entityLinks;
    }
}
