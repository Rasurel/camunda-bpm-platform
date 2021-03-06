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
package org.camunda.bpm.engine.rest.sub.repository.impl;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.rest.dto.repository.DeploymentDto;
import org.camunda.bpm.engine.rest.exception.InvalidRequestException;
import org.camunda.bpm.engine.rest.sub.repository.DeploymentResource;
import org.camunda.bpm.engine.rest.sub.repository.DeploymentResourcesResource;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

public class DeploymentResourceImpl implements DeploymentResource {

  private ProcessEngine engine;
  private String deploymentId;

  public DeploymentResourceImpl(ProcessEngine engine, String deploymentId) {
    this.engine = engine;
    this.deploymentId = deploymentId;
  }

  public DeploymentDto getDeployment() {
    RepositoryService repositoryService = engine.getRepositoryService();
    Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();

    if (deployment == null) {
      throw new InvalidRequestException(Status.NOT_FOUND, "Deployment with id '" + deploymentId + "' does not exist");
    }

    return DeploymentDto.fromDeployment(deployment);
  }

  public DeploymentResourcesResource getDeploymentResources() {
    return new DeploymentResourcesResourceImpl(engine, deploymentId);
  }
  
  @Override
  public void deleteDeployment(String deploymentId, UriInfo uriInfo) {
    RepositoryService repositoryService = engine.getRepositoryService();
    Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();

    if (deployment == null) {
      throw new InvalidRequestException(Status.NOT_FOUND, "Deployment with id '" + deploymentId + "' do not exist");
    }

    boolean cascade = isQueryPropertyEnabled(uriInfo, CASCADE);
    boolean skipCustomListeners = isQueryPropertyEnabled(uriInfo, "skipCustomListeners");

    repositoryService.deleteDeployment(deploymentId, cascade, skipCustomListeners);
  }

  protected boolean isQueryPropertyEnabled(UriInfo uriInfo, String property) {
    MultivaluedMap<String,String> queryParams = uriInfo.getQueryParameters();

    return queryParams.containsKey(property)
        && queryParams.get(property).size() > 0
        && "true".equals(queryParams.get(property).get(0));
  }

}
