ALTER TABLE book DROP FOREIGN KEY book_ibfk_1;
ALTER TABLE library_info DROP FOREIGN KEY fk_library;
ALTER TABLE library_book DROP FOREIGN KEY fk_library_book_book;
ALTER TABLE library_book DROP FOREIGN KEY fk_library_book_library;

DROP INDEX author_id ON book;

DROP TABLE IF EXISTS library_info;
DROP TABLE IF EXISTS library_book;
DROP TABLE IF EXISTS library;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;
