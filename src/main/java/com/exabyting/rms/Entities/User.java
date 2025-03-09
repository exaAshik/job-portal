package com.exabyting.rms.Entities;

import com.exabyting.rms.Entities.Helper.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "\"user\"")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class User extends BaseEntity{

    @Column(nullable = false)
    private String name;

    private String email;

    @Column(nullable = true)
    private String password;

    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role",nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<Role> roles=new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Profile profile;


}
