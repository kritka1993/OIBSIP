package com.library.digital_library_sysytem.repository;

import com.library.digital_library_sysytem.model.Transaction;
import com.library.digital_library_sysytem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserAndStatus(User user, String status);
    List<Transaction> findByStatus(String status);
}
