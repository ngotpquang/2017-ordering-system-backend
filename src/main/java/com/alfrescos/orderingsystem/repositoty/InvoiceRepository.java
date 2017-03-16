package com.alfrescos.orderingsystem.repositoty;

import com.alfrescos.orderingsystem.entity.Invoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Liger on 14-Mar-17.
 */
@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, String>{
    @Query(value = "SELECT * FROM invoice WHERE customer_user_id = ?1", nativeQuery = true)
    public List<Invoice> findAllInvoicesByCustomerId(Long customerId);

    @Query(value = "SELECT * FROM invoice WHERE staff_user_id = ?1", nativeQuery = true)
    public List<Invoice> findAllInvoicesByStaffId(Long customerId);
}
