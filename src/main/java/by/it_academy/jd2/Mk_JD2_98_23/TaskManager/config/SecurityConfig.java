package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.config;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.controller.filter.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter filter) throws Exception {

        // Enable CORS and disable CSRF
        http = http.csrf(AbstractHttpConfigurer::disable);


        // Set session management to stateless
        http = http
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(
                                        (request, response, ex) -> {
                                            response.setStatus(
                                                    HttpServletResponse.SC_UNAUTHORIZED
                                            );
                                        }
                                )
                                .accessDeniedHandler((request, response, ex) -> {
                                    response.setStatus(
                                            HttpServletResponse.SC_FORBIDDEN
                                    );
                                }));

        // Set permissions on endpoints
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/users/login").permitAll()
                .requestMatchers("users/registration").permitAll()
                .requestMatchers("users/verification/*").permitAll()
                .requestMatchers("users/verification").permitAll()
                .requestMatchers("/users/me").authenticated()
                .requestMatchers(HttpMethod.PATCH,"/users").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                .requestMatchers("/users").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/users/*/dt_update/*").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("users/*").authenticated()
                .anyRequest().authenticated()
        );

        // Add JWT token filter
        http.addFilterBefore(
                filter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}


//    private final IUserService userService;
//
//    private final PasswordEncoder encoder;
//
//    public SecurityConfig(IUserService userService, PasswordEncoder encoder) {
//        this.userService = userService;
//        this.encoder = encoder;
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> {
//            User user = userService.getCardByMail(username);
//            return UserDetailsDto.builder()
//                    .username(user.getMail())
//                    .password(user.getPassword())
//                    .roles(user.getRole().toString())
//                    .disabled(user.getStatus() != EUserStatus.ACTIVATED)
//                    .accountLocked(user.getStatus() != EUserStatus.ACTIVATED)
//                    .build();
//        };
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService());
//        authenticationProvider.setPasswordEncoder(encoder);
//        return authenticationProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {
//        return new ProviderManager(authenticationProvider());
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http,
//                                                   JwtFilter filter) throws Exception  {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
//                        .requestMatchers("/app/**").permitAll()
//                        .requestMatchers("/users/verification",
//                                "/users/registration",
//                                "/users/login").permitAll()
//                        .requestMatchers("/users/me").authenticated()
//                        .requestMatchers("/users", "/users/**").hasAnyRole("ADMIN")
//                        .anyRequest().authenticated())
//                .sessionManagement((session) -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationManager(authenticationManager())
//                .exceptionHandling((exceptionHandling) -> exceptionHandling
//                        .authenticationEntryPoint((request, response, authException) -> {
//                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                        })
//                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                        })
//                )
//                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter filter) throws Exception  {
//        // Enable CORS and disable CSRF
//        http = http.cors().and().csrf().disable();
//
//        // Set session management to stateless
//        http = http
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and();
//
//        // Set unauthorized requests exception handler
//        http = http
//                .exceptionHandling()
//                .authenticationEntryPoint(
//                        (request, response, ex) -> {
//                            response.setStatus(
//                                    HttpServletResponse.SC_UNAUTHORIZED
//                            );
//                        }
//                )
//                .accessDeniedHandler((request, response, ex) -> {
//                    response.setStatus(
//                            HttpServletResponse.SC_FORBIDDEN
//                    );
//                })
//                .and();
//
//        // Set permissions on endpoints
//        http.authorizeHttpRequests(requests -> requests
//                // Our public endpoints
//                .requestMatchers( "/users/registration/**").permitAll()
//                .requestMatchers( "/users/verification/**").permitAll()
//                .requestMatchers( "/users/login/**").permitAll()
//                .requestMatchers(HttpMethod.GET,"/users/me").authenticated()
//
//                .requestMatchers(HttpMethod.POST,"/users").hasAnyRole("ADMIN")
//                .requestMatchers(HttpMethod.GET,"/users").authenticated()
//                .requestMatchers("/users/dt_update").hasAnyRole("ADMIN") //Обрати внимание что тут нет префикса ROLE_
//
//                .requestMatchers("/users/dt_update").hasAnyRole("ADMIN") //Обрати внимание что тут нет префикса ROLE_
//                .requestMatchers("/audit").hasAnyRole("ADMIN")
//
//                .anyRequest().authenticated()
//        );
//
//        // Add JWT token filter
//        http.addFilterBefore(
//                filter,
//                UsernamePasswordAuthenticationFilter.class
//        );
//
//        return http.build();
//    }

//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource, PasswordEncoder encoder) {
////        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//
//        try{
//            UserDetails user = User.builder()
//                    .username("user")
//                    .password(encoder.encode("123"))
//                    .roles("USER")
//                    .build();
//            UserDetails admin = User.builder()
//                    .username("admin")
//                    .password(encoder.encode("321"))
//                    .roles("USER", "ADMIN")
//                    .build();
//
//            manager.createUser(user);
//            manager.createUser(admin);
//        }catch (DuplicateKeyException e){
//            //всё ок, уже есть
//        }
//
//        return manager;
//    }
