//package com.trandat.btl_ltw.entity;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Optional;
//
//public class CustomUserDetails implements UserDetails{
//    private Optional<User> user;
//    private Collection<? extends GrantedAuthority> authorities;
//
//    public CustomUserDetails() {
//    }
//
//    public CustomUserDetails(Optional<User> user, Collection<? extends GrantedAuthority> authorities) {
//        this.user = user;
//        this.authorities = authorities;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return user.get().getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.get().getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
