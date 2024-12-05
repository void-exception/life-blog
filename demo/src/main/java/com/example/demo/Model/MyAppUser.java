//package com.example.demo.Model;
//
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//@Entity
//public class MyAppUser {
//    @Id
//    @GeneratedValue(
//            strategy = GenerationType.AUTO
//    )
//    private Long id;
//    private String username;
//    private String email;
//    private String password;
//
//    public MyAppUser() {
//    }
//
//    public Long getId() {
//        return this.id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return this.username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getEmail() {
//        return this.email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return this.password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//}
//
package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
public class MyAppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;

    // Конструктор без аргументов
    public MyAppUser() {
    }

    // Геттеры и сеттеры
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Реализация методов интерфейса UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Вернуть роли или полномочия. Например, пустой список:
        return Collections.emptyList(); // Или заменить на реальные роли
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Например, вернуть `true`, если срок действия аккаунта не истёк
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Вернуть `true`, если аккаунт не заблокирован
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Вернуть `true`, если учётные данные не истекли
    }

    @Override
    public boolean isEnabled() {
        return true; // Вернуть `true`, если аккаунт включён
    }
}
