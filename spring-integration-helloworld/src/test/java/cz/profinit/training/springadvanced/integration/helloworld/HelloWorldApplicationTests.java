package cz.profinit.training.springadvanced.integration.helloworld;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;

/**
 * TODO Make the test succeed.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HelloWorldApplication.class})
public class HelloWorldApplicationTests {

    private static final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@Autowired
	private HelloWorldApplication.HelloWorldGateway helloWorldGateway;

    @Before
    public void setUp() throws Exception {
        outputStream.reset();
    }

    @Test
	public void flowDecoratesInputProperly() {
		helloWorldGateway.place("123");

        Assert.assertEquals("2011-12-08T12:30 HELLO 123\n", outputStream.toString());
	}
}
