package com.aponte.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, estatus from Usuarios where username=?")
                .authoritiesByUsernameQuery("Select u.username, p.perfil from UsuarioPerfil up " +
                        "inner join Usuarios u on u.id = up.idUsuario " +
                        "inner join Perfiles p on p.id = up.idPerfil " +
                        "where u.username = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //LOS RECURSOS ESTATICOS NO REQUIERES AUTENTICACION
                .antMatchers(
                        "/bootstrap/**",
                        "/images/**",
                        "/tinymce/**",
                        "/logos/**"
                ).permitAll()
                //LAS VISTAS PUBLICAS NO REQUIEREN AUTENTICACION
                .antMatchers(
                        "/",
                        "/signup",
                        "/search",
                        "/bcrypt/**",
                        "/vacantes/view/**"
                ).permitAll()
                //PERMISOS URLs POR ROLES
                .antMatchers("/vacantes/**/").hasAnyAuthority("SUPERVISOR", "ADMINISTRADOR")
                .antMatchers("/categorias/**").hasAnyAuthority("SUPERVISOR", "ADMINISTRADOR")
                .antMatchers("/usuarios/**").hasAnyAuthority("ADMINISTRADOR")
                //LAS DEMAS URLS REQUIEREN AUTENTICACION
                .anyRequest().authenticated()
                //EL FORMULARIO DE LOGIN NO REQUIERE AUTENTICACION
                .and().formLogin().loginPage("/login").permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
