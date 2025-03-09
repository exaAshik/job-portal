package com.exabyting.rms.Entities;

import com.exabyting.rms.Entities.Helper.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "\"profile\"")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Profile extends BaseEntity{


    private String firstName;
    private String lastName;
    private String contactNumber;
    private Address address;
    @Column(name = "profile_image_url")
    private String profileImgUrl;
    @OneToOne(mappedBy = "profile")
    @Cascade(CascadeType.ALL)
    private User user;

}
