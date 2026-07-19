/*
`schema.sql` is used to create the database structure.

It contains SQL statements like

- CREATE TABLE
- ALTER TABLE
- DROP TABLE

Spring Boot automatically executes this file when the application starts.
This we have declared for h2 database

*/
create table Student
(
	name varchar(20),
	rollNo int primary key,
	marks int
);