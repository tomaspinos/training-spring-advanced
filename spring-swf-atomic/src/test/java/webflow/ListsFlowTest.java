package webflow;

import java.util.Collections;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.binding.convert.service.GenericConversionService;
import org.springframework.faces.model.OneSelectionTrackingListDataModel;
import org.springframework.faces.model.converter.DataModelConverter;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.engine.EndState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;

import cz.profinit.training.springadvanced.swf.model.MagnificentListModel;
import cz.profinit.training.springadvanced.swf.service.MagnificentListModelService;

public class ListsFlowTest extends FlowTestBase {

    private MagnificentListModelService magnificentListModelService;

    public ListsFlowTest() {
        super("lists/lists-flow.xml");
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        magnificentListModelService = easyMockSupport.createMock(MagnificentListModelService.class);
    }

    protected void configureFlowBuilderContext(final MockFlowBuilderContext builderContext) {
        builderContext.registerBean("listModelService", magnificentListModelService);
        // TODO: presunout do Base
        final GenericConversionService genericConversionService = (GenericConversionService) builderContext.getConversionService();
        genericConversionService.addAlias("dataModel", OneSelectionTrackingListDataModel.class);
        genericConversionService.addConverter(new DataModelConverter());
    }


    @Test
    public void testStartFlow() {
        final List<MagnificentListModel> model = Collections.emptyList();
        EasyMock.expect(magnificentListModelService.getLists()).andReturn(model);
        easyMockSupport.replayAll();

        final ExternalContext context = new MockExternalContext();
        startFlow(context);

        easyMockSupport.verifyAll();
        assertCurrentStateEquals("lists-view");
    }


    @Test
    public void testTransition() {

        final List<MagnificentListModel> mlists = Collections.emptyList();
        EasyMock.expect(magnificentListModelService.getLists()).andReturn(mlists);
        easyMockSupport.replayAll();

        setCurrentState("lists-view");

        // takhle lze nastavit potrebne promenne
        getFlowScope().put("mlists", mlists);

        getFlowDefinitionRegistry().registerFlowDefinition(createMockExampleSubflow());

        final MockExternalContext context = new MockExternalContext();
        context.setEventId("add-list");
        resumeFlow(context);

        easyMockSupport.verifyAll();
        setCurrentState("lists-view");
    }


    private Flow createMockExampleSubflow() {
        final Flow mockExampleFlow = new Flow("edit-list");
        /* Takhle lze zkontrolovat pripadne vstupy
        mockExampleFlow.setInputMapper(new Mapper() {
            public MappingResults map(Object source, Object target) {
                // assert that 1L was passed in as input
                assertEquals(1L, ((AttributeMap) source).get("hotelId"));
                return null;
            }
        });
        */
        // immediately return the bookingConfirmed outcome so the caller can respond
        new EndState(mockExampleFlow, "finish");
        return mockExampleFlow;
    }

}
