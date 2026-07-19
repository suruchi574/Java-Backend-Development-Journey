/*
`data.sql` is used to insert initial data into the database.

After Spring executes `schema.sql`, it automatically executes `data.sql`.

This we have declared for h2 database

*/
insert into Student(name,rollNo,marks) values ('Rahul', 58, 89);
insert into Student(name,rollNo,marks) values ('Shubham', 59, 95);
insert into Student(name,rollNo,marks) values ('Sumran', 60, 87);
