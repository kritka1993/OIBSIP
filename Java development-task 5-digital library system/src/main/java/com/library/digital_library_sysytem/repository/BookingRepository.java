package com.library.digital_library_sysytem.repository;

import com.library.digital_library_sysytem.model.Book;
import com.library.digital_library_sysytem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookAndStatus(Book book, String status);
}
