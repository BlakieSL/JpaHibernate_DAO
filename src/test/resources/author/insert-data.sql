INSERT INTO author (id, first_name, last_name)
VALUES
    (1, 'John', 'Doe'),
    (2, 'Jane', 'Smith'),
    (3, 'Emily', 'Johnson');

INSERT INTO book (id, author_id, title, release_date)
VALUES
    (1, 1, 'Book One by John', '2023-01-15'),
    (2, 1, 'Book Two by John', '2023-03-10'),
    (3, 2, 'Jane\'s Journey', '2022-05-22'),
    (4, 3, 'Emily\'s Adventures', '2021-12-05');

INSERT INTO library (id, name)
VALUES
   (1, 'Central Library'),
   (2, 'Community Library');

INSERT INTO library_info (id, address, phone)
VALUES
    (1, '123 Main St, Springfield', '555-1234'),
    (2, '456 Elm St, Springfield', '555-5678');


INSERT INTO library_book (library_id, book_id)
VALUES
   (1, 1),
   (1, 2),
   (2, 2),
   (2, 3);