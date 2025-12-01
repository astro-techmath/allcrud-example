-- Categories
INSERT INTO category_tb (id, name, description) VALUES 
('FICTION', 'Fiction', 'Fictional stories and novels'),
('SCIENCE', 'Science', 'Scientific and technical books'),
('HISTORY', 'History', 'Historical books and biographies'),
('TECHNOLOGY', 'Technology', 'Technology and programming books');

-- Authors
INSERT INTO author_tb (id, name, email, bio, active, aud_created_by, aud_created_date) VALUES 
(1, 'George Orwell', 'george@example.com', 'English novelist and essayist', true, 1, NOW()),
(2, 'Isaac Asimov', 'isaac@example.com', 'Science fiction author and professor', true, 1, NOW()),
(3, 'Martin Fowler', 'martin@example.com', 'Software developer and author', true, 1, NOW());

-- Books
INSERT INTO book_tb (id, title, isbn, price, pages, publication_date, description, author_id, category_id) VALUES
('b00c1111-1111-1111-1111-111111111111', '1984', '978-0-452-28423-4', 15.99, 328, '1949-06-08', 'Dystopian social science fiction novel', 1, 'FICTION'),
('b00c2222-2222-2222-2222-222222222222', 'Animal Farm', '978-0-452-28424-1', 12.99, 112, '1945-08-17', 'Allegorical novella', 1, 'FICTION'),
('b00c3333-3333-3333-3333-333333333333', 'Foundation', '978-0-553-29335-7', 18.99, 255, '1951-06-01', 'Science fiction novel', 2, 'SCIENCE'),
('b00c4444-4444-4444-4444-444444444444', 'Refactoring', '978-0-201-48567-7', 54.99, 448, '1999-07-08', 'Improving the design of existing code', 3, 'TECHNOLOGY');

-- Orders
INSERT INTO order_tb (id, customer_name, order_date) VALUES
('00de1111-1111-1111-1111-111111111111', 'Alice Wonderland', NOW()),
('00de2222-2222-2222-2222-222222222222', 'Bob Builder', DATEADD('DAY', -2, NOW()));

-- Order Items (Composite Key: order_id + book_id)
INSERT INTO order_item_tb (order_id, book_id, quantity, unit_price) VALUES
-- Alice's order (Bought 1984 and Foundation)
('00de1111-1111-1111-1111-111111111111', 'b00c1111-1111-1111-1111-111111111111', 1, 15.99),
('00de1111-1111-1111-1111-111111111111', 'b00c3333-3333-3333-3333-333333333333', 2, 18.99),

-- Bob's order (Bought Refactoring)
('00de2222-2222-2222-2222-222222222222', 'b00c4444-4444-4444-4444-444444444444', 1, 54.99);
