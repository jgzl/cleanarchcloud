package com.alibaba.csp.sentinel.adapter.dubbo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Test;

import com.alibaba.dubbo.rpc.Invocation;

/**
 * @author cdfive
 */
public class DubboUtilsTest {

	@Test
	public void testGetApplication() {
		Invocation invocation = mock(Invocation.class);
		when(invocation.getAttachments()).thenReturn(new HashMap<String, String>());
		when(invocation.getAttachment(DubboUtils.DUBBO_APPLICATION_KEY, "")).thenReturn("consumerA");

		String application = DubboUtils.getApplication(invocation, "");
		verify(invocation).getAttachment(DubboUtils.DUBBO_APPLICATION_KEY, "");

		assertEquals("consumerA", application);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetApplicationNoAttachments() {
		Invocation invocation = mock(Invocation.class);
		when(invocation.getAttachments()).thenReturn(null);
		when(invocation.getAttachment(DubboUtils.DUBBO_APPLICATION_KEY, "")).thenReturn("consumerA");

		DubboUtils.getApplication(invocation, "");

		fail("No attachments in invocation, IllegalArgumentException should be thrown!");
	}
}
