package webflow;

import org.easymock.EasyMockSupport;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;

public abstract class FlowTestBase extends AbstractXmlFlowExecutionTests {

    public static final String WEBINF = "src/main/webapp/WEB-INF/";
    private final String flowName;
    EasyMockSupport easyMockSupport = new EasyMockSupport();

    protected FlowTestBase(String flowName) {
        this.flowName = flowName;
    }

    @Override
    protected FlowDefinitionResource getResource(FlowDefinitionResourceFactory flowDefinitionResourceFactory) {
        return flowDefinitionResourceFactory.createFileResource(WEBINF + "app/" + flowName);
    }

}
