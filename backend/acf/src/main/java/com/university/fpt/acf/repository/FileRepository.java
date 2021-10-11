package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File,String> {
}
