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
package it.infn.mw.iam.notification.service.resolver;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import it.infn.mw.iam.persistence.model.IamAccount;
import it.infn.mw.iam.persistence.repository.IamAccountRepository;

@Service
public class IamAccountByAuthorityAddressResolver implements ContextAwareAddressResolver<String> {

  final IamAccountRepository repo;

  @Autowired
  public IamAccountByAuthorityAddressResolver(IamAccountRepository repo) {
    this.repo = repo;
  }

  @Override
  public List<String> resolveEmailAddressForContext(String authority) {
    checkNotNull(authority);

    List<String> results = Lists.newArrayList();

    for (IamAccount a : repo.findByAuthority(authority)) {
      results.add(a.getUserInfo().getEmail());
    }
    
    return results;
  }
}
