package com.wld.config.security;

import com.wld.modular.domain.User;
import com.wld.modular.domain.Permission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SpringSecurity需要的用户详情
 *
 * @author wangzg
 */
public class SecurityUserDetails implements UserDetails {
    private User user;
    private List<Permission> permissionList;


    public SecurityUserDetails(User user, List<Permission> permissionList) {
        this.user = user;
        this.permissionList = permissionList;
    }

    public User getUser() {
        return this.user;
    }

    public Long getUserId() {
        return this.user.getId();
    }

    public List<Permission> getUserPermissions() {
        return this.permissionList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return permissionList.stream()
                .filter(permission -> permission.getValue() != null)
                .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !user.getIsDisabled();
    }
}
