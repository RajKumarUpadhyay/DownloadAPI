package com.download.Repositiory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.download.model.DownloadModel;

@Repository
public interface DownloadRepository extends JpaRepository<DownloadModel, Long> {

}
