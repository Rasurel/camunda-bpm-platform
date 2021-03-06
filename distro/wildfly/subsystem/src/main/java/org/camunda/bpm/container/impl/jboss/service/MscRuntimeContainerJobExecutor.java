/* Licensed under the Apache License, Version 2.0 (the "License");
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
package org.camunda.bpm.container.impl.jboss.service;

import org.camunda.bpm.engine.impl.jobexecutor.RuntimeContainerJobExecutor;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

/**
 * @author Daniel Meyer
 * @author Roman Smirnov
 *
 */
public class MscRuntimeContainerJobExecutor extends RuntimeContainerJobExecutor implements Service<RuntimeContainerJobExecutor> {

  public RuntimeContainerJobExecutor getValue() throws IllegalStateException, IllegalArgumentException {
    return this;
  }

  public void start(StartContext arg0) throws StartException {
    start();
  }

  public void stop(StopContext arg0) {
    shutdown();
  }

}
