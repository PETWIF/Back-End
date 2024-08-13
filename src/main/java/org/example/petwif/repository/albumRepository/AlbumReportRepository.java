package org.example.petwif.repository.albumRepository;

import org.example.petwif.domain.entity.AlbumReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumReportRepository extends JpaRepository<AlbumReport, Long> {
}
