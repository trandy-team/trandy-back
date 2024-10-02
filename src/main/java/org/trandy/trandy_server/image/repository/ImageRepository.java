package org.trandy.trandy_server.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.trandy.trandy_server.image.domain.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
