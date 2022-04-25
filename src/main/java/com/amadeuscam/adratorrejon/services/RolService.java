package com.amadeuscam.adratorrejon.services;

import com.amadeuscam.adratorrejon.enums.RolName;
import com.amadeuscam.adratorrejon.models.Rol;
import com.amadeuscam.adratorrejon.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class RolService {
    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getRoleName(RolName rolName) {
        return rolRepository.findByRolName(rolName);
    }

}
