/**
 * Copyright (c) Istituto Nazionale di Fisica Nucleare (INFN). 2016-2018
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.infn.mw.iam.actuator.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.OrderedHealthAggregator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import it.infn.mw.iam.actuator.health.ExternalServiceProbeIndicator;

@Component
@ConfigurationProperties(prefix = "health.externalServiceProbe")
public class ExternalServiceProbeEndpoint extends AbstractEndpoint<Health> {

  private static final String ENDPOINT_ID = "healthExternalService";

  private final HealthIndicator healthIndicator;

  @Autowired
  private HealthAggregator healthAggregator = new OrderedHealthAggregator();

  @Autowired
  public ExternalServiceProbeEndpoint(ExternalServiceProbeIndicator remoteHttpHostHealthIndicator) {
    super(ENDPOINT_ID, false);

    CompositeHealthIndicator indicator = new CompositeHealthIndicator(healthAggregator);
    indicator.addHealthIndicator("external", remoteHttpHostHealthIndicator);

    this.healthIndicator = indicator;
  }

  @Override
  public Health invoke() {
    return this.healthIndicator.health();
  }
}
