package com.library.digital_library_sysytem.controller;

import com.library.digital_library_sysytem.model.*;
import com.library.digital_library_sysytem.repository.TransactionRepository;
import com.library.digital_library_sysytem.repository.UserRepository;
import com.library.digital_library_sysytem.service.LibraryService;
import com.library.digital_library_sysytem.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppController {

    @Autowired private LibraryService libraryService;
    @Autowired private CustomUserDetailsService userDetailsService;
    @Autowired private UserRepository userRepository;
    @Autowired private TransactionRepository transactionRepository;

    @GetMapping("/login") public String login()
    { return "login"; }

    @GetMapping("/register") public String registerPage(Model model)
    {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user)
    {
        userDetailsService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model)
    {
        User user = userRepository
                .findByUsername(userDetails
                        .getUsername())
                .orElse(null);
        if (user != null && user.getRole().equals("ROLE_ADMIN"))
            return "redirect:/admin/dashboard";

        model.addAttribute("books", libraryService.getAllBooks());
        model.addAttribute("myIssues", transactionRepository.findByUserAndStatus(user, "ISSUED"));

        return "user-dashboard";
    }

    // User Operations
    @GetMapping("/book/issue/{id}")
    public String issueBook(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id)
    {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElse(null);

        libraryService.issueBook(user, id);
        return "redirect:/dashboard";
    }

    @GetMapping("/book/return/{txId}")
    public String returnBook(@PathVariable Long txId)
    {
        libraryService.returnBook(txId);
        return "redirect:/dashboard";
    }

    @GetMapping("/book/reserve/{id}")
    public String reserveBook(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id)
    {
        User user = userRepository
                .findByUsername(userDetails
                        .getUsername())
                .orElse(null);
        libraryService.reserveBook(user, id);
        return "redirect:/dashboard";
    }

    @PostMapping("/contact")
    public String submitQuery(@ModelAttribute QueryMessage msg)
    {
        libraryService.saveQuery(msg);
        return "redirect:/dashboard?messageSent";
    }

    // Admin Operations
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model)
    {
        model.addAttribute("books", libraryService.getAllBooks());
        model.addAttribute("newBook", new Book());
        model.addAttribute("issuedBooks", libraryService.getAllIssuedBooks());
        model.addAttribute("members", userRepository.findAll());
        model.addAttribute("queries", libraryService.getAllQueries());

        return "admin-dashboard";
    }

    @PostMapping("/admin/book/add")
    public String addBook(@ModelAttribute Book book)
    {
        libraryService.saveBook(book);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/book/delete/{id}")
    public String deleteBook(@PathVariable Long id)
    {
        libraryService.deleteBook(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/fine/pay/{txId}")
    public String clearFine(@PathVariable Long txId)
    {
        libraryService.payFine(txId);
        return "redirect:/admin/dashboard";
    }
}

