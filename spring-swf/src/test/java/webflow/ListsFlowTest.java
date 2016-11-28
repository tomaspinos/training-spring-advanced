package webflow;

import java.util.Collections;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.engine.EndState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;

import cz.profinit.training.springadvanced.domain.MagnificentList;
import cz.profinit.training.springadvanced.service.MagnificentListService;

public class ListsFlowTest extends FlowTestBase {

    private MagnificentListService magnificentListService;

    public ListsFlowTest() {
        super("lists/lists-flow.xml");
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        magnificentListService = easyMockSupport.createMock(MagnificentListService.class);
    }

    protected void configureFlowBuilderContext(final MockFlowBuilderContext builderContext) {
        super.configureFlowBuilderContext(builderContext);
        builderContext.registerBean("listService", magnificentListService);
    }


    @Test
    public void testStartFlow() {
        final List<MagnificentList> model = Collections.emptyList();
        EasyMock.expect(magnificentListService.getLists()).andReturn(model);
        easyMockSupport.replayAll();

        final ExternalContext context = new MockExternalContext();
        startFlow(context);

        easyMockSupport.verifyAll();
        assertCurrentStateEquals("lists-view");
    }


    @Test
    public void testTransition() {

        final List<MagnificentList> model = Collections.emptyList();
        EasyMock.expect(magnificentListService.getLists()).andReturn(model);
        easyMockSupport.replayAll();

        setCurrentState("lists-view");

        // takhle lze nastavit potrebne promenne
        getFlowScope().put("mlists", model);

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
