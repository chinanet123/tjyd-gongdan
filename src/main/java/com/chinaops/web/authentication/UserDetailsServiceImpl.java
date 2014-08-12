/*
 * $Id$
 * 
 * All Rights Reserved 2012 China OPS Information Technology Co.,Ltd.
 */
package com.chinaops.web.authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * UserDetailsService的实现，
 * @author Harley Ren  
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    // ========================== Attributes ============================
    private static Log       LOG = LogFactory.getLog(UserDetailsServiceImpl.class);

    /* （非 Javadoc）
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
        // TODO 自动生成方法存根
        return null;
    }

 /*   private UserManager      userManager;

    private PrivilegeManager privilegeManager;*/

    // ======================= Getters & Setters ========================
  /*  @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Autowired
    public void setPrivilegeManager(PrivilegeManager privilegeManager) {
        this.privilegeManager = privilegeManager;
    }*/

    // ======================== Public methods ==========================

    /* （非 Javadoc）
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
  /*  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EcloudUserDetails userDetails = null;
        try {
            LOG.debug("Get user by username : " + username);
            User user = userManager.getUserByLoginId(username);
            LOG.debug("Company ID : " + user.getCompanyId() + " Department ID: "
                    + user.getDepartmentId());

            List<GrantedAuthority> authorities = getUserGrantedAuthorities(user);
            boolean accountNonExpired = true;
            boolean accountNonLocked = true;
            boolean credentialsNonExpired = true;
            boolean enabled = true;
            userDetails = new EcloudUserDetails(user.getId(), user.getUsername(),
                    user.getPassword(), user.getCompanyId(), user.getDepartmentId(),
                    user.getRole(), enabled, accountNonExpired, credentialsNonExpired,
                    accountNonLocked, authorities);

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        if (userDetails != null) {
            return userDetails;
        }
        throw new UsernameNotFoundException("Username:" + username);
    }*/

    // ==================== Private utility methods =====================
    /**
     * 获得指定用户的权限，并用权限Privilege的token生成一个SimpleGrantedAuthoritys 
     * @param user 
     * @return SimpleGrantedAuthority数组，每个SimpleGrantedAuthority都包含了一个Privilege的token.
     */
   /* private List<GrantedAuthority> getUserGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        List<Privilege> privileges = privilegeManager.getUserPrivileges(user,
                PrivilegeCategory.ElasticInstance.toString());
        for (Privilege privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege.getToken()));
        }
        return authorities;
    }*/
    // ========================== main method ===========================
}
