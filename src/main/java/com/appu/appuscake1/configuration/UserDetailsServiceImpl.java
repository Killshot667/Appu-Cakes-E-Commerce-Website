package com.appu.appuscake1.configuration;

import com.appu.appuscake1.dao.Userdao;
import com.appu.appuscake1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private Userdao userdao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userdao.getUserByEmail(s);
        if(user==null)
        {
            throw new UsernameNotFoundException("Could not find user !!");
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        return customUserDetails;
    }
}
