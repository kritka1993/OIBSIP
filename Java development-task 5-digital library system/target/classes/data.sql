-- 1. Populate Seed Administrative & Member Accounts

INSERT IGNORE INTO users (username, password, email, role)
VALUES ('admin', '$2a$10$RzVb7/c1p27a1AECb7Hze.9tTspzWdJ0lWwDk5.k3bX3PkWmF7n3.', 'admin@library.com', 'ROLE_ADMIN');

INSERT IGNORE INTO users (username, password, email, role)
VALUES ('member', '$2a$10$e0M2lH.3QG7v5vFjR3m8Oux3d2d2c1WwDk5.k3bX3PkWmF7n3.', 'member@library.com', 'ROLE_USER');


-- 2. Seed Diverse Library Asset Catalogue Inventory Across Major Categories

-- TECHNOLOGY & SOFTWARE DEVELOPMENT
INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('Clean Code', 'Robert C. Martin', '978-0132350884', 'Technology & Software', 5, 5);

INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('Effective Java', 'Joshua Bloch', '978-0134685991', 'Technology & Software', 6, 6);

-- FICTION & LITERATURE
INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('To Kill a Mockingbird', 'Harper Lee', '978-0446310789', 'Fiction & Literature', 4, 4);

INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('1984', 'George Orwell', '978-0451524935', 'Fiction & Literature', 3, 3);

-- BUSINESS & FINANCE
INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('The Intelligent Investor', 'Benjamin Graham', '978-0060555665', 'Business & Finance', 3, 3);

INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('Atomic Habits', 'James Clear', '978-0735211292', 'Business & Finance', 8, 8);

-- SCIENCE & MATHEMATICS
INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('A Brief History of Time', 'Stephen Hawking', '978-0553380163', 'Science & Maths', 4, 4);

INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('Cosmos', 'Carl Sagan', '978-0345331359', 'Science & Maths', 2, 2);

-- HISTORY & BIOGRAPHY
INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', '978-0062316097', 'History & Biography', 5, 5);

INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('Steve Jobs', 'Walter Isaacson', '978-1451648539', 'History & Biography', 3, 3);

-- MYSTERY & THRILLER
INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('The Da Vinci Code', 'Dan Brown', '978-0307474278', 'Mystery & Thriller', 4, 4);

INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('Gone Girl', 'Gillian Flynn', '978-0307588371', 'Mystery & Thriller', 3, 3);

-- SELF-HELP & PSYCHOLOGY
INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('Thinking, Fast and Slow', 'Daniel Kahneman', '978-0374275631', 'Self-Help & Psychology', 5, 5);
INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('The Alchemist', 'Paulo Coelho', '978-0062315007', 'Fiction / Allegory', 5, 5);

INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('Interpreter of Maladies', 'Jhumpa Lahiri', '978-0786264346', 'Fiction / Short Stories', 3, 3);

INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('A Suitable Boy', 'Vikram Seth', '978-0060925000', 'Fiction / Drama', 4, 4);

INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('Pride and Prejudice', 'Jane Austen', '978-0140434262', 'Classic Literature', 6, 6);

INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('The Kite Runner', 'Khaled Hosseini', '978-1594631931', 'Historical Fiction', 5, 5);

INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('Gitanjali', 'Rabindranath Tagore', '978-0684839349', 'Poetry', 4, 4);

INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('A Chinaman''s Chance', 'Eric Liu', '978-1610391948', 'Social Science / Biography', 2, 2);

INSERT INTO books (title, author, isbn, category, total_quantity, available_quantity)
VALUES ('Belonging: A Novel', 'Nancy Thayer', '978-0345432360', 'Contemporary Romance', 3, 3);


