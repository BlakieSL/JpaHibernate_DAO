delete from library_book;
delete from library_info;
delete from book;
delete from library;
delete from author;

alter table author AUTO_INCREMENT = 1;
alter table book AUTO_INCREMENT = 1;
alter table library AUTO_INCREMENT = 1;
alter table library_info AUTO_INCREMENT = 1;