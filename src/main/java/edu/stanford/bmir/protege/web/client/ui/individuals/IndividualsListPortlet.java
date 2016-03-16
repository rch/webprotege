package edu.stanford.bmir.protege.web.client.ui.individuals;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import edu.stanford.bmir.protege.web.client.LoggedInUserProvider;
import edu.stanford.bmir.protege.web.client.individualslist.IndividualsListPresenter;
import edu.stanford.bmir.protege.web.client.portlet.AbstractOWLEntityPortlet;
import edu.stanford.bmir.protege.web.shared.project.ProjectId;
import edu.stanford.bmir.protege.web.shared.selection.SelectionModel;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;

public class IndividualsListPortlet extends AbstractOWLEntityPortlet {

    private final IndividualsListPresenter presenter;

    /*
     * Retrieved from the project configuration. If it is set,
     * then the individuals list will always display the instances
     * of the preconfigured class.
     */
    // TODO: This needs fixing
    private Optional<OWLClass> preconfiguredClass = Optional.absent();

    @Inject
    public IndividualsListPortlet(IndividualsListPresenter presenter,
                                  SelectionModel selectionModel,
                                  EventBus eventBus,
                                  LoggedInUserProvider loggedInUserProvider,
                                  ProjectId projectId) {
        super(selectionModel, eventBus, projectId, loggedInUserProvider);
        this.presenter = presenter;
        setTitle("Individuals");
        presenter.installActions(this);
        presenter.start(getContentHolder());

//        if (preconfiguredClass != null && preconfiguredClass.isPresent()) {
            // TODO:
//            presenter.setType(preconfiguredClass.get());
//        }
    }

    @Override
    protected void handleAfterSetEntity(Optional<OWLEntity> entity) {
        Optional<OWLClass> selectedClass;
        if(preconfiguredClass.isPresent()) {
            selectedClass = preconfiguredClass;
        }
        else if(getSelectionModel().getLastSelectedClass().isPresent()) {
            selectedClass = Optional.of(getSelectionModel().getLastSelectedClass().get());
        }
        else {
            selectedClass = Optional.absent();
        }


        if(selectedClass.isPresent()) {
            presenter.setType(selectedClass.get());
            updateTitle(selectedClass);
        }
        else {
            presenter.clearType();
        }
    }

    private void updateTitle(Optional<? extends OWLEntity> entityData) {
        if(entityData.isPresent()) {
            setTitle("Individuals");
        }
        else {
            setTitle("Individuals (nothing selected)");
        }
    }

    @Override
    public void onPermissionsChanged() {
    }


}
