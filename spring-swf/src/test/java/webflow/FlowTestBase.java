package webflow;

import org.easymock.EasyMockSupport;
import org.springframework.binding.convert.service.GenericConversionService;
import org.springframework.faces.model.OneSelectionTrackingListDataModel;
import org.springframework.faces.model.converter.DataModelConverter;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;

public abstract class FlowTestBase extends AbstractXmlFlowExecutionTests {

    public static final String WEBINF = "src/main/webapp/WEB-INF/";
    private final String flowName;
    EasyMockSupport easyMockSupport = new EasyMockSupport();

    protected FlowTestBase(final String flowName) {
        this.flowName = flowName;
    }

    @Override
    protected FlowDefinitionResource getResource(final FlowDefinitionResourceFactory flowDefinitionResourceFactory) {
        return flowDefinitionResourceFactory.createFileResource(WEBINF + "app/" + flowName);
    }

    @Override
    protected void configureFlowBuilderContext(final MockFlowBuilderContext builderContext) {
        final GenericConversionService genericConversionService = (GenericConversionService) builderContext.getConversionService();
        genericConversionService.addAlias("dataModel", OneSelectionTrackingListDataModel.class);
        genericConversionService.addConverter(new DataModelConverter());
    }
}
