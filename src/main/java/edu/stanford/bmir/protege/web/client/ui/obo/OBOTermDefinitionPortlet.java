package edu.stanford.bmir.protege.web.client.ui.obo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtext.client.widgets.MessageBox;
import edu.stanford.bmir.protege.web.client.LoggedInUserProvider;
import edu.stanford.bmir.protege.web.client.rpc.data.NotSignedInException;
import edu.stanford.bmir.protege.web.shared.obo.OBOTermDefinition;
import edu.stanford.bmir.protege.web.shared.project.ProjectId;
import edu.stanford.bmir.protege.web.shared.selection.SelectionModel;
import org.semanticweb.owlapi.model.OWLEntity;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 20/05/2012
 */
public class OBOTermDefinitionPortlet extends AbstractOBOTermPortlet {

    private OBOTermDefinitionEditor editor;

    @Inject
    public OBOTermDefinitionPortlet(ProjectId projectId, SelectionModel selectionModel, EventBus eventBus, LoggedInUserProvider loggedInUserProvider) {
        super(selectionModel, eventBus, projectId, loggedInUserProvider);
        editor = new OBOTermDefinitionEditorImpl();
        getContentHolder().setWidget(editor.asWidget());
    }

    @Override
    protected boolean isDirty() {
        return editor.isDirty();
    }

    @Override
    protected void clearDisplay() {
        editor.clearValue();
    }

    @Override
    protected void commitChangesForEntity(OWLEntity entity) {
        if(!editor.getValue().isPresent()) {
            return;
        }
        getService().setDefinition(getProjectId(), entity, editor.getValue().get(), new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
                if(caught instanceof NotSignedInException) {
                    MessageBox.alert("Your changes to the term definition have not been saved.  You must be signed in to make changes.");
                }
                else {
                    MessageBox.alert(caught.getMessage());
                    GWT.log(caught.getMessage(), caught);
                }

            }

            public void onSuccess(Void result) {

            }
        });
    }

    @Override
    protected void displayEntity(OWLEntity entity) {
        getService().getDefinition(getProjectId(), entity, new AsyncCallback<OBOTermDefinition>() {
            public void onFailure(Throwable caught) {
                MessageBox.alert(caught.getMessage());
            }

            public void onSuccess(OBOTermDefinition result) {
                if (result == null) {
                    editor.clearValue();
                }
                else {
                    editor.setValue(result);
                }
                updateTitle();
            }
        });
    }

    @Override
    protected String getTitlePrefix() {
        return "Definition";
    }

}
