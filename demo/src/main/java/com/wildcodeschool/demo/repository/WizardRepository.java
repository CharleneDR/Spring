package com.wildcodeschool.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wildcodeschool.demo.entity.Wizard;

@Repository
public interface WizardRepository extends JpaRepository<Wizard, Long> {
}
