package com.company.repository;

import com.company.entity.AttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.beans.Transient;
import java.util.Optional;

public interface AttachRepository extends JpaRepository<AttachEntity,String> {




}
