package edu.stanford.bmir.protege.web.client.ui.tab;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 04/01/16
 */
public enum Tabs {

    CLASSES_TAB("edu.stanford.bmir.protege.web.client.ui.ontology.classes", "Classes"),

    PROPERTIES_TAB("edu.stanford.bmir.protege.web.client.ui.ontology.properties.PropertiesTab", "Properties"),

    INDIVIDUALS_TAB("edu.stanford.bmir.protege.web.client.ui.ontology.individuals.IndividualsTab", "Individuals");


    private final String id;

    private final String shortName;

    Tabs(String tabId, String shortName) {
        this.id = tabId;
        this.shortName = shortName;
    }

    public String getId() {
        return id;
    }

    public String getShortName() {
        return shortName;
    }
}
