/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.test;


import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.alibaba.csp.sentinel.util.TimeUtil;

/**
 * Mock support for {@link TimeUtil}
 *
 * @author jason
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({TimeUtil.class})
public abstract class AbstractTimeBasedTest {

	private long currentMillis = 0;

	{
		PowerMockito.mockStatic(TimeUtil.class);
		PowerMockito.when(TimeUtil.currentTimeMillis()).thenReturn(currentMillis);
	}

	protected final void useActualTime() {
		PowerMockito.when(TimeUtil.currentTimeMillis()).thenCallRealMethod();
	}

	protected final void setCurrentMillis(long cur) {
		currentMillis = cur;
		PowerMockito.when(TimeUtil.currentTimeMillis()).thenReturn(currentMillis);
	}

	protected final void sleep(int t) {
		currentMillis += t;
		PowerMockito.when(TimeUtil.currentTimeMillis()).thenReturn(currentMillis);
	}

	protected final void sleepSecond(int timeSec) {
		sleep(timeSec * 1000);
	}
}
