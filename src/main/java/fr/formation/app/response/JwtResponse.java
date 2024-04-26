package fr.formation.app.response;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtResponse {
	@NonNull
	private String token;
	private String type = "Bearer";
	@NonNull
	private String username;
	@NonNull
	private Collection<? extends GrantedAuthority> authorities;
}
