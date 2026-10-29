package com.library.digital_library_sysytem.service;

import com.library.digital_library_sysytem.model.*;
import com.library.digital_library_sysytem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LibraryService {

    @Autowired private BookRepository bookRepository;
    @Autowired private TransactionRepository transactionRepository;
    @Autowired private BookingRepository bookingRepository;
    @Autowired private QueryMessageRepository queryRepository;

    // Book Management
    public Book saveBook(Book book)
    {
        if(book.getId() == null)
            book.setAvailableQuantity(book.getTotalQuantity());
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks()
    { return bookRepository.findAll(); }

    public Book getBookById(Long id)
    { return bookRepository
            .findById(id)
            .orElse(null);
    }

    public void deleteBook(Long id)
    { bookRepository.deleteById(id); }

    public List<Book> searchBooks(String keyword)
    { return bookRepository
            .findByTitleContainingOrAuthorContaining(keyword, keyword);
    }

    public List<Book> getBooksByCategory(String cat)
    { return bookRepository.findByCategory(cat); }

    // Issue & Return Operations
    public String issueBook(User user, Long bookId)
    {
        Book book = getBookById(bookId);
        if (book == null || book.getAvailableQuantity() <= 0)
        {
            return "Book unavailable for direct issue. Try Advance Booking.";
        }

        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);

        Transaction tx = new Transaction();
        tx.setUser(user);
        tx.setBook(book);
        tx.setIssueDate(LocalDate.now());
        tx.setDueDate(LocalDate.now().plusDays(14)); // 14-day standard validation
        tx.setStatus("ISSUED");
        transactionRepository.save(tx);
        return "Book issued successfully!";
    }

    public void returnBook(Long transactionId)
    {
        Transaction tx = transactionRepository
                .findById(transactionId)
                .orElse(null);
        if (tx != null && tx.getStatus().equals("ISSUED")) {
            Book book = tx.getBook();
            book.setAvailableQuantity(book.getAvailableQuantity() + 1);
            bookRepository.save(book);

            tx.setReturnDate(LocalDate.now());
            tx.setStatus("RETURNED");

            // Automatic Fine Generation (₹5 per day overdue)
            if (tx.getReturnDate().isAfter(tx.getDueDate())) {
                long daysOverdue = ChronoUnit.DAYS.between(tx.getDueDate(), tx.getReturnDate());
                tx.setFineAmount(daysOverdue * 5.0);
            }
            transactionRepository.save(tx);
        }
    }

    public void payFine(Long transactionId) {
        Transaction tx = transactionRepository.findById(transactionId).orElse(null);
        if (tx != null) {
            tx.setFinePaid(true);
            tx.setFineAmount(0.0);
            transactionRepository.save(tx);
        }
    }

    // Advance Booking
    public String reserveBook(User user, Long bookId)
    {
        Book book = getBookById(bookId);
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBook(book);
        booking.setStatus("PENDING");
        bookingRepository.save(booking);
        return "Book reserved successfully!";
    }

    //Contact messages
    public void saveQuery(QueryMessage msg)
    { queryRepository.save(msg); }

    public List<QueryMessage> getAllQueries()
    { return queryRepository.findAll(); }

    public List<Transaction> getAllIssuedBooks()
    { return transactionRepository.findByStatus("ISSUED"); }
}

