//package com.trandat.btl_ltw.service;
//
//import com.trandat.btl_ltw.entity.CustomUserDetails;
//import com.trandat.btl_ltw.entity.User;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class CustomUserDetailService implements UserDetailsService {
//
//    UserService userService;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> user = userService.findByUserName(username);
//        if(user.isEmpty()){
//            throw new UsernameNotFoundException("user not found");
//        }
//        Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
//
//        Set<String> roles = user.get().getRoles();
//        for(String userRole : roles){
//            grantedAuthoritySet.add(new SimpleGrantedAuthority(userRole));
//        }
//        return new CustomUserDetails(user, grantedAuthoritySet);
//    }
//}
