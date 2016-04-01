package it.infn.mw.iam.persistence.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 
 *
 */
@Entity
@Table(name = "iam_account")
public class IamAccount {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, length = 36, unique = true)
  private String uuid;

  @Column(nullable = false, length = 128, unique = true)
  @NotNull
  private String username;

  @Column(length = 128)
  private String password;

  @OneToOne
  @JoinColumn(name = "iam_user_info_id")
  private IamAccountUserInfo userInfo;

  @ManyToMany
  private Set<Authority> authorities;

  @OneToOne(optional = true)
  private SamlAccount samlAccount;

  protected IamAccount() {
  }

  public Long getId() {

    return id;
  }

  public void setId(Long id) {

    this.id = id;
  }

  public String getUuid() {

    return uuid;
  }

  public void setUuid(String uuid) {

    this.uuid = uuid;
  }

  public String getUsername() {

    return username;
  }

  public void setUsername(String username) {

    this.username = username;
  }

  public String getPassword() {

    return password;
  }

  public void setPassword(String password) {

    this.password = password;
  }

  public IamAccountUserInfo getUserInfo() {

    return userInfo;
  }

  public void setUserInfo(IamAccountUserInfo userInfo) {

    this.userInfo = userInfo;
  }

  public Set<Authority> getAuthorities() {

    return authorities;
  }

  public void setAuthorities(Set<Authority> authorities) {

    this.authorities = authorities;
  }

  public SamlAccount getSamlAccount() {

    return samlAccount;
  }

  public void setSamlAccount(SamlAccount samlAccount) {

    this.samlAccount = samlAccount;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    IamAccount other = (IamAccount) obj;
    if (uuid == null) {
      if (other.uuid != null)
        return false;
    } else if (!uuid.equals(other.uuid))
      return false;
    return true;
  }

}