package com.test.corretto.repository;

import com.test.corretto.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment,Long> {

}
