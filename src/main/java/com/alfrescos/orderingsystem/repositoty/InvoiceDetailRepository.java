package com.alfrescos.orderingsystem.repositoty;

import com.alfrescos.orderingsystem.entity.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Liger on 15-Mar-17.
 */
@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {

    @Query(value = "SELECT * FROM invoice_detail WHERE invoice_id = ?1", nativeQuery = true)
    public List<InvoiceDetail> findAllInvoiceDetailsByInvoiceId(String invoiceId);
}
