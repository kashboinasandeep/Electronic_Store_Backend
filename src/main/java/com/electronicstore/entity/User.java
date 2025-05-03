package com.electronicstore.entity;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class User implements UserDetails{

	
	 @Id
	    //@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private String userId;

	    @Column(name = "user_name")
	    private String name;

	    @Column(name = "user_email", unique = true)
	    private String email;

	    @Column(name = "user_password", length = 500)
	    private String password;

	    private String gender;

	    @Column(length = 1000)
	    private String about;

	    @Column(name = "user_image_name")
	    private String imageName;

	    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
	    private List<Order> orders=new ArrayList<>();
	    
	    
	    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	    private List<Role> roles = new ArrayList<>();
	    
	    private Providers provider;
	    
	    
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			Set<SimpleGrantedAuthority> authorities = roles.stream().map(role-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
			return authorities ;
		}

		@Override
		public String getUsername() {
			// TODO Auto-generated method stub
			return this.getEmail();
		}
		
		

		@Override
		public String getPassword() {
			// TODO Auto-generated method stub
			return password;
		}

		@Override
		public boolean isAccountNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			return true;
		}
		
		
		


}