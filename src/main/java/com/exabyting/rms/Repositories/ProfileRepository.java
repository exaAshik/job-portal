package com.exabyting.rms.Repositories;

import com.exabyting.rms.Entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile,Integer> {
}
